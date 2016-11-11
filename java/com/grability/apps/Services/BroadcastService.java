package com.grability.apps.Services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.grability.apps.R;

/**
 * Created by ana on 08/11/2016.
 */

public class BroadcastService {

    private AlertDialog alertDialog;

    private BroadcastReceiver broadcastReceiver;

    private Activity activity;

    private int ID_CONNECTIVITY_ACTION = 1;

    public BroadcastService (final Activity activity) {

        this.activity = activity;

        // Crear Receiver
        broadcastReceiver = new BroadcastReceiver() {

            /**
             * Valida que acción se ejecuto
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                    /**
                     * Valida la conexión a internet
                     */
                    if (!estadoConexionInternet(intent)){
                        mostrarAlerta();
                    } else {
                        destruirAlerta();
                    }
                }
            }
        };
    }

    /**
     * Registrar Receiver
     */
    public void registrarReceiver () {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * Remover Receiver
     */
    public void removerReceiver () {
        activity.unregisterReceiver(broadcastReceiver);
    }

    /**
     * Valida si el usuario esta conectado a internet
     * @param intent
     * @return
     */
    public boolean estadoConexionInternet (Intent intent) {

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            return true;
        } else {

            Bundle extras = intent.getExtras();
            if (extras != null) {
                NetworkInfo info = extras.getParcelable("networkInfo");
                NetworkInfo.State state = info.getState();
                if (state == NetworkInfo.State.CONNECTED) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Muestra una alerta con un id: ID_CONNECTIVITY_ACTION
     */
    public void mostrarAlerta () {

        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }

        if (alertDialog ==  null) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_alert, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

    /**
     * Destruir alerta por id: ID_CONNECTIVITY_ACTION
     */
    public void destruirAlerta () {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
