package me.abdulkarim.carrental.client;

public class ConcreteClientBuilder implements ClientBuilder {

    private int id;
    private String name;
    private String phoneNumber;

    @Override
    public ClientBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ClientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ClientBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public Client build() {
        return new Client(id, name, phoneNumber);
    }
}
