package com.gokul.quickrapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gokul.quickrapp.model.DataModel;

import java.util.ArrayList;

public class BuyViewModel extends ViewModel {
    MutableLiveData<ArrayList<DataModel>> userLiveData;
    ArrayList<DataModel> userArrayList;
    public  BuyViewModel() {
        userLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<DataModel>> getUserMutableLiveData() {
        return userLiveData;
    }

    public void init(){
        populateList();
        userLiveData.setValue(userArrayList);
    }

    public void populateList(){

        DataModel user = new DataModel();
        user.setTitle("Darknight");
        user.setDescription("Best rating movie");

        userArrayList = new ArrayList<>();
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
    }
}
