package dto;

import java.util.Date;
import java.util.Objects;

public class Person implements Kiii, Liii {

    public Date birthDate;
    public String name;

    public Person(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return new Date(birthDate.getTime());
    }

    public String getName() {
        return name;
    }

    @Override
    public void get() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(birthDate, person.birthDate) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, name);
    }
}
