package com.snahva.commphoneapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneActivity extends AppCompatActivity {

    Button btn_dial;
    Button btn_call;
    EditText ed_phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        btn_dial = findViewById(R.id.btnDial);
        btn_call = findViewById(R.id.btnCall);
        ed_phoneNumber = findViewById(R.id.edPhoneNumber);

        if(isTelephoneEnable()){
            Toast.makeText(this, "SIM CARD siap digunakan untuk layanan Telepon", Toast.LENGTH_SHORT).show();
            checkPhonePermission();
        }else{
            Toast.makeText(this, "SIM CARD tidak ada/belum teregistrasi", Toast.LENGTH_SHORT).show();
        }

    }

    public void doDial(View view) {
        String nomorTelepon = ed_phoneNumber.getText().toString();

        String formNomorTelepon = String.format("tel: %s", nomorTelepon);
        Intent intentDial = new Intent(Intent.ACTION_DIAL);
        intentDial.setData(Uri.parse(formNomorTelepon));

        if(intentDial.resolveActivity(getPackageManager()) != null){
            startActivity(intentDial);
        }else{
            Toast.makeText(this, "Tidak dapat melakukan Panggilan", Toast.LENGTH_SHORT).show();
        }
    }

    public void doCall(View view) {
        String nomorTelepon = ed_phoneNumber.getText().toString();

        String formNomorTelepon = String.format("tel: %s", nomorTelepon);
        Intent intentDial = new Intent(Intent.ACTION_CALL);
        intentDial.setData(Uri.parse(formNomorTelepon));

        if(intentDial.resolveActivity(getPackageManager()) != null){
            startActivity(intentDial);
        }else{
            Toast.makeText(this, "Tidak dapat melakukan Panggilan", Toast.LENGTH_SHORT).show();
        }
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

    private void checkPhonePermission(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }else{
            Toast.makeText(this, "Izin CALL PHONE sudah diberikan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.CALL_PHONE)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CALL PHONE Permission is Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "CALL PHONE Permission is Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
