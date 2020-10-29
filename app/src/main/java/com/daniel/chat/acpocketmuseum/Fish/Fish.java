package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Parcel;

import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.google.gson.annotations.SerializedName;

public class Fish extends MuseumSpecimen {
    @SerializedName("price-cj")
    private String priceCJ;
    private String shadow;


    // Constructor
    public Fish(int id, String name, String location, String price, String times, String rarity,
                String monthsNorthern, String monthsSouthern, String catchphrase, String museumPhrase, String priceCJ, String shadow) {
        super(id, name, location, price, times, rarity, monthsNorthern, monthsSouthern, catchphrase, museumPhrase);
        this.priceCJ = priceCJ;
        this.shadow = shadow;
    }

    public String getPriceCJ() {
        return priceCJ;
    }

    public String getShadow() {
        return shadow;
    }

    @Override
    public String getType() {
        return "Fish";
    }

    // Parcelable constructor
    protected Fish(Parcel in) {
        super(in);
        priceCJ = in.readString();
        shadow = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(priceCJ);
        parcel.writeString(shadow);
    }

    // Parcelable stuff; leave as is
    public static final Creator<Fish> CREATOR = new Creator<Fish>() {
        @Override
        public Fish createFromParcel(Parcel in) {
            return new Fish(in);
        }

        @Override
        public Fish[] newArray(int size) {
            return new Fish[size];
        }
    };
}
