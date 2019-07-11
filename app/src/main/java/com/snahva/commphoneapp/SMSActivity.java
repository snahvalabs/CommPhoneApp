package com.snahva.commphoneapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends AppCompatActivity {
    Button btn_send, btn_sendNow;
    EditText ed_smsNumber, ed_smsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        btn_send = findViewById(R.id.btnSendSMS);
        btn_sendNow = findViewById(R.id.btnSendSMSNow);
        ed_smsNumber = findViewById(R.id.edSMSNumber);
        ed_smsContent = findViewById(R.id.edSMSContent);


        if(isTelephoneEnable()){
            Toast.makeText(this, "SIM CARD siap digunakan untuk layanan Telepon", Toast.LENGTH_SHORT).show();
            checkSMSPermission();
        }else{
            Toast.makeText(this, "SIM CARD tidak ada/belum teregistrasi", Toast.LENGTH_SHORT).show();
        }
    }

    public void doSendSMS(View view) {
        String nomorSMS = ed_smsNumber.getText().toString();
        String isiSMS = ed_smsContent.getText().toString();

        String formNomorSMS = String.format("smsto: %s", nomorSMS);
        Intent intentSMS = new Intent(Intent.ACTION_SENDTO);
        intentSMS.setData(Uri.parse(formNomorSMS));

//        //enrkipsi --> kodein
//        StringBuffer smsCipher;
//        smsCipher = encrypt(isiSMS,4);
//        intentSMS.putExtra("sms_body", smsCipher.toString());

        intentSMS.putExtra("sms_body", isiSMS);

        if(intentSMS.resolveActivity(getPackageManager()) != null){
            startActivity(intentSMS);
        }else{
            Toast.makeText(this, "Tidak dapat mengirimkan pesnn SMS", Toast.LENGTH_SHORT).show();
        }

    }

    public void doSendSMSNow(View view) {
        String nomorSMS = ed_smsNumber.getText().toString();
        String isiSMS = ed_smsContent.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
//        //enkripsi sms
//        StringBuffer smsCipher;
//        smsCipher = encrypt(isiSMS,4);
//        smsManager.sendTextMessage(nomorSMS, null, smsCipher.toString(),null,null);

        smsManager.sendTextMessage(nomorSMS, null, isiSMS,null,null);

        Toast.makeText(this, "SMS telah terkirim", Toast.LENGTH_SHORT).show();
    }

    private Boolean isTelephoneEnable(){
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(telephonyManager != null){
            if(telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY){
                return true;
            }
        }
        return false;
    }

    private void checkSMSPermission(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        }else{
            Toast.makeText(this, "Izin SEND SMS sudah diberikan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "SEND SMS Permission is Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "SEND SMS Permission is Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    //optional encryption message
    public static StringBuffer encrypt(String text, int s)
    {
        StringBuffer result= new StringBuffer();

        for (int i=0; i<text.length(); i++)
        {
            if (Character.isUpperCase(text.charAt(i)))
            {
                char ch = (char)(((int)text.charAt(i) +
                        s - 65) % 26 + 65);
                result.append(ch);
            }
            else
            {
                char ch = (char)(((int)text.charAt(i) +
                        s - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }
}
