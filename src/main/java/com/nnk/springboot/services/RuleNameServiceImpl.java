package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RuleNameServiceImpl implements RuleNameService {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(RatingServiceImpl.class);
    /**
     * @see RuleNameRepository
     */
    private final RuleNameRepository ruleNameRepository;

    /**
     * Autowired constructor.
     * @param pRuleNameRepository .
     */
    public RuleNameServiceImpl(final RuleNameRepository pRuleNameRepository) {
        ruleNameRepository = pRuleNameRepository;
    }

    /**
     * @see RuleNameService .
     * @param id .
     * @return rule name.
     */
    @Override
    public RuleName findRuleNameById(final Integer id) {
        LOGGER.info("Try to find curve point with id : " + id);
        RuleName result = ruleNameRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No rule name found for id : " + id));
        LOGGER.info("Get rule name with id : " + id + "\n result : " + result);
        return result;
    }

    /**
     * @see RuleNameService .
     * @return list of rule name
     */
    @Override
    public List<RuleName> findAllRuleName() {
        LOGGER.info("Try to get all rule name");
        List<RuleName> result = ruleNameRepository.findAll();
        LOGGER.info("Get all rule name :" + result);
        return result;
    }

    /**
     * @see RuleNameService .
     * @param ruleName to save.
     * @return rule name.
     */
    @Transactional
    @Override
    public RuleName saveRuleName(final RuleName ruleName) {
        LOGGER.info("Try to save ruleName : " + ruleName);
        RuleName result = ruleNameRepository.save(ruleName);
        LOGGER.info("Save ruleName : " + result);
        return result;
    }

    /**
     * @see RatingService .
     * @param ruleName to delete.
     */
    @Transactional
    @Override
    public void deleteRuleName(final RuleName ruleName) {
        LOGGER.info("Try to delete ruleName : " + ruleName);
        ruleNameRepository.delete(ruleName);
        LOGGER.info("Delete ruleName");
    }
}
