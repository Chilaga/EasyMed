package com.easymed.leonty.easymed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PacientDao {
    @Query("SELECT * FROM pacient")
    List<Pacient> getAllPacients();

    @Query("SELECT * FROM pacient WHERE id = :id LIMIT 1")
    Pacient loadPacientById(int id);

    @Query("UPDATE pacient SET surname = :surname, name = :name, patronymic = :patron, birthDate = :dob, address = :address, diagnosis = :diagnosis, branch = :branch, note = :note WHERE id = :id")
    void updatePacient(String surname, String name, String patron, String dob, String address, String diagnosis, String branch, String note, int id);

    @Delete
    void deletePacient(Pacient... pacients);

    @Insert
    void insertAll(Pacient... pacients);
}
