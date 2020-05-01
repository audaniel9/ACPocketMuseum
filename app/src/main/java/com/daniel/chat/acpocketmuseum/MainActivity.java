package com.daniel.chat.acpocketmuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daniel.chat.acpocketmuseum.Favorite.FavoriteFragment;
import com.daniel.chat.acpocketmuseum.Fish.FishFragment;
import com.daniel.chat.acpocketmuseum.Insect.InsectFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private String currentFragment; // For fragment stack use

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildToolbar();
        buildDrawer();
        fragmentStackListener();
    }

    // Toolbar menu logic
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Pressing back button while navigation drawer is open won't close the activity home screen
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(currentFragment.equals("FishFragment") && !drawer.isDrawerOpen(GravityCompat.START)) {
            finish();   // End activity
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
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final NavigationView navigationView = findViewById(R.id.navigation_view);

        // Set museum fragment to start when activity starts
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container, FishFragment.newInstance())
                .addToBackStack(null).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.fish:
                        if(!currentFragment.equals("FishFragment")) {
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .replace(R.id.fragment_container, FishFragment.newInstance())
                                    .addToBackStack(null).commit();
                        }
                        break;
                    case R.id.insect:
                        if(!currentFragment.equals("InsectFragment")) {
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .replace(R.id.fragment_container, InsectFragment.newInstance())
                                    .addToBackStack(null).commit();
                        }
                        break;
                    case R.id.favorites:
                        if(!currentFragment.equals("FavoriteFragment")) {
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .replace(R.id.fragment_container, FavoriteFragment.newInstance())
                                    .addToBackStack(null).commit();
                        }
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
}