package io.ariku.rest.backend;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import io.ariku.util.data.RestSettings;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Ari Aaltonen
 */
public class Util {

    static RestSettings restSettings = new RestSettings();

    public static void startServerAndLetClientKnowAboutTCPPort() {
        int freePort = freePort();
        ArikuRest.start("localhost", freePort);
        restSettings.port = new Integer(freePort).toString();
    }

    private static int freePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
