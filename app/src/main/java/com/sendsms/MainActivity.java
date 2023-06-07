package com.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;

import android.view.View;
import android.widget.Toast;

import com.sendsms.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SmsManager smsManager;
    String smsFor, smsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    //Solicitando a permissão
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else {
                    sendSms();
                }

            }

        });
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 1) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // A permissão foi concedida pelo usuário, envie a mensagem SMS
                    sendSms();
                } else {
                    // A permissão foi negada pelo usuário, trate esse caso conforme necessário
                    Toast.makeText(MainActivity.this, "Permissão de envio de SMS negada", Toast.LENGTH_SHORT).show();
                }
            }
        }
        private void sendSms () {
            try {
                smsFor = binding.editSmsFor.getText().toString();
                smsMessage = binding.editSmsMessage.getText().toString();

                smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(smsFor, null, smsMessage, null, null);

                Toast.makeText(MainActivity.this, "SMS enviado", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "SMS não enviado", Toast.LENGTH_SHORT).show();
            }
        }
    }
