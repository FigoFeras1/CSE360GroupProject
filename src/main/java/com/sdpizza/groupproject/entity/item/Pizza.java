package com.sdpizza.groupproject.entity.item;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sdpizza.groupproject.database.serializer.ItemSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@JsonSerialize(using = ItemSerializer.class)
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

    @JsonIgnore
    private static final ItemType type = ItemType.PIZZA;
    private Size size;
    private Base base;
    private List<Topping> toppings;

    public Pizza() {}

    public Pizza(Size size, Base base, Topping... toppings) {
        setSize(size);
        setBase(base);
        this.toppings = List.of(toppings);
    }
    public Pizza(int quantity, Size size, Base base, Topping... toppings) {
        setQuantity(quantity);
        setSize(size);
        setBase(base);
        this.toppings = List.of(toppings);
    }

    @Override
    public String toString() {
        return "size: " + size
                + ", base: " + base
                + ", toppings: " + toppings
                + ", quantity: " + getQuantity();
    }

    public void setType(ItemType type) {}

    public ItemType getType() {
        return type;
    }

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

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(Topping... toppings) {
        this.toppings = List.of(toppings);
    }

    @Override
    public Map<String, Object> getJsonFields() {
        Map<String, Object> fields = new HashMap<>();

        fields.put("size", size);
        fields.put("base", base);
        fields.put("toppings", toppings);

        return fields;
    }
}
