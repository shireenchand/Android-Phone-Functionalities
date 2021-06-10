package com.example.phonefunctionalities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Send_Message extends AppCompatActivity {

    private EditText et_phonennumber;
    private EditText et_messagetext;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        et_phonennumber = findViewById(R.id.phonenumber);
        et_messagetext = findViewById(R.id.messagetext);
        btn_send = findViewById(R.id.send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage(){
        if(ContextCompat.checkSelfPermission(Send_Message.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Send_Message.this,
                    new String[]{Manifest.permission.SEND_SMS},100);
        }
        else{
            String mNumber = et_phonennumber.getText().toString().trim();
            String mMessage = et_messagetext.getText().toString();

            if(!mNumber.equals("") && !mMessage.equals("")){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mNumber,null,mMessage,
                        null,null);
                Toast.makeText(Send_Message.this,"SMS Sent successfully",Toast.LENGTH_LONG).show();
                Log.d("check","SMS SENT");

            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode==100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }
        else{
            Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
        }
    }
}