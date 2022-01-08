package com.company;

class Coffee {
    public int cost;
    public int water;
    public int coffee;
    public int milk = 0;

    public Coffee(int cost, int water, int coffee, int milk) {
        this.cost = cost;
        this.water = water;
        this.coffee = coffee;
        this.milk = milk;
    }

    public Coffee(int cost, int water, int coffee) {
        this.cost = cost;
        this.water = water;
        this.coffee = coffee;
    }
}
