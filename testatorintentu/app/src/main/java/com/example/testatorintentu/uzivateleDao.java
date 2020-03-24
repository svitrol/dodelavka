package com.example.testatorintentu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface uzivateleDao {
    @Query("SELECT * FROM uzivatel")
    List<Uzivatel> getAll();

    @Query("SELECT * FROM uzivatel WHERE email=:email")
    Uzivatel getUzivatelePodleEmailu(String email);

    @Insert
//(onConflict = OnConflictStrategy.REPLACE)
    void insert(Uzivatel prvek);

    @Delete
    void delete(Uzivatel prvek);

    @Update
    void update(Uzivatel prvek);

    @Query("DELETE FROM Uzivatel")
    public void nukeTable();
}
