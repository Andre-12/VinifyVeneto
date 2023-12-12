package com.example.vinifyveneto.selleractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.model.Product;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProductActivity extends AppCompatActivity {

    Product myProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vini_in_specifico);

        Intent intent = getIntent();
        /*HashMap<String, String> map = new HashMap<>();
        map.put("id", intent.getStringExtra("product"));*/

        updateProduct(intent.getStringExtra("product"));

        /*Call<ResponseEntity<Product>> call = RetrofitEntity.getRetrofit().executeGetProduct(map);

        call.enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if(response.body().getCode()==200){
                    myProduct=response.body().getEntity();
                    //Toast.makeText(SellerProductActivity.this, response.body().getEntity().getDescription(), Toast.LENGTH_LONG).show();
                    drawProduct();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                Toast.makeText(SellerProductActivity.this, "Connessione fallita", Toast.LENGTH_LONG).show();
            }
        });*/


        findViewById(R.id.elimina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("seller", intent.getStringExtra("id"));
                map.put("password", intent.getStringExtra("password"));
                map.put("product", intent.getStringExtra("product"));

                Log.d("prodId", intent.getStringExtra("product"));

                Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().executeDeleteProduct(map);

                call.enqueue(new Callback<ResponseEntity<Void>>() {
                    @Override
                    public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                       if(response.body().getCode()==400){
                           Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                           finish();
                       }
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connessione fallita", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        findViewById(R.id.modifica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getApplicationContext(), ModProductActivity.class);
                newIntent.putExtra("id", intent.getStringExtra("id"));
                newIntent.putExtra("password", intent.getStringExtra("password"));
                newIntent.putExtra("product", intent.getStringExtra("product"));
                startActivity(newIntent);
            }
        });

        findViewById(R.id.esci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SellerHome.class));
                finish();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        updateProduct(getIntent().getStringExtra("product"));
    }

    void updateProduct(String product){

        HashMap<String, String> map = new HashMap<>();
        map.put("id", product);

        Call<ResponseEntity<Product>> call = RetrofitEntity.getRetrofit().executeGetProduct(map);

        call.enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if(response.body().getCode()==200){
                    myProduct=response.body().getEntity();
                    //Toast.makeText(SellerProductActivity.this, response.body().getEntity().getDescription(), Toast.LENGTH_LONG).show();
                    drawProduct();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                Toast.makeText(SellerProductActivity.this, "Connessione fallita", Toast.LENGTH_LONG).show();
            }
        });
    }

    void drawProduct(){
        ((TextView)findViewById(R.id.Nomeprod)).setText(myProduct.getName());
        ((TextView)findViewById(R.id.prezzo)).setText("Prezzo: "+String.valueOf(myProduct.getPrice()));
        ((TextView)findViewById(R.id.tipo)).setText("Tipo: "+myProduct.getType());
        ((TextView)findViewById(R.id.descrizione)).setText("Descrizione: "+myProduct.getDescription());
    }
}