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

    // Constructor
    public MuseumSpecimen(int id, String name, String location, String price, String times) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.times = times;
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

    // Parcelable constructor
    protected MuseumSpecimen(Parcel in) {
        name = in.readString();
        location = in.readString();
        price = in.readString();
        times = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(price);
        parcel.writeString(times);
    }

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
