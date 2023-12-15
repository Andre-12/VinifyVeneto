package com.example.vinifyveneto.useractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

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

public class UserFavorite extends AppCompatActivity {

    private List<Product> favorities = new ArrayList<>();
    private String item="Tutti";
    private String origin="Tutti";
    private String queryFilter="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ricerca_vini);

        ((TextView)findViewById(R.id.scritta)).setText("Preferiti");

        SearchView searchBar = findViewById(R.id.barradiricerca);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryFilter=query;
                drawCatalog();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    queryFilter="";
                    drawCatalog();
                }
                return false;
            }
        });

        findViewById(R.id.userHomeToolbar).findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.logoutUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        getFavotites();

        createSpinner();

    }

    @Override
    protected void onResume(){
        super.onResume();
        getFavotites();
    }

    private synchronized void createSpinner(){

        Spinner spinnerType = findViewById(R.id.stype);
        String[] items = getResources().getStringArray(R.array.tipi_vino);
        ArrayList<String> arrayList = new ArrayList<>();
        for(String s : items)
            arrayList.add(s);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                drawCatalog();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerType.setAdapter(adapter);

        Spinner spinnerOrigin = findViewById(R.id.sorigin);
        String[] origins = getResources().getStringArray(R.array.province_spinner);
        ArrayList<String> arrayOrigin = new ArrayList<>();
        for(String s : origins)
            arrayOrigin.add(s);

        spinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                origin = adapterView.getItemAtPosition(i).toString();
                drawCatalog();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterOrigin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayOrigin);
        adapterOrigin.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerOrigin.setAdapter(adapterOrigin);

    }

    private void getFavotites(){
        HashMap<String, String> map = new HashMap<>();
        map.put("name", CurrentUser.getId());
        map.put("password", CurrentUser.getPassword());

        Call<ResponseEntity<List<Product>>> call = RetrofitEntity.getRetrofit().getFavorities(map);

        call.enqueue(new Callback<ResponseEntity<List<Product>>>() {
            @Override
            public void onResponse(Call<ResponseEntity<List<Product>>> call, Response<ResponseEntity<List<Product>>> response) {
                if(response.body().getCode()==200){
                    favorities = response.body().getEntity();
                    drawCatalog();
                }
                else{
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<List<Product>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connectionFault, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void drawCatalog(){
        ScrollView scrollView = findViewById(R.id.lista2);
        LinearLayout linearLayout =findViewById(R.id.cardItems);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.removeAllViews();
        layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.card_margin_bottom));

        for(int i=0;i<favorities.size();++i){
            if((origin.equals("Tutti") || origin.equals(favorities.get(i).getOrigin())) && (item.equals("Tutti") || item.equals(favorities.get(i).getType())) && favorities.get(i).getName().contains(queryFilter)) {
                CardView c = (CardView) getLayoutInflater().inflate(R.layout.lista_vini_utente, null);
                ((TextView) c.findViewById(R.id.Lnome)).setText(favorities.get(i).getName());
                ((TextView) c.findViewById(R.id.nseller)).setText("Venditore: " + favorities.get(i).getSeller());
                ((TextView) c.findViewById(R.id.origin)).setText(favorities.get(i).getOrigin());

                c.setLayoutParams(layoutParams);
                linearLayout.addView(c);
                Product p = favorities.get(i);

                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), UserProduct.class);
                        intent.putExtra("product", ((Integer)p.getId()).toString());
                        intent.putExtra("isFavorite", "true");
                        Log.d("ErroreGet", "Inserisco in intent "+p.getId());
                        startActivity(intent);
                    }
                });
            }
        }


    }

}