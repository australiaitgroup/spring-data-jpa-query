package com.jiangren.springdatajpa.query.springdatajpaquery;

public enum Country {

    US("US"), KOREA("korea");

    private String name;

    private Country(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
