package com.daniel.chat.acpocketmuseum.Favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.Fossil.Fossil;
import com.daniel.chat.acpocketmuseum.Insect.Insect;
import com.daniel.chat.acpocketmuseum.MuseumSharedViewModel;
import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.daniel.chat.acpocketmuseum.R;

import java.util.List;
import java.util.Objects;

public class FavoriteFragment extends Fragment {
    private MuseumSharedViewModel museumSharedViewModel;
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

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
        RecyclerView favoriteRecyclerView = rootView.findViewById(R.id.favoriteRecyclerView);
        final List<MuseumSpecimen> favoriteList = museumSharedViewModel.getFavoriteList().getValue();

        favoriteRecyclerView.setHasFixedSize(true);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // change this to implement grid view i think?

        adapter = new FavoriteAdapter();
        adapter.setHasStableIds(true);

        adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onCardItemClick(int position) {
                assert favoriteList != null;
                switch(favoriteList.get(position).getType()) {
                    case "Fish":
                        NavDirections fishAction =
                                FavoriteFragmentDirections.actionFavoritesToFishDetailsFragment((Fish) favoriteList.get(position));
                        Navigation.findNavController(rootView).navigate(fishAction);
                        break;
                    case "Insect":
                        NavDirections insectAction =
                                FavoriteFragmentDirections.actionFavoritesToInsectDetailsFragment((Insect) favoriteList.get(position));
                        Navigation.findNavController(rootView).navigate(insectAction);
                        break;
                    case "Fossil":
                        NavDirections fossilAction =
                                FavoriteFragmentDirections.actionFavoritesToFossilDetailsFragment((Fossil) favoriteList.get(position));
                        Navigation.findNavController(rootView).navigate(fossilAction);
                        break;
                }
            }
        });

        favoriteRecyclerView.setAdapter(adapter);

        // Listener to favorite list to set list
        museumSharedViewModel.getFavoriteList().observe(getViewLifecycleOwner(), new Observer<List<MuseumSpecimen>>() {
            @Override
            public void onChanged(List<MuseumSpecimen> museumSpecimenList) {
                adapter.setFavoriteListInAdapter(museumSpecimenList);
            }
        });
    }

}
