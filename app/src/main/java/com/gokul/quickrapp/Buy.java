package com.gokul.quickrapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.gokul.quickrapp.adapter.ProductAdapter;
import com.gokul.quickrapp.model.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Buy extends Fragment {
    RecyclerView rv;

    Context context;
    BuyViewModel viewModel;
    ProductAdapter productAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy, container, false);
        context = getActivity();
        rv = view.findViewById(R.id.rvBuy);
        viewModel = new ViewModelProvider(requireActivity()).get(BuyViewModel.class);
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
        setUI();
        return view;
    }
    Observer<ArrayList<DataModel>> userListUpdateObserver = new Observer<ArrayList<DataModel>>() {
        @Override
        public void onChanged(ArrayList<DataModel> userArrayList) {
            productAdapter = new ProductAdapter(context,userArrayList);
            rv.setAdapter(productAdapter);
        }
    };


    private void setUI() {
    }


}
