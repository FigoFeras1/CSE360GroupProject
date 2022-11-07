package com.sdpizza.groupproject.entity.model;


import com.sdpizza.groupproject.database.annotations.Column;

public class User extends Model {
    public User() {

    }

    public enum Role {
        CUSTOMER,
        CUSTOMER_SERVICE,
        CHEF,
    }

    @Column("id")
    private long id;
    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("password")
    private String password;

    @Column("role")
    private Role role;

    public User(long id, String firstName, String lastName, String password,
                Role role) {
        setID(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setRole(role);
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

    private void setID(long id) { this.id = id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) { this.role = role; }
}
