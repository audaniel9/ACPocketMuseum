package com.daniel.chat.acpocketmuseum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.daniel.chat.acpocketmuseum.model.MuseumSpecimen;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        buildToolbar();

        Intent intent = getIntent();
        MuseumSpecimen museumSpecimen = intent.getParcelableExtra("Main Info");

        String nameLine = museumSpecimen.getSpecimenName();
        String priceLine = museumSpecimen.getPrice();
        String locationLine = museumSpecimen.getLocation();
        String timesLine = museumSpecimen.getTimes();

        TextView infoSpecimenName = findViewById(R.id.infoSpecimenName);
        TextView infoPrice = findViewById(R.id.infoPrice);
        TextView infoLocation = findViewById(R.id.infoLocation);
        TextView infoTimes = findViewById(R.id.infoTimes);

        infoSpecimenName.setText(nameLine);
        infoPrice.setText(priceLine);
        infoLocation.setText(locationLine);
        infoTimes.setText(timesLine);
    }

    // Build the toolbar
    public void buildToolbar() {
        final Toolbar toolbar = findViewById(R.id.infoToolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
