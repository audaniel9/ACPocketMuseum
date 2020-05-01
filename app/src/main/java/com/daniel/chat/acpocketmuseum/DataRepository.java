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
        fishList = new ArrayList<>();
        insectList = new ArrayList<>();
        favoriteList = new MutableLiveData<>();
        favoriteList.setValue(new ArrayList<MuseumSpecimen>()); // Don't remove this
    }

    // Parse and get fish data
    public List<Fish> getFishListFromRepo() {
        try {
            JSONArray array = new JSONArray(JSONFileReader("Fish.json"));

            for (int i = 0; i < array.length(); i++) {
                JSONObject fish = array.getJSONObject(i);

                int id = fish.getInt("id");
                String name = fish.getJSONObject("name").getString("name-en");
                String location = fish.getJSONObject("availability").getString("location");
                String price = fish.getString("price") + " bells";
                String times = fish.getJSONObject("availability").getString("time");
                String rarity = fish.getJSONObject("availability").getString("rarity");
                String monthsNorthern = fish.getJSONObject("availability").getString("month-northern");
                String monthsSouthern = fish.getJSONObject("availability").getString("month-southern");
                String catchphrase = fish.getString("catch-phrase");
                String museumPhrase = fish.getString("museum-phrase");
                String priceCJ = fish.getString("price-cj");
                String shadow = fish.getString("shadow");

                fishList.add(new Fish(id, name, location, price, times, rarity, monthsNorthern, monthsSouthern, catchphrase, museumPhrase, priceCJ, shadow));
            }
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return fishList;
    }

    // Parse and get insect data
    public List<Insect> getInsectListFromRepo() {
        try {
            JSONArray array = new JSONArray(JSONFileReader("Insect.json"));

            for (int i = 0; i < array.length(); i++) {
                JSONObject insect = array.getJSONObject(i);

                int id = insect.getInt("id");
                String name = insect.getJSONObject("name").getString("name-en");
                String location = insect.getJSONObject("availability").getString("location");
                String price = insect.getString("price") + " bells";
                String times = insect.getJSONObject("availability").getString("time");
                String rarity = insect.getJSONObject("availability").getString("rarity");
                String monthsNorthern = insect.getJSONObject("availability").getString("month-northern");
                String monthsSouthern = insect.getJSONObject("availability").getString("month-southern");
                String catchphrase = insect.getString("catch-phrase");
                String museumPhrase = insect.getString("museum-phrase");
                String priceFlick = insect.getString("price-flick");

                insectList.add(new Insect(id, name, location, price, times, rarity, monthsNorthern, monthsSouthern, catchphrase, museumPhrase, priceFlick));
            }
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return insectList;
    }

    public MutableLiveData<List<MuseumSpecimen>> getFavoriteListFromRepo() {
        return favoriteList;
    }

    public void addFavoriteSpecimenFromRepo(MuseumSpecimen specimen) {
        List<MuseumSpecimen> list = favoriteList.getValue();
        assert list != null;
        if(!list.contains(specimen)) {
            list.add(specimen);
        }

        favoriteList.setValue(list);
    }

    public void removeFavoriteSpecimenFromRepo(MuseumSpecimen specimen) {
        List<MuseumSpecimen> list = favoriteList.getValue();
        assert list != null;
        list.remove(specimen);
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