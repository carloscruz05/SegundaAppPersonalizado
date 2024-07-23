package com.example.segundaapp;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNotification = findViewById(R.id.btnNotification);
        Button btnBasicToast = findViewById(R.id.btnBasicToast);
        Button btnCustomToast = findViewById(R.id.btnCustomToast);

        // Configurar Notificación
        btnNotification.setOnClickListener(v -> showNotification());

        // Configurar Toast básico
        btnBasicToast.setOnClickListener(v -> showBasicToast());

        // Configurar Toast personalizado
        btnCustomToast.setOnClickListener(v -> showCustomDialog());
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notificación")
                .setContentText("Esta es una notificación simple.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }

    private void showBasicToast() {
        Toast.makeText(this, "Este es un diálogo básico", Toast.LENGTH_SHORT).show();
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextPassword = dialogView.findViewById(R.id.editTextPassword);
        EditText editTextAccount = dialogView.findViewById(R.id.editTextAccount);
        EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String name = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();
            String account = editTextAccount.getText().toString();
            String phone = editTextPhone.getText().toString();
            Toast.makeText(this, "Datos: " + name + ", " + password + ", " + account + ", " + phone, Toast.LENGTH_LONG).show();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

