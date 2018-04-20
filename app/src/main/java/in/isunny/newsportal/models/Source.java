package in.isunny.newsportal.models;

import java.io.Serializable;

/**
 * Created by Sunny on 4/17/2018.
 */

public class Source implements Serializable {

    private String id, name, author;

    Source() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
