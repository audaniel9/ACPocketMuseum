package com.daniel.chat.acpocketmuseum.Fish;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.daniel.chat.acpocketmuseum.DataRepository;
import com.daniel.chat.acpocketmuseum.MuseumSpecimen;

import java.util.List;

public class FishViewModel extends AndroidViewModel {
    private DataRepository repository;
    private List<Fish> fishList;

    public FishViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        fishList = repository.getFishList();

    }

    public List<Fish> getFishList() {
        return fishList;
    }

    public LiveData<List<MuseumSpecimen>> getFavoriteList() {
        return repository.getFavoriteListFromRepo();
    }

    public void setFavoriteList(List<MuseumSpecimen> list) {
        repository.setFavoriteListFromRepo(list);
        Log.d("SET", list.toString());
    }
}