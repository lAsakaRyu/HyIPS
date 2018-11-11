package com.dmsl.anyplace.nav;

public class BeaconModel{
    private int RSSI;
    private String lat;
    private String lng;
    private String mac;

    public BeaconModel(PoisModel poisModel) {
        this.mac = poisModel.description;
        this.lat = poisModel.lat;
        this.lng = poisModel.lng;
        this.RSSI = -1000;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }

    public double getLat() {
        return Double.parseDouble(lat);
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public double getLng() {
        return Double.parseDouble(lng);
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
