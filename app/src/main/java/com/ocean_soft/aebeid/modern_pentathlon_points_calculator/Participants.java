package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.ocean_soft.aebeid.modern_pentathlon_points_calculator.MainActivity.db;

public class Participants extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView participantsList;
    ArrayAdapter<String> adapter;
    AthleteList athleteList;
    ArrayList<String> names;
    ArrayList<Integer> years;
    int location;
    String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        participantsList = (ListView) findViewById(R.id.listView);
        athleteList = new AthleteList();

        Intent i = getIntent();
        listName = i.getStringExtra("name");
        location = i.getIntExtra("location", -1);

        if (i.getIntExtra("location", -1) == -1) {
            if (savedInstanceState != null) {
                // Restore value of members from saved state
                location = savedInstanceState.getInt("location");
                if(location <= 0){
                    names = new ArrayList<>();
                    years = new ArrayList<>();
                }
                else {
                    athleteList = db.getParticipantList(location);
                    names = athleteList.getListOfNamesArray();
                    years = athleteList.getListOfYearOfBirthArray();
                }
            }
            else
            {
                names = new ArrayList<>();
                years = new ArrayList<>();
            }

        } else {
            ArrayList<Integer> ids = db.getAllParticipantsListsIDs();
            long id = ids.get(location);
            athleteList = db.getParticipantList(id);
            names = athleteList.getListOfNamesArray();
            years = athleteList.getListOfYearOfBirthArray();

        }


        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        participantsList.setAdapter(adapter);
        final Context context = this;

        participantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(context)
                        .setTitle("Participants Info.")
                        .setMessage("Participant Name is: " + names.get(position) + ", Year of birth is: " + years.get(position))
                        .setNeutralButton("Done", null).show();
            }
        });

        participantsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                LayoutInflater factory = LayoutInflater.from(context);

                //text_entry is an Layout XML file containing two text field to display in alert dialog
                final View textEntryView = factory.inflate(R.layout.popup_add_new_participants, null);

                final EditText nameEdit = (EditText) textEntryView.findViewById(R.id.nameEditText);
                final EditText yearEdit = (EditText) textEntryView.findViewById(R.id.yearEditText);

                nameEdit.setText(names.get(position), TextView.BufferType.EDITABLE);
                yearEdit.setText(String.valueOf(years.get(position)), TextView.BufferType.EDITABLE);

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Enter participant info.").setView(textEntryView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Delete")
                                        .setMessage("Are you sure ?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(names.size() == 1){
                                                    db.deleteParticipantList(athleteList.getID());
                                                    db.closeDB();
                                                    Intent i = new Intent(getApplicationContext(), ParticipantsList.class);
                                                    startActivity(i);
                                                }else {
                                                    names.remove(position);
                                                    years.remove(position);
                                                    adapter.notifyDataSetChanged();
                                                    athleteList.setListOfNamesArray(names);
                                                    athleteList.setListOfYearOfBirthArray(years);
                                                    db.updateParticipantList(athleteList);
                                                    db.closeDB();
                                                }
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        }).setNeutralButton("Cancel", null);


                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (nameEdit.getText().toString().trim().equalsIgnoreCase("")) {
                            nameEdit.setError("This field can not be blank");
                        }
                        else if(yearEdit.getText().toString().trim().equalsIgnoreCase("")) {
                            yearEdit.setError("This field can not be blank");
                        }
                        else {
                            String name = nameEdit.getText().toString();
                            names.add(position, name);
                            names.remove(position + 1);
                            int year = Integer.parseInt(yearEdit.getText().toString());
                            years.add(position, year);
                            years.remove(position + 1);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            athleteList.setListOfNamesArray(names);
                            athleteList.setListOfYearOfBirthArray(years);
                            db.updateParticipantList(athleteList);
                            db.closeDB();

                        }
                    }
                });

                return true;
            }
        });

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
                LayoutInflater factory = LayoutInflater.from(this);

                //text_entry is an Layout XML file containing two text field to display in alert dialog
                final View textEntryView = factory.inflate(R.layout.popup_add_new_participants, null);

                final EditText nameEdit = (EditText) textEntryView.findViewById(R.id.nameEditText);
                final EditText yearEdit = (EditText) textEntryView.findViewById(R.id.yearEditText);

                final AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Enter participant info.").setView(textEntryView).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                            }
                        }).setNegativeButton("Cancel",null);


                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (nameEdit.getText().toString().trim().equalsIgnoreCase("")) {
                            nameEdit.setError("This field can not be blank");
                        }
                        else if(yearEdit.getText().toString().trim().equalsIgnoreCase("")) {
                            yearEdit.setError("This field can not be blank");
                        }
                        else {
                            String name = nameEdit.getText().toString();
                            names.add(0, name);
                            int year = Integer.parseInt(yearEdit.getText().toString());
                            years.add(0, year);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            if(location == -1){
                                athleteList.setName(listName);
                                athleteList.setListOfNamesArray(names);
                                athleteList.setListOfYearOfBirthArray(years);
                                location = db.createParticipantList(athleteList);
                                System.out.println(location);
                                db.closeDB();
                            }else{
                                athleteList.setListOfNamesArray(names);
                                athleteList.setListOfYearOfBirthArray(years);
                                db.updateParticipantList(athleteList);
                                db.closeDB();
                            }
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("location", location);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        //return nothing
        return;
    }

}
