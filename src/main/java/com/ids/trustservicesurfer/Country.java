package com.ids.trustservicesurfer;

public class Country implements Comparable<Country>{
    private final String name;
    private final String code;

    public Country (String _name, String _code) {
        if (_name == null || _code == null || _name.length() == 0 || _code.length() == 0)
            throw new IllegalArgumentException("One or more string is null or empty");
        name = _name;
        code = _code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return code + " : " + name;
    }

    @Override
    public int compareTo(Country country) {
        return this.code.compareTo(country.getCode());
    }
}
