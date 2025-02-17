package com.TeamToWin.course_work.rule;

import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация интерфейса RecommendationRuleSet для  продукта Простой кредит
 * Пользователь не использует продукты с типом CREDIT.
 * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
 * Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
 */

@Component
public class RuleCreditSimple implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;

    public RuleCreditSimple(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendations(UUID usersId) {

        if (!recommendationsRepository.haveProductType(usersId, "CREDIT") &&
                recommendationsRepository.getSumAmount(usersId, "DEBIT", "DEPOSIT")
                        > recommendationsRepository.getSumAmount(usersId, "DEBIT", "WITHDRAW") &&
                recommendationsRepository.getSumAmount(usersId, "DEBIT", "DEPOSIT") > 100_000
        ) {
            return Optional.of(new Recommendation(
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    "Простой кредит",
                    "Откройте мир выгодных кредитов с нами!\n" +
                            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит" +
                            " — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и" +
                            " индивидуальный подход к каждому клиенту.\n" +
                            "Почему выбирают нас:\n" +
                            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки " +
                            "занимает всего несколько часов.\n" +
                            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном" +
                            " приложении.\n" +
                            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку " +
                            "недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"));

        }

        return Optional.empty();
    }

}
