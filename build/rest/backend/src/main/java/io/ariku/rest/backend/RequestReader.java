package io.ariku.rest.backend;

import spark.Request;

/**
 * @author Ari Aaltonen
 */
public class RequestReader {
    public String username(Request request) {
        return request.queryParams("username");
    }
    public String securityToken(Request request) {
        return request.queryParams("security_token");
    }
}
