package com.daniel.chat.acpocketmuseum;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class MuseumSpecimen implements Parcelable {
    private int id;
    private String name;
    private String location;
    private String price;
    private String times;
    private String rarity;
    private String monthsNorthern;
    private String monthsSouthern;
    private String catchphrase;
    private String museumPhrase;
    private String type;

    // Constructor
    public MuseumSpecimen(int id, String name, String location, String price, String times, String rarity,
                          String monthsNorthern, String monthsSouthern, String catchphrase, String museumPhrase) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.times = times;
        this.rarity = rarity;
        this.monthsNorthern = monthsNorthern;
        this.monthsSouthern = monthsSouthern;
        this.catchphrase = catchphrase;
        this.museumPhrase = museumPhrase;
    }

    // Constructor for fossils
    public MuseumSpecimen(int id, String name, String price, String museumPhrase) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.museumPhrase = museumPhrase;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getTimes() {
        return times;
    }

    public String getRarity() {
        return rarity;
    }

    public String getMonthsNorthern() {
        return monthsNorthern;
    }

    public String getMonthsSouthern() {
        return monthsSouthern;
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
        name = in.readString();
        location = in.readString();
        price = in.readString();
        times = in.readString();
        rarity = in.readString();
        monthsNorthern = in.readString();
        monthsSouthern = in.readString();
        catchphrase = in.readString();
        museumPhrase = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(price);
        parcel.writeString(times);
        parcel.writeString(rarity);
        parcel.writeString(monthsNorthern);
        parcel.writeString(monthsSouthern);
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
