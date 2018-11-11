package com.dmsl.anyplace;

import com.dmsl.anyplace.nav.PoisModel;

import java.util.HashMap;
import java.util.Map;

public class InstanceDataHolder {
    public Map<String, PoisModel> beacons;
    private static final InstanceDataHolder instance = new InstanceDataHolder();

    private InstanceDataHolder() {
        this.beacons = new HashMap<>();
    }

    public static InstanceDataHolder getInstance(){
        return instance;
    }
}
