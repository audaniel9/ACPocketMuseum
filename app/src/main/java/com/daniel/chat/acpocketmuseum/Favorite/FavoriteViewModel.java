package com.daniel.chat.acpocketmuseum.Favorite;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.daniel.chat.acpocketmuseum.DataRepository;
import com.daniel.chat.acpocketmuseum.MuseumSpecimen;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private DataRepository repository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public LiveData<List<MuseumSpecimen>> getFavoriteList()  {
        Log.d("GetFromVM", repository.getFavoriteListFromRepo().toString());
        return repository.getFavoriteListFromRepo();
    }
}
