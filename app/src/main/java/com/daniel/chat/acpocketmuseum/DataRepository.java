package com.daniel.chat.acpocketmuseum;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.Fossil.Fossil;
import com.daniel.chat.acpocketmuseum.Insect.Insect;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {
    private Context context;
    private MutableLiveData<List<Fish>> fishList;
    private MutableLiveData<List<Insect>> insectList;
    private MutableLiveData<List<Fossil>> fossilList;
    private AcnhApi acnhApi;
    private final String BASE_URL = "https://acnhapi.com/";

    public DataRepository(Context context) {
        this.context = context;
        fishList = new MutableLiveData<>();
        insectList = new MutableLiveData<>();
        fossilList = new MutableLiveData<>();
        fishList.setValue(new ArrayList<Fish>());
        insectList.setValue((new ArrayList<Insect>()));
        fossilList.setValue(new ArrayList<Fossil>());

        acnhApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AcnhApi.class);
    }

    // API call for fish
    public void getFishResponse() {
        acnhApi.getFish().enqueue(new Callback<List<Fish>>() {
            @Override
            public void onResponse(Call<List<Fish>> call, Response<List<Fish>> response) {
                if(response.body() != null) {
                    // List has been set
                    fishList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable t) {
                fishList.postValue(new ArrayList<Fish>());
                Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    // API call for insects
    public void getInsectsResponse() {
        acnhApi.getInsects().enqueue(new Callback<List<Insect>>() {
            @Override
            public void onResponse(Call<List<Insect>> call, Response<List<Insect>> response) {
                if(response.body() != null) {
                    // List has been set
                    insectList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Insect>> call, Throwable t) {
                insectList.postValue(new ArrayList<Insect>());
                Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    // API call for fossils
    public void getFossilsResponse() {
        acnhApi.getFossils().enqueue(new Callback<List<Fossil>>() {
            @Override
            public void onResponse(Call<List<Fossil>> call, Response<List<Fossil>> response) {
                if(response.body() != null) {
                    // List has been set
                    fossilList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Fossil>> call, Throwable t) {
                fossilList.postValue(new ArrayList<Fossil>());
                Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public MutableLiveData<List<Fish>> getFishListFromRepo() {
        return fishList;
    }

    public MutableLiveData<List<Insect>> getInsectListFromRepo() {
        return insectList;
    }

    public MutableLiveData<List<Fossil>> getFossilListFromRepo() {
        return fossilList;
    }
}
