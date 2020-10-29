package com.daniel.chat.acpocketmuseum;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class MuseumSpecimen implements Parcelable {
    private int id;
    private Name name;
    private Availability availability;
    private String price;
    @SerializedName("catch-phrase")
    private String catchphrase;
    @SerializedName("museum-phrase")
    private String museumPhrase;
    private String type;

    public static class Name {
        @SerializedName("name-USen")
        public String nameUSen;
    }

    public static class Availability {
        @SerializedName("month-northern")
        public String monthsNorthern;
        @SerializedName("month-southern")
        public String monthsSouthern;
        public String time;
        public String location;
        public String rarity;
    }

    // Constructor
    public MuseumSpecimen(int id, String name, String location, String price, String times, String rarity,
                          String monthsNorthern, String monthsSouthern, String catchphrase, String museumPhrase) {
        this.id = id;
        this.name = new Name();
        this.name.nameUSen = name;
        this.availability = new Availability();
        this.availability.location = location;
        this.price = price;
        this.availability.time = times;
        this.availability.rarity = rarity;
        this.availability.monthsNorthern = monthsNorthern;
        this.availability.monthsSouthern = monthsSouthern;
        this.catchphrase = catchphrase;
        this.museumPhrase = museumPhrase;
    }

    // Constructor for fossils
    public MuseumSpecimen(int id, String name, String price, String museumPhrase) {
        this.id = id;
        this.name = new Name();
        this.name.nameUSen = name;
        this.price = price;
        this.museumPhrase = museumPhrase;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.nameUSen;
    }

    public String getLocation() {
        return availability.location;
    }

    public String getPrice() {
        return price;
    }

    public String getTimes() {
        return availability.time;
    }

    public String getRarity() {
        return availability.rarity;
    }

    public String getMonthsNorthern() {
        return availability.monthsNorthern;
    }

    public String getMonthsSouthern() {
        return availability.monthsSouthern;
    }

    public String getCatchphrase() {
        return catchphrase;
    }

    public String getMuseumPhrase() {
        return museumPhrase;
    }

    public String getType() {
        return type;
    }

    // Parcelable constructor
    protected MuseumSpecimen(Parcel in) {
        name.nameUSen = in.readString();
        availability.location = in.readString();
        price = in.readString();
        availability.time = in.readString();
        availability.rarity = in.readString();
        availability.monthsNorthern = in.readString();
        availability.monthsSouthern = in.readString();
        catchphrase = in.readString();
        museumPhrase = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name.nameUSen);
        parcel.writeString(availability.location);
        parcel.writeString(price);
        parcel.writeString(availability.time);
        parcel.writeString(availability.rarity);
        parcel.writeString(availability.monthsNorthern);
        parcel.writeString(availability.monthsSouthern);
        parcel.writeString(catchphrase);
        parcel.writeString(museumPhrase);
    }

    // Parcelable stuff; leave as is
    public static final Creator<MuseumSpecimen> CREATOR = new Creator<MuseumSpecimen>() {
        @Override
        public MuseumSpecimen createFromParcel(Parcel in) {
            return new MuseumSpecimen(in);
        }

        @Override
        public MuseumSpecimen[] newArray(int size) {
            return new MuseumSpecimen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public static Comparator<MuseumSpecimen> MuseumSpecimenSortAscending = new Comparator<MuseumSpecimen>() {
        @Override
        public int compare(MuseumSpecimen fish, MuseumSpecimen fish2) {
            return fish.getName().compareTo(fish2.getName());
        }
    };

    public static Comparator<MuseumSpecimen> MuseumSpecimenSortDescending = new Comparator<MuseumSpecimen>() {
        @Override
        public int compare(MuseumSpecimen fish, MuseumSpecimen fish2) {
            return fish2.getName().compareTo(fish.getName());
        }
    };
}
