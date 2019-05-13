package com.m10798.fitzapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class SMSActivity extends AppCompatActivity {

    EditText eTextMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);


        eTextMsg = (EditText) findViewById(R.id.etextMsg);
        final Button btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                sendSMS();
            }
        });
    }
    public void sendSMS()
    {
        finish();
        SmsManager sm = SmsManager.getDefault();
        String number = "9947755133";
        String msg = eTextMsg.getText().toString();
        sm.sendTextMessage(number, null, msg, null, null);

    }
}
