package com.example.vinifyveneto.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.model.Seller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoSeller extends AppCompatActivity {

    private Seller seller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_venditore);

        Intent intent = getIntent();
        Call<ResponseEntity<Seller>> call = RetrofitEntity.getRetrofit().getSellerInfo(intent.getStringExtra("product"));

        call.enqueue(new Callback<ResponseEntity<Seller>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Seller>> call, Response<ResponseEntity<Seller>> response) {
                if(response.body().getCode()==200) {
                    seller = response.body().getEntity();
                    drawSeller();
                }
                else{
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Seller>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connectionFault, Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.esci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.userHomeToolbar).findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserHome.class));
                finish();
            }
        });
    }

    private void drawSeller(){
        ((TextView)findViewById(R.id.address)).setText(seller.getAddress());
        ((TextView)findViewById(R.id.sellerName)).setText(seller.getName());
        ((TextView)findViewById(R.id.provincia)).setText(((TextView)findViewById(R.id.provincia)).getText().toString()+seller.getProv());
        ((TextView)findViewById(R.id.telNum)).setText(seller.getTelNum());
    }
}