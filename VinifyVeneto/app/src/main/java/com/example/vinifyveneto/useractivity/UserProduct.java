package com.example.vinifyveneto.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.CurrentUser;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.model.Product;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProduct extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vini_in_specifico);

        //((RelativeLayout)findViewById(R.id.relativeLayout)).removeView(findViewById(R.id.modifica));

        ((Button)findViewById(R.id.modifica)).setText("Info venditore");

        findViewById(R.id.modifica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoSeller.class);
                intent.putExtra("product", getIntent().getStringExtra("product"));
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.elimina);
        if(getIntent().hasExtra("isFavorite")){
            button.setText("Rimuovi dai preferiti");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<>();

                    map.put("product", getIntent().getStringExtra("product"));
                    map.put("user", CurrentUser.getId());
                    map.put("password", CurrentUser.getPassword());

                    Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().removeFavorite(map);

                    call.enqueue(new Callback<ResponseEntity<Void>>() {
                        @Override
                        public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            if(response.body().getCode()==200){
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), R.string.connectionFault, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
        else {
            button.setText("Aggiungi ai preferiti");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<>();

                    map.put("product", getIntent().getStringExtra("product"));
                    map.put("user", CurrentUser.getId());
                    map.put("password", CurrentUser.getPassword());

                    Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().addFavorite(map);

                    call.enqueue(new Callback<ResponseEntity<Void>>() {
                        @Override
                        public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {

                        }
                    });
                }
            });
        }

        Intent intent = getIntent();
        if(intent.hasExtra("product"))
            Log.d("ErroreGet", intent.getStringExtra("product"));


        Call<ResponseEntity<Product>> call = RetrofitEntity.getRetrofit().getUserProduct(getIntent().getStringExtra("product"));

        call.enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if(response.body().getCode()==200){
                    product=response.body().getEntity();
                    drawProduct();
                }
                else{
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connectionFault, Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.userHomeToolbar).findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserHome.class));
                finish();
            }
        });


        findViewById(R.id.esci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    private void drawProduct(){
        ((TextView)findViewById(R.id.Nomeprod)).setText(product.getName());
        ((TextView)findViewById(R.id.prezzo)).setText("Prezzo: "+product.getPrice());
        ((TextView)findViewById(R.id.tipo)).setText("Tipo: "+product.getType());
        ((TextView)findViewById(R.id.descrizione)).setText("Descrizione: "+product.getDescription());
    }
}