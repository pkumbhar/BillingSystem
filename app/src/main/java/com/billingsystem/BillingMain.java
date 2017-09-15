package com.billingsystem;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;

public class BillingMain extends AppCompatActivity {
    private Button btnStartOrder;
    private DBAdapter dbAdapter;
    private ImageView imageView,imageViewTagLine;
    private Animation logoMoveAnimation;
    private LinearLayout parentLayout;
    private LinearLayout login_layout,lin_tag_id;
    private TextView tvForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_main);
        btnStartOrder=(Button)findViewById(R.id.btn_start_orderId);
        imageView=(ImageView)findViewById(R.id.image_logo_id);
        imageViewTagLine=(ImageView)findViewById(R.id.tv_rajjke_khawa_id);
        login_layout=(LinearLayout)findViewById(R.id.linearLayout_btnID);
        lin_tag_id=(LinearLayout)findViewById(R.id.lin_title_tag_id);
        tvForgetPassword=(TextView)findViewById(R.id.tv_reset_password_id);
        login_layout.setVisibility(View.GONE);
        parentLayout=(LinearLayout)findViewById(R.id.activity_billing_main);
        logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.slidedoen);
        imageView.startAnimation(logoMoveAnimation);
        btnStartOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStartOrder.getText().toString().equals("Login")) {
                    startActivity(new Intent(BillingMain.this,TableAct.class));
                    finish();

                } else {
                    dbAdapter = new DBAdapter(getApplicationContext());
                    Cursor mCursor = dbAdapter.getTableDetails(BaseTable.TABLELIST.USER_TABLE);
                    if (mCursor != null) {
                        // startActivity(new Intent(BillingMain.this,LoginAct.class));


                        Toast.makeText(getApplication(), "click", Toast.LENGTH_SHORT).show();
                        login_layout.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        btnStartOrder.setText("Login");

                        lin_tag_id.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillingMain.this,ForgetPassword.class));
                finish();
            }
        });

        /*
        animationlistner
         */
        logoMoveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {



            }

            @Override
            public void onAnimationEnd(Animation animation) {



               Animation logoRajjKeKhawa = AnimationUtils.loadAnimation(BillingMain.this, R.anim.fade_in);

                imageViewTagLine.startAnimation(logoRajjKeKhawa);
                imageViewTagLine.setVisibility(View.VISIBLE);
                btnStartOrder.setAnimation(logoRajjKeKhawa);
                btnStartOrder.setVisibility(View.VISIBLE);
                //Animation white_color = AnimationUtils.loadAnimation(BillingMain.this, R.anim.whitecolor);
                //parentLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                //parentLayout.startAnimation(white_color);

                ValueAnimator anim = ValueAnimator.ofInt(parentLayout.getDrawingCacheBackgroundColor(), Color.parseColor("#FF0000"));
                parentLayout.setBackgroundColor(Color.parseColor("#f7f7f7"));



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
