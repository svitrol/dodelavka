package com.example.rozjedprihlasovocku;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Uzivatel.class}, version = 1)
public abstract class databazeUzivatelu extends RoomDatabase {
    public abstract uzivateleDao uzivatelousDao();
}
