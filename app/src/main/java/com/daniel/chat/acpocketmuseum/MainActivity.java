package com.daniel.chat.acpocketmuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MuseumSpecimen> museumSpecimenList;
    private List<MuseumSpecimen> museumSpecimenListFishOnly;
    private List<MuseumSpecimen> museumSpecimenListInsectOnly;
    private MuseumSpecimenAdapter adapter;
    private MenuItem prevMenuItem;  // For toolbar menu use

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildToolbar();
        try {
            JSONParser();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        buildRecyclerView();
    }

    // Toolbar menu logic
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        getMenuInflater().inflate(R.menu.filter_menu, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();

        // Search menu logic
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sortByDefault:
                adapter.getFilter().filter("@sortDefault");
                toggleCheck(item);
                return true;
            case R.id.sortByAZ:
                adapter.getFilter().filter("@sortAZ");
                toggleCheck(item);
                return true;
            case R.id.sortByZA:
                adapter.getFilter().filter("@sortZA");
                toggleCheck(item);
                return true;
            case R.id.filterFish:
                adapter.getFilter().filter("@filterFish");
                toggleCheck(item);
                return true;
            case R.id.filterInsect:
                adapter.getFilter().filter("@filterInsect");
                toggleCheck(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Build the toolbar
    public void buildToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    // Build the recycler view
    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // change this to implement grid view i think?

        adapter = new MuseumSpecimenAdapter(museumSpecimenList, museumSpecimenListFishOnly, museumSpecimenListInsectOnly);

        adapter.setOnItemClickListener(new MuseumSpecimenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("Main Info", museumSpecimenList.get(position));

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // Performs menu check toggling
    public void toggleCheck(MenuItem item) {
        item.setChecked(true);

        if (prevMenuItem != null && prevMenuItem != item) {
            prevMenuItem.setChecked(false);
        }
        prevMenuItem = item;
    }

    // Reads JSON file
    public String JSONFileReader(String file) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(getAssets().open(file), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null) {
            content = content + line;
        }

        return content;
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

            String specimenName = fish.getString("name");
            String location = "Location: " + fish.getString("location");
            String price = "Price: " + fish.getString("price") + " bells";
            String times = "Times: " + fish.getJSONObject("times").getString("text");

            museumSpecimenListFishOnly.add(new MuseumSpecimen(specimenName, location, price, times));
        }

        for (int i = 0; i < insectJSONArray.length(); i++) {
            JSONObject insect = insectJSONArray.getJSONObject(i);

            String specimenName = insect.getString("name");
            String location = "Location: " + insect.getString("location");
            String price = "Price: " + insect.getString("price")+ " bells";
            String times = "Times: " + insect.getJSONObject("times").getString("text");

            museumSpecimenListInsectOnly.add(new MuseumSpecimen(specimenName, location, price, times));
        }
        museumSpecimenList.addAll(museumSpecimenListFishOnly);
        museumSpecimenList.addAll(museumSpecimenListInsectOnly);
    }
}