package com.example.vinifyveneto.selleractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.CurrentUser;

import java.util.HashMap;

public class SellerHome extends AppCompatActivity {

    HashMap<String, String> currentUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_seller);

        if(getIntent().hasExtra("id") && getIntent().hasExtra("password")){
            CurrentUser.setId(getIntent().getStringExtra("id"));
            CurrentUser.setPassword(getIntent().getStringExtra("password"));
            Log.d("logCreate", "entra");
        }

        Log.d("logCreate", "Il seller nella home è "+CurrentUser.getId());

        currentUser.put("id", getIntent().getStringExtra("id"));
        currentUser.put("password", getIntent().getStringExtra("password"));

        findViewById(R.id.logoutSeller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //((TextView)findViewById(R.id.scritta)).setText(currentUser.get("id")+":");

        ((TextView)findViewById(R.id.scritta)).setText(CurrentUser.getId()+":");

        findViewById(R.id.catalogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellerCatalog.class);
                intent.putExtra("id", currentUser.get("id"));
                intent.putExtra("password", currentUser.get("password"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}