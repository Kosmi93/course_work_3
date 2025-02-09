package com.TeamToWin.course_work.rule;

import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
/**
 *Реализация интерфейса RecommendationRuleSet для  продукта Top Saving
 * Пользователь использует как минимум один продукт с типом DEBIT.
 * Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽
 *   ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
 * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
 */

@Component
public class RuleTopSaving implements RecommendationRuleSet{

    private final RecommendationsRepository recommendationsRepository;
    public RuleTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }
    @Override
    public Optional<Recommendation> getRecommendations(UUID usersId) {
        if (recommendationsRepository.haveProductType(usersId,"DEBIT") &&
                (recommendationsRepository.getSumAmount(usersId,"DEBIT","DEPOSIT") >= 50_000 ||
                        recommendationsRepository.getSumAmount(usersId,"SAVING","DEPOSIT") >= 50_000 &&
                recommendationsRepository.getSumAmount(usersId,"DEBIT","DEPOSIT") >
                        recommendationsRepository.getSumAmount(usersId,"SAVING","DEPOSIT")
        ))
        {
            return Optional.of(new Recommendation(
                    UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                    "Top Saving",
                    "Откройте свою собственную «Копилку» с нашим банком!\n«Копилка» — это уникальный банковский " +
                            "инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. " +
                            "Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                            "\nПреимущества «Копилки»:\n" +
                            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет " +
                            "автоматически переводить определенную сумму на ваш счет.\n" +
                            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс " +
                            "накопления и корректируйте стратегию при необходимости.\n" +
                            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним " +
                            "возможен только через мобильное приложение или интернет-банкинг.\n" +
                            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"));
        }
        return Optional.empty();
    }
}
