package com.example.luyentap.Models;

public class Ex {
    private int id;
    private String name;
    private int item;
    private int score;

    public Ex() {}

    public Ex(int id, String name, int item, int score) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
