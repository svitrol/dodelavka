package com.example.rozjedprihlasovocku;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface uzivateleDao {
    @Query("SELECT * FROM uzivatel")
    List<Uzivatel> getAll();

    @Query("SELECT * FROM uzivatel WHERE email=:email")
    Uzivatel getUzivatelePodleEmailu(String email);

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insert(Uzivatel prvek);

    @Delete
    void delete(Uzivatel prvek);

    @Update
    void update(Uzivatel prvek);

    @Query("DELETE FROM Uzivatel")
    public void nukeTable();
}
