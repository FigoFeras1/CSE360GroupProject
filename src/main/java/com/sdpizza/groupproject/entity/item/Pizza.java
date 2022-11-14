package com.sdpizza.groupproject.entity.item;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return (size == pizza.size
                && base == pizza.base
                && Objects.equals(toppings, pizza.toppings));
    }

    public Pizza(int quantity, Size size, Base base, Topping... toppings) {
        setQuantity(quantity);
        setSize(size);
        setBase(base);
        this.toppings = List.of(toppings);
    }

    @Override
    public String toString() {
        if (toppings.isEmpty()) {
            return "" + getQuantity() + " " + titleCase(size.toString())
                    + " " + titleCase(base.toString())
                    + (getQuantity() > 1 ? " Pizzas" : " Pizza") ;
        } else {
            return "" + getQuantity() + " " + titleCase(size.toString())
                    + " " + titleCase(base.toString())
                    + (getQuantity() > 1 ? " Pizzas" : " Pizza") + " with "
                    + (toppings.stream()
                        .map(Topping::toString)
                        .map(Pizza::titleCase)
                        .map(s -> s.replace("_", " "))
                        .collect(Collectors.toList())
                        .toString()
                        .replaceAll("[\\[\\]]", ""));
        }
    }

    private static String titleCase(String str) {
        return (str.charAt(0) + str.substring(1).toLowerCase());
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

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }
}
