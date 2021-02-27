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
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return Objects.equals(name, user.name);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }
}
