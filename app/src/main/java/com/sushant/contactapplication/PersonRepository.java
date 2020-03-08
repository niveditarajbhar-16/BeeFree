package com.sushant.contactapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class PersonRepository {
    private PersonDao personDao;
    private LiveData<List<Person>> persons;
    private MutableLiveData<Person> person;

    public PersonRepository(Application application) {
        PersonDatabase database = PersonDatabase.getInstance(application);
        personDao = database.personDao();
        persons = personDao.getAllPerson();
    }

    public LiveData<Person> getPerson(Person personData) {
        if (person == null) {
            person.setValue(personData);
        }

        return person;
    }

    public LiveData<Person> getPersonFromId(int id) {
        return personDao.getPersonFromId(id);
    }

    public LiveData<List<Person>> getGetAllPerson() {
        return persons;
    }

    public void insert(Person person) {
        new insertPerson(personDao).execute(person);
    }

    public void update(Person person) {
        new updatePerson(personDao).execute(person);
    }

    public void delete(Person person) {
        new deletePerson(personDao).execute(person);
    }

    public static class insertPerson extends AsyncTask<Person, Void, Void> {
        private PersonDao personDao;

        public insertPerson(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.insert(people[0]);
            return null;
        }
    }

    public static class updatePerson extends AsyncTask<Person, Void, Void> {
        private PersonDao personDao;

        public updatePerson(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.update(people[0]);
            return null;
        }
    }

    public static class deletePerson extends AsyncTask<Person, Void, Void> {
        private PersonDao personDao;

        public deletePerson(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.delete(people[0]);
            return null;
        }
    }


}
