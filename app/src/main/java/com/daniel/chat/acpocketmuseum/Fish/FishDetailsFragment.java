package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daniel.chat.acpocketmuseum.R;

import java.util.Objects;

public class FishDetailsFragment extends Fragment {
    private static final String FISH = "fish";

    // Interface to communicate data from parent fragment -> this fragment
    public static FishDetailsFragment newInstance(Fish fish) {
        FishDetailsFragment fragment = new FishDetailsFragment();
        Bundle args = new Bundle();

        args.putParcelable(FISH, fish);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fish_details, container, false);

        // Set toolbar title
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Details");
        setHasOptionsMenu(true);

        assert getArguments() != null;
        Fish fish = getArguments().getParcelable(FISH);

        assert fish != null;
        String nameLine = fish.getName();
        String priceLine = "Price: " + fish.getPrice();
        String locationLine = "Location: " + fish.getLocation();
        String timesLine = "Times: " + (fish.getTimes().equals("") ? "All day" : fish.getTimes());
        String rarityLine = "Rarity: " + fish.getRarity();
        String monthsNorthernLine = "Months(Northern): " + (fish.getMonthsNorthern().equals("") ? "All Year" : fish.getMonthsNorthern());
        String monthsSouthernLine = "Months(Southern): " + (fish.getMonthsSouthern().equals("") ? "All Year" : fish.getMonthsSouthern());
        String catchphraseLine = "\"" + fish.getCatchphrase() + "\"";
        String museumPhraseLine = fish.getMuseumPhrase();
        String priceCJLine = "Price(CJ): " + fish.getPriceCJ() + " bells";
        String shadowLine = "Shadow: " + fish.getShadow();

        TextView detailsName = rootView.findViewById(R.id.fishDetailsName);
        TextView detailsPrice = rootView.findViewById(R.id.fishDetailsPrice);
        TextView detailsLocation = rootView.findViewById(R.id.fishDetailsLocation);
        TextView detailsTimes = rootView.findViewById(R.id.fishDetailsTimes);
        TextView detailsRarity = rootView.findViewById(R.id.fishDetailsRarity);
        TextView detailsMonthsNorthern = rootView.findViewById(R.id.fishDetailsMonthsNorthern);
        TextView detailsMonthsSouthern = rootView.findViewById(R.id.fishDetailsMonthsSouthern);
        TextView detailsCatchphrase = rootView.findViewById(R.id.fishDetailsCatchphrase);
        TextView detailsMuseumPhrase = rootView.findViewById(R.id.fishDetailsMuseumPhrase);
        TextView detailsPriceCJ = rootView.findViewById(R.id.fishDetailsPriceCJ);
        TextView detailsShadow = rootView.findViewById(R.id.fishDetailsShadow);

        detailsName.setText(nameLine);
        detailsPrice.setText(priceLine);
        detailsLocation.setText(locationLine);
        detailsTimes.setText(timesLine);
        detailsRarity.setText(rarityLine);
        detailsMonthsNorthern.setText(monthsNorthernLine);
        detailsMonthsSouthern.setText(monthsSouthernLine);
        detailsCatchphrase.setText(catchphraseLine);
        detailsMuseumPhrase.setText(museumPhraseLine);
        detailsPriceCJ.setText(priceCJLine);
        detailsShadow.setText(shadowLine);

        return rootView;
    }

    // Dynamically set the menu options
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.searchMenu).setVisible(false);
        menu.findItem(R.id.sortMenu).setVisible(false);

        super.onPrepareOptionsMenu(menu);
    }
}
