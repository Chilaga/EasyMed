package com.easymed.leonty.easymed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PacientDao {
    @Query("SELECT * FROM pacient")
    List<Pacient> getAllPacients();

    @Query("SELECT * FROM pacient WHERE id = :id LIMIT 1")
    Pacient loadPacientById(int id);

    @Insert
    void insertAll(Pacient... pacients);
}
