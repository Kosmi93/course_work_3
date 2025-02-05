package com.TeamToWin.course_work.rule;

import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleCreditSimple implements RecommendationRuleSet{
    private final RecommendationsRepository recommendationsRepository;
    public RuleCreditSimple(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;}
    @Override
    public Optional<Recommendation> getRecommendations(UUID users_id) {
        // Вариант 1 - несколько запросов
        if (!recommendationsRepository.haveProductType(users_id,"CREDIT") &&
                recommendationsRepository.getSumAmount(users_id,"DEBIT","DEPOSIT")
                        > recommendationsRepository.getSumAmount(users_id,"DEBIT","WITHDRAW") &&
                recommendationsRepository.getSumAmount(users_id,"DEBIT","DEPOSIT") > 100_000
        )
        // Вариант 2 - один запрос
        if (recommendationsRepository.haveCreditSimple(users_id,users_id,users_id,users_id))
        {
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
