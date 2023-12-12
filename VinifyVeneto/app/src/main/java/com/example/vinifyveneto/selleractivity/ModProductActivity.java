package com.example.vinifyveneto.selleractivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModProductActivity extends AppCompatActivity {

    private Product myProduct;

    private String type;
    private String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_vino);

        Intent intent = getIntent();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", intent.getStringExtra("product"));

        Call<ResponseEntity<Product>> call = RetrofitEntity.getRetrofit().executeGetProduct(map);

        call.enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if(response.body().getCode()==200){
                    myProduct=response.body().getEntity();
                    //Toast.makeText(SellerProductActivity.this, response.body().getEntity().getDescription(), Toast.LENGTH_LONG).show();
                    //drawProduct();
                    ((TextView)findViewById(R.id.editTextDescrizione)).setText(myProduct.getDescription());
                    ((TextView)findViewById(R.id.editprezzo)).setText(String.valueOf(myProduct.getPrice()));
                    ((TextView)findViewById(R.id.editvino)).setText(myProduct.getName());
                    initSpinner();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                Toast.makeText(ModProductActivity.this, "Connessione fallita", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.cancella).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.salva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("sellerId", intent.getStringExtra("id"));
                map.put("sellerPassword", intent.getStringExtra("password"));
                map.put("origin", origin);
                map.put("type", type);
                map.put("name", ((TextView)findViewById(R.id.editvino)).getText().toString());
                map.put("price", ((TextView)findViewById(R.id.editprezzo)).getText().toString());
                map.put("description", ((TextView)findViewById(R.id.editTextDescrizione)).getText().toString());
                map.put("productId", ((Integer)myProduct.getId()).toString());

                Log.d("mapLog", map.toString());

                Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().executeModifyProduct(map);

                call.enqueue(new Callback<ResponseEntity<Void>>() {
                    @Override
                    public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if(response.body().getCode()==200)
                            finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connessione fallita", Toast.LENGTH_LONG).show();
                    }
                });

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
                Intent intent1 = new Intent(getApplicationContext(), SellerHome.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent1.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent1);
                finish();
            }
        });


    }

    private void initSpinner(){

        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        Spinner originSpinner = findViewById(R.id.originSpinner);

        String[] types = getResources().getStringArray(R.array.tipi_vino);
        String[] province = getResources().getStringArray(R.array.province_spinner);
        ArrayList<String> arrayTypes = new ArrayList<>();
        ArrayList<String> arrayProvince = new ArrayList<>();
        for(String s : types)
            arrayTypes.add(s);

        arrayTypes.remove(myProduct.getType());
        arrayTypes.add(0,myProduct.getType());
        arrayTypes.remove("Tutti");

        for(String s : province)
            arrayProvince.add(s);

        arrayProvince.remove(myProduct.getOrigin());
        arrayProvince.add(0, myProduct.getOrigin());
        arrayProvince.remove("Tutti");

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> originAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayProvince);
        originAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        originSpinner.setAdapter(originAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        originSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                origin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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