package com.a10.mejabelajar.admin.model;

public enum LogStatus {
    BELUM_BAYAR("Belum Bayar"),
    VERIFIKASI("Pembayaran sedang diverifikasi"),
    LUNAS("LUNAS");

    private String displayName;

    LogStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
