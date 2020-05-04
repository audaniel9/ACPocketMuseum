package com.daniel.chat.acpocketmuseum.Fossil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.daniel.chat.acpocketmuseum.R;

import java.util.Objects;

public class FossilDetailsFragment extends Fragment {
    private static final String FOSSIL = "fossil";

    // Interface to communicate data from parent fragment -> this fragment
    public static FossilDetailsFragment newInstance(Fossil fossil) {
        FossilDetailsFragment fragment = new FossilDetailsFragment();
        Bundle args = new Bundle();

        args.putParcelable(FOSSIL, fossil);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_fossil_details, container, false);

        // Set toolbar title
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Details");
       setHasOptionsMenu(true);

        assert getArguments() != null;
        Fossil fossil = getArguments().getParcelable(FOSSIL);

        assert fossil!= null;
        String nameLine = fossil.getName();
        String priceLine = "Price: " + fossil.getPrice() + " bells";
        String museumPhrase = fossil.getMuseumPhrase();

        TextView detailsName = rootView.findViewById(R.id.fossilDetailsName);
        TextView detailsPrice = rootView.findViewById(R.id.fossilDetailsPrice);
        TextView detailsMuseumPhrase = rootView.findViewById(R.id.fossilDetailsMuseumPhrase);

        detailsName.setText(nameLine);
        detailsPrice.setText(priceLine);
        detailsMuseumPhrase.setText(museumPhrase);

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
