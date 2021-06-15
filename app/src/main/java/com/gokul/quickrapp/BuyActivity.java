package com.gokul.quickrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gokul.quickrapp.adapter.ProductAdapter;
import com.gokul.quickrapp.model.DataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<DataModel> dm ;
    ArrayList<String> id;
    private FirebaseFirestore db;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        rv = findViewById(R.id.rvBuy);
        ProgressBar pb = findViewById(R.id.pbLoading);
        db = FirebaseFirestore.getInstance();
        dm = new ArrayList<>();
        id = new ArrayList<>();
        productAdapter = new ProductAdapter(this, dm, id);
        rv.setAdapter(productAdapter);

        pb.setVisibility(View.VISIBLE);

        db.collection("products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list =  queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d: list){
                                DataModel c = d.toObject(DataModel.class);
                                Log.d("Api Response",""+d );
                                Log.d("Api Key", ""+d.getId());
                                id.add(d.getId());
                                dm.add(c);
                            }
                            productAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                        pb.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getApplicationContext(),"Getting error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sell(View view) {
        startActivity(new Intent(getApplicationContext(), SellActivity.class));
    }
}