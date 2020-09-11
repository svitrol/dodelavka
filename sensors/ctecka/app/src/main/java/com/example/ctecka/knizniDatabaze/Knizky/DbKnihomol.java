package com.example.ctecka.knizniDatabaze.Knizky;

import android.content.Context;

import androidx.room.Room;

public class DbKnihomol {
    private Context mCtx;
    private static DbKnihomol mInstance;

    //our app database object
    private Knihovnice appDatabase;

    private DbKnihomol(Context mCtx) {
        this.mCtx = mCtx;

        //vytvoření app database v Room database builder//MojePrvky//TvojePrvky
        appDatabase = Room.databaseBuilder(mCtx, Knihovnice.class, "MojeKnizky").build();
    }

    public static synchronized DbKnihomol getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DbKnihomol(mCtx);
        }
        return mInstance;
    }

    public Knihovnice getAppDatabase() {
        return appDatabase;
    }
}
