package com.daniel.chat.acpocketmuseum.Insect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.R;

import java.util.Objects;

public class InsectDetailsFragment extends Fragment {
    private static final String INSECT = "insect";

    // Interface to communicate data from parent fragment -> this fragment
    public static InsectDetailsFragment newInstance(Insect insect) {
        InsectDetailsFragment fragment = new InsectDetailsFragment();
        Bundle args = new Bundle();

        args.putParcelable(INSECT, insect);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insect_details, container, false);

        // Set toolbar title
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Details");
        setHasOptionsMenu(true);

        assert getArguments() != null;
        Insect insect = getArguments().getParcelable(INSECT);

        assert insect != null;
        String nameLine = insect.getName();
        String priceLine = insect.getPrice();
        String locationLine = insect.getLocation();
        String timesLine = insect.getTimes();

        TextView detailsName = rootView.findViewById(R.id.insectDetailsName);
        TextView detailsPrice = rootView.findViewById(R.id.insectDetailsPrice);
        TextView detailsLocation = rootView.findViewById(R.id.insectDetailsLocation);
        TextView detailsTimes = rootView.findViewById(R.id.insectDetailsTimes);

        detailsName.setText(nameLine);
        detailsPrice.setText(priceLine);
        detailsLocation.setText(locationLine);
        detailsTimes.setText(timesLine);

        return rootView;
    }


}
