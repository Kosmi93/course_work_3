package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserDTO;
import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.listener.TelegramBotUpdatesListener;
import com.TeamToWin.course_work.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TelegramBotServiceTest {

    @InjectMocks
    private TelegramBotService telegramBotService;

    @Mock
    private TelegramBotUpdatesListener listener;

    @Mock
    private RecommendationsService recommendationsService;

    private UserDTO mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new UserDTO();
        mockUser.setId(UUID.randomUUID());
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
    }

    @Test
    public void testGetTask_StartCommand() {
        telegramBotService.getTask(123L, "/start");
        verify(listener).sendingMessage(123L, "Здравствуйте! Для получения информации по банковским продуктам введите \"/recommend <username>\"");
    }

    @Test
    public void testGetTask_RecommendCommand_UserFound() {
        when(recommendationsService.getUser("john_doe")).thenReturn(Arrays.asList(mockUser));
        Optional<UserRecommendation> mockUserRecommendation = Optional.of(new UserRecommendation(mockUser.getId(), Arrays.asList(
                new Recommendation(UUID.randomUUID(), "Product 1", "Description of Product 1"),
                new Recommendation(UUID.randomUUID(), "Product 2", "Description of Product 2")
        )));
        when(recommendationsService.getRecommendationsRule(mockUser.getId())).thenReturn(mockUserRecommendation);

        telegramBotService.getTask(123L, "/recommend john_doe");
        String expectedMessage = "Здравствуйте John Doe\nНовые продукты для вас:\n\n" +
                "Product 1\nDescription of Product 1\n\n" +
                "Product 2\nDescription of Product 2\n\n";
        verify(listener).sendingMessage(123L, expectedMessage);
    }

    @Test
    public void testGetTask_RecommendCommand_UserNotFound() {
        when(recommendationsService.getUser("unknown_user")).thenReturn(Arrays.asList());
        telegramBotService.getTask(123L, "/recommend unknown_user");
        verify(listener).sendingMessage(123L, "Пользователь не найден");
    }

    @Test
    public void testGetTask_InvalidCommand() {
        telegramBotService.getTask(123L, "/unknownCommand");
        verify(listener).sendingMessage(123L, "Неизвестная команда");
    }
}
