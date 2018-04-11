package com.sadi.sreda;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class LoginActivity extends AppCompatActivity implements Callback<LoinResponse> {

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
                    loginUser(etUseName.getText().toString(),etPassword.getText().toString());
                    //startActivity(new Intent(con,MainActivity.class));
                    finish();
                }
            }
        });

    }



    private void loginUser(String userName,String pass) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL_login)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_login)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("username", userName);
            paramObject.put("password", pass);
            Call<LoinResponse> userCall = api.getUser(paramObject.toString());
            userCall.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<LoinResponse> call, Response<LoinResponse> response) {

        LoinResponse loinResponse =  new LoinResponse();

        loinResponse = response.body();

        if (loinResponse.getStatus()==1){
            Toast.makeText(con, "Login Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<LoinResponse> call, Throwable t) {

    }
}
