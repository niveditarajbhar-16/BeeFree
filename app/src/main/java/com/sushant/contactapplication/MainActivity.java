package com.sushant.contactapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sushant.contactapplication.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PersonListAdapter adapter;
    private ActivityMainBinding binding;
    private PersonViewModel viewModel;
    public static final String Extra_PERSON_ID = "person_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(this).get(PersonViewModel.class);

        binding.contactListView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                RecyclerView.VERTICAL, false));
        adapter = new PersonListAdapter();
        binding.contactListView.setAdapter(adapter);

        viewModel.getAllPerson().observe(this, people -> adapter.submitList(people));

        binding.floatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditActivity.class);
            startActivityForResult(intent, 111);
        });

        adapter.setOnItemClickListener(person -> {
            Intent intent = new Intent(MainActivity.this, DetailPageActivity.class);
            intent.putExtra(Extra_PERSON_ID, person.getId());
            startActivity(intent);
        });

    }
}
