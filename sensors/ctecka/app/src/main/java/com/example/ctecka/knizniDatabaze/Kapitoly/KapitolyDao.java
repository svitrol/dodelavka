package com.example.ctecka.knizniDatabaze.Kapitoly;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KapitolyDao {
    @Query("SELECT * FROM kapitola")
    List<Kapitola> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Kapitola prvek);

    @Delete
    void delete(Kapitola prvek);

    @Update
    void update(Kapitola prvek);

    @Query("DELETE FROM kapitola")
    public void nukeTable();
}
