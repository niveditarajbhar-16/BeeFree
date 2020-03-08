package com.sushant.contactapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("Select * from person_contact_table where id = :id LIMIT 1")
    LiveData<Person> getPersonFromId(int id);

    @Query("Select * from person_contact_table")
    LiveData<List<Person>> getAllPerson();

    @Query("SELECT * FROM person_contact_table WHERE firstName LIKE :f_name AND lastName LIKE :l_name")
    LiveData<List<Person>> getPersonsFromName(String f_name,String l_name);

}
