package code.chat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {
    private final String name;
    private String lol;

    public String getLol() {
        return lol;
    }


    public User(@JsonProperty("name") String name) {
        this.name = name;
    }

    public void setLol(String lol) {
        this.lol = lol;
    }

    public String getName() {
        return name;
    }

}
