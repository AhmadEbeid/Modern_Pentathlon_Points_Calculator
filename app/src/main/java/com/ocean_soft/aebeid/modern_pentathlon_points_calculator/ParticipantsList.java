package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


import java.util.ArrayList;

import static com.ocean_soft.aebeid.modern_pentathlon_points_calculator.MainActivity.db;

public class ParticipantsList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView participantsList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_list);

        final ArrayList<String> names = db.getAllParticipantsListsNames();
        final ArrayList<Integer> IDs = db.getAllParticipantsListsIDs();

        participantsList = (ListView) findViewById(R.id.participantsListView);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        participantsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        participantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), Participants.class);
                i.putExtra("name", names.get(position));
                i.putExtra("location", position);
                startActivity(i);
            }
        });
        final Context context = this;
        participantsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteParticipantList(IDs.get(position));
                                names.remove(position);
                                adapter.notifyDataSetChanged();
                                db.closeDB();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });


        db.closeDB();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_participants_listview, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.add:

                LinearLayout layout = new LinearLayout(getApplicationContext());

                final EditText titleBox = new EditText(getApplicationContext());
                titleBox.setTextColor(Color.BLACK);
                titleBox.setTextSize(18);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(30, 0, 30, 0);
                titleBox.setLayoutParams(layoutParams);
                layout.addView(titleBox);

                final AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Enter a list name").setView(layout);

                alert.setPositiveButton("Save",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //
                        }
                    }).setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (titleBox.getText().toString().trim().equalsIgnoreCase("")) {
                            titleBox.setError("This field can not be blank");
                        }
                        else {
                            String name = titleBox.getText().toString();
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), Participants.class);
                            i.putExtra("name", name);
                            startActivity(i);
                        }
                    }
                });


                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //return nothing
        return;
    }
}
