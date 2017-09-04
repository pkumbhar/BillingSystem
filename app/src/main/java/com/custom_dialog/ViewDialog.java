package com.custom_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.billingsystem.R;

public class ViewDialog {

  public void showDialog(Activity activity, String msg) {

    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);

    dialog.setContentView(R.layout.custom_order_xml);

    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
    text.setText(msg);


    Button okButton = (Button) dialog.findViewById(R.id.btn_dialog);
    Button cancleButton = (Button) dialog.findViewById(R.id.btn_dialog_cancelid);
    final EditText edittext_tv = (EditText) dialog.findViewById(R.id.edit_quantity_id);

    okButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          dialog.dismiss();
        }
    });
    cancleButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });


    dialog.show();

    }
}