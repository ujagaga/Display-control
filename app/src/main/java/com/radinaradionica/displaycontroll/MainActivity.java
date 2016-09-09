package com.radinaradionica.displaycontroll;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private class pinState{
        public Boolean pin1 = false;
        public Boolean pin2 = false;
        public Boolean pin3 = false;
        public Boolean pin4 = false;
        public Boolean pin5 = false;
        public Boolean pin6 = false;
        public Boolean pin7 = false;
    }

    private pinState PinState;

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
    private Handler delayedCommandHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinState = new pinState();

        btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnTouchListener(this);
        btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnTouchListener(this);
        btn3 = (Button)findViewById(R.id.button3);
        btn3.setOnTouchListener(this);
        btn4 = (Button)findViewById(R.id.button4);
        btn4.setOnTouchListener(this);
        btn5 = (Button)findViewById(R.id.button5);
        btn5.setOnTouchListener(this);
        btn6 = (Button)findViewById(R.id.button6);
        btn6.setOnTouchListener(this);
        btn7 = (Button)findViewById(R.id.button7);
        btn7.setOnTouchListener(this);

        btn_rescan = (Button)findViewById(R.id.button_rescan);
        btn_rescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });


        device_list = (ListView)findViewById(R.id.listView);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device

        if(myBluetooth == null){
            //Show a message that the device has no bluetooth adapter
            msg("Bluetooth Device Not Available");
            //finish apk
            finish();
        }else if(!myBluetooth.isEnabled()){
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }

        LoadDeviceAddr();

        if (address == null){
            // choose device
            pairedDevicesList();
        }else{
            new ConnectBT().execute(); //Call the class to connect
        }


    }

    @Override
    protected void onPause()
    {
        Disconnect();
        myBluetooth = null;
        finish();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                v.setAlpha(0.2f);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                v.setAlpha(1);
                v.invalidate();
                break;
            }
        }

        switch (v.getId()) {

            case R.id.button1: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin1 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin1 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

            case R.id.button2: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin2 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin2 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

            case R.id.button3: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin3 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin3 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

            case R.id.button4: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin4 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin4 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

            case R.id.button5: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin5 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin5 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

            case R.id.button6: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin6 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin6 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

            case R.id.button7: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        PinState.pin7 = true;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        PinState.pin7 = false;
                        scheduleStateChangeUpdate();
                        break;
                    }
                    default:
                        break;
                }
                break;
            }

        }

        return false;
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public void SaveDeviceAddress(){
        SharedPreferences prefs = getSharedPreferences("my_prefs", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("device_address", address);
        editor.commit();
    }

    private void LoadDeviceAddr(){
        SharedPreferences prefs = getSharedPreferences("my_prefs", MainActivity.MODE_PRIVATE);
        address = prefs.getString("device_address", null);
    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            msg("No Paired Bluetooth Devices Found.");
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        device_list.setAdapter(adapter);
        device_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
            {
                // Get the device MAC address, the last 17 chars in the View
                String info = ((TextView) v).getText().toString();
                address = info.substring(info.length() - 17);
                SaveDeviceAddress();
                new ConnectBT().execute(); //Call the class to reconnect
            }
        });

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if ((btSocket == null) || !isBtConnected)
                {
                    BluetoothDevice DeviceCommunicator = myBluetooth.getRemoteDevice(address); //connects to the device's address and checks if it's available
                    btSocket = DeviceCommunicator.createInsecureRfcommSocketToServiceRecord(myUUID); //create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            progress.dismiss();

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Try again.");
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
        }
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }

    }

    /* This is so you have time to press multiple buttons virtually simultaneously
    * and still send only one message
    * */
    private void scheduleStateChangeUpdate(){
        if (delayedCommandHandler == null) {
            delayedCommandHandler = new Handler();
            delayedCommandHandler.postDelayed(updateCurrentState, 20);
        }
    }

    Runnable updateCurrentState = new Runnable() {
        @Override public void run() {
            
            if (delayedCommandHandler != null) {
                delayedCommandHandler.removeCallbacks(updateCurrentState);
                delayedCommandHandler = null;
            }

            int devicePinOutput = 0;

            if (PinState.pin1) {
                devicePinOutput |= 1;
            } else {
                devicePinOutput &= ~1;
            }

            if (PinState.pin2) {
                devicePinOutput |= 2;
            } else {
                devicePinOutput &= ~2;
            }

            if (PinState.pin3) {
                devicePinOutput |= 4;
            } else {
                devicePinOutput &= ~4;
            }

            if (PinState.pin4) {
                devicePinOutput |= 8;
            } else {
                devicePinOutput &= ~8;
            }

            if (PinState.pin5) {
                devicePinOutput |= 16;
            } else {
                devicePinOutput &= ~16;
            }


            if (PinState.pin6) {
                devicePinOutput |= 32;
            } else {
                devicePinOutput &= ~32;
            }

            if (PinState.pin7) {
                devicePinOutput |= 64;
            } else {
                devicePinOutput &= ~64;
            }

    
            /* send in one sweep STOP_ALL message, current state and small duration.
             * NOTE: after the duration is expired, the HW will retain the last state.
             * If you wish to set output to 0, you must send either STOP_ALL message (:FF00)
             * or state 0 which will be applied here after all the buttons have been released
             * */
            sendBtMsg(":FE00:" + String.format("%02X", (devicePinOutput & 0xFF)) + "01");
        }
    };

    private void sendBtMsg(String msgToDevice){
        if (btSocket!=null){
            try{
                btSocket.getOutputStream().write(msgToDevice.getBytes());
            }
            catch (IOException e){
                msg("Error: No communication to device");
            }
        }
    }


}
