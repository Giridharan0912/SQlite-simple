package com.example.app4_groceryneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app4_groceryneeds.R;
import com.example.app4_groceryneeds.model.Grocery;
import com.example.app4_groceryneeds.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private final Context context;

    public DataBaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create the table
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_ITEM_NAME + " TEXT,"
                + Util.KEY_ITEM_QUANTITY + " INTEGER,"
                + Util.KEY_ITEM_BRAND + " TEXT,"
                + Util.KEY_ITEM_SIZE + " INTEGER,"
                + Util.KEY_ITEM_DATE_ADDED + " LONG);";
        sqLiteDatabase.execSQL(CREATE_GROCERY_TABLE);//creating the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.drop_db);
        sqLiteDatabase.execSQL(DROP_TABLE + Util.TABLE_NAME);

        //Create a table again
        onCreate(sqLiteDatabase);

    }

    public void addGrocery(Grocery grocery) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //create a content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ITEM_NAME, grocery.getItem_name());
        contentValues.put(Util.KEY_ITEM_QUANTITY, grocery.getItem_quantity());
        contentValues.put(Util.KEY_ITEM_BRAND, grocery.getItem_brand());
        contentValues.put(Util.KEY_ITEM_SIZE, grocery.getItem_size());
        contentValues.put(Util.KEY_ITEM_DATE_ADDED, java.lang.System.currentTimeMillis());//inserting time stamp


        sqLiteDatabase.insert(Util.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();//closing the database
    }

    public Grocery getGrocery(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID,
                        Util.KEY_ITEM_NAME,
                        Util.KEY_ITEM_QUANTITY,
                        Util.KEY_ITEM_BRAND,
                        Util.KEY_ITEM_SIZE,
                        Util.KEY_ITEM_DATE_ADDED},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Grocery grocery = new Grocery();
        grocery.setItem_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID))));
        grocery.setItem_name(cursor.getString(cursor.getColumnIndex(Util.KEY_ITEM_NAME)));
        grocery.setItem_quantity(cursor.getInt(cursor.getColumnIndex(Util.KEY_ITEM_QUANTITY)));
        grocery.setItem_brand(cursor.getString(cursor.getColumnIndex(Util.KEY_ITEM_BRAND)));
        grocery.setItem_size(cursor.getInt(cursor.getColumnIndex(Util.KEY_ITEM_SIZE)));
        //convert time stamp to readable
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattingDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Util.KEY_ITEM_DATE_ADDED))).getTime());
        grocery.setItem_date_added(formattingDate);


        return grocery;
    }

    public List<Grocery> getAllGrocery() {
        //get all the grocery items in arraylist
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Grocery> groceryList = new ArrayList<>();//creating the list

        //Select all grocery
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID,
                        Util.KEY_ITEM_NAME,
                        Util.KEY_ITEM_QUANTITY,
                        Util.KEY_ITEM_BRAND,
                        Util.KEY_ITEM_SIZE,
                        Util.KEY_ITEM_DATE_ADDED},
                null, null, null, null,
                Util.KEY_ITEM_DATE_ADDED + " DESC");

//Loop through our data
        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setItem_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID))));
                grocery.setItem_name(cursor.getString(cursor.getColumnIndex(Util.KEY_ITEM_NAME)));
                grocery.setItem_quantity(cursor.getInt(cursor.getColumnIndex(Util.KEY_ITEM_QUANTITY)));
                grocery.setItem_brand(cursor.getString(cursor.getColumnIndex(Util.KEY_ITEM_BRAND)));
                grocery.setItem_size(cursor.getInt(cursor.getColumnIndex(Util.KEY_ITEM_SIZE)));
                //convert time stamp to readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattingDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Util.KEY_ITEM_DATE_ADDED))).getTime());
                grocery.setItem_date_added(formattingDate);


                //add grocery objects to our list
                groceryList.add(grocery);
            } while (cursor.moveToNext());
        }


        return groceryList;
    }

    //Update grocery
    public int updateGrocery(Grocery grocery) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ITEM_NAME, grocery.getItem_name());
        contentValues.put(Util.KEY_ITEM_QUANTITY, grocery.getItem_quantity());
        contentValues.put(Util.KEY_ITEM_BRAND, grocery.getItem_brand());
        contentValues.put(Util.KEY_ITEM_SIZE, grocery.getItem_size());
        contentValues.put(Util.KEY_ITEM_DATE_ADDED, java.lang.System.currentTimeMillis());//inserting time stamp


        //update the row
        //update(tablename, values, where id = 43)
        return sqLiteDatabase.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[]{String.valueOf(grocery.getItem_id())});
    }

    //Delete single grocery
    public void deleteGrocery(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(String.valueOf(id))});

        sqLiteDatabase.close();
    }

    //Get Grocery count
    public int getGroceryCount() {
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);

        return cursor.getCount();

    }
}
