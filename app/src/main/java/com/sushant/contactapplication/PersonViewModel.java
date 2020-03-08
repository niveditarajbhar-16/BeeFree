package com.sushant.contactapplication;

import android.app.Application;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository personRepository;
    private LiveData<List<Person>> allPerson;
    public MutableLiveData<String> firstName = new MutableLiveData<>();
    public MutableLiveData<String> lastName = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<Person> personData;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository = new PersonRepository(application);
        allPerson = personRepository.getGetAllPerson();
    }

    public void insertPerson(Person person) {
        personRepository.insert(person);
    }

    public void updatePerson(Person person) {
        personRepository.update(person);
    }

    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    public LiveData<List<Person>> getAllPerson() {
        return allPerson;
    }

    public LiveData<Person> getSinglePersonFromId(int id) {
        return personRepository.getPersonFromId(id);
    }


    public TextViewBindingAdapter.AfterTextChanged firstNameTextChange() {
        return s -> firstName.setValue(s.toString().trim());
    }

    public TextViewBindingAdapter.AfterTextChanged lastNameTextChange() {
        return s -> lastName.setValue(s.toString().trim());
    }

    public TextViewBindingAdapter.AfterTextChanged mobileNoTextChange() {
        return s -> mobile.setValue(s.toString().trim());
    }

    public LiveData<Person> isValidate(TextInputEditText f_name, TextInputEditText l_name, TextInputEditText mobile_no) {
        if (personData == null)
            personData = new MutableLiveData<>();
        if (TextUtils.isEmpty(firstName.getValue()) && TextUtils.isEmpty(lastName.getValue()) && TextUtils.isEmpty(mobile.getValue())) {

        } else {
            personData.setValue(new Person(firstName.getValue(),lastName.getValue(),mobile.getValue()));
        }

        return personData;
    }

    public boolean isValidInput() {
        if (!TextUtils.isEmpty(firstName.getValue()) && !TextUtils.isEmpty(lastName.getValue()) && !TextUtils.isEmpty(mobile.getValue())) {
            return true;
        } else {
            return false;
        }
    }

    public void showError(TextInputEditText f_name, TextInputEditText l_name, TextInputEditText mobile_no) {
        if (TextUtils.isEmpty(f_name.getText().toString())) {
            f_name.setError(getApplication().getString(R.string.first_name_error));
        }
        if (TextUtils.isEmpty(l_name.getText().toString())) {
            l_name.setError(getApplication().getString(R.string.last_name_error));
        }
        if(TextUtils.isEmpty(mobile_no.getText().toString())){
            mobile_no.setError(getApplication().getString(R.string.mobile_no_error));
        }
    }

    public void setErrorToFirstName(EditText editText) {
        editText.setError("Please insert first name");
    }

    public void setErrorToLastName(EditText editText) {
        editText.setError("Please insert last name");
    }

    public void setErrorToMobile(EditText editText) {
        editText.setError("Please insert mobile");
    }

    public void setError() {
        Toast.makeText(getApplication(), "Please don't leave blank in name and mobile fields", Toast.LENGTH_SHORT).show();
        return;
    }

    public LiveData<Person> getPersonData() {
        if (personData == null) {
            personData = new MutableLiveData<>();
        }

        return personData;
    }

    public LiveData<String> getFName() {
        if (firstName == null)
            firstName.setValue("");
        return firstName;
    }

    public LiveData<String> getLName() {
        if (lastName == null)
            lastName.setValue("");
        return lastName;
    }

    public LiveData<String> getMobileNo() {
        if (mobile == null)
            mobile.setValue("");
        return mobile;
    }

}
