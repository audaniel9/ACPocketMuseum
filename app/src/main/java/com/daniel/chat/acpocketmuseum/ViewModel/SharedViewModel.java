package com.daniel.chat.acpocketmuseum.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.daniel.chat.acpocketmuseum.Model.MuseumSpecimen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private List<MuseumSpecimen> museumSpecimenList;
    private List<MuseumSpecimen> museumSpecimenListFishOnly;
    private List<MuseumSpecimen> museumSpecimenListInsectOnly;

    public SharedViewModel(@NonNull Application application) {
        super(application);
    }

    public List<MuseumSpecimen> getMuseumSpecimenList() {
        try {
            JSONParser();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return museumSpecimenList;
    }

    public List<MuseumSpecimen> getMuseumSpecimenListFishOnly() {
        try {
            JSONParser();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return museumSpecimenListFishOnly;
    }

    public List<MuseumSpecimen> getMuseumSpecimenListInsectOnly() {
        try {
            JSONParser();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return museumSpecimenListInsectOnly;
    }

    // Reads JSON file
    public String JSONFileReader(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getApplication().getAssets().open(file)));

        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }

    // Parses the file that have been read
    public void JSONParser() throws IOException, JSONException {
        museumSpecimenList = new ArrayList<>();
        museumSpecimenListFishOnly = new ArrayList<>();
        museumSpecimenListInsectOnly = new ArrayList<>();

        String fishJSON = JSONFileReader("Fish.json");
        String insectJSON = JSONFileReader("Insect.json");

        JSONArray fishJSONArray = new JSONArray(fishJSON);
        JSONArray insectJSONArray = new JSONArray(insectJSON);

        for (int i = 0; i < fishJSONArray.length(); i++) {
            JSONObject fish = fishJSONArray.getJSONObject(i);

            int id = fish.getInt("id");
            String specimenName = fish.getString("name");
            String location = "Location: " + fish.getString("location");
            String price = "Price: " + fish.getString("price") + " bells";
            String times = "Times: " + fish.getJSONObject("times").getString("text");

            museumSpecimenListFishOnly.add(new MuseumSpecimen(id, specimenName, location, price, times));
        }

        for (int i = 0; i < insectJSONArray.length(); i++) {
            JSONObject insect = insectJSONArray.getJSONObject(i);

            int id = insect.getInt("id");
            String specimenName = insect.getString("name");
            String location = "Location: " + insect.getString("location");
            String price = "Price: " + insect.getString("price")+ " bells";
            String times = "Times: " + insect.getJSONObject("times").getString("text");

            museumSpecimenListInsectOnly.add(new MuseumSpecimen(id, specimenName, location, price, times));
        }
        museumSpecimenList.addAll(museumSpecimenListFishOnly);
        museumSpecimenList.addAll(museumSpecimenListInsectOnly);
    }
}
