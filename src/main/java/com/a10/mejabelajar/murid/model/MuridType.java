package com.a10.mejabelajar.murid.model;

public enum MuridType {
    IPA("IPA"),
    IPS("IPS"),
    IPA_DAN_IPS("IPA DAN IPS");

    private String displayName;

    MuridType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

