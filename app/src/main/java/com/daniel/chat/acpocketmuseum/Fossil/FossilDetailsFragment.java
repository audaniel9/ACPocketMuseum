package com.daniel.chat.acpocketmuseum.Fossil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.daniel.chat.acpocketmuseum.R;

public class FossilDetailsFragment extends Fragment {
    private static final String FOSSIL = "fossil";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_fossil_details, container, false);

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
