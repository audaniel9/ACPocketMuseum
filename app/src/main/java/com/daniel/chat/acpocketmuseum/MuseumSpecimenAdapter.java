package com.daniel.chat.acpocketmuseum;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MuseumSpecimenAdapter extends RecyclerView.Adapter<MuseumSpecimenAdapter.ViewHolder> implements Filterable {
    private List<MuseumSpecimen> museumSpecimenList;
    private List<MuseumSpecimen> museumSpecimenListFishOnly;
    private List<MuseumSpecimen> museumSpecimenListInsectOnly;
    private List<MuseumSpecimen> museumSpecimenListFull;
    private List<MuseumSpecimen> museumSpecimenListStatic;
    private OnItemClickListener itemListener;

    private static final String prefs = "prefs";
    private static final String prefSaveButtonState = "id";
    private boolean prefSaveButtonChecked;

    // Click listener interface
    public interface OnItemClickListener {
        void onCardItemClick(int position);
        void onSaveButtonClick(long id, ToggleButton saveButton);
    }

    // Adapter constructor
    public MuseumSpecimenAdapter(List<MuseumSpecimen> museumSpecimenList, List<MuseumSpecimen> museumSpecimenListFishOnly, List<MuseumSpecimen> museumSpecimenListInsectOnly) {
        this.museumSpecimenList = museumSpecimenList;
        this.museumSpecimenListFishOnly = museumSpecimenListFishOnly;
        this.museumSpecimenListInsectOnly = museumSpecimenListInsectOnly;
        this.museumSpecimenListFull = new ArrayList<>(museumSpecimenList);
        this.museumSpecimenListStatic = new ArrayList<>(museumSpecimenList);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_specimen_card, parent, false);
        return new ViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.specimenNameTextView.setText(museumSpecimenList.get(position).getSpecimenName());
        holder.locationTextView.setText(museumSpecimenList.get(position).getLocation());
        holder.priceTextView.setText(museumSpecimenList.get(position).getPrice());
        holder.timesTextView.setText(museumSpecimenList.get(position).getTimes());

        loadData(holder);
    }

    // Load data through SharedPreferences
    public void loadData(ViewHolder holder) {
        SharedPreferences sharedPreferences = holder.saveButton.getContext().getSharedPreferences(prefs, Context.MODE_PRIVATE);
        prefSaveButtonChecked = sharedPreferences.getBoolean(prefSaveButtonState + holder.getItemId(), false);

        // Update view
        holder.saveButton.setChecked(prefSaveButtonChecked);
    }

    // Save data through SharedPreferences
    public void saveData(long id, ToggleButton saveButton) {
        SharedPreferences sharedPreferences = saveButton.getContext().getSharedPreferences(prefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(prefSaveButtonState + id, saveButton.isChecked());
        editor.apply();

        Toast.makeText(saveButton.getContext(), prefSaveButtonState + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public long getItemId(int position) {
        return museumSpecimenList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return museumSpecimenList.size();
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    // Filter logic
    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MuseumSpecimen> museumSpecimenListFiltered = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                museumSpecimenListFiltered.addAll(museumSpecimenListFull);
            }
            else {
                String searchString = charSequence.toString().toLowerCase().trim();

                for(MuseumSpecimen specimen : museumSpecimenListFull) {
                    if(specimen.getSpecimenName().toLowerCase().startsWith(searchString)) {
                        museumSpecimenListFiltered.add(specimen);
                    }
                }
            }

            if(charSequence == "@sortDefault") {
                museumSpecimenListFiltered.addAll(museumSpecimenListStatic);
            }
            else if(charSequence == "@sortAZ") {
                Collections.sort(museumSpecimenListFull, MuseumSpecimen.MuseumSpecimenSortAscending);
                museumSpecimenListFiltered.addAll(museumSpecimenListFull);
            }
            else if(charSequence == "@sortZA") {
                Collections.sort(museumSpecimenListFull, MuseumSpecimen.MuseumSpecimenSortDescending);
                museumSpecimenListFiltered.addAll(museumSpecimenListFull);
            }
            else if(charSequence == "@filterFish") {
                museumSpecimenListFiltered.addAll(museumSpecimenListFishOnly);
            }
            else if(charSequence == "@filterInsect") {
                museumSpecimenListFiltered.addAll(museumSpecimenListInsectOnly);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = museumSpecimenListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            museumSpecimenList.clear();
            museumSpecimenList.addAll((List) filterResults.values);
            System.out.println(museumSpecimenListStatic);
            notifyDataSetChanged();
        }
    };

    // Method
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView specimenNameTextView;
        public TextView locationTextView;
        public TextView priceTextView;
        public TextView timesTextView;
        public ToggleButton saveButton;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            specimenNameTextView = itemView.findViewById(R.id.specimenName);
            locationTextView = itemView.findViewById(R.id.location);
            priceTextView = itemView.findViewById(R.id.price);
            timesTextView = itemView.findViewById(R.id.times);
            saveButton = itemView.findViewById(R.id.saveButton);

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
        }
    }
}
