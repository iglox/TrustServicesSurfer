package com.ids.trustservicesurfer;

public class ServiceType implements Comparable<ServiceType> {
    private final String type;

    public ServiceType(String _type) {
        if (_type == null || _type.length() == 0)
            throw new IllegalArgumentException("String is null or empty");
        type = _type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public int compareTo(ServiceType serviceType) {
        return this.type.compareTo(serviceType.getType());
    }
}
