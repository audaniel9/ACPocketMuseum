package com.daniel.chat.acpocketmuseum.Favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.daniel.chat.acpocketmuseum.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class FavoriteFragment extends Fragment {
    private FavoriteViewModel favoriteViewModel;
    private FavoriteAdapter adapter;
    private MenuItem prevMenuItem;  // For toolbar menu use

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Assign view model
        favoriteViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FavoriteViewModel.class);

        // Set toolbar title
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Favorites");

        // Set toolbar menus
        setHasOptionsMenu(true);

        buildRecyclerView(rootView);

        return rootView;
    }

    // Toolbar menu logic
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.sort_menu, menu);
        inflater.inflate(R.menu.filter_menu, menu);
/*
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
*/
        super.onCreateOptionsMenu(menu,inflater);
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
*/
    // Performs menu check toggling
    private void toggleCheck(MenuItem item) {
        item.setChecked(true);

        if (prevMenuItem != null && prevMenuItem != item) {
            prevMenuItem.setChecked(false);
        }
        prevMenuItem = item;
    }

    // Build the recycler view
    private void buildRecyclerView(View rootView) {
        RecyclerView favoriteRecyclerView = rootView.findViewById(R.id.favoriteRecyclerView);
        //LiveData<List<MuseumSpecimen>> favoriteList;

        favoriteRecyclerView.setHasFixedSize(true);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // change this to implement grid view i think?

        adapter = new FavoriteAdapter();
        adapter.setHasStableIds(true);

        favoriteRecyclerView.setAdapter(adapter);

        // Listener to favorite list to set list
        favoriteViewModel.getFavoriteList().observe(getViewLifecycleOwner(), new Observer<List<MuseumSpecimen>>() {
            @Override
            public void onChanged(List<MuseumSpecimen> museumSpecimenList) {
                Toast.makeText(getContext(), "DataReceived", Toast.LENGTH_SHORT).show();
                adapter.setFavoriteListInAdapter(museumSpecimenList);
            }
        });

        //adapter.setFavoriteList(museumSpecimenList);
    }

}
