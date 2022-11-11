package com.sdpizza.groupproject.entity.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sdpizza.groupproject.database.serializer.OrderDeserializer;
import com.sdpizza.groupproject.database.serializer.OrderSerializer;
import com.sdpizza.groupproject.entity.item.Item;

import java.util.List;

@JsonSerialize(using = OrderSerializer.class)
@JsonDeserialize(using = OrderDeserializer.class)
public class Order extends Model {


    /* Pending is order placed, but not being made,
           Processed is order being made by chef
           Saved is completed order */
    public enum Status {
        PENDING,
        PROCESSED,
        SAVED
    }
    /* This should not be set by anything except the OrderRepository, since the
       table in the database auto-increments the id. */
    @JsonIgnore
    private long id = -1;

    @JsonProperty
    private List<Item> items;

    @JsonIgnore
    private User user;

    @JsonIgnore
    private Status status;

    public Order() {}
    public Order(List<Item> items, User user, Status status) {
        setItems(items);
        setUser(user);
        setStatus(status);
    }

    public void add(Item item) {
        items.add(item);
    }
    @Override
    public long getID() {
        return id;
    }
    public void setID(long id) { this.id = id; }

    @JsonIgnore
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) { this.status = status; }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public List<Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) { this.user = user; }
}
