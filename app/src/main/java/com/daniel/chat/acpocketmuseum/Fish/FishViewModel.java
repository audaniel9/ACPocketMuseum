package com.daniel.chat.acpocketmuseum.Fish;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.daniel.chat.acpocketmuseum.DataRepository;

import java.util.List;

public class FishViewModel extends AndroidViewModel {

    public FishViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Fish> getFishList() {
        DataRepository repository = new DataRepository(getApplication());

        return repository.getFishList();
    }
}
