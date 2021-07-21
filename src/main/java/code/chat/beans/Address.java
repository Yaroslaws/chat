package code.chat.beans;

public class Address {
    private String street;
    private int numberOfHouse;

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", numberOfHouse=" + numberOfHouse +
                '}';
    }

    public Address(String street, int numberOfHouse) {
        this.street = street;
        this.numberOfHouse = numberOfHouse;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumberOfHouse() {
        return numberOfHouse;
    }

    public void setNumberOfHouse(int numberOfHouse) {
        this.numberOfHouse = numberOfHouse;
    }
}
