package com.daniel.chat.acpocketmuseum;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArraySet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildToolbar();
        buildDrawer();
    }

    // Toolbar menu logic
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Handles the up button on nav drawer
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    // Build the toolbar
    public void buildToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Build the navigation drawer
    public void buildDrawer() {
        drawerLayout= findViewById(R.id.drawer_layout);
        Set<Integer> topLevelDestinations = new ArraySet<>();

        // Add top level destinations
        topLevelDestinations.add(R.id.fish);
        topLevelDestinations.add(R.id.insect);
        topLevelDestinations.add(R.id.fossil);
        topLevelDestinations.add(R.id.favorites);

        appBarConfig = new AppBarConfiguration.Builder(topLevelDestinations).setOpenableLayout(drawerLayout).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        NavigationUI.setupWithNavController(this.<NavigationView>findViewById(R.id.navigation_view), navController);
    }
}