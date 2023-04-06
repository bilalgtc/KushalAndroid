package com.example.petcare;

import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class RecyclerViewModel implements Serializable {

    @Exclude
    String pet_img;
   private String id, pet_name,pet_species,pet_breed,pet_size;
   Boolean pet_gender,Neutered,Vaccinated,Friendly_with_dogs,Friendly_with_cats,Friendly_with_kids_less_then_10_year,Friendly_with_kids_greater_then_10_year;

    public RecyclerViewModel() {
    }

    public String getPet_species() {
        return pet_species;
    }

    public void setPet_species(String pet_species) {
        this.pet_species = pet_species;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPet_img() {
        return pet_img;
    }

    public void setPet_img(String pet_img) {
        this.pet_img = pet_img;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public Boolean getPet_gender() {
        return pet_gender;
    }

    public void setPet_gender(Boolean pet_gender) {
        this.pet_gender = pet_gender;
    }

    public String getPet_size() {
        return pet_size;
    }

    public void setPet_size(String pet_size) {
        this.pet_size = pet_size;
    }

    public Boolean getNeutered() {
        return Neutered;
    }

    public void setNeutered(Boolean neutered) {
        Neutered = neutered;
    }

    public Boolean getVaccinated() {
        return Vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        Vaccinated = vaccinated;
    }

    public Boolean getFriendly_with_dogs() {
        return Friendly_with_dogs;
    }

    public void setFriendly_with_dogs(Boolean friendly_with_dogs) {
        Friendly_with_dogs = friendly_with_dogs;
    }

    public Boolean getFriendly_with_cats() {
        return Friendly_with_cats;
    }

    public void setFriendly_with_cats(Boolean friendly_with_cats) {
        Friendly_with_cats = friendly_with_cats;
    }

    public Boolean getFriendly_with_kids_less_then_10_year() {
        return Friendly_with_kids_less_then_10_year;
    }

    public void setFriendly_with_kids_less_then_10_year(Boolean friendly_with_kids_less_then_10_year) {
        Friendly_with_kids_less_then_10_year = friendly_with_kids_less_then_10_year;
    }

    public Boolean getFriendly_with_kids_greater_then_10_year() {
        return Friendly_with_kids_greater_then_10_year;
    }

    public void setFriendly_with_kids_greater_then_10_year(Boolean friendly_with_kids_greater_then_10_year) {
        Friendly_with_kids_greater_then_10_year = friendly_with_kids_greater_then_10_year;
    }
}