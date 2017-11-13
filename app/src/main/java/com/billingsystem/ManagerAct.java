package com.billingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.databaseAdapter.BookAutoCompleteAdapter;
import com.entity.CorporateCustomer;

public class ManagerAct extends AppCompatActivity {
    private EditText edNumberOfPlates,edSpecialNote;
    private Button btnSubmit;
    private Spinner spOrderType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        final DelayAutoCompleteTextView delayAutoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.et_book_title);
        delayAutoCompleteTextView.setThreshold(3);
        delayAutoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(this,delayAutoCompleteTextView.getText().toString(),ManagerAct.this)); // 'this' is Activity instance
        delayAutoCompleteTextView.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        delayAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CorporateCustomer book = (CorporateCustomer) adapterView.getItemAtPosition(position);
                delayAutoCompleteTextView.setText(book.getCorporateCustomerName());
            }
        });


    }
}
