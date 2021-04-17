package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface RuleNameService {
    /**
     * Find a rule name by its id.
     * @param id .
     * @return rule name found - if not found, throws ElementNotFoundException
     * @see RuleName
     */
    RuleName findRuleNameById(Integer id);

    /**
     * Find all rule name.
     * @return a list with all rule name.
     * @see RuleName
     */
    List<RuleName> findAllRuleName();

    /**
     * Save a new rule name or update an existing rule name.
     * @param ruleName to save.
     * @return rule name saved.
     * @see RuleName
     */
    RuleName saveRuleName(RuleName ruleName);

    /**
     * Delete an existing rule name.
     * @param ruleName to delete.
     */
    void deleteRuleName(RuleName ruleName);
}
