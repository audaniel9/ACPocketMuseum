package com.daniel.chat.acpocketmuseum.Fossil;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FossilAdapter extends RecyclerView.Adapter<FossilAdapter.ViewHolder> implements Filterable {
    private List<Fossil> fossilList;
    private List<Fossil> fossilListFull;
    private List<Fossil> fossilListStatic;
    private OnItemClickListener itemListener;

    private static final String saveButtonPrefs = "saveFossilPrefs";
    private static final String favoriteButtonPrefs = "favoriteFossilPrefs";
    private static final String prefButtonIdPrefix = "fossilId";

    // Click listener interface
    public interface OnItemClickListener {
        void onCardItemClick(int position);
        void onSaveButtonClick(long id, ToggleButton saveButton);
        void onFavoriteButtonClick(long id, ToggleButton favoriteButton, int position);
    }

    // Adapter constructor
    public FossilAdapter(List<Fossil> fossilList) {
        this.fossilList = fossilList;
        this.fossilListFull = new ArrayList<>(fossilList);
        this.fossilListStatic = new ArrayList<>(fossilList);
    }

    // Update the UI when observer has changes
    public void setResults(List<Fossil> results) {
        this.fossilList = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_specimen_card, parent, false);
        return new ViewHolder(view, itemListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(fossilList.get(position).getName());
        holder.priceTextView.setText("Price: " + fossilList.get(position).getPrice() + " bells");
        holder.locationTextView.setText("");    // Placeholder
        holder.timesTextView.setText("");   // Placeholder

        loadDataSaveButton(holder);
        loadDataFavoriteButton(holder);
    }

    @Override
    public int getItemCount() {
        return fossilList.size();
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
        return fossilList.get(position).getId();
    }

    @Override
    public Filter getFilter() {
        return fossilFilter;
    }

    // Filter logic
    private Filter fossilFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Fossil> fossilListFiltered = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                fossilListFiltered.addAll(fossilListFull);
            }
            else {
                String searchString = charSequence.toString().toLowerCase().trim();

                for(Fossil fossil : fossilListFull) {
                    if(fossil.getName().toLowerCase().startsWith(searchString)) {
                        fossilListFiltered.add(fossil);
                    }
                }
            }

            if(charSequence == "@sortDefault") {
                fossilListFiltered.addAll(fossilListStatic);
            }
            else if(charSequence == "@sortAZ") {
                Collections.sort(fossilListFull, Fossil.MuseumSpecimenSortAscending);
                fossilListFiltered.addAll(fossilListFull);
            }
            else if(charSequence == "@sortZA") {
                Collections.sort(fossilListFull, Fossil.MuseumSpecimenSortDescending);
                fossilListFiltered.addAll(fossilListFull);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = fossilListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            fossilList.clear();
            fossilList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Method
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView priceTextView;
        public TextView locationTextView;   // Placeholder
        public TextView timesTextView;  // Placeholder
        public ToggleButton saveButton;
        public ToggleButton favoriteButton;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.specimenName);
            priceTextView = itemView.findViewById(R.id.price);
            locationTextView = itemView.findViewById(R.id.location);    // Placeholder
            timesTextView = itemView.findViewById(R.id.times);  // Placeholder
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
