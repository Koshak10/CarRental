package me.abdulkarim.carrental.client;

public interface ClientBuilder {

    ClientBuilder setId(int id);

    ClientBuilder setName(String name);

    ClientBuilder setPhoneNumber(String phoneNumber);

    Client build();

}
