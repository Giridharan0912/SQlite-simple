package com.example.app4_groceryneeds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4_groceryneeds.R;
import com.example.app4_groceryneeds.data.DataBaseHandler;
import com.example.app4_groceryneeds.model.Grocery;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Grocery> groceryList;


    public RecyclerViewAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery grocery = groceryList.get(position);
        holder.name_show.setText(String.valueOf(grocery.getItem_name()));
        holder.quantity_show.setText("Quantity: " + String.valueOf(grocery.getItem_quantity()));
        holder.brand_show.setText("Brand: " + String.valueOf(grocery.getItem_brand()));
        holder.size_show.setText("Size: " + String.valueOf(grocery.getItem_size()));
        holder.date_show.setText("Date Added: " + String.valueOf(grocery.getItem_date_added()));


    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name_show;
        private TextView quantity_show;
        private TextView brand_show;
        private TextView size_show;
        private TextView date_show;
        private int id;
        private ImageButton edit_show;
        private ImageButton delete_show;
        private DataBaseHandler dataBaseHandler;
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        private Button yes;
        private Button no;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            name_show = itemView.findViewById(R.id.name);
            quantity_show = itemView.findViewById(R.id.quantity);
            brand_show = itemView.findViewById(R.id.brand);
            size_show = itemView.findViewById(R.id.size);
            date_show = itemView.findViewById(R.id.date);
            edit_show = itemView.findViewById(R.id.edit);
            delete_show = itemView.findViewById(R.id.delete);
            edit_show.setOnClickListener(this);
            delete_show.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            final Grocery grocery = groceryList.get(position);
            switch (view.getId()) {


                case (R.id.edit):
//                    TODO: edit the list of item
                    editItem(grocery);
                    break;
                case (R.id.delete):
//                    TODO: delete the specific list of item
                    builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view1 = inflater.inflate(R.layout.delete_popup, null);
                    yes = view1.findViewById(R.id.yes_button);
                    no = view1.findViewById(R.id.no_button);
                    builder.setView(view1);
                    dialog = builder.create();
                    dialog.show();
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            int position = getAdapterPosition();
//                            Grocery grocery = groceryList.get(position);
                            deleteItem(grocery.getItem_id());
                            dialog.dismiss();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    break;
            }
        }

        private void deleteItem(int id) {

            DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
            dataBaseHandler.deleteGrocery(id);
            groceryList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());

        }

        private void editItem(final Grocery newGrocery) {
            builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.pop_up, null);
            Button saveButton;
            final EditText item_name;
            final EditText item_quantity;
            final EditText item_brand;
            final EditText item_size;
            item_name = view.findViewById(R.id.itemName);
            item_quantity = view.findViewById(R.id.itemQuantity);
            item_brand = view.findViewById(R.id.itemBrand);
            item_size = view.findViewById(R.id.itemSize);
            saveButton = view.findViewById(R.id.save_button);
            TextView title = view.findViewById(R.id.enter_item);
            saveButton.setText("Update button");
            title.setText("Update Item");
            item_name.setText(newGrocery.getItem_name());
            item_quantity.setText(String.valueOf(newGrocery.getItem_quantity()));
            item_brand.setText(newGrocery.getItem_brand());
            item_size.setText(String.valueOf(newGrocery.getItem_size()));
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                    if (!item_name.getText().toString().isEmpty()
                            && !item_quantity.getText().toString().isEmpty()
                            && !item_brand.getText().toString().isEmpty()
                            && !item_size.getText().toString().isEmpty()) {
                        newGrocery.setItem_name(item_name.getText().toString());
                        newGrocery.setItem_quantity(Integer.parseInt(item_quantity.getText().toString()));
                        newGrocery.setItem_brand(item_brand.getText().toString());
                        newGrocery.setItem_size(Integer.parseInt(item_size.getText().toString()));
                        dataBaseHandler.updateGrocery(newGrocery);
                        notifyItemChanged(getAdapterPosition(), newGrocery);
                        dialog.dismiss();
                    } else {
                        Snackbar.make(view, "field is empty", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


}
