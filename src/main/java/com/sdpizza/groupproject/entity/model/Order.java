package com.sdpizza.groupproject.entity.model;


public class Order extends Model {
    public enum Type {
        PENDING("pending"),
        PROCESSED("processed"),
        SAVED("saved");

        private final String name;

        Type(String name) {
            this.name = name;
        }
    }
    private long id;

    @Override
    public long getID() {
        return -1;
    }
}
