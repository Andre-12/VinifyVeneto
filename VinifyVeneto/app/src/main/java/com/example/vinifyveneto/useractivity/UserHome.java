package com.example.vinifyveneto.useractivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.CurrentUser;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


        findViewById(R.id.deleteProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View allert = getLayoutInflater().inflate(R.layout.delete_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserHome.this);

                AlertDialog alertDialog = builder.create();
                alertDialog.setView(allert);
                alertDialog.show();

                allert.findViewById(R.id.si).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View confirmAllert = getLayoutInflater().inflate(R.layout.conferma_delete, null);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(UserHome.this);


                        AlertDialog confirmDeleteDialog = builder1.create();
                        confirmDeleteDialog.setView(confirmAllert);
                        confirmDeleteDialog.show();


                        alertDialog.dismiss();

                        confirmAllert.findViewById(R.id.annullaButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                confirmDeleteDialog.dismiss();
                            }
                        });

                        confirmAllert.findViewById(R.id.confermaButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText editText = confirmAllert.findViewById(R.id.confermaText);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("user", CurrentUser.getId());
                                map.put("password", editText.getText().toString());

                                Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().deleteUser(map);

                                call.enqueue(new Callback<ResponseEntity<Void>>() {
                                    @Override
                                    public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                        if(response.body().getCode()==200){
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                });


                allert.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}