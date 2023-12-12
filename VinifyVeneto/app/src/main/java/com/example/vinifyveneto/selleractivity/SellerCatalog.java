package com.example.vinifyveneto.selleractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.CurrentUser;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerCatalog extends AppCompatActivity {

    List<Product> myProduct = new ArrayList<>();

    private String item="Tutti";

    private HashMap<String, String> currentUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogo);

        Intent intent = getIntent();

        currentUser.put("id", intent.getStringExtra("id"));
        currentUser.put("password", intent.getStringExtra("password"));

        Log.d("mytag", "Intent: "+intent.getStringExtra("id"));

        //------------------------------------------------------------------------------------------


        //inizializzazione spinner
        String[] items = getResources().getStringArray(R.array.tipi_vino);
        ArrayList<String> arrayList = new ArrayList<>();
        for(String s : items)
            arrayList.add(s);

        Log.d("logCreate", "Sto creando il catalogo");
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(SellerCatalog.this, "Item selected "+item, Toast.LENGTH_LONG).show();
                //updateCatalog(currentUser.get("id"));
                updateCatalog(CurrentUser.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        /*ScrollView scrollView = findViewById(R.id.lista2);
        LinearLayout linearLayout = findViewById(R.id.items);
        CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.lista_vini, null);
        TextView textView = cardView.findViewById(R.id.Lnome);
        textView.setText("Ciao");
        linearLayout.addView(cardView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.card_margin_bottom));
        cardView.setLayoutParams(layoutParams);*/


        findViewById(R.id.aggiungi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), AddProductActivity.class);
                intent1.putExtra("id", currentUser.get("id"));
                intent1.putExtra("password", currentUser.get("password"));
                startActivity(intent1);

                /*View addProduct = getLayoutInflater().inflate(R.layout.aggiungi_vino, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setView(addProduct);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();*/
            }
        });


        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SellerHome.class));
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

    @Override
    protected void onResume(){
        super.onResume();
        //updateCatalog(currentUser.get("id"));
        updateCatalog(CurrentUser.getId());
    }

    void updateCatalog(String id){
        Log.d("logCreate", "Il seller è "+CurrentUser.getId());
        HashMap<String, String> map = new HashMap<>();

        map.put("seller", id);
        map.put("type", item);
        Call<ResponseEntity<List<Product>>> call = RetrofitEntity.getRetrofit().executeGetProducts(map);
        call.enqueue(new Callback<ResponseEntity<List<Product>>>() {
            @Override
            public void onResponse(Call<ResponseEntity<List<Product>>> call, Response<ResponseEntity<List<Product>>> response) {
                ResponseEntity<List<Product>> e = response.body();
                if (e.getEntity() != null) {
                    myProduct = e.getEntity();
                    drawCatalog();
                    //------------------------------------------------------------
                    //updateCatalog(item);
                } else {
                    Log.d("mytag", "product is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<List<Product>>> call, Throwable t) {
                //Toast.makeText(SellerCatalog.this,"fjhfsdjjfshj", Toast.LENGTH_LONG).show();
            }
        });


    }

    void drawCatalog(){
        ScrollView scrollView = findViewById(R.id.lista2);
        LinearLayout linearLayout = findViewById(R.id.items);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.removeAllViews();

        layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.card_margin_bottom));

        for (int i = 0; i < myProduct.size(); ++i) {
            if (myProduct.get(i).getType().equals(item) || item.equals("Tutti")) {
                CardView c = (CardView) getLayoutInflater().inflate(R.layout.lista_vini, null);
                c.setLayoutParams(layoutParams);
                TextView t = c.findViewById(R.id.Lnome);
                t.setText(myProduct.get(i).getName());
                //((TextView)c.findViewById(R.id.descrizione)).setText(myProduct.get(i).getDescription());
                linearLayout.addView(c);
                Product p = myProduct.get(i);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SellerProductActivity.class);
                        intent.putExtra("id", currentUser.get("id"));
                        intent.putExtra("password", currentUser.get("password"));
                        intent.putExtra("product", String.valueOf(p.getId()));
                        Log.d("prodId", String.valueOf(p.getId()));
                        startActivity(intent);
                    }
                });
            }
        }
    }
}