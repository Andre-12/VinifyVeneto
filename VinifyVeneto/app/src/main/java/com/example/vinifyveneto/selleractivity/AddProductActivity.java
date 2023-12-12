package com.example.vinifyveneto.selleractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private String type;
    private String origin;
    private String price;

    private String name;

    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_vino);

        View thisView = getCurrentFocus();

        TextView nomeVino = findViewById(R.id.editvino);
        TextView priceVino = findViewById(R.id.editprezzo);
        TextView descrVino = findViewById(R.id.editTextDescrizione);

        String[] types = getResources().getStringArray(R.array.tipi_vino);
        String[] province = getResources().getStringArray(R.array.province_spinner);
        ArrayList<String> arrayTypes = new ArrayList<>();
        ArrayList<String> arrayProvince = new ArrayList<>();
        for(String s : types)
            arrayTypes.add(s);

        arrayTypes.add(0,"Tipo");
        arrayTypes.remove("Tutti");

        for(String s : province)
            arrayProvince.add(s);

        arrayProvince.add(0, "Origine");
        arrayProvince.remove("Tutti");

        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        Spinner originSpinner = findViewById(R.id.originSpinner);

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

        findViewById(R.id.aggiungi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nomeVino.getText().toString();
                price = priceVino.getText().toString();
                description = descrVino.getText().toString();
                handleAddProduct();
            }
        });

        findViewById(R.id.esci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    private void handleAddProduct(){
        //Toast.makeText(this, origin, Toast.LENGTH_LONG).show();
        if(origin.equals("Origine") || type.equals("Tipo") || name.isEmpty() || price.isEmpty() || description.isEmpty())
            Toast.makeText(this, "Dati inseriti non validi", Toast.LENGTH_LONG).show();
        else{
            HashMap<String, String> map = new HashMap<>();
            map.put("sellerId", getIntent().getStringExtra("id"));
            map.put("sellerPassword", getIntent().getStringExtra("password"));
            map.put("name", name);
            map.put("origin", origin);
            map.put("type", type);
            map.put("description", description);
            map.put("price", price);

            Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().executeAddProduct(map);

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
                    Toast.makeText(getApplicationContext(), "Connessione fallita", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}