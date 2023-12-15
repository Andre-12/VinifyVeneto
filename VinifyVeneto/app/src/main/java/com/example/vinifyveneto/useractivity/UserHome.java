package com.example.vinifyveneto.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.CurrentUser;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_user);
        if(getIntent().hasExtra("userName")&& getIntent().hasExtra("password")){
            CurrentUser.setId(getIntent().getStringExtra("userName"));
            CurrentUser.setPassword(getIntent().getStringExtra("password"));
        }

        ((TextView)findViewById(R.id.scritta)).setText(CurrentUser.getId()+":");

        findViewById(R.id.logoutUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.catalogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserCatalog.class));
            }
        });

        findViewById(R.id.Cutente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserFavorite.class));
            }
        });
    }
}