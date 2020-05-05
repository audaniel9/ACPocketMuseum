package com.daniel.chat.acpocketmuseum.Insect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.daniel.chat.acpocketmuseum.R;

public class InsectDetailsFragment extends Fragment {
    private static final String INSECT = "insect";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insect_details, container, false);

        setHasOptionsMenu(true);

        assert getArguments() != null;
        Insect insect = getArguments().getParcelable(INSECT);

        assert insect != null;
        String nameLine = insect.getName();
        String priceLine = "Price: " + insect.getPrice();
        String locationLine = "Location: " + insect.getLocation();
        String timesLine = "Times: " + (insect.getTimes().equals("") ? "All day" : insect.getTimes());
        String rarityLine = "Rarity: " + insect.getRarity();
        String monthsNorthernLine = "Months(Northern): " + (insect.getMonthsNorthern().equals("") ? "All Year" : insect.getMonthsNorthern());
        String monthsSouthernLine = "Months(Southern): " + (insect.getMonthsSouthern().equals("") ? "All Year" : insect.getMonthsSouthern());
        String catchphraseLine = "\"" + insect.getCatchphrase() + "\"";
        String museumPhraseLine = insect.getMuseumPhrase();
        String priceFlickLine = "Price(Flick): " + insect.getPriceFlick() + " bells";

        TextView detailsName = rootView.findViewById(R.id.insectDetailsName);
        TextView detailsPrice = rootView.findViewById(R.id.insectDetailsPrice);
        TextView detailsLocation = rootView.findViewById(R.id.insectDetailsLocation);
        TextView detailsTimes = rootView.findViewById(R.id.insectDetailsTimes);
        TextView detailsRarity = rootView.findViewById(R.id.insectDetailsRarity);
        TextView detailsMonthsNorthern = rootView.findViewById(R.id.insectDetailsMonthsNorthern);
        TextView detailsMonthsSouthern = rootView.findViewById(R.id.insectDetailsMonthsSouthern);
        TextView detailsCatchphrase = rootView.findViewById(R.id.insectDetailsCatchphrase);
        TextView detailsMuseumPhrase = rootView.findViewById(R.id.insectDetailsMuseumPhrase);
        TextView detailsPriceFlick = rootView.findViewById(R.id.insectDetailsPriceFlick);

        detailsName.setText(nameLine);
        detailsPrice.setText(priceLine);
        detailsLocation.setText(locationLine);
        detailsTimes.setText(timesLine);
        detailsRarity.setText(rarityLine);
        detailsMonthsNorthern.setText(monthsNorthernLine);
        detailsMonthsSouthern.setText(monthsSouthernLine);
        detailsCatchphrase.setText(catchphraseLine);
        detailsMuseumPhrase.setText(museumPhraseLine);
        detailsPriceFlick.setText(priceFlickLine);

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
