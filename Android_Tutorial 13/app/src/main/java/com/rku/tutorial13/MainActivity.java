package com.rku.tutorial13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtNumber, edtTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNumber = findViewById(R.id.edtNumber);
        edtTextMessage = findViewById(R.id.edtTextMessage);
    }

    public void callToNumber(View view) {
        if (isCallPermissionAllowed()) {
            call();
        }
    }

    private boolean isCallPermissionAllowed() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "PERMISSION IS GRANTED");
                return true;
            } else {
                Log.v("TAG", "PERMISSION IS REVOKED");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 11);
                return false;
            }
        } else {
            Log.v("TAG", "PERMISSION IS GRANTED");
            return true;
        }
    }

    public void call() {
        String phoneNumber = edtNumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    public void sendTextMessage(View view) {
        if (isSMSPermissionAllowed()) {
            sms();
        }
    }

    private boolean isSMSPermissionAllowed() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "PERMISSION IS GRANTED");
                return true;
            } else {
                Log.v("TAG", "PERMISSION IS REVOKED");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 12);
                return false;
            }
        } else {
            Log.v("TAG", "PERMISSION IS GRANTED");
            return true;
        }
    }

    public void sms() {
        String phoneNumber = edtNumber.getText().toString();
        String smsText = edtTextMessage.getText().toString();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, smsText, null, null);
        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
    }

    public void onRequestPermissionsResult(int requestCode, String permission[],
                                           int[] grantResult) {
        switch (requestCode) {
            case 11:
                if (grantResult.length > 0
                        && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                    Toast.makeText(getApplicationContext(), "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            case 12:

                break;
        }
    }
}