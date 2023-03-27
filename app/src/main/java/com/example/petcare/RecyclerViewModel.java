package com.example.petcare;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class RecyclerViewModel implements Serializable {

    @Exclude
    byte[] image;
   private String id, pet_name,pet_species,pet_breed,pet_gender,pet_size,quality1,quality2,quality3,quality4,quality5,quality6;

    public RecyclerViewModel( /*String id, byte[] image,  */String pet_name, String pet_species, String pet_breed, String pet_size, String pet_gender, String quality1, String quality2, String quality3, String quality4 , String quality5, String quality6){
       /* this.id = id;
        this.image = image;*/
        this.pet_name = pet_name;
        this.pet_species = pet_species;
        this.pet_breed = pet_breed;
        this.pet_size = pet_size;
        this.pet_gender = pet_gender;
        this.quality1 = quality1;
        this.quality2 = quality2;
        this.quality3 = quality3;
        this.quality4 = quality4;
        this.quality5 = quality5;
        this.quality6 = quality6;
    }

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_gender() {
        return pet_gender;
    }

    public void setPet_gender(String pet_gender) {
        this.pet_gender = pet_gender;
    }

    public String getPet_size() {
        return pet_size;
    }

    public void setPet_size(String pet_size) {
        this.pet_size = pet_size;
    }

    public String getQuality1() {
        return quality1;
    }

    public void setQuality1(String quality1) {
        this.quality1 = quality1;
    }

    public String getQuality2() {
        return quality2;
    }

    public void setQuality2(String quality2) {
        this.quality2 = quality2;
    }

    public String getQuality3() {
        return quality3;
    }

    public void setQuality3(String quality3) {
        this.quality3 = quality3;
    }

    public String getQuality4() {
        return quality4;
    }
    public void setQuality4(String quality4) {
        this.quality4 = quality4;
    }

    public String getQuality5() {
        return quality5;
    }

    public void setQuality5(String quality5) {
        this.quality5 = quality5;
    }

    public String getQuality6() {
        return quality6;
    }

    public void setQuality6(String quality6) {
        this.quality6 = quality6;
    }
}