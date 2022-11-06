package com.sdpizza.groupproject.entity.model;


public class User extends Model {
    public User() {

    }

    public enum Role {
        CUSTOMER,
        CUSTOMER_SERVICE,
        CHEF,
    }
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;

    public User(long id, String firstName, String lastName, String password,
                Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;

    }

    public Object[] toArray() {
        return new Object[]{ id, firstName, lastName, password, role.toString() };
    }


    @Override
    public String toString() {
        return "{id: " + id +
                ", first_name: " + firstName +
                ", last_name: " + lastName +
                ", password: " + password +
                ", role: " + role +
                "}";
    }

    @Override
    public long getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
