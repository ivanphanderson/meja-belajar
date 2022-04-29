package com.a10.mejabelajar.course.model;

public enum CourseType {
    IPA("IPA"),
    IPS("IPS"),
    IPA_DAN_IPS("IPA DAN IPS");

    private String displayName;

    CourseType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
