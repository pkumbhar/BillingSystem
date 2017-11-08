package com.billingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ManagerAct extends AppCompatActivity {
    private EditText edNumberOfPlates,edSpecialNote;
    private Button btnSubmit;
    private Spinner spOrderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        edNumberOfPlates=(EditText)findViewById(R.id.ed_man_number_of_plates_id);
        edSpecialNote=(EditText)findViewById(R.id.ed_man_special_note_id);
        spOrderType=(Spinner)findViewById(R.id.sp_order_type_id);
        btnSubmit=(Button)findViewById(R.id.btn_man_submit_id);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spOrderType.getSelectedItemPosition()>0){
                    if(edNumberOfPlates.getText().toString().length()>0){
                        if(edSpecialNote.getText().toString().length()>0){

                            String oredrType=spOrderType.getSelectedItem().toString();
                            String numberOfPlates=edNumberOfPlates.getText().toString();
                            String specialNotes=edSpecialNote.getText().toString();



                        }else {
                            Toast.makeText(getApplicationContext(),"Special note should not be empty",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Enter plates quantity",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Select order type",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
