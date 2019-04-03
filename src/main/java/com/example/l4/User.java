package com.example.l4;

import androidx.room.Dao;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @Ignore
    public User(){}

    public User(String firstName,String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

}
