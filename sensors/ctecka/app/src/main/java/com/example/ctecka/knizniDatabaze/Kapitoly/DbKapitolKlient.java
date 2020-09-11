package com.example.ctecka.knizniDatabaze.Kapitoly;

import android.content.Context;

import androidx.room.Room;

public class DbKapitolKlient {
    private Context mCtx;
    private int idKnizky;
    private static DbKapitolKlient mInstance;

    //our app database object
    private SvazekKapitol appDatabase;

    private DbKapitolKlient(Context mCtx, int idKnizky) {
        this.mCtx = mCtx;
        this.idKnizky=idKnizky;

        //vytvoření app database v Room database builder//MojePrvky//TvojePrvky
        appDatabase = Room.databaseBuilder(mCtx, SvazekKapitol.class, ""+idKnizky).build();
    }

    public static synchronized DbKapitolKlient getInstance(Context mCtx,int idKnizky) {
        if (mInstance == null) {
            mInstance = new DbKapitolKlient(mCtx,idKnizky);
        }
        return mInstance;
    }

    public SvazekKapitol getAppDatabase() {
        return appDatabase;
    }
}
