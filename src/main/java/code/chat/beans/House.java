package code.chat.beans;

import java.util.Set;

public class House {
    private int countFLor;
    private int countFlats;
    private Address address;

    @Override
    public String toString() {
        return "House{" +
                "countFLor=" + countFLor +
                ", countFlats=" + countFlats +
                ", address=" + address +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getCountFLor() {
        return countFLor;
    }

    public void setCountFLor(int countFLor) {
        this.countFLor = countFLor;
    }

    public int getCountFlats() {
        return countFlats;
    }

    public void setCountFlats(int countFlats) {
        this.countFlats = countFlats;
    }
}
