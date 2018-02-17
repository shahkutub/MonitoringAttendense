package com.sadi.sreda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class LoginActivity extends AppCompatActivity {

    Context con;
    private EditText etUseName,etPassword;
    private Button btnSignIn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        con=this;
        initUi();
    }

    private void initUi() {
        etUseName = (EditText)findViewById(R.id.etUseName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etUseName.getText().toString())){
                    Toast.makeText(con, "Input user name.", Toast.LENGTH_SHORT).show();
                    etUseName.requestFocus();
                }else if(TextUtils.isEmpty(etPassword.getText().toString())){
                    Toast.makeText(con, "Input user password.", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }else {
                    startActivity(new Intent(con,MainActivity.class));
                    finish();
                }
            }
        });


    }
}
