//package com.dmsl.anyplace.ble;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.ListActivity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothClass;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.le.BluetoothLeScanner;
//import android.bluetooth.le.ScanCallback;
//import android.bluetooth.le.ScanFilter;
//import android.bluetooth.le.ScanResult;
//import android.bluetooth.le.ScanSettings;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Handler;
//import android.os.ParcelUuid;
//import android.os.Parcelable;
//import android.os.RemoteException;
//import android.preference.PreferenceManager;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.method.ScrollingMovementMethod;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.altbeacon.beacon.Beacon;
//import org.altbeacon.beacon.BeaconConsumer;
//import org.altbeacon.beacon.BeaconManager;
//import org.altbeacon.beacon.BeaconParser;
//import org.altbeacon.beacon.RangeNotifier;
//import org.altbeacon.beacon.Region;
//
//import java.math.BigInteger;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.Vector;
//
//@RequiresApi(api = Build.VERSION_CODES.M)
//public class MainActivity extends AppCompatActivity {
//
//    ArrayList<ScanInstance> beacon=new ArrayList<ScanInstance>();
//    ScanInstance selectedBeacon=new ScanInstance();
//    private String TAG="Peripheral found";
//    final int txPower=-59;
//    BluetoothManager btManager;
//    BluetoothAdapter btAdapter;
//    BluetoothLeScanner btScanner;
//    Button startScanningButton;
//    Button stopScanningButton;
//    private ListView peripheralListView;
//    List<String> deviceaddress=new ArrayList<>();
//    private final static int REQUEST_ENABLE_BT = 1;
//    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
//
//
//
//    @Override
//    public void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        peripheralListView = (ListView) findViewById(R.id.device_list);
//        //peripheralTextView.setMovementMethod(new ScrollingMovementMethod());
//
//        startScanningButton = (Button) findViewById(R.id.StartScanButton);
//        startScanningButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startScanning();
//            }
//        });
//
//        Button sensor=(Button)findViewById(R.id.StartScanButton);
//        sensor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startScanning();
//            }
//        });
//        stopScanningButton = (Button) findViewById(R.id.StopScanButton);
//        stopScanningButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                stopScanning();
//            }
//        });
//        stopScanningButton.setVisibility(View.INVISIBLE);
//
//        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
//        btAdapter = btManager.getAdapter();
//        btScanner = btAdapter.getBluetoothLeScanner();
//
//
//        if (btAdapter != null && !btAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
//        }
//
//        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
//        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("This app needs location access");
//            builder.setMessage("Please grant location access so this app can detect peripherals.");
//            builder.setPositiveButton(android.R.string.ok, null);
//            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
//                }
//            });
//            builder.show();
//        }
//    }
//
//    // Device scan callback.
//    private BluetoothAdapter.LeScanCallback leScanCallback =
//            new BluetoothAdapter.LeScanCallback() {
//                @Override
//                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//
//                    //check to see whether the list contain similar device
//                for(int a=0;a<deviceaddress.size();a++) {
//                    if(deviceaddress.get(a).equals(device.getAddress())){
//                        beacon.remove(a);
//                        Log.d(TAG, "same device: " + device.getAddress());
//                        deviceaddress.remove(a);
//                    }
//                }
//               // if(device.getAddress().contains("00:A0:50")) {
//
//                    beacon.add(new ScanInstance(device,rssi,scanRecord) );
//                    Log.d(TAG, "scan record: " + scanRecord.toString() + " device name: " + device.getAddress());
//                    deviceaddress.add(device.getAddress());
//                    Log.d(TAG, "Device name: " + device.getAddress());
//                //}
//
//        }
//
//    };
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_COARSE_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    System.out.println("coarse location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//                return;
//            }
//        }
//    }
//
//    public void startScanning() {
//        //peripheralTextView.setText("");
//        startScanningButton.setVisibility(View.INVISIBLE);
//        stopScanningButton.setVisibility(View.VISIBLE);
//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                btAdapter.startLeScan(leScanCallback);
//            }
//        });
//
//    }
//
//    public void stopScanning() {
//        System.out.println("stopping scanning");
//        //peripheralTextView.append("Stopped Scanning");
//        startScanningButton.setVisibility(View.VISIBLE);
//        stopScanningButton.setVisibility(View.INVISIBLE);
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                btAdapter.stopLeScan(leScanCallback);
//            }
//        });
//    }
//
//}
//
//
