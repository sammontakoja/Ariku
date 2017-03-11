package io.ariku.rest;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * @author Ari Aaltonen
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}
