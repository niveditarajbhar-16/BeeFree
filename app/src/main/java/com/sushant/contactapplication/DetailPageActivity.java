package com.sushant.contactapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sushant.contactapplication.databinding.ActivityDetailPageBinding;

public class DetailPageActivity extends AppCompatActivity {
    private int person_id;
    private PersonViewModel viewModel;
    private ActivityDetailPageBinding binding;
    private MutableLiveData<Person> personData = new MutableLiveData<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_page);
        viewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        binding.setViewModel(viewModel);

        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        person_id = intent.getIntExtra(MainActivity.Extra_PERSON_ID, -1);
        if (person_id != -1) {
            viewModel.getSinglePersonFromId(person_id).observe(this, person -> {
                if (person != null) {
                    personData.setValue(person);
                    binding.profileWordTv.setText(personData.getValue().getFirstName().substring(0, 1));
                    binding.personNameTv.setText(personData.getValue().getFirstName() + " " + person.getLastName());
                    binding.personMobileTv.setText(personData.getValue().getMobileNo());
                }
            });
        }

        binding.floatingActionButton.setOnClickListener(v -> {
            Intent i = new Intent(this, AddEditActivity.class);
            i.putExtra(MainActivity.Extra_PERSON_ID, person_id);
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_btn:
                deleteContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteContact() {
        if (person_id == -1) {
            return;
        }
        //viewModel.getSinglePersonFromId(person_id).observe(this, person -> viewModel.deletePerson(person));
        viewModel.deletePerson(personData.getValue());
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
