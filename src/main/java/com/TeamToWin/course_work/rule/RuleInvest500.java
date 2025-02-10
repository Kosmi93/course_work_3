package com.TeamToWin.course_work.rule;

import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 *Реализация интерфейса RecommendationRuleSet для  продукта Invest 500
 * Пользователь использует как минимум один продукт с типом DEBIT.
 * Пользователь не использует продукты с типом INVEST.
 * Сумма пополнений продуктов с типом SAVING больше 1000 ₽.
 */

@Component
public class RuleInvest500 implements RecommendationRuleSet {



    private final RecommendationsRepository recommendationsRepository;

    public RuleInvest500(RecommendationsRepository recommendationsRepository) {
            this.recommendationsRepository = recommendationsRepository;
        }
        @Override
        public Optional<Recommendation> getRecommendations (UUID usersId){
            if (recommendationsRepository.haveProductType(usersId,"DEBIT")     &&
                    !recommendationsRepository.haveProductType(usersId,"INVEST") &&
                   recommendationsRepository.getSumAmount(usersId,"SAVING","DEPOSIT") > 1000
            )
            {
                return Optional.of(new Recommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                        "Invest 500",
                        "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                                "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет " +
                                "до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. " +
                                "Не упустите возможность разнообразить свой портфель, снизить риски и следить за " +
                                "актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой " +
                                "независимости!"));
            }
            return Optional.empty();
        }
    }

