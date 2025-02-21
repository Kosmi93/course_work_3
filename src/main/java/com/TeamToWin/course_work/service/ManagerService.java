package com.TeamToWin.course_work.service;


import com.TeamToWin.course_work.model.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final BuildProperties buildProperties;

    public ManagerService(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public Info getInfo() {
        return new Info(buildProperties.getName(), buildProperties.getVersion());
    }

    public void clearCaches() {
    }
}
