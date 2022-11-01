package com.sdpizza.groupproject.entity.item;

import com.sdpizza.groupproject.entity.Entity;

public abstract class Item extends Entity {
    public enum ItemType {
        PIZZA,
    }

    private final ItemType type = null;

    public Item() {}
}
