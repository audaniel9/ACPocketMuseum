package com.daniel.chat.acpocketmuseum.Fossil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.MuseumSharedViewModel;
import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.daniel.chat.acpocketmuseum.R;

import java.util.List;

public class FossilFragment extends Fragment {
    private MuseumSharedViewModel museumSharedViewModel;
    private FossilAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fossil, container, false);

        museumSharedViewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);  // Assign view model

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
        RecyclerView fossilRecyclerView = rootView.findViewById(R.id.fossilRecyclerView);
        final List<Fossil> fossilList = museumSharedViewModel.getFossilList();

        fossilRecyclerView.setHasFixedSize(true);
        fossilRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // change this to implement grid view i think?

        adapter = new FossilAdapter(fossilList);
        adapter.setHasStableIds(true);

        adapter.setOnItemClickListener(new FossilAdapter.OnItemClickListener() {
            @Override
            public void onCardItemClick(int position) {
                NavDirections action =
                        FossilFragmentDirections.actionFossilFragmentToFossilDetailsFragment(fossilList.get(position));
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
            public void onFavoriteButtonClick(long id, ToggleButton favoriteButton, int position) {
                final MuseumSpecimen passFossilToFavorite = fossilList.get(position);

                if(favoriteButton.isChecked()) {
                    adapter.saveDataFavoriteButton(id, favoriteButton);
                    museumSharedViewModel.addFavoriteSpecimen(passFossilToFavorite);
                }
                else {
                    adapter.saveDataFavoriteButton(id, favoriteButton);
                    museumSharedViewModel.removeFavoriteSpecimen(passFossilToFavorite);
                }
            }
        });

        fossilRecyclerView.setAdapter(adapter);
    }
}
