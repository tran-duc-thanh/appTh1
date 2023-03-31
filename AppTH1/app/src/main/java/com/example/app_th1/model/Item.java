package com.example.app_th1.model;

import java.util.List;

public class Item {

    public static int count = 0;

    private int id;
    private int img;
    private String month;
    private String name;
    private String date;
    private String description;
    private String gender;
    private List<String> languages;

    public Item() {
    }

    public Item(int id, int img, String month, String name, String date, String description, String gender, List<String> languages) {
        this.id = id;
        this.img = img;
        this.month = month;
        this.name = name;
        this.date = date;
        this.description = description;
        this.gender = gender;
        this.languages = languages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}
