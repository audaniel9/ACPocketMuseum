package com.daniel.chat.acpocketmuseum.Insect;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.daniel.chat.acpocketmuseum.DataRepository;


import java.util.List;

public class InsectViewModel extends AndroidViewModel {
    private List<Insect> insectList;

    public InsectViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Insect> getInsectList() {
        DataRepository repository = new DataRepository(getApplication());

        return repository.getInsectList();
    }
}
