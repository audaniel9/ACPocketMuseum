package com.daniel.chat.acpocketmuseum.Fish;

import android.os.Parcel;

import com.daniel.chat.acpocketmuseum.MuseumSpecimen;

import java.util.Comparator;

public class Fish extends MuseumSpecimen {

    // Constructor
    public Fish(int id, String name, String location, String price, String times) {
        super(id, name, location, price, times);
    }

    // Parcelable constructor
    protected Fish(Parcel in) {
        super(in);
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
}
