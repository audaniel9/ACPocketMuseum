package com.daniel.chat.acpocketmuseum;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.Insect.Insect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private Context context;
    private List<Fish> fishList;
    private List<Insect> insectList;
    private MutableLiveData<List<MuseumSpecimen>> favoriteList;

    public DataRepository(Context context) {
        this.context = context;
        favoriteList = new MutableLiveData<>();
    }

    // Parse and get fish data
    public List<Fish> getFishList() {
        fishList = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(JSONFileReader("Fish.json"));

            for (int i = 0; i < array.length(); i++) {
                JSONObject fish = array.getJSONObject(i);

                int id = fish.getInt("id");
                String name = fish.getJSONObject("name").getString("name-en");
                String location = "Location: " + fish.getJSONObject("availability").getString("location");
                String price = "Price: " + fish.getString("price") + " bells";
                String times = "Times: " + (fish.getJSONObject("availability").getString("time").equals("") ? "All day" : fish.getJSONObject("availability").getString("time"));

                fishList.add(new Fish(id, name, location, price, times));
            }
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return fishList;
    }

    // Parse and get insect data
    public List<Insect> getInsectList() {
        insectList = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(JSONFileReader("Insect.json"));

            for (int i = 0; i < array.length(); i++) {
                JSONObject insect = array.getJSONObject(i);

                int id = insect.getInt("id");
                String name = insect.getJSONObject("name").getString("name-en");
                String location = "Location: " + insect.getJSONObject("availability").getString("location");
                String price = "Price: " + insect.getString("price") + " bells";
                String times = "Times: " + (insect.getJSONObject("availability").getString("time").equals("") ? "All day" : insect.getJSONObject("availability").getString("time"));

                insectList.add(new Insect(id, name, location, price, times));
            }
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return insectList;
    }

    public MutableLiveData<List<MuseumSpecimen>> getFavoriteListFromRepo() {
        Log.d("GetFromRepo", favoriteList.toString());
        return favoriteList;
    }

    public void setFavoriteListFromRepo(List<MuseumSpecimen> list) {
        favoriteList.setValue(list);
    }

    // Reads JSON file
    public String JSONFileReader(String file) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(file)));
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }
}
