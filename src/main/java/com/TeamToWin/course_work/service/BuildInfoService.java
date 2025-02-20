package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.configuration.BuildInfo;
import com.TeamToWin.course_work.model.Info;
import org.springframework.stereotype.Service;

@Service
public class BuildInfoService {



    public Info getBuildInfo() {
        return new Info();
    }
}
