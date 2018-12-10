package com.dmsl.anyplace;

import com.dmsl.anyplace.ble.ScanInstance;
import com.dmsl.anyplace.nav.PoisModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InstanceDataHolder {
    public Map<PoisModel, ScanInstance> beacons;
    private static final InstanceDataHolder instance = new InstanceDataHolder();

    private InstanceDataHolder() {
        this.beacons = new HashMap<>();
    }

    public static InstanceDataHolder getInstance(){
        return instance;
    }

    public Map.Entry<PoisModel, ScanInstance> getClosestBeacon(){

        Calendar now = Calendar.getInstance();
        for (Map.Entry<PoisModel, ScanInstance> entry : beacons.entrySet()) {
            if(entry.getValue() != null) {
                if ((now.getTimeInMillis() - entry.getValue().getTimeRecieved().getTimeInMillis()) / 1000 > 5) {
                    System.out.println("Exceeded time tolerance! " + entry.getValue().getBluetoothDevice().getAddress() + " purged.");
                    entry.setValue(null);
                }
            }
        }

        Map.Entry<PoisModel, ScanInstance> closest = null;
        for (Map.Entry<PoisModel, ScanInstance> entry : beacons.entrySet()) {
            if(entry.getValue() != null) {
                if (closest == null || closest.getValue().getEstimatedDistance() > entry.getValue().getEstimatedDistance()) {
                    closest = entry;
                }
            }
        }
        return closest;
    }

    public float distanceTo(Map.Entry<PoisModel, ScanInstance> closest, double lat_a, double lng_a)
    {
        double lat_b = Double.valueOf(closest.getKey().lat.trim());
        double lng_b = Double.valueOf(closest.getKey().lng.trim());
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return (float) (distance * meterConversion);
    }
}
