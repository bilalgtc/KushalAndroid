package com.example.petcare;

public class RecyclerViewModel {
    int image;
    String name,pet_type,pet_verity,pet_gender,pet_size,quality1,quality2,quality3,quality4;

    public RecyclerViewModel(int image,String name,String pet_type,String pet_verity,String pet_gender,String pet_size,String quality1,String quality2,String quality3,String quality4){
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
    }
}
