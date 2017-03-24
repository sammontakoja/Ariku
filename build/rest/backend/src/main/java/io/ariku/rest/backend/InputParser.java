package io.ariku.rest.backend;

import static org.kohsuke.args4j.ExampleMode.ALL;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;


/**
 * @author Ari Aaltonen (sammontakoja)
 */
public class InputParser {

    @Option(name = "-h", required = true, usage = "Host dns name or ip-address")
    private String host = "";

    @Option(name = "-p", required = true, usage = "Host port")
    private int port = 5000;

    @Option(name = "-h", help = true)
    private boolean help = false;

    public void parseContents(String[] args) throws IOException {

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java InputParser [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}