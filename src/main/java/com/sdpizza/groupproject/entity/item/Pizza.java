package com.sdpizza.groupproject.entity.item;


public class Pizza extends Item {
    public enum Base {
        CHEESE,
        PEPPERONI,
        VEGAN,
        SAUCELESS
    }
    public enum Topping {
        EXCHEESE,
        MUSHROOMS,
        OLIVES,
        ONIONS,
        PINEAPPLE
    }
    public enum Size {
        SMALL,
        MEDIUM,
        LARGE,
        XLARGE
    }
    private final ItemType type = ItemType.PIZZA;
}
