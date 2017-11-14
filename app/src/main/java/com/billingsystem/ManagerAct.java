package com.billingsystem;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.databaseAdapter.BookAutoCompleteAdapter;
import com.entity.CorporateCustomer;

import org.w3c.dom.Text;

public class ManagerAct extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {
    private EditText edNumberOfPlates,edSpecialNote,edDeliveryDate;
    private Button btnSubmit;
    private Spinner spOrderType;
    private EditText edAddress,edContactNumber,edConcernPerson;
    private TextView txtSave,txtCustomerId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        edAddress=(EditText)findViewById(R.id.ed_man_address_id);
        edContactNumber=(EditText)findViewById(R.id.ed_man_contact_number_id);
        edConcernPerson=(EditText)findViewById(R.id.ed_man_concern_person_id);
        txtSave=(TextView)findViewById(R.id.txt_man_save_id);
        txtCustomerId=(TextView)findViewById(R.id.txt_man_customer_id);
        edDeliveryDate=(EditText)findViewById(R.id.ed_man_date_id);
        txtSave.setOnClickListener(this);
        final DelayAutoCompleteTextView delayAutoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.et_book_title);
        delayAutoCompleteTextView.setThreshold(3);
        delayAutoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(this,delayAutoCompleteTextView.getText().toString(),ManagerAct.this,txtSave)); // 'this' is Activity instance
        delayAutoCompleteTextView.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        delayAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CorporateCustomer book = (CorporateCustomer) adapterView.getItemAtPosition(position);
                if(book!=null){
                    delayAutoCompleteTextView.setText(book.getCorporateCustomerName());
                    edAddress.setText(book.getAddress());
                    edAddress.setEnabled(false);
                    edConcernPerson.setText(book.getConsernPerson());
                    edConcernPerson.setEnabled(false);
                    edContactNumber.setText(book.getContactNumber());
                    edContactNumber.setEnabled(false);
                    txtSave.setVisibility(View.VISIBLE);
                    txtSave.setText("EDIT");
                    txtCustomerId.setText(book.getCorporateCustomerId());
                }else {
                    txtSave.setVisibility(View.VISIBLE);
                    txtSave.setText("SAVE");
                }


            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.txt_man_save_id){
            if(txtSave.getText().equals("EDIT")){
                edAddress.setEnabled(true);
                edConcernPerson.setEnabled(true);
                edContactNumber.setEnabled(true);
                txtSave.setText("SAVE");
            }else if(txtSave.getText().equals("SAVE")){




            }
        }else if(v.getId()==R.id.ed_man_date_id){

        }

    }


}
