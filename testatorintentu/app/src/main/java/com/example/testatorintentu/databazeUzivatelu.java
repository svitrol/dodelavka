package com.example.testatorintentu;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Uzivatel.class}, version = 1)
public abstract class databazeUzivatelu extends RoomDatabase {
    public abstract uzivateleDao uzivatelousDao();
}
