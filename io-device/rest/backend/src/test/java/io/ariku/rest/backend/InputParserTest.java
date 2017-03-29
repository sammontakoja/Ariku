package io.ariku.rest.backend;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author sammontakoja
 */
public class InputParserTest {

    @Test
    public void parsing_succeed_when_both_parameters_and_values_are_valid() throws Exception {
        String host = "localhost";
        int port = 6000;
        String[] inputs = {"-h", host, "-p", new Integer(port).toString()};
        assertTrue(new InputParser().areInputUsable(inputs));
    }

    @Test
    public void when_parsing_succeed_then_same_values_returned_back() throws Exception {
        String host = "localhost";
        int port = 6000;
        String[] inputs = {"-h", host, "-p", new Integer(port).toString()};
        InputParser parser = new InputParser();
        parser.areInputUsable(inputs);
        assertThat(parser.getHost(), is(host));
        assertThat(parser.getPort(), is(port));
    }

    @Test
    public void parsing_fails_when_host_is_invalid() throws Exception {
        String host = "localhost%"; // according to RFC-2396 % char is not valid url char
        int port = 6000;
        String[] inputs = {"-h", host, "-p", new Integer(port).toString()};
        assertFalse(new InputParser().areInputUsable(inputs));
    }

    @Test
    public void parsing_fails_when_port_is_invalid() throws Exception {
        String host = "localhost";
        int port = -2000;
        String[] inputs = {"-h", host, "-p", new Integer(port).toString()};
        assertFalse(new InputParser().areInputUsable(inputs));
    }


    @Test
    public void parsing_fails_when_port_parameter_invalid() throws Exception {
        String host = "localhost";
        int port = 2000;
        String[] inputs = {"-h", host, "-pInvalid", new Integer(port).toString()};
        assertFalse(new InputParser().areInputUsable(inputs));
    }

    @Test
    public void parsing_fails_when_host_parameter_invalid() throws Exception {
        String host = "localhost";
        int port = 2000;
        String[] inputs = {"-hInvalid", host, "-p", new Integer(port).toString()};
        assertFalse(new InputParser().areInputUsable(inputs));
    }

}