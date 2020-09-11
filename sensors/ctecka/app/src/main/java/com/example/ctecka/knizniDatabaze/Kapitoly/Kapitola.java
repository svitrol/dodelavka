package com.example.ctecka.knizniDatabaze.Kapitoly;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Kapitola implements Serializable {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "Obsah")
    public String Obsah;
}
