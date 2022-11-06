package com.sdpizza.groupproject.entity.model;


public class User extends Model {
    public enum Role {
        CUSTOMER,
        CUSTOMER_SERVICE,
        CHEF,
    }
    private long id;
    private String first_name;
    private String last_name;
    private String password;
    private Role role;

    public User(long id, String first_name, String last_name, String password,
                Role role) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.role = role;

    }
    @Override
    public long getID() {
        return -1;
    }


    @Override
    public String toString() {
        return "{id: " + id +
                ", first_name: " + first_name +
                ", last_name: " + last_name +
                ", password: " + password +
                ", role: " + role +
                "}";
    }
}
