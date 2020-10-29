package com.daniel.chat.acpocketmuseum;

import com.daniel.chat.acpocketmuseum.Fish.Fish;
import com.daniel.chat.acpocketmuseum.Fossil.Fossil;
import com.daniel.chat.acpocketmuseum.Insect.Insect;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AcnhApi {

    @GET("v1a/fish")
    Call<List<Fish>> getFish();

    @GET("v1a/bugs")
    Call<List<Insect>> getInsects();

    @GET("v1a/fossils")
    Call<List<Fossil>> getFossils();
}
