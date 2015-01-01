package com.asaphyuan.simpleprayr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.asaphyuan.simpleprayr.db.TaskContract;
import com.asaphyuan.simpleprayr.db.TaskDBHelper;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private TaskDBHelper helper;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ADD HERE
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        SQLiteDatabase sqlDB = new TaskDBHelper(this).getWritableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns.REQUEST},
                null,null,null,null,null);

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String tmp = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            TaskContract.Columns.REQUEST));
            Log.d("MainActivity cursor", tmp);
            itemsAdapter.add(tmp);
        }
        setupListViewListener();
    }

    // Attaches a click listener to the listview
    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                                   View v, final int pos, long id) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("Confirm Delete");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Delete this request?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //remove from DB
                                        String task = (String) lvItems.getItemAtPosition(pos);

                                        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                                                TaskContract.TABLE,
                                                TaskContract.Columns.REQUEST,
                                                task);


                                        helper = new TaskDBHelper(MainActivity.this);
                                        SQLiteDatabase sqlDB = helper.getWritableDatabase();
                                        sqlDB.execSQL(sql);

                                        // Remove the item within array at position
                                        items.remove(pos);

                                        // Refresh the adapter
                                        itemsAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }

                });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Log.d("MainActivity", itemText);

        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.clear();
        values.put(TaskContract.Columns.REQUEST,itemText);

        db.insertWithOnConflict(TaskContract.TABLE,null,values,
                SQLiteDatabase.CONFLICT_IGNORE);

        itemsAdapter.add(itemText);
        etNewItem.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etNewItem.getWindowToken(), 0);
    }
}