package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.daniel.chat.acpocketmuseum.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FishFragment extends Fragment {
    private FishViewModel fishViewModel;
    private FishAdapter adapter;
    private MenuItem prevMenuItem;  // For toolbar menu use

    // Interface to communicate data from main activity -> this fragment
    public static FishFragment newInstance() {
        return new FishFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fish, container, false);

        fishViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FishViewModel.class);   // Assign view model

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Fish"); // Set toolbar title

        setHasOptionsMenu(true);    // Set toolbar menus

        buildRecyclerView(rootView);

        return rootView;
    }

    // Toolbar menu logic
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.sort_menu, menu);
        inflater.inflate(R.menu.filter_menu, menu);

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

        super.onCreateOptionsMenu(menu,inflater);
    }

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
        RecyclerView fishRecyclerView = rootView.findViewById(R.id.fishRecyclerView);
        final List<Fish> fishList = fishViewModel.getFishList();

        fishRecyclerView.setHasFixedSize(true);
        fishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // change this to implement grid view i think?

        adapter = new FishAdapter(fishList);
        adapter.setHasStableIds(true);

        adapter.setOnItemClickListener(new FishAdapter.OnItemClickListener() {
            @Override
            public void onCardItemClick(int position) {
                getParentFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, R.anim.fade_out)
                        .replace(R.id.fragment_container, FishDetailsFragment.newInstance(fishList.get(position)))
                        .addToBackStack(null).commit();
            }

            @Override
            public void onSaveButtonClick(long id, ToggleButton saveButton) {
                if(saveButton.isChecked()) {
                    adapter.saveDataSaveButton(id, saveButton);
                }
                else {
                    adapter.saveDataSaveButton(id, saveButton);
                }
            }

            @Override
            public void onFavoriteButtonClick(long id, ToggleButton favoriteButton, final int position) {
                if(favoriteButton.isChecked()) {
                    adapter.saveDataFavoriteButton(id, favoriteButton);

                    List<MuseumSpecimen> fishToAdd = new ArrayList<>();
                    fishToAdd.add(fishList.get(position));  // May only result in fav having 1 thing
                    fishViewModel.setFavoriteList(fishToAdd);
                    Toast.makeText(getContext(), "buttonPressed", Toast.LENGTH_SHORT).show();
                }
                else {
                    adapter.saveDataFavoriteButton(id, favoriteButton);
                    //fishViewModel.removeFavoriteFish(fishList.get(position));
                }
            }
        });

        fishRecyclerView.setAdapter(adapter);
    }

}
