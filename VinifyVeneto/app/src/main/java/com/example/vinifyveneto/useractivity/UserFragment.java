package com.example.vinifyveneto.useractivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vinifyveneto.R;
import com.example.vinifyveneto.SecondActivity;
import com.example.vinifyveneto.entity.ResponseEntity;
import com.example.vinifyveneto.entity.RetrofitEntity;
import com.example.vinifyveneto.entity.RetrofitInterface;
import com.example.vinifyveneto.model.User;
import com.example.vinifyveneto.selleractivity.SellerFragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private boolean isHidden=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        View view1 = view;

        view.findViewById(R.id.switch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentSlot, new SellerFragment());

                fragmentTransaction.commit();
            }
        });

        view.findViewById(R.id.userImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHidden){
                    ((ImageView)view1.findViewById(R.id.userImageView)).setImageResource(R.drawable.baseline_visibility_24);
                    ((EditText)view1.findViewById(R.id.passwordEditF)).setTransformationMethod(null);
                    ((EditText)view1.findViewById(R.id.passwordEditF)).setSelection(((EditText)view1.findViewById(R.id.passwordEditF)).getText().toString().length());
                    isHidden=!isHidden;
                }
                else{
                    ((ImageView)view1.findViewById(R.id.userImageView)).setImageResource(R.drawable.baseline_visibility_off_24);
                    ((EditText)view1.findViewById(R.id.passwordEditF)).setTransformationMethod(new PasswordTransformationMethod());
                    ((EditText)view1.findViewById(R.id.passwordEditF)).setSelection(((EditText)view1.findViewById(R.id.passwordEditF)).getText().toString().length());
                    isHidden=!isHidden;
                }
            }
        });

        view.findViewById(R.id.loginButtonF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mytag", "login cliccato");
                handleLogin(view1, RetrofitEntity.getRetrofit());
            }
        });

        view.findViewById(R.id.signupButtonF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignup(RetrofitEntity.getRetrofit());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void handleSignup(RetrofitInterface retrofitInterface) {
        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /*GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(getResources().getColor(R.color.white));
        gradientDrawable.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.your_corner_radius));
        view.setBackground(gradientDrawable);*/

        builder.setView(view).show();

        Button button = view.findViewById(R.id.signupButton);
        EditText userName = view.findViewById(R.id.signupUserNameEdit);
        EditText password = view.findViewById(R.id.signupPasswordEdit);
        Log.d("mytag", "fsfsfs");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkField(userName.getText().toString(), password.getText().toString())) {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", userName.getText().toString());
                    map.put("password", password.getText().toString());
                    Call<ResponseEntity<Void>> call = retrofitInterface.executeSignup(map);
                    call.enqueue(new Callback<ResponseEntity<Void>>() {
                        @Override
                        public void onResponse(Call<ResponseEntity<Void>> call, Response<ResponseEntity<Void>> response) {
                            ResponseEntity<Void> e = response.body();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseEntity<Void>> call, Throwable t) {
                            Toast.makeText(getActivity(), "Signup Fallito", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Credenziali errate", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void handleLogin(View view, RetrofitInterface retrofitInterface) {

        Log.d("mytag", "pippo");
        HashMap<String, String> map = new HashMap<>();
        EditText userName = view.findViewById(R.id.userNameEditF);
        EditText password = view.findViewById(R.id.passwordEditF);

        if (checkField(userName.getText().toString(), password.getText().toString())) {

            map.put("name", userName.getText().toString());
            map.put("password", password.getText().toString());

            Call<ResponseEntity<User>> call = retrofitInterface.executeLoginUser(map);

            call.enqueue(new Callback<ResponseEntity<User>>() {
                @Override
                public void onResponse(Call<ResponseEntity<User>> call, Response<ResponseEntity<User>> response) {
                    ResponseEntity<User> e = response.body();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    if (e.getCode() == 200) {
                        Intent intent = new Intent(requireContext().getApplicationContext(), UserHome.class);
                        intent.putExtra("userName", e.getEntity().getName());
                        intent.putExtra("password", e.getEntity().getPassword());
                        startActivity(intent);
                        getActivity().getSupportFragmentManager().beginTransaction().remove(getParentFragment()).commit();
                    }
                }

                @Override
                public void onFailure(Call<ResponseEntity<User>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Connessione non riuscita", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Credenziali errate", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkField(String username, String password){
        return !username.isEmpty() && !password.isEmpty();
    }
}

