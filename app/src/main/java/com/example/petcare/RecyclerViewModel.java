package com.example.petcare;

import android.graphics.Bitmap;

public class RecyclerViewModel {
    int image;
//    byte[] image;
    String id, name,pet_type,pet_verity,pet_gender,pet_size,quality1,quality2,quality3,quality4/*,quality5,quality6*/;


    public RecyclerViewModel(int image, String name, String pet_type, String pet_verity, String pet_gender, String pet_size, String quality1, String quality2, String quality3, String quality4/*, String quality5, String quality6*/) {

        this.image = image;
        this.name = name;
        this.pet_type = pet_type;
        this.pet_verity = pet_verity;
        this.pet_gender = pet_gender;
        this.pet_size = pet_size;
        this.quality1 = quality1;
        this.quality2 = quality2;
        this.quality3 = quality3;
        this.quality4 = quality4;
//        this.quality5 = quality5;
//        this.quality6 = quality6;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPet_type() {
        return pet_type;
    }

    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
    }

    public String getPet_verity() {
        return pet_verity;
    }

    public void setPet_verity(String pet_verity) {
        this.pet_verity = pet_verity;
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
}
