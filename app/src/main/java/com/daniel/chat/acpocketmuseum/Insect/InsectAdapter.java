package com.daniel.chat.acpocketmuseum.Insect;

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

import com.daniel.chat.acpocketmuseum.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsectAdapter extends RecyclerView.Adapter<InsectAdapter.ViewHolder> implements Filterable {
    private List<Insect> insectList;
    private List<Insect> insectListFull;
    private List<Insect> insectListStatic;
    private OnItemClickListener itemListener;

    private static final String saveButtonPrefs = "saveInsectPrefs";
    private static final String favoriteButtonPrefs = "favoriteInsectPrefs";
    private static final String prefButtonIdPrefix = "insectId";

    // Click listener interface
    public interface OnItemClickListener {
        void onCardItemClick(int position);
        void onSaveButtonClick(long id, ToggleButton saveButton);
        void onFavoriteButtonClick(long id, ToggleButton favoriteButton);
    }

    // Adapter constructor
    public InsectAdapter(List<Insect> insectList) {
        this.insectList = insectList;
        this.insectListFull = new ArrayList<>(insectList);
        this.insectListStatic = new ArrayList<>(insectList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_specimen_card, parent, false);
        return new ViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(insectList.get(position).getName());
        holder.locationTextView.setText(insectList.get(position).getLocation());
        holder.priceTextView.setText(insectList.get(position).getPrice());
        holder.timesTextView.setText(insectList.get(position).getTimes());

        loadDataSaveButton(holder);
        loadDataFavoriteButton(holder);
    }

    @Override
    public int getItemCount() {
        return insectList.size();
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

    @Override
    public long getItemId(int position) {
        return insectList.get(position).getId();
    }

    @Override
    public Filter getFilter() {
        return insectFilter;
    }

    // Filter logic
    private Filter insectFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Insect> insectListFiltered = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                insectListFiltered.addAll(insectListFull);
            }
            else {
                String searchString = charSequence.toString().toLowerCase().trim();

                for(Insect insect : insectListFull) {
                    if(insect.getName().toLowerCase().startsWith(searchString)) {
                        insectListFiltered.add(insect);
                    }
                }
            }

            if(charSequence == "@sortDefault") {
                insectListFiltered.addAll(insectListStatic);
            }
            else if(charSequence == "@sortAZ") {
                Collections.sort(insectListFull, Insect.InsectSortAscending);
                insectListFiltered.addAll(insectListFull);
            }
            else if(charSequence == "@sortZA") {
                Collections.sort(insectListFull, Insect.InsectSortDescending);
                insectListFiltered.addAll(insectListFull);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = insectListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            insectList.clear();
            insectList.addAll((List) filterResults.values);
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
            favoriteButton = itemView.findViewById((R.id.favoriteButton));

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
                        int position = getLayoutPosition();
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
                        int position = getLayoutPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onFavoriteButtonClick(id, favoriteButton);
                        }
                    }
                }
            });
        }
    }
}
