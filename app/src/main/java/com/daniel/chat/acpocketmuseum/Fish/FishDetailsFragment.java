package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
        String priceLine = fish.getPrice();
        String locationLine = fish.getLocation();
        String timesLine = fish.getTimes();

        TextView detailsName = rootView.findViewById(R.id.fishDetailsName);
        TextView detailsPrice = rootView.findViewById(R.id.fishDetailsPrice);
        TextView detailsLocation = rootView.findViewById(R.id.fishDetailsLocation);
        TextView detailsTimes = rootView.findViewById(R.id.fishDetailsTimes);

        detailsName.setText(nameLine);
        detailsPrice.setText(priceLine);
        detailsLocation.setText(locationLine);
        detailsTimes.setText(timesLine);

        return rootView;
    }


}
