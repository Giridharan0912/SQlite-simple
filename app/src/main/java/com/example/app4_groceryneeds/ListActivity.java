package com.example.app4_groceryneeds;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4_groceryneeds.adapter.RecyclerViewAdapter;
import com.example.app4_groceryneeds.data.DataBaseHandler;
import com.example.app4_groceryneeds.model.Grocery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Grocery> groceryArrayList;
    private DataBaseHandler dataBaseHandler;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText item_name;
    private EditText item_quantity;
    private EditText item_brand;
    private EditText item_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        dataBaseHandler = new DataBaseHandler(this);
        groceryArrayList = new ArrayList<>();


        List<Grocery> groceryList = dataBaseHandler.getAllGrocery();
        for (Grocery grocery : groceryList) {
            Log.d("Second", "onClick" + grocery.getItem_quantity());
            groceryArrayList.add(grocery);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, groceryArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
            }
        });

    }

    private void popup() {
        builder = new AlertDialog.Builder(ListActivity.this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        item_name = view.findViewById(R.id.itemName);
        item_quantity = view.findViewById(R.id.itemQuantity);
        item_brand = view.findViewById(R.id.itemBrand);
        item_size = view.findViewById(R.id.itemSize);
        saveButton = view.findViewById(R.id.save_button);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!item_name.getText().toString().isEmpty()
                        && !item_quantity.getText().toString().isEmpty()
                        && !item_brand.getText().toString().isEmpty()
                        && !item_size.getText().toString().isEmpty()) {
                    saveGrocery(view);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            startActivity(new Intent(ListActivity.this, ListActivity.class));
                            finish();
                        }
                    }, 1200);
                } else {
                    Snackbar.make(view, "Empty List is not Allowed", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void saveGrocery(View view) {

        String grocery_name = item_name.getText().toString().trim();
        String grocery_brand = item_brand.getText().toString().trim();
        int grocery_quantity = Integer.parseInt(item_quantity.getText().toString().trim());
        int grocery_size = Integer.parseInt(item_size.getText().toString().trim());
        Grocery grocery = new Grocery();
        grocery.setItem_name(grocery_name);
        grocery.setItem_quantity(grocery_quantity);
        grocery.setItem_brand(grocery_brand);
        grocery.setItem_size(grocery_size);
        dataBaseHandler.addGrocery(grocery);
        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();
    }
}