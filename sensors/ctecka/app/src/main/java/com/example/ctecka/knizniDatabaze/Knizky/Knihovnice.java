package com.example.ctecka.knizniDatabaze.Knizky;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Knizka.class},version = 1)
public abstract class Knihovnice extends RoomDatabase {
    public abstract KnizkyDao knizkyDao();

}
