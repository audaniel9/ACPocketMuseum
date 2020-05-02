package com.daniel.chat.acpocketmuseum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.Fossil.Fossil;
import com.daniel.chat.acpocketmuseum.Insect.Insect;

import java.util.ArrayList;
import java.util.List;

public class MuseumSharedViewModel extends AndroidViewModel {
    private DataRepository repository;
    private List<Fish> fishList;
    private List<Insect> insectList;
    private List<Fossil> fossilList;
    private MutableLiveData<List<MuseumSpecimen>> favoriteList;

    public MuseumSharedViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        fishList = repository.getFishListFromRepo();
        insectList = repository.getInsectListFromRepo();
        fossilList = repository.getFossilListFromRepo();
        favoriteList = new MutableLiveData<>();
        favoriteList.setValue(new ArrayList<MuseumSpecimen>());
    }

    public List<Fish> getFishList() {
        return fishList;
    }

    public List<Insect> getInsectList() {
        return insectList;
    }

    public List<Fossil> getFossilList() {
        return fossilList;
    }

    public LiveData<List<MuseumSpecimen>> getFavoriteList() {
        return favoriteList;
    }

    public void addFavoriteSpecimen(MuseumSpecimen specimen) {
        List<MuseumSpecimen> list = favoriteList.getValue();
        assert list != null;
        if(!list.contains(specimen)) {
            list.add(specimen);
        }

        favoriteList.setValue(list);
    }

    public void removeFavoriteSpecimen(MuseumSpecimen specimen) {
        List<MuseumSpecimen> list = favoriteList.getValue();
        assert list != null;
        list.remove(specimen);

        favoriteList.setValue(list);
    }
}