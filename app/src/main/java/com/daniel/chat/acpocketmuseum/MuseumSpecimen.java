package com.daniel.chat.acpocketmuseum;

import java.util.Comparator;

public class MuseumSpecimen {
    private String specimenName;
    private String location;
    private String price;
    private String times;

    public MuseumSpecimen(String specimenName, String location, String price, String times) {
        this.specimenName = specimenName;
        this.location = location;
        this.price = price;
        this.times = times;
    }

    public String getSpecimenName() {
        return specimenName;
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

    public static Comparator<MuseumSpecimen> MuseumSpecimenSortAscending = new Comparator<MuseumSpecimen>() {
        @Override
        public int compare(MuseumSpecimen specimen, MuseumSpecimen specimen2) {
            return specimen.getSpecimenName().compareTo(specimen2.getSpecimenName());
        }
    };

    public static Comparator<MuseumSpecimen> MuseumSpecimenSortDescending = new Comparator<MuseumSpecimen>() {
        @Override
        public int compare(MuseumSpecimen specimen, MuseumSpecimen specimen2) {
            return specimen2.getSpecimenName().compareTo(specimen.getSpecimenName());
        }
    };
}
