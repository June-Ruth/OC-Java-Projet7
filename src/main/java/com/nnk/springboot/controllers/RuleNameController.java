package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RuleNameController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(RuleNameController.class);
    /**
     * @see RuleNameService
     */
    private RuleNameService ruleNameService;

    /**
     * Autowired constructor.
     * @param pRuleNameService .
     */
    public RuleNameController(final RuleNameService pRuleNameService) {
        ruleNameService = pRuleNameService;
    }

    /**
     * Get all the ruleName.
     * @param model .
     * @return all ruleName.
     */
    @RequestMapping("/ruleName/list")
    public String home(final Model model) {
        List<RuleName> result = ruleNameService.findAllRuleName();
        model.addAttribute("ruleNameList", result);
        LOGGER.info("Find all ruleName, size = " + result.size());
        return "ruleName/list";
    }

    /**
     * Prepare the form to add a ruleName.
     * @param ruleName .
     * @return the form.
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(final RuleName ruleName) {
        LOGGER.info("Show form to add ruleName");
        return "ruleName/add";
    }

    /**
     * Add a new ruleName.
     * @param ruleName to add.
     * @param result .
     * @param model .
     * @return the new list of ruleName.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid final RuleName ruleName,
                           final BindingResult result,
                           final Model model) {
        LOGGER.info("Try to save new ruleName : " + ruleName);
        if (!result.hasErrors()) {
            RuleName ruleName1 = ruleNameService.saveRuleName(ruleName);
            LOGGER.info("Save ruleName : " + ruleName1);
            return "redirect:/ruleName/list";
        }
        LOGGER.error("Can't save following ruleName, "
                + "must be invalid data :" + ruleName);
        return "ruleName/add";
    }

    /**
     * Get the form to update ruleName.
     * @param id of the ruleName to update.
     * @param model .
     * @return form.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        LOGGER.info("Show the form to update ruleName with id " + id);
        model.addAttribute("ruleName", ruleNameService.findRuleNameById(id));
        return "ruleName/update";
    }

    /**
     * Update a ruleName.
     * @param id of the ruleName to update.
     * @param ruleName with updated information.
     * @param result .
     * @param model .
     * @return list with updated ruleName.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer id,
                               @Valid final RuleName ruleName,
                               final BindingResult result,
                               final Model model) {
        LOGGER.info("Try to update ruleName with id " + id);
        if (!result.hasErrors()) {
            RuleName ruleName1 = ruleNameService.findRuleNameById(id);
            ruleName1.setName(ruleName.getName());
            ruleName1.setDescription(ruleName.getDescription());
            ruleName1.setJson(ruleName.getJson());
            ruleName1.setTemplate(ruleName.getTemplate());
            ruleName1.setSqlStr(ruleName.getSqlStr());
            ruleName1.setSqlPart(ruleName.getSqlPart());
            ruleNameService.saveRuleName(ruleName1);
            LOGGER.info("Succes to update ruleName " + ruleName1);
            return "redirect:/ruleName/list";
        }
        ruleName.setId(id);
        LOGGER.error("Can't update following ruleName, "
                + "must be invalid data " + ruleName);
        return "ruleName/update";
    }

    /**
     * Delete a ruleName.
     * @param id of ruleName to delete.
     * @param model .
     * @return the new list of ruleName.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer id,
                               final Model model) {
        LOGGER.info("Try to delete ruleName with id : " + id);
        RuleName ruleName = ruleNameService.findRuleNameById(id);
        ruleNameService.deleteRuleName(ruleName);
        return "redirect:/ruleName/list";
    }
}
