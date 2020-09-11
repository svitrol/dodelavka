package com.example.ctecka;

import com.example.ctecka.knizniDatabaze.Knizky.Knizka;

public interface Dotek {
    void onTouch(Knizka knizka);
    void onLongTouch(Knizka knizka);
}
