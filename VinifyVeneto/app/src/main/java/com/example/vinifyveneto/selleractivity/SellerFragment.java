package com.example.vinifyveneto.selleractivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinifyveneto.R;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.entity.RetrofitInterface;
import com.example.vinifyveneto.useractivity.UserFragment;
import com.example.vinifyveneto.model.Seller;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerFragment extends Fragment {

    private boolean isHidden=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seller, container, false);

        View view1 = view;

        view.findViewById(R.id.switch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentSlot, new UserFragment());

                fragmentTransaction.commit();
            }
        });

        view.findViewById(R.id.sellerLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin(view1, RetrofitEntity.getRetrofit());
            }
        });

        view.findViewById(R.id.sellerSignupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignup(RetrofitEntity.getRetrofit());
            }
        });

        view.findViewById(R.id.sellerImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHidden) {
                    isHidden=!isHidden;
                    //Toast.makeText(getContext(), "Resa visibile", Toast.LENGTH_SHORT).show();
                    ((ImageView) view1.findViewById(R.id.sellerImageView)).setImageResource(R.drawable.baseline_remove_red_eye_24);
                    ((EditText)view1.findViewById(R.id.sellerPasswordEdit)).setTransformationMethod(null);
                    ((EditText)view1.findViewById(R.id.sellerPasswordEdit)).setSelection(((EditText)view1.findViewById(R.id.sellerPasswordEdit)).getText().toString().length());
                }
                else{
                    isHidden=!isHidden;
                    ((ImageView)view1.findViewById(R.id.sellerImageView)).setImageResource(R.drawable.baseline_visibility_off_24);
                    ((EditText)view1.findViewById(R.id.sellerPasswordEdit)).setTransformationMethod(new PasswordTransformationMethod());
                    ((EditText)view1.findViewById(R.id.sellerPasswordEdit)).setSelection(((EditText)view1.findViewById(R.id.sellerPasswordEdit)).getText().toString().length());
                }
            }
        });

        view.findViewById(R.id.forgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View forgotPassword = getLayoutInflater().inflate(R.layout.recupero_password, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(forgotPassword).show();

                EditText id = forgotPassword.findViewById(R.id.idEdit);
                EditText tel = forgotPassword.findViewById(R.id.telEdit);

                forgotPassword.findViewById(R.id.forgotButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<ResponseEntity<String>> call = RetrofitEntity.getRetrofit().forgotPassword(id.getText().toString(), tel.getText().toString());

                        call.enqueue(new Callback<ResponseEntity<String>>() {
                            @Override
                            public void onResponse(Call<ResponseEntity<String>> call, Response<ResponseEntity<String>> response) {
                                if(response.body().getCode()==200){
                                    ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clipData = ClipData.newPlainText("Password", response.body().getEntity());
                                    clipboardManager.setPrimaryClip(clipData);
                                    Toast.makeText(getContext(), "Password copiata negli appunti", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getContext(), "Recupero password non riuscito", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseEntity<String>> call, Throwable t) {
                                Toast.makeText(getContext(), R.string.connectionFault, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void handleSignup(RetrofitInterface retrofitInterface){
        View view = getLayoutInflater().inflate(R.layout.seller_signup, null);
        View view1 = view;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).show();

        TextView provSel = view.findViewById(R.id.provinceSelector);
        TextView province = view.findViewById(R.id.provincia);
        provSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View provinceSelector = getLayoutInflater().inflate(R.layout.provincia_selector, null);
                AlertDialog.Builder builderp = new AlertDialog.Builder(getActivity());
                //builderp.setView(provinceSelector).show();
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

        view.findViewById(R.id.sellerSignupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText sellerId = view1.findViewById(R.id.signupSellerIdEdit);
                EditText sellerName = view1.findViewById(R.id.signupSellerNameEdit);
                EditText sellerTel = view1.findViewById(R.id.signupSellerTelEdit);
                EditText sellerAddress = view1.findViewById(R.id.signupSellerAddressEdit);
                TextView sellerProvincia = view1.findViewById(R.id.provincia);
                EditText sellerPassword = view1.findViewById(R.id.signupSellerPasswordEdit);


                if(sellerId.getText().toString().isEmpty() || sellerName.getText().toString().isEmpty() || !telFormat(sellerTel.getText().toString()) ||
                    sellerAddress.getText().toString().isEmpty() || sellerProvincia.getText().toString().isEmpty() || sellerPassword.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Campi inseriti errati o mancanti", Toast.LENGTH_LONG).show();


                HashMap<String, String> map = new HashMap<>();
                map.put("id", sellerId.getText().toString());
                map.put("name", sellerName.getText().toString());
                map.put("password", sellerPassword.getText().toString());
                map.put("prov", sellerProvincia.getText().toString());
                map.put("telNum", sellerTel.getText().toString());
                map.put("address", sellerAddress.getText().toString());

                Call<ResponseEntity<Void>> call = retrofitInterface.executeSellerSignup(map);

                //Toast.makeText(getActivity(), "disjjcsjk", Toast.LENGTH_LONG).show();

                call.enqueue(new Callback<ResponseEntity<Void>>() {
                    @Override
                    public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                        //Toast.makeText(getActivity(), "disjjcsjk", Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Richiesta fallita", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private boolean telFormat(String tel){
        if(tel.length()!=13)
            return false;
        boolean isValid=true;
        for(int i=0; i<tel.length() && isValid; ++i){
            if(i==0 && tel.charAt(i)!='+')
                isValid = false;

            else{
                if(tel.charAt(i)<48 && tel.charAt(i)>57)
                    isValid=false;
            }
        }

        return isValid;
    }


    private void handleLogin(View view, RetrofitInterface retrofitInterface) {

        Log.d("mytag", "pippo");
        HashMap<String, String> map = new HashMap<>();
        EditText sellerId = view.findViewById(R.id.sellerIdEdit);
        EditText sellerName = view.findViewById(R.id.sellerNameEdit);
        EditText password = view.findViewById(R.id.sellerPasswordEdit);


        map.put("name", sellerName.getText().toString());
        map.put("id", sellerId.getText().toString());
        map.put("password", password.getText().toString());

        Call<ResponseEntity<Seller>> call = retrofitInterface.executeLoginSeller(map);

        call.enqueue(new Callback<ResponseEntity<Seller>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Seller>> call, Response<ResponseEntity<Seller>> response) {
                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if(response.body().getCode()==200){
                    Intent intent = new Intent(getContext(), SellerHome.class);
                    intent.putExtra("id", response.body().getEntity().getId());
                    intent.putExtra("password", response.body().getEntity().getPassword());
                    startActivity(intent);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(getParentFragment()).commit();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Seller>> call, Throwable t) {
                Toast.makeText(getContext(), "Ops", Toast.LENGTH_LONG).show();
            }
        });
    }
}