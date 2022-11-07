package com.sdpizza.groupproject.entity.model;


import com.sdpizza.groupproject.entity.item.Item;

import java.util.List;

public class Order extends Model {

    /* Pending is order placed, but not being made,
       Processed is order being made by chef
       Saved is completed order */
    public enum Status {
        PENDING,
        PROCESSED,
        SAVED;
    }
    /* This should not be set by anything except the OrderRepository, since the
       table in the database auto-increments the id. */
    private long id;
    private List<? extends Item> items;
    private User user;
    private Status status;

    public Order(List <? extends Item> items, User user, Status status) {
        this.items = items;
        this.user = user;
        this.status = status;
    }
    @Override
    public long getID() {
        return id;
    }

    public Object getType() {
        return status;
    }

    public List<? extends Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }
}
