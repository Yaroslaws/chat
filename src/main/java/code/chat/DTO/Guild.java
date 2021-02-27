package code.chat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Guild {

    private final List<User> users;


    public Guild(@JsonProperty("guild") List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guild guild = (Guild) o;
        return Objects.equals(users, guild.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }
}
