package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.MuseumSharedViewModel;
import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.daniel.chat.acpocketmuseum.R;

import java.util.List;

public class FishFragment extends Fragment {
    private MuseumSharedViewModel museumSharedViewModel;
    private FishAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fish, container, false);

        museumSharedViewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);   // Assign view model
        museumSharedViewModel.getFishResponse(); // Initiate API call

        museumSharedViewModel.getFishList().observe(getViewLifecycleOwner(), new Observer<List<Fish>>() {
            @Override
            public void onChanged(List<Fish> list) {
                adapter.setResults(list);
                Toast.makeText(getContext(), "Loaded fish", Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);    // Set toolbar menus

        buildRecyclerView(rootView);

        return rootView;
    }

    // Dynamically set the menu options
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        // Search menu logic
        final SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();

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

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sortByDefault:
                adapter.getFilter().filter("@sortDefault");
                item.setChecked(true);
                return true;
            case R.id.sortByAZ:
                adapter.getFilter().filter("@sortAZ");
                item.setChecked(true);
                return true;
            case R.id.sortByZA:
                adapter.getFilter().filter("@sortZA");
                item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Build the recycler view
    private void buildRecyclerView(final View rootView) {
        RecyclerView fishRecyclerView = rootView.findViewById(R.id.fishRecyclerView);
        final List<Fish> fishList = museumSharedViewModel.getFishList().getValue();

        fishRecyclerView.setHasFixedSize(true);
        fishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // change this to implement grid view i think?

        adapter = new FishAdapter();
        adapter.setHasStableIds(true);

        adapter.setOnItemClickListener(new FishAdapter.OnItemClickListener() {
            @Override
            public void onCardItemClick(int position) {
                NavDirections action =
                        FishFragmentDirections.actionFishFragmentToFishDetailsFragment(fishList.get(position));
                Navigation.findNavController(rootView).navigate(action);
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
                final MuseumSpecimen passFishToFavorite = fishList.get(position);

                if(favoriteButton.isChecked()) {
                    adapter.saveDataFavoriteButton(id, favoriteButton);
                    museumSharedViewModel.addFavoriteSpecimen(passFishToFavorite);
                }
                else {
                    adapter.saveDataFavoriteButton(id, favoriteButton);
                    museumSharedViewModel.removeFavoriteSpecimen(passFishToFavorite);
                }
            }
        });

        fishRecyclerView.setAdapter(adapter);
    }

}
