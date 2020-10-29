package com.daniel.chat.acpocketmuseum.Insect;

import android.os.Parcel;

import com.daniel.chat.acpocketmuseum.MuseumSpecimen;
import com.google.gson.annotations.SerializedName;

public class Insect extends MuseumSpecimen {
    @SerializedName("price-flick")
    private String priceFlick;

    // Constructor
    public Insect(int id, String name, String location, String price, String times, String rarity,
                  String monthsNorthern, String monthsSouthern, String catchphrase, String museumPhrase, String priceFlick) {
        super(id, name, location, price, times, rarity, monthsNorthern, monthsSouthern, catchphrase, museumPhrase);
        this.priceFlick = priceFlick;
    }

    public String getPriceFlick() {
        return priceFlick;
    }

    @Override
    public String getType() {
        return "Insect";
    }

    // Parcelable constructor
    protected Insect(Parcel in) {
        super(in);
        priceFlick = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(priceFlick);
    }

    // Parcelable stuff; leave as is
    public static final Creator<Insect> CREATOR = new Creator<Insect>() {
        @Override
        public Insect createFromParcel(Parcel in) {
            return new Insect(in);
        }

        @Override
        public Insect[] newArray(int size) {
            return new Insect[size];
        }
    };
}
