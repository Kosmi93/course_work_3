package com.TeamToWin.course_work.model;

public class Info {
    private final String name;
    private final String version;

    public Info(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
