package io.ariku.rest.backend;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * @author Ari Aaltonen (sammontakoja)
 */
public class InputParser {
    public static Logger logger = LoggerFactory.getLogger(InputParser.class);

    @Option(name = "-h", required = true, usage = "Host dns name or ip-address")
    private String host = null;

    @Option(name = "-p", required = true, usage = "Host port")
    private int port = 5000;

    @Option(name = "--help", help = true)
    private boolean help = false;

    public boolean areInputUsable(String[] args) {

        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            return valuesAreValid();

        } catch (CmdLineException e) {
            logger.debug(e.getMessage());
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