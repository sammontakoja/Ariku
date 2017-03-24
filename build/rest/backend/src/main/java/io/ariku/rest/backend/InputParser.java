package io.ariku.rest.backend;

import static org.kohsuke.args4j.ExampleMode.ALL;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * @author Ari Aaltonen (sammontakoja)
 */
public class InputParser {

    @Option(name = "-h", required = true, usage = "Host dns name or ip-address")
    private String host = null;

    @Option(name = "-p", required = true, usage = "Host port")
    private int port = 5000;

    @Option(name = "--help", help = true)
    private boolean help = false;

    public boolean parseContents(String[] args) {

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

            return valuesAreValid();

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return false;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    private boolean valuesAreValid() {
        try {
            new URL("http://"+host+":"+port).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}