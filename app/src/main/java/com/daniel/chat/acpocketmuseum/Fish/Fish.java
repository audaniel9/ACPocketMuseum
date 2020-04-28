package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;

public class Fish implements Parcelable {
    private int id;
    private String name;
    private String location;
    private String price;
    private String times;

    // Constructor
    public Fish(int id, String name, String location, String price, String times) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.times = times;
    }

    // Parcelable constructor
    protected Fish(Parcel in) {
        name = in.readString();
        location = in.readString();
        price = in.readString();
        times = in.readString();
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

    public static Comparator<Fish> FishSortAscending = new Comparator<Fish>() {
        @Override
        public int compare(Fish fish, Fish fish2) {
            return fish.getName().compareTo(fish2.getName());
        }
    };

    public static Comparator<Fish> FishSortDescending = new Comparator<Fish>() {
        @Override
        public int compare(Fish fish, Fish fish2) {
            return fish2.getName().compareTo(fish.getName());
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(price);
        parcel.writeString(times);
    }
}
