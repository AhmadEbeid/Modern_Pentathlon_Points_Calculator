package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by aebei on 18-Nov-16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ModernPentathlon";

    //tables names
    private static final String TABLE_COMPETITION = "competition";
    //private static final String TABLE_PARTICIPANTS = "participants";
    private static final String TABLE_PARTICIPANTS_LIST = "participantsList";
    private static final String TABLE_RESULTS = "results";

    // Common Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    // Competition Table columns names
    private static final String KEY_S_MODE = "sMode";
    private static final String KEY_R_MODE = "rMode";
    private static final String KEY_PARTICIPANTS_NO = "participantsNo";
    private static final String KEY_PARTICIPANTS_LIST_ID = "participantsID";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_CREATED_AT = "timeOfCreation";

    // Participants Table columns names
    //private static final String KEY_YEAR_OF_BIRTH = "yearOfBirth";

    // Participants List Table columns names
    private static final String KEY_LIST_NAME = "participantsListName";
    private static final String KEY_LIST_BIRTH_YEAR = "participantsListBirthYear";

    // Results Table columns names
    private static final String KEY_S_TIME = "swimmingTime";
    private static final String KEY_R_TIME = "runningTime";
    private static final String KEY_S_POINTS = "swimmingPoints";
    private static final String KEY_R_POINTS = "runningPoints";
    private static final String KEY_T_POINTS = "totalPoints";

    // Table Create Statements

    // Competition Table create statement
    private static final String CREATE_TABLE_COMPETITION = "CREATE TABLE "
            + TABLE_COMPETITION + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_S_MODE + " TEXT," + KEY_R_MODE + " TEXT,"  + KEY_PARTICIPANTS_LIST_ID + " INTEGER,"
            + KEY_PARTICIPANTS_NO + " INTEGER," + KEY_RESULTS + " TEXT," + KEY_CREATED_AT  + " DATETIME" + ")";

    // Participants table create statement
  //  private static final String CREATE_TABLE_PARTICIPANTS = "CREATE TABLE "
    //        + TABLE_PARTICIPANTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
      //      + " TEXT," + KEY_PARTICIPANTS_ARRAY + " TEXT," + KEY_YEAR_OF_BIRTH + " INTEGER" + ")";

    // Participants List table create statement
    private static final String CREATE_TABLE_PARTICIPANTS_LIST = "CREATE TABLE "
            + TABLE_PARTICIPANTS_LIST + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_LIST_NAME + " TEXT," + KEY_LIST_BIRTH_YEAR + " TEXT" + ")";

    // Results table create statement
    private static final String CREATE_TABLE_TODO_RESULT = "CREATE TABLE "
            + TABLE_RESULTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_S_TIME + " TEXT," + KEY_R_TIME + " TEXT,"  + KEY_S_POINTS + " INTEGER,"
            + KEY_R_POINTS + " INTEGER," + KEY_T_POINTS + " INTEGER" + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_COMPETITION);
        //db.execSQL(CREATE_TABLE_PARTICIPANTS);
        db.execSQL(CREATE_TABLE_PARTICIPANTS_LIST);
        db.execSQL(CREATE_TABLE_TODO_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPETITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPANTS_LIST);

        // create new tables
        onCreate(db);

    }

    ////////////////////////////////////////////////////

    // Create a Competition
    public int createCompetition(Competition competition) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, competition.getCompetitionName());
        values.put(KEY_PARTICIPANTS_LIST_ID, competition.getParticipantsListID());
        values.put(KEY_PARTICIPANTS_NO, competition.getNumberOfParticipants());
        values.put(KEY_S_MODE, competition.getSwimmingMode());
        values.put(KEY_R_MODE, competition.getRunningMode());
        values.put(KEY_RESULTS, competition.getResult());
        values.put(KEY_CREATED_AT, competition.getFormattedDate());

        // insert row
        int id = (int) db.insert(TABLE_COMPETITION, null, values);
        competition.setID(id);

        return id;
    }

    // get Competition
    public Competition getCompetition(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_COMPETITION + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Competition competition = new Competition();
        competition.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        competition.setCompetitionName((c.getString(c.getColumnIndex(KEY_NAME))));
        competition.setNumberOfParticipants((c.getInt(c.getColumnIndex(KEY_PARTICIPANTS_NO))));
        competition.setParticipantsListID((c.getInt(c.getColumnIndex(KEY_PARTICIPANTS_LIST_ID))));
        competition.setResult((c.getString(c.getColumnIndex(KEY_RESULTS))));
        competition.setRunningMode((c.getString(c.getColumnIndex(KEY_R_MODE))));
        competition.setSwimmingMode((c.getString(c.getColumnIndex(KEY_S_MODE))));
        competition.setFormattedDate(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return competition;
    }

    //get All Competitions
    public ArrayList<Competition> getAllCompetitions() {
        ArrayList<Competition> competitions = new ArrayList<Competition>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMPETITION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Competition competition = new Competition();
                competition.setID(c.getInt(c.getColumnIndex(KEY_ID)));
                competition.setCompetitionName((c.getString(c.getColumnIndex(KEY_NAME))));
                competition.setNumberOfParticipants((c.getInt(c.getColumnIndex(KEY_PARTICIPANTS_NO))));
                competition.setParticipantsListID((c.getInt(c.getColumnIndex(KEY_PARTICIPANTS_LIST_ID))));
                competition.setResult((c.getString(c.getColumnIndex(KEY_RESULTS))));
                competition.setRunningMode((c.getString(c.getColumnIndex(KEY_R_MODE))));
                competition.setSwimmingMode((c.getString(c.getColumnIndex(KEY_S_MODE))));
                competition.setFormattedDate(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to Competitions ArrayList
                competitions.add(competition);

            } while (c.moveToNext());
        }

        return competitions;
    }

    //get Competitions IDs
    public ArrayList<Integer> getAllCompetitionsIDs(){
        ArrayList<Integer> IDs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMPETITION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                IDs.add(c.getInt(c.getColumnIndex(KEY_ID)));
            } while (c.moveToNext());
        }
        return IDs;
    }

    //get Competitions Names
    public ArrayList<String> getAllCompetitionsNames(){
        ArrayList<String> names = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMPETITION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                names.add(c.getString(c.getColumnIndex(KEY_NAME)));
            } while (c.moveToNext());
        }
        return names;
    }

    // updating Competition
    public int updateCompetition(Competition competition) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, competition.getCompetitionName());
        values.put(KEY_PARTICIPANTS_LIST_ID, competition.getParticipantsListID());
        values.put(KEY_PARTICIPANTS_NO, competition.getNumberOfParticipants());
        values.put(KEY_S_MODE, competition.getSwimmingMode());
        values.put(KEY_R_MODE, competition.getRunningMode());
        values.put(KEY_RESULTS, competition.getResult());
        values.put(KEY_CREATED_AT, competition.getFormattedDate());

        // updating row
        return db.update(TABLE_COMPETITION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(competition.getID()) });
    }

    // Deleting a Competition
    public void deleteCompetition(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPETITION, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    ////////////////////////////////////////////////////////////////////////

    // Create a Participant List
    public int createParticipantList(AthleteList athleteList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, athleteList.getName());
        values.put(KEY_LIST_NAME, athleteList.getListOfNames());
        values.put(KEY_LIST_BIRTH_YEAR, athleteList.getListOfYearOfBirth());

        // insert row
        int id = (int) db.insert(TABLE_PARTICIPANTS_LIST, null, values);
        athleteList.setID(id);

        return id;
    }

    // get Participant List
    public AthleteList getParticipantList(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPANTS_LIST + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        AthleteList athleteList = new AthleteList();
        athleteList.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        athleteList.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        athleteList.setListOfNames(c.getString(c.getColumnIndex(KEY_LIST_NAME)));
        athleteList.setListOfYearOfBirth(c.getString(c.getColumnIndex(KEY_LIST_BIRTH_YEAR)));


        return athleteList;
    }

    //get All Participants List
    public ArrayList<AthleteList> getAllParticipantLists() {
        ArrayList<AthleteList> athleteLists = new ArrayList<AthleteList>();
        String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPANTS_LIST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                AthleteList athleteList = new AthleteList();
                athleteList.setID(c.getInt(c.getColumnIndex(KEY_ID)));
                athleteList.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                athleteList.setListOfNames(c.getString(c.getColumnIndex(KEY_LIST_NAME)));
                athleteList.setListOfYearOfBirth(c.getString(c.getColumnIndex(KEY_LIST_BIRTH_YEAR)));

                // adding to Competitions ArrayList
                athleteLists.add(athleteList);

            } while (c.moveToNext());
        }

        return athleteLists;
    }

    //get Participants Lists IDs
    public ArrayList<Integer> getAllParticipantsListsIDs(){
        ArrayList<Integer> IDs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPANTS_LIST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                IDs.add(c.getInt(c.getColumnIndex(KEY_ID)));
            } while (c.moveToNext());
        }
        return IDs;
    }

    //get Participants Lists Names
    public ArrayList<String> getAllParticipantsListsNames(){
        ArrayList<String> names = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPANTS_LIST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                names.add(c.getString(c.getColumnIndex(KEY_NAME)));
            } while (c.moveToNext());
        }
        return names;
    }

    // get Participant List Names
    public String getParticipantListNames(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPANTS_LIST + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return  c.getString(c.getColumnIndex(KEY_LIST_NAME));
    }


    // updating Participant List
    public int updateParticipantList(AthleteList athleteList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, athleteList.getName());
        values.put(KEY_LIST_NAME, athleteList.getListOfNames());
        values.put(KEY_LIST_BIRTH_YEAR, athleteList.getListOfYearOfBirth());


        // updating row
        return db.update(TABLE_PARTICIPANTS_LIST, values, KEY_ID + " = ?",
                new String[] { String.valueOf(athleteList.getID()) });
    }

    // Deleting a Participant List
    public void deleteParticipantList(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PARTICIPANTS_LIST, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    ///////////////////////////////////////////////////////////////////////////

    // Create a Result
    public int createResult(ParticipantResult participantResult) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, participantResult.getParticipantName());
        values.put(KEY_S_POINTS, participantResult.getSwimmingPoints());
        values.put(KEY_R_POINTS, participantResult.getRunningPoints());
        values.put(KEY_S_TIME, participantResult.getSwimmingTime());
        values.put(KEY_R_TIME, participantResult.getRunningTime());
        values.put(KEY_T_POINTS, participantResult.getTotalPoints());

        // insert row
        int id = (int) db.insert(TABLE_RESULTS, null, values);
        participantResult.setID(id);

        return id;
    }

    // get Result
    public ParticipantResult getResult(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESULTS + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ParticipantResult participantResult = new ParticipantResult();
        participantResult.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        participantResult.setParticipantName((c.getString(c.getColumnIndex(KEY_NAME))));
        participantResult.setRunningTime(c.getString(c.getColumnIndex(KEY_R_TIME)));
        participantResult.setSwimmingTime(c.getString(c.getColumnIndex(KEY_S_TIME)));
        participantResult.setRunningPoints(c.getInt(c.getColumnIndex(KEY_R_POINTS)));
        participantResult.setSwimmingPoints(c.getInt(c.getColumnIndex(KEY_S_POINTS)));
        participantResult.setTotalPoints(c.getInt(c.getColumnIndex(KEY_T_POINTS)));


        return participantResult;
    }

    //get All Results
    public ArrayList<ParticipantResult> getAllResult() {
        ArrayList<ParticipantResult> participantResults = new ArrayList<ParticipantResult>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESULTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ParticipantResult participantResult = new ParticipantResult();
                participantResult.setID(c.getInt(c.getColumnIndex(KEY_ID)));
                participantResult.setParticipantName((c.getString(c.getColumnIndex(KEY_NAME))));
                participantResult.setRunningTime(c.getString(c.getColumnIndex(KEY_R_TIME)));
                participantResult.setSwimmingTime(c.getString(c.getColumnIndex(KEY_S_TIME)));
                participantResult.setRunningPoints(c.getInt(c.getColumnIndex(KEY_R_POINTS)));
                participantResult.setSwimmingPoints(c.getInt(c.getColumnIndex(KEY_S_POINTS)));
                participantResult.setTotalPoints(c.getInt(c.getColumnIndex(KEY_T_POINTS)));


                // adding to Competitions ArrayList
                participantResults.add(participantResult);

            } while (c.moveToNext());
        }

        return participantResults;
    }

    // updating Participant
    public int updateResult(ParticipantResult participantResult) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, participantResult.getParticipantName());
        values.put(KEY_S_POINTS, participantResult.getSwimmingPoints());
        values.put(KEY_R_POINTS, participantResult.getRunningPoints());
        values.put(KEY_S_TIME, participantResult.getSwimmingTime());
        values.put(KEY_R_TIME, participantResult.getRunningTime());
        values.put(KEY_T_POINTS, participantResult.getTotalPoints());

        // updating row
        return db.update(TABLE_RESULTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(participantResult.getID()) });
    }

    // Deleting a Participant
    public void deleteResult(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESULTS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public ArrayList<String> sortResults(){
        ArrayList<String> results = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_RESULTS + " ORDER BY SALARY DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String result = "";
                result = c.getString(c.getColumnIndex(KEY_NAME));
                result = result + " _,_ " + c.getString(c.getColumnIndex(KEY_R_TIME));
                result = result + " _,_ " + c.getString(c.getColumnIndex(KEY_S_TIME));
                result = result + " _,_ " + c.getInt(c.getColumnIndex(KEY_R_POINTS));
                result = result + " _,_ " + c.getInt(c.getColumnIndex(KEY_S_POINTS));
                result = result + " _,_ " + c.getInt(c.getColumnIndex(KEY_T_POINTS));

                // adding to Competitions ArrayList
                results.add(result);

            } while (c.moveToNext());
        }

        return results;

    }

    ///////////////////////////////////////////////////////////////////////////


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
