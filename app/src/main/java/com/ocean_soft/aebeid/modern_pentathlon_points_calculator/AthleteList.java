package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import java.util.ArrayList;

/**
 * Created by aebei on 19-Nov-16.
 */

public class AthleteList {

    private int ID;
    private String Name;
    private String listOfNames;
    private ArrayList<String> listOfNamesArray;
    private String listOfYearOfBirth;
    private ArrayList<Integer> listOfYearOfBirthArray;

    public AthleteList() {
        Name = "";
        listOfNames = "";
        listOfYearOfBirth = "";
        listOfNamesArray = new ArrayList<>();
        listOfYearOfBirthArray = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getListOfNames() {
        return listOfNames;
    }

    public void setListOfNames(String listOfNames) {
        this.listOfNames = listOfNames;
    }

    public ArrayList<String> getListOfNamesArray() {
        listOfNamesArray = MainActivity.deserialize(listOfNames);
        return listOfNamesArray;
    }

    public void setListOfNamesArray(ArrayList<String> listOfNamesArray) {
        listOfNames = MainActivity.serialize(listOfNamesArray);
        this.listOfNamesArray = listOfNamesArray;
    }

    public String getListOfYearOfBirth() {
        return listOfYearOfBirth;
    }

    public void setListOfYearOfBirth(String listOfYearOfBirth) {
        this.listOfYearOfBirth = listOfYearOfBirth;
    }

    public ArrayList<Integer> getListOfYearOfBirthArray() {

        ArrayList<String> arrayList = MainActivity.deserialize(listOfYearOfBirth);
        for(int i = 0; i < arrayList.size(); i++){
            listOfYearOfBirthArray.add(Integer.parseInt(arrayList.get(i)));
        }
        return listOfYearOfBirthArray;
    }

    public void setListOfYearOfBirthArray(ArrayList<Integer> listOfYearOfBirthArray) {
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < listOfYearOfBirthArray.size(); i++){
            arrayList.add(String.valueOf(listOfYearOfBirthArray.get(i)));
        }
        listOfYearOfBirth = MainActivity.serialize(arrayList);
        this.listOfYearOfBirthArray = listOfYearOfBirthArray;
    }
}
