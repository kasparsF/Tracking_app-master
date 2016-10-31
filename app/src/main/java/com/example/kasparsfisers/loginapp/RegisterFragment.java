package com.example.kasparsfisers.loginapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends DialogFragment {
    EditText username, name, email, password, password2;
    Button btnRegister;
    String newUser, newEmail, newName, newPass, newPass2;
    SharedPreferencesUtils preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_frag, container, false);


        preferences = new SharedPreferencesUtils(getActivity());


        username = (EditText) rootView.findViewById(R.id.mUsername);
        name = (EditText) rootView.findViewById(R.id.mName);
        email = (EditText) rootView.findViewById(R.id.mEmail);
        password = (EditText) rootView.findViewById(R.id.mPass);
        password2 = (EditText) rootView.findViewById(R.id.mPass2);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                newUser = username.getText().toString();
                newEmail = email.getText().toString();
                newName = name.getText().toString();
                newPass = password.getText().toString();
                newPass2 = password2.getText().toString();

                if(validation(newUser,newEmail,newName,newPass,newPass2)) {

                        preferences.register(newUser,newPass,newEmail,newName);

                    Toast.makeText(getActivity(), R.string.registered, Toast.LENGTH_SHORT).show();
                    dismiss();

                }else{
                    Toast.makeText(getActivity(), R.string.registerError, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }

    public boolean validation(String newUser,String newEmail,String newName,String newPass,String newPass2){
        boolean valid =true;

        if(newUser.isEmpty() || newUser.length()>32){
            valid = false;
            username.setError(getString(R.string.validUsername));
        }

        if(newName.isEmpty()){
            valid = false;
            name.setError(getString(R.string.validName));
        }

        if(newEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
            valid = false;
            email.setError(getString(R.string.validEmail));
        }

        if(!Functions.isValidPassword(newPass)){
            valid = false;
            password.setError(getString(R.string.validPass));
        }
        if(!newPass2.equals(newPass)){
            valid = false;
            password2.setError(getString(R.string.matchPass));
        }
        return valid;
    }
}