package io.ariku.owner.api;

/**
 * @author Ari Aaltonen
 */
public class Competition {
    public String id;
    public String name;
    public String type;
    public Competition(String id) {
        this.id = id;
    }

    public Competition() {
    }
}
