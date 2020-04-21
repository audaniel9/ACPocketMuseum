package com.daniel.chat.acpocketmuseum.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daniel.chat.acpocketmuseum.R;
import com.daniel.chat.acpocketmuseum.model.MuseumSpecimen;

import java.util.Objects;

public class InfoFragment extends Fragment {
    private static final String MUSEUM_SPECIMEN = "museum_specimen";

    // Interface to communicate data from main activity -> this fragment
    public static InfoFragment newInstance(MuseumSpecimen museumSpecimen) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();

        args.putParcelable(MUSEUM_SPECIMEN, museumSpecimen);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        // Set toolbar title
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Information");
        setHasOptionsMenu(true);

        assert getArguments() != null;
        MuseumSpecimen museumSpecimen = getArguments().getParcelable(MUSEUM_SPECIMEN);

        assert museumSpecimen != null;
        String nameLine = museumSpecimen.getSpecimenName();
        String priceLine = museumSpecimen.getPrice();
        String locationLine = museumSpecimen.getLocation();
        String timesLine = museumSpecimen.getTimes();

        TextView infoSpecimenName = rootView.findViewById(R.id.infoSpecimenName);
        TextView infoPrice = rootView.findViewById(R.id.infoPrice);
        TextView infoLocation = rootView.findViewById(R.id.infoLocation);
        TextView infoTimes = rootView.findViewById(R.id.infoTimes);

        infoSpecimenName.setText(nameLine);
        infoPrice.setText(priceLine);
        infoLocation.setText(locationLine);
        infoTimes.setText(timesLine);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();

        super.onCreateOptionsMenu(menu, inflater);
    }
}
