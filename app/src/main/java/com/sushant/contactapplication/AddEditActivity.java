package com.sushant.contactapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sushant.contactapplication.databinding.ActivityAddEditBinding;

public class AddEditActivity extends AppCompatActivity {
    private ActivityAddEditBinding binding;
    private PersonViewModel personViewModel;
    private int person_id;
    private EditText et1, et2, et3;
    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);
        binding.setLifecycleOwner(this);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        binding.setViewModel(personViewModel);

        et1 = findViewById(R.id.first_name_et_input);
        et2 = findViewById(R.id.last_name_et_input);
        et3 = findViewById(R.id.mobile_et_input);
        reff = FirebaseDatabase.getInstance().getReference("UserData");


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        person_id = intent.getIntExtra(MainActivity.Extra_PERSON_ID, -1);
        if (person_id != -1) {
            personViewModel.getSinglePersonFromId(person_id).observe(this, person -> {
                personViewModel.firstName.postValue(person.getFirstName());
                personViewModel.lastName.postValue(person.getLastName());
                personViewModel.mobile.postValue(person.getMobileNo());
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_btn:
                SaveData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void SaveData() {

        String fname = et1.getText().toString();
        String lname = et2.getText().toString();
        String number = et3.getText().toString();


       if (personViewModel.isValidInput()) {
            if (getIntent().hasExtra(MainActivity.Extra_PERSON_ID) || person_id != -1) {
                Person person = new Person(personViewModel.firstName.getValue(), personViewModel.lastName.getValue(), personViewModel.mobile.getValue());
                person.setId(person_id);
                personViewModel.updatePerson(person);
            } else
                personViewModel.insertPerson(new Person(personViewModel.firstName.getValue(), personViewModel.lastName.getValue(), personViewModel.mobile.getValue()));
            finish();
        } else {
            personViewModel.showError(binding.firstNameEtInput, binding.lastNameEtInput, binding.mobileEtInput);
        }

        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(number)) {

            String id = reff.push().getKey();

            UserData useract = new UserData(fname, lname, number);

            reff.child(id).setValue(useract);
            et1.setText("");
            et2.setText("");
            et3.setText("");

        }

    }

}
