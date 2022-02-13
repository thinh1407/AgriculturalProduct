package com.orfarmweb.constaint;

public enum Status {

    PROCESSING("PROCESSING", 0), APPROVED("APPROVED", 1), CANCELED("CANCELED", 2), DELIVERED("DELIVERED", 3);
    private final String type;
    private final Integer value;

    Status(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }
}
