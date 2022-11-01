package com.sdpizza.groupproject.entity.item;


public class Pizza extends Item {
    public enum Base {
        VEGETABLE,
    }
    public enum Topping {
        PEPPERONI,
    }
    private final ItemType type = ItemType.PIZZA;
}
