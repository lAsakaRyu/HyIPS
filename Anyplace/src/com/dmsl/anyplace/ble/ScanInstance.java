package com.dmsl.anyplace.ble;


import android.bluetooth.BluetoothDevice;

import java.util.Calendar;

public class ScanInstance{
    private transient BluetoothDevice bluetoothDevice;
    private int rssi;
    private byte[] scanrecord;
    private Calendar timeRecieved;

    public ScanInstance(BluetoothDevice bluetoothDevice, int rssi, byte[] scanrecord) {
        this.bluetoothDevice = bluetoothDevice;
        this.rssi = rssi;
        this.scanrecord = scanrecord;
        timeRecieved = Calendar.getInstance();
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public byte[] getScanrecord() {
        return scanrecord;
    }

    public void setScanrecord(byte[] scanrecord) {
        this.scanrecord = scanrecord;
    }

    public int getTxPower() {
        return -61;
    }

    public Calendar getTimeRecieved() {
        return timeRecieved;
    }

    public void setTimeRecieved(Calendar timeRecieved) {
        this.timeRecieved = timeRecieved;
    }

    public double getEstimatedDistance(){
        return (double)Math.round((Math.pow(10d, ((double) this.getTxPower() - this.rssi) / (10 * 2))) * 100) / 100;
    }
}
