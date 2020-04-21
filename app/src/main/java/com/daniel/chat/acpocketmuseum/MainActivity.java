package com.daniel.chat.acpocketmuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.daniel.chat.acpocketmuseum.adapter.MuseumSpecimenAdapter;
import com.daniel.chat.acpocketmuseum.fragment.MuseumFragment;
import com.daniel.chat.acpocketmuseum.model.MuseumSpecimen;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MuseumFragment.MuseumFragmentDataPasser {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private MuseumSpecimenAdapter adapter;
    private List<MuseumSpecimen> museumSpecimenList;
    private List<MuseumSpecimen> museumSpecimenListFishOnly;
    private List<MuseumSpecimen> museumSpecimenListInsectOnly;
    private MenuItem prevMenuItem;  // For toolbar menu use
    private String currentFragment; // For fragment stack use

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

        buildDrawer();

        fragmentStackListener();
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

    // Pressing back button while navigation drawer is open won't close the activity home screen
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(currentFragment.equals("MuseumFragment")) {
            finish();   // End activity
        }
        else {
            super.onBackPressed();
        }
    }

    // Retrieve data from fragment
    @Override
    public void adapterDataPass(MuseumSpecimenAdapter adapter) {
        this.adapter = adapter;
    }

    // Build the toolbar
    public void buildToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Build the navigation drawer
    public void buildDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final NavigationView navigationView = findViewById(R.id.navigation_view);

        // Set museum fragment to start when activity starts
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container, MuseumFragment.newInstance(museumSpecimenList, museumSpecimenListFishOnly, museumSpecimenListInsectOnly))
                .addToBackStack(null).commit();

        navigationView.setCheckedItem(R.id.museum); // Set Museum menu item in navigation drawer to checked

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(!(navigationView.getCheckedItem() == item)) {
                    switch(item.getItemId()) {
                        case R.id.museum:
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_bottom, R.anim.fade_out)
                                    .replace(R.id.fragment_container, MuseumFragment.newInstance(museumSpecimenList, museumSpecimenListFishOnly, museumSpecimenListInsectOnly))
                                    .addToBackStack(null).commit();
                            Objects.requireNonNull(getSupportActionBar()).setTitle("Museum");   // It has to have requireNonNull to prevent null object references
                            break;
                    }
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    // Performs menu check toggling
    public void toggleCheck(MenuItem item) {
        item.setChecked(true);

        if (prevMenuItem != null && prevMenuItem != item) {
            prevMenuItem.setChecked(false);
        }
        prevMenuItem = item;
    }

    // Listens for change in fragment stack; used for destroying activity on back press while on first fragment
    public void fragmentStackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                Fragment frag = fragmentList.get(0);
                currentFragment = frag.getClass().getSimpleName();
            }
        });
    }

    // Reads JSON file
    public String JSONFileReader(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(file), StandardCharsets.UTF_8));

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