package ariku.util;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
