package io.ariku.rest;

/**
 * @author Ari Aaltonen
 */

import io.ariku.composer.Composer;

import static spark.Spark.get;
import static spark.Spark.port;

public class ArikuRest {

    public static final Composer composer = new Composer();

    public static void main(String[] args) {
        port(5000);
        get("/hello", (req, res) -> "Moikka");
    }

}

