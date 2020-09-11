package com.example.ctecka.knizniDatabaze.Knizky;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KnizkyDao {

    @Query("SELECT * FROM knizka")
    List<Knizka> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Knizka prvek);

    @Delete
    void delete(Knizka prvek);

    @Update
    void update(Knizka prvek);

    @Query("DELETE FROM knizka")
    public void nukeTable();
}
