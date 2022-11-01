package com.sdpizza.groupproject.model;

public class User extends Model {
    public enum ROLE {
        CUSTOMER,
        CUSTOMER_SERVICE,
        CHEF,
    }
    private long id;
    private String first_name;
    private String last_name;
    private String password;
    private ROLE role;
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
