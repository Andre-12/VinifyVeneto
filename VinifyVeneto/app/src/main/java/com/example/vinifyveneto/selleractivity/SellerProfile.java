package com.example.vinifyveneto.selleractivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.MainActivity;
import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.CurrentUser;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.model.Seller;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProfile extends AppCompatActivity {

    private Seller s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilo);

        ((TextView)findViewById(R.id.Nutente)).setText(CurrentUser.getId());


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

        findViewById(R.id.cancellaU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialog = getLayoutInflater().inflate(R.layout.delete_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SellerProfile.this);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(dialog);
                alertDialog.show();

                dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.si).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View confirm = getLayoutInflater().inflate(R.layout.conferma_delete, null);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SellerProfile.this);
                        AlertDialog alertDialog1 = builder1.create();
                        alertDialog1.setView(confirm);
                        alertDialog1.show();
                        alertDialog.dismiss();

                        confirm.findViewById(R.id.annullaButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog1.dismiss();
                            }
                        });

                        confirm.findViewById(R.id.confermaButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText editText = confirm.findViewById(R.id.confermaText);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("user", CurrentUser.getId());
                                map.put("password", editText.getText().toString());

                                Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().deleteSeller(map);

                                call.enqueue(new Callback<ResponseEntity<Void>>() {
                                    @Override
                                    public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT);
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

            }
        });


        findViewById(R.id.Cpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View changePassword = getLayoutInflater().inflate(R.layout.change_password, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SellerProfile.this);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(changePassword);
                alertDialog.show();

                changePassword.findViewById(R.id.annullaButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                changePassword.findViewById(R.id.confermaButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText oldPassword = changePassword.findViewById(R.id.editPasswordAttuale);
                        EditText newPassword = changePassword.findViewById(R.id.editNuovaPassword);
                        EditText confirmNewPassword = changePassword.findViewById(R.id.confermaPassword);

                        if(!oldPassword.getText().toString().equals(CurrentUser.getPassword()) || newPassword.getText().toString().isEmpty() || confirmNewPassword.getText().toString().isEmpty() || !newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Valori inseriti non validi", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", CurrentUser.getId());
                        map.put("oldPassword", oldPassword.getText().toString());
                        map.put("newPassword", newPassword.getText().toString());

                        Call<ResponseEntity<Void>> call = RetrofitEntity.getRetrofit().changeSellerPassword(map);

                        call.enqueue(new Callback<ResponseEntity<Void>>() {
                            @Override
                            public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

        getSeller();


    }

    private void getSeller(){
        HashMap<String, String> map = new HashMap<>();
        map.put("id", CurrentUser.getId());
        map.put("password", CurrentUser.getPassword());
        Log.d("pippo", map.toString());

        Call<ResponseEntity<Seller>> call = RetrofitEntity.getRetrofit().getSellerProfile(map);

        call.enqueue(new Callback<ResponseEntity<Seller>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Seller>> call, Response<ResponseEntity<Seller>> response) {
                if(response.body().getCode()==200){
                    s=response.body().getEntity();
                    //TODO aggiungere modifica nome dell'attività
                    ((TextView)findViewById(R.id.modAddress)).setText(s.getAddress());
                    ((TextView)findViewById(R.id.modNumber)).setText(s.getTelNum());
                    ((TextView)findViewById(R.id.modProv)).setText(s.getProv());
                    TextView province = findViewById(R.id.modProv);
                    province.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            View provinceSelector = getLayoutInflater().inflate(R.layout.provincia_selector, null);
                            AlertDialog.Builder builderp = new AlertDialog.Builder(SellerProfile.this);
                            AlertDialog alertDialog = builderp.create();
                            alertDialog.setView(provinceSelector);
                            alertDialog.show();

                            provinceSelector.findViewById(R.id.belluno).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Belluno");
                                    alertDialog.dismiss();
                                }
                            });

                            provinceSelector.findViewById(R.id.padova).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Padova");
                                    alertDialog.dismiss();
                                }
                            });

                            provinceSelector.findViewById(R.id.rovigo).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Rovigo");
                                    alertDialog.dismiss();
                                }
                            });


                            provinceSelector.findViewById(R.id.treviso).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Treviso");
                                    alertDialog.dismiss();
                                }
                            });

                            provinceSelector.findViewById(R.id.venezia).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Venezia");
                                    alertDialog.dismiss();
                                }
                            });

                            provinceSelector.findViewById(R.id.verona).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Verona");
                                    alertDialog.dismiss();
                                }
                            });

                            provinceSelector.findViewById(R.id.vicenza).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    province.setText("Vicenza");
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Errore di connessione", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Seller>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connectionFault, Toast.LENGTH_LONG).show();
            }
        });

    }
}