package com.sdpizza.groupproject.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sdpizza.groupproject.entity.Entity;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Pizza.class, name = "PIZZA"),
})
public abstract class Item extends Entity {
    public enum ItemType {
        PIZZA,
    }

    private final ItemType type = null;
    private int quantity = 1;

    public Item() {}

    @JsonIgnore
    public ItemType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
