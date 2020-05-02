package com.daniel.chat.acpocketmuseum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.Fossil.Fossil;
import com.daniel.chat.acpocketmuseum.Insect.Insect;

import java.util.List;

public class MuseumSharedViewModel extends AndroidViewModel {
    private DataRepository repository;

    public MuseumSharedViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public List<Fish> getFishList() {
        return repository.getFishListFromRepo();
    }

    public List<Insect> getInsectList() {
        return repository.getInsectListFromRepo();
    }

    public List<Fossil> getFossilList() {
        return repository.getFossilListFromRepo();
    }

    public LiveData<List<MuseumSpecimen>> getFavoriteList() {
        return repository.getFavoriteListFromRepo();
    }

    public void addFavoriteSpecimen(MuseumSpecimen specimen) {
        repository.addFavoriteSpecimenFromRepo(specimen);
    }

    public void removeFavoriteSpecimen(MuseumSpecimen specimen) {
        repository.removeFavoriteSpecimenFromRepo(specimen);
    }


}