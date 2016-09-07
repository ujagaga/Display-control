package com.radinaradionica.displaycontroll;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn_rescan;

    private ListView device_list;
    String address = null;
    private ProgressDialog progress;

    // Bluetooth
    private BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private Set<BluetoothDevice> pairedDevices;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}
