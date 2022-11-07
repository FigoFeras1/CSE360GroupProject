package com.sdpizza.groupproject.entity.item;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;

public class Pizza extends Item {
    public enum Base {
        CHEESE,
        PEPPERONI,
        VEGAN,
        SAUCELESS
    }
    public enum Topping {
        EXTRA_CHEESE,
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

    /* TODO: Deal with this if we decide to add more items in the future */
    @JsonIgnore
    private static final ItemType type = ItemType.PIZZA;
    private Size size;
    private Base base;
    private Topping[] toppings;

    public Pizza() {}
    public Pizza(Size size, Base base, Topping... toppings) {
        setSize(size);
        setBase(base);
        setToppings(toppings);
    }

    @Override
    public String toString() {
        return "size: " + size
                + ", base: " + base
                + ", toppings: " + Arrays.toString(toppings);
    }

/*
    private void setType(ItemType type) {
        Pizza.type = type;
    }
*/

/*
    public ItemType getType() {
        return type;
    }
*/

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Topping[] getToppings() {
        return toppings;
    }

    public void setToppings(Topping... toppings) {
        this.toppings = toppings;
    }
}
