package com.example.vinifyveneto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitInterface;

import java.util.HashMap;
import com.example.vinifyveneto.model.*;
import com.example.vinifyveneto.useractivity.UserFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    private RetrofitInterface retrofitInterface;
    private String BASE_URL2="http://172.17.0.1:9000";

    private String BASE_URL = "http://157.138.167.138:9000";

    private String BASE_URL3 = "http://192.168.1.24:9000";

    private boolean isMain = true;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*retrofit = new Retrofit.Builder().baseUrl(BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        retrofitInterface = RetrofitEntity.getRetrofit();

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        findViewById(R.id.signupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignup();
            }
        });*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentSlot, new UserFragment());
        fragmentTransaction.commit();
    }

    private void handleSignup() {
        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button button = view.findViewById(R.id.signupButton);
        EditText userName = view.findViewById(R.id.signupUserNameEdit);
        EditText password = view.findViewById(R.id.signupPasswordEdit);
        Log.d("mytag", "fsfsfs");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();
                map.put("name", userName.getText().toString());
                map.put("password", password.getText().toString());
                Log.d("mytag", "dioBestia");
                Call<ResponseEntity<Void>> call = retrofitInterface.executeSignup(map);
                call.enqueue(new Callback<ResponseEntity<Void>>() {
                    @Override
                    public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                        ResponseEntity<Void> e = response.body();
                        Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Signup Fallito",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void handleLogin() {
        Log.d("mytag", "pippo");
        HashMap<String, String> map = new HashMap<>();
        EditText userName = findViewById(R.id.userNameEdit);
        EditText password = findViewById(R.id.passwordEdit);

        map.put("name", userName.getText().toString());
        map.put("password", password.getText().toString());

        Call<ResponseEntity<User>> call = retrofitInterface.executeLoginUser(map);

        call.enqueue(new Callback<ResponseEntity<User>>() {
            @Override
            public void onResponse(Call<ResponseEntity<User>> call, Response<ResponseEntity<User>> response) {
                ResponseEntity<User> e = response.body();
                Toast.makeText(MainActivity.this,  e.getMessage(),Toast.LENGTH_LONG).show();
                if(e.getCode()==200) {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    intent.putExtra("name", e.getEntity().getName());
                    intent.putExtra("password", e.getEntity().getPassword());
                    startActivity(intent);
                    Log.d("mytag", "intent2");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connessione non riuscita",Toast.LENGTH_LONG).show();
            }
        });
    }

}