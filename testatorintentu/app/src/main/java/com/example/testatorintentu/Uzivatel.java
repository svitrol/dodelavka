package com.example.testatorintentu;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Uzivatel implements Serializable {
    public Uzivatel() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "jmeno")
    private String jmeno;
    @ColumnInfo(name = "prijmeni")
    private String prijmeni;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "heslo")
    private String heslo;
    @Ignore
    public Uzivatel( String jmeno, String prijmeni, String email, String heslo) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.email = email;
        this.heslo = heslo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }
}
