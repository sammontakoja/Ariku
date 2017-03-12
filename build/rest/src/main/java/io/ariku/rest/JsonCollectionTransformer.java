package io.ariku.rest;

import org.json.JSONArray;
import spark.ResponseTransformer;

import java.util.Collection;

/**
 * @author Ari Aaltonen
 */
public class JsonCollectionTransformer implements ResponseTransformer {

    @Override
    public String render(Object o) throws Exception {
        Collection list = (Collection) o;
        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();
    }
}
