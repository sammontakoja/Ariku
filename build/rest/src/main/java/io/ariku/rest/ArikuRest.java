package io.ariku.rest;

/**
 * @author Ari Aaltonen
 */

import com.google.gson.Gson;
import io.ariku.composer.Composer;

import static spark.Spark.get;
import static spark.Spark.port;

public class ArikuRest {

    public static final Composer composer = new Composer();

    public static void main(String[] args) {
        port(5000);
        Gson gson = new Gson();
        get("/hello", (request, response) -> "Moikka", gson::toJson);
    }

}

