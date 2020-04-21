package com.daniel.chat.acpocketmuseum.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.chat.acpocketmuseum.R;
import com.daniel.chat.acpocketmuseum.adapter.MuseumSpecimenAdapter;
import com.daniel.chat.acpocketmuseum.model.MuseumSpecimen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MuseumFragment extends Fragment {
    private RecyclerView recyclerView;
    private MuseumSpecimenAdapter adapter;
    private static final String MUSEUM_SPECIMEN_LIST = "museum_specimen_list";
    private static final String MUSEUM_SPECIMEN_LIST_FISH_ONLY = "museum_specimen_list_fish_only";
    private static final String MUSEUM_SPECIMEN_LIST_INSECT_ONLY = "museum_specimen_list_insect_only";
    private List<MuseumSpecimen> museumSpecimenList;
    private List<MuseumSpecimen> museumSpecimenListFishOnly;
    private List<MuseumSpecimen> museumSpecimenListInsectOnly;
    private MuseumFragmentDataPasser dataPasser;

    // Interface to communicate data from this fragment -> main activity
    public interface MuseumFragmentDataPasser {
        void adapterDataPass(MuseumSpecimenAdapter adapter);
    }

    // Interface to communicate data from main activity -> this fragment
    public static MuseumFragment newInstance(List<MuseumSpecimen> museumSpecimenList, List<MuseumSpecimen> museumSpecimenListFishOnly, List<MuseumSpecimen> museumSpecimenListInsectOnly) {
        MuseumFragment fragment = new MuseumFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(MUSEUM_SPECIMEN_LIST, (ArrayList) museumSpecimenList);
        args.putParcelableArrayList(MUSEUM_SPECIMEN_LIST_FISH_ONLY, (ArrayList) museumSpecimenListFishOnly);
        args.putParcelableArrayList(MUSEUM_SPECIMEN_LIST_INSECT_ONLY, (ArrayList) museumSpecimenListInsectOnly);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_museum, container, false);

        // Set toolbar title
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Museum");

        buildRecyclerView(rootView);
        dataPasser.adapterDataPass(adapter);


        return rootView;
    }

    // Related to passing data to activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (MuseumFragmentDataPasser) context;
    }

    // Build the recycler view
    public void buildRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // change this to implement grid view i think?

        assert getArguments() != null;
        museumSpecimenList = getArguments().getParcelableArrayList(MUSEUM_SPECIMEN_LIST);
        museumSpecimenListFishOnly = getArguments().getParcelableArrayList(MUSEUM_SPECIMEN_LIST_FISH_ONLY);
        museumSpecimenListInsectOnly = getArguments().getParcelableArrayList(MUSEUM_SPECIMEN_LIST_INSECT_ONLY);

        adapter = new MuseumSpecimenAdapter(museumSpecimenList, museumSpecimenListFishOnly, museumSpecimenListInsectOnly);
        adapter.setHasStableIds(true);

        adapter.setOnItemClickListener(new MuseumSpecimenAdapter.OnItemClickListener() {
            @Override
            public void onCardItemClick(int position) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, R.anim.fade_out)
                        .replace(R.id.fragment_container, InfoFragment.newInstance(museumSpecimenList.get(position)))
                        .addToBackStack(null).commit();
            }

            @Override
            public void onSaveButtonClick(long id, ToggleButton saveButton) {
                if(saveButton.isChecked()) {
                    saveButton.setBackgroundColor(ContextCompat.getColor(saveButton.getContext(), R.color.saveButtonStateOn));
                    adapter.saveData(id, saveButton);
                }
                else {
                    saveButton.setBackgroundColor(ContextCompat.getColor(saveButton.getContext(), R.color.saveButtonStateOff));
                    adapter.saveData(id, saveButton);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

}
