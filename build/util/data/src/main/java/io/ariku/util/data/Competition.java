package io.ariku.util.data;

/**
 * @author Ari Aaltonen
 */
public class Competition {

    public String name;
    public String type;
    public String id;

    public Competition(String id) {
        this.id = id;
    }

    public Competition() {
    }

    @Override
    public String toString() {
        return "Competition{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
