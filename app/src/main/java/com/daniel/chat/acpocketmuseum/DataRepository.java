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

        // Initialize with empty list so app doesn't crash
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
                    List<Fish> list = new ArrayList<>();

                    for(Fish fish : response.body()) {
                        int id = fish.getId();
                        String name = fish.getName().substring(0, 1).toUpperCase() + fish.getName().substring(1);
                        String location = fish.getLocation();
                        String price = fish.getPrice() + " bells";
                        String times = fish.getTimes();
                        String rarity = fish.getRarity();
                        String monthsNorthern = fish.getMonthsNorthern();
                        String monthsSouthern = fish.getMonthsSouthern();
                        String catchphrase = fish.getCatchphrase();
                        String museumPhrase = fish.getMuseumPhrase();
                        String priceCJ = fish.getPriceCJ();
                        String shadow = fish.getShadow();

                        list.add(new Fish(id, name, location, price, times, rarity, monthsNorthern, monthsSouthern, catchphrase, museumPhrase, priceCJ, shadow));
                        fishList.postValue(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable t) {
                fishList.setValue(new ArrayList<Fish>());
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
                    List<Insect> list = new ArrayList<>();

                    for(Insect insect : response.body()) {
                        int id = insect.getId();
                        String name = insect.getName().substring(0, 1).toUpperCase() + insect.getName().substring(1);;
                        String location = insect.getLocation();
                        String price = insect.getPrice() + " bells";
                        String times = insect.getTimes();
                        String rarity = insect.getRarity();
                        String monthsNorthern = insect.getMonthsNorthern();
                        String monthsSouthern = insect.getMonthsSouthern();
                        String catchphrase = insect.getCatchphrase();
                        String museumPhrase = insect.getMuseumPhrase();
                        String priceFlick = insect.getPriceFlick();

                        list.add(new Insect(id, name, location, price, times, rarity, monthsNorthern, monthsSouthern, catchphrase, museumPhrase, priceFlick));
                        insectList.postValue(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Insect>> call, Throwable t) {
                insectList.setValue(new ArrayList<Insect>());
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
                    List<Fossil> list = new ArrayList<>();

                    for(Fossil fossil : response.body()) {
                        int id = fossil.getId();
                        String name = fossil.getName().substring(0, 1).toUpperCase() + fossil.getName().substring(1);;
                        String price = fossil.getPrice() + " bells";
                        String museumPhrase = fossil.getMuseumPhrase();

                        list.add(new Fossil(id, name, price, museumPhrase));
                        fossilList.postValue(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Fossil>> call, Throwable t) {
                fossilList.setValue(new ArrayList<Fossil>());
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
