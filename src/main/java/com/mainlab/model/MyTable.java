package com.mainlab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyTable {
    private int id;
    private String name;

    public MyTable() {

    }
    public MyTable(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
