package com.daniel.chat.acpocketmuseum.Fish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.R;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.ViewHolder> implements Filterable {
    private List<Fish> fishList;
    private List<Fish> fishListFull;
    private List<Fish> fishListStatic;
    private OnItemClickListener itemListener;

    private static final String saveButtonPrefs = "saveFishPrefs";
    private static final String favoriteButtonPrefs = "favoriteFishPrefs";
    private static final String prefButtonIdPrefix = "fishId";

    // Click listener interface
    public interface OnItemClickListener {
        void onCardItemClick(int position);
        void onSaveButtonClick(long id, ToggleButton saveButton);
        void onFavoriteButtonClick(long id, ToggleButton favoriteButton, int position);
    }

    // Adapter constructor
    public FishAdapter(List<Fish> fishList) {
        this.fishList = fishList;
        this.fishListFull = new ArrayList<>(fishList);
        this.fishListStatic = new ArrayList<>(fishList);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_specimen_card, parent, false);
        return new ViewHolder(view, itemListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(fishList.get(position).getName());
        holder.locationTextView.setText("Location: " + fishList.get(position).getLocation());
        holder.priceTextView.setText("Price: " + fishList.get(position).getPrice());
        holder.timesTextView.setText("Times: " + (fishList.get(position).getTimes().equals("") ? "All day" : fishList.get(position).getTimes()));

        loadDataSaveButton(holder);
        loadDataFavoriteButton(holder);
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    // Load save button state through SharedPreferences
    public void loadDataSaveButton(ViewHolder holder) {
        SharedPreferences sharedPreferences = holder.saveButton.getContext().getSharedPreferences(saveButtonPrefs, Context.MODE_PRIVATE);
        boolean prefSaveButtonChecked = sharedPreferences.getBoolean(prefButtonIdPrefix + holder.getItemId(), false);

        // Update view
        holder.saveButton.setChecked(prefSaveButtonChecked);
    }

    // Load favorite button state through SharedPreferences
    public void loadDataFavoriteButton(ViewHolder holder) {
        SharedPreferences sharedPreferences = holder.favoriteButton.getContext().getSharedPreferences(favoriteButtonPrefs, Context.MODE_PRIVATE);
        boolean prefFavoriteButtonChecked = sharedPreferences.getBoolean(prefButtonIdPrefix + holder.getItemId(), false);

        // Update view
        holder.favoriteButton.setChecked((prefFavoriteButtonChecked));
    }

    // Save save button state through SharedPreferences
    public void saveDataSaveButton(long id, ToggleButton saveButton) {
        SharedPreferences sharedPreferences = saveButton.getContext().getSharedPreferences(saveButtonPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(prefButtonIdPrefix + id, saveButton.isChecked());
        editor.apply();
    }

    // Save favorite button state through SharedPreferences
    public void saveDataFavoriteButton(long id, ToggleButton favoriteButton) {
        SharedPreferences sharedPreferences = favoriteButton.getContext().getSharedPreferences(favoriteButtonPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(prefButtonIdPrefix + id, favoriteButton.isChecked());
        editor.apply();
    }

    // Related to the buttons
    @Override
    public long getItemId(int position) {
        return fishList.get(position).getId();
    }

    @Override
    public Filter getFilter() {
        return fishFilter;
    }

    // Filter logic
    private Filter fishFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Fish> fishListFiltered = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                fishListFiltered.addAll(fishListFull);
            }
            else {
                String searchString = charSequence.toString().toLowerCase().trim();

                for(Fish fish : fishListFull) {
                    if(fish.getName().toLowerCase().startsWith(searchString)) {
                        fishListFiltered.add(fish);
                    }
                }
            }

            if(charSequence == "@sortDefault") {
                fishListFiltered.addAll(fishListStatic);
            }
            else if(charSequence == "@sortAZ") {
                Collections.sort(fishListFull, Fish.MuseumSpecimenSortAscending);
                fishListFiltered.addAll(fishListFull);
            }
            else if(charSequence == "@sortZA") {
                Collections.sort(fishListFull, Fish.MuseumSpecimenSortDescending);
                fishListFiltered.addAll(fishListFull);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = fishListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            fishList.clear();
            fishList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Method
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView locationTextView;
        public TextView priceTextView;
        public TextView timesTextView;
        public ToggleButton saveButton;
        public ToggleButton favoriteButton;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.specimenName);
            locationTextView = itemView.findViewById(R.id.location);
            priceTextView = itemView.findViewById(R.id.price);
            timesTextView = itemView.findViewById(R.id.times);
            saveButton = itemView.findViewById(R.id.saveButton);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onCardItemClick(position);
                        }
                    }
                }
            });

            saveButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(listener != null) {
                        long id = getItemId();
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onSaveButtonClick(id, saveButton);
                        }
                    }
                }
            });

            favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(listener != null) {
                        long id = getItemId();
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onFavoriteButtonClick(id, favoriteButton, position);
                        }
                    }
                }
            });
        }
    }
}
