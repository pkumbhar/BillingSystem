package com.billingsystem;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {
    private Button btn_forgetPassword;
    private TextView tvEmail;
    private TextInputEditText tvInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        btn_forgetPassword=(Button)findViewById(R.id.btn_forget_password_id);
        tvInputEditText=(TextInputEditText)findViewById(R.id.input_forget_password_email_id);
        tvEmail=(TextView)findViewById(R.id.tvinput_forget_password_email_id);
        btn_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
