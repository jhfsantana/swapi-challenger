package com.desafio.bw2.swapi.model;


import java.io.Serializable;

public class Film implements Serializable {

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
