package com.billingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginAct extends AppCompatActivity {
    private Button btnCongigure,btnLogin;
    private EditText edIpaddress,edUserName,edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnCongigure=(Button)findViewById(R.id.btn_login_configure_id);
        btnLogin=(Button)findViewById(R.id.btn_loginid);
        edIpaddress=(EditText)findViewById(R.id.ed_login_serveripId);
        edUserName=(EditText)findViewById(R.id.ed_login_user_name_id);
        edPassword=(EditText)findViewById(R.id.ed_login_user_password_id);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAct.this,TableAct.class));

            }
        });

        btnCongigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
