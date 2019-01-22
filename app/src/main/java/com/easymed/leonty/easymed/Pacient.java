package com.easymed.leonty.easymed;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Pacient {
    @PrimaryKey
    public int id;

    public String surname;
    public String name;
    public String patronymic;
    public Date birthDate;
    public String address;
    public String diagnosis;
    public String branch;
    public String note;

}
