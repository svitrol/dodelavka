package com.example.rozjedprihlasovocku;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.Serializable;
@Entity
public class Uzivatel implements Serializable{
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
    @ColumnInfo(name = "mistnosti")
    private String mistnosti="";
    @Ignore
    public Uzivatel(int id, String jmeno, String prijmeni, String email, String heslo) {
        this.id = id;
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

    public String getMistnosti() {
        return mistnosti;
    }

    public void setMistnosti(String mistnosti) {
        this.mistnosti = mistnosti;
    }
    protected void deleteTask(final Context kde) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                clientosDatabazosUzivatelos.getInstance(kde).getAppDatabase().uzivatelousDao().delete(Uzivatel.this);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }
    protected void updateTask(final Context kde) {


        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                clientosDatabazosUzivatelos.getInstance(kde).getAppDatabase().uzivatelousDao().update(Uzivatel.this);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }
}
