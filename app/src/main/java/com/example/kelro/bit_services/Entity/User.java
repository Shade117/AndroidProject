package com.example.kelro.bit_services.Entity;

public class User {
    private int id;
    private String name;
    private String type;

    public User(int id, String name, String type) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
