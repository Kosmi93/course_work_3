package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.model.Info;
import org.springframework.boot.info.BuildProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private BuildProperties buildProperties;

    @BeforeEach
    public void setUp() {
        buildProperties = mock(BuildProperties.class);
        managerService = new ManagerService(buildProperties);
    }

    @Test
    public void testGetInfo() {
        when(buildProperties.getName()).thenReturn("MyApplication");
        when(buildProperties.getVersion()).thenReturn("1.0.0");

        Info info = managerService.getInfo();
        assertNotNull(info);
        assertEquals("MyApplication", info.getName());
        assertEquals("1.0.0", info.getVersion());

        verify(buildProperties, times(1)).getName();
        verify(buildProperties, times(1)).getVersion();
    }

    @Test
    public void testClearCaches() {
        managerService.clearCaches();
        assertTrue(true);
    }
}
