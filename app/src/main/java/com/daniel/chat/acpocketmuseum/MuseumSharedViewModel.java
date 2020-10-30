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
    private MutableLiveData<List<Fish>> fishList;
    private MutableLiveData<List<Insect>> insectList;
    private MutableLiveData<List<Fossil>> fossilList;
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

    public void getFishResponse() {
        repository.getFishResponse();
    }

    public void getInsectsResponse() {
        repository.getInsectsResponse();
    }

    public void getFossilsResponse() {
        repository.getFossilsResponse();
    }

    public MutableLiveData<List<Fish>> getFishList() {
        return fishList;
    }

    public MutableLiveData<List<Insect>> getInsectList() {
        return insectList;
    }

    public MutableLiveData<List<Fossil>> getFossilList() {
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