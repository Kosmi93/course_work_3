package com.TeamToWin.course_work.configuration;

import org.springframework.beans.factory.annotation.Value;


public class BuildInfo {
    @Value("${build.artifact}")
    private String artifact;

    @Value("${build.group}")
    private String group;

    @Value("${build.name}")
    private String name;

    @Value("${build.version}")
    private String version;

    public String getArtifact() {
        return artifact;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
