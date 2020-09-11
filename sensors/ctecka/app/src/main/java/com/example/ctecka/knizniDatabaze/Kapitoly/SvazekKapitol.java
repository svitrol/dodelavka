package com.example.ctecka.knizniDatabaze.Kapitoly;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ctecka.knizniDatabaze.Kapitoly.Kapitola;

@Database(entities = {Kapitola.class},version = 1)
public abstract class SvazekKapitol extends RoomDatabase {
    public abstract KapitolyDao kapitolyDao();
}
