package com.sdpizza.groupproject.entity.model;


import com.sdpizza.groupproject.entity.item.Item;

import java.util.List;

public class Order extends Model {
    public enum Type {
        PENDING,
        PROCESSED,
        SAVED;
    }
    private long id;
    private List<? extends Item> items;

    @Override
    public long getID() {
        return id;
    }
}
