package com.example.rozjedprihlasovocku;

import android.net.Uri;

public class obrazZmistnosti {
    Uri odkazNaObraz;
    Uri odkazNaThumbnail;
    String nazevMistnosti;

    public obrazZmistnosti(Uri odkazNaObraz, Uri odkazNaThumbnail, String nazevMistnosti) {
        this.odkazNaObraz = odkazNaObraz;
        this.odkazNaThumbnail = odkazNaThumbnail;
        this.nazevMistnosti = nazevMistnosti;
    }
}
