package com.example.rozjedprihlasovocku;

import android.arch.persistence.room.Room;
import android.content.Context;

public class clientosDatabazosUzivatelos {
    private Context mCtx;
    private static clientosDatabazosUzivatelos mInstance;

    //our app database object
    private databazeUzivatelu appDatabase;

    private clientosDatabazosUzivatelos(Context mCtx) {
        this.mCtx = mCtx;

        //vytvoření app database v Room database builder//MojePrvky//TvojePrvky
        appDatabase = Room.databaseBuilder(mCtx, databazeUzivatelu.class, "MojiUzivatele").build();
    }

    public static synchronized clientosDatabazosUzivatelos getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new clientosDatabazosUzivatelos(mCtx);
        }
        return mInstance;
    }

    public databazeUzivatelu getAppDatabase() {
        return appDatabase;
    }
}
