package com.daniel.chat.acpocketmuseum.Favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.daniel.chat.acpocketmuseum.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> implements Filterable {
    private List<MuseumSpecimen> favoriteList;
    private List<MuseumSpecimen> favoriteListFull;
    private List<MuseumSpecimen> favoriteListStatic;

    // This method will act as a pseudo constructor; sets data from observer
    public void setFavoriteListInAdapter(List<MuseumSpecimen> favoriteList) {
        this.favoriteList = favoriteList;
        this.favoriteListFull = new ArrayList<>(favoriteList);
        this.favoriteListStatic = new ArrayList<>(favoriteList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(favoriteList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    @Override
    public Filter getFilter() {
        return favoriteFilter;
    }

    // Filter logic
    private Filter favoriteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MuseumSpecimen> favoriteListFiltered = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                favoriteListFiltered.addAll(favoriteListFull);
            }
            else if(charSequence == "@sortDefault") {
                favoriteListFiltered.addAll(favoriteListStatic);
            }
            else if(charSequence == "@sortAZ") {
                Collections.sort(favoriteListFull, MuseumSpecimen.MuseumSpecimenSortAscending);
                favoriteListFiltered.addAll(favoriteListFull);
            }
            else if(charSequence == "@sortZA") {
                Collections.sort(favoriteListFull, MuseumSpecimen.MuseumSpecimenSortDescending);
                favoriteListFiltered.addAll(favoriteListFull);
            }
            else {
                String searchString = charSequence.toString().toLowerCase().trim();

                for (MuseumSpecimen specimen : favoriteListFull) {
                    if(specimen.getName().toLowerCase().startsWith(searchString)) {
                        favoriteListFiltered.add(specimen);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = favoriteListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            favoriteList.clear();
            favoriteList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.favoriteName);
        }
    }
}
