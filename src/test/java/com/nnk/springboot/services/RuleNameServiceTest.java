package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class RuleNameServiceTest {

    @Mock
    private static RuleNameRepository ruleNameRepository;

    private static RuleNameService ruleNameService;

    private RuleName rating1;
    private RuleName rating2;

    private List<RuleName> ratingList;

    @BeforeEach
    void beforeEach() {
        ruleNameService = new RuleNameServiceImpl(ruleNameRepository);
        ratingList = new ArrayList<>();
        rating1 = new RuleName("name1", "description1", "json1", "template1", "sql1", "sqlPArt1");
        rating2 = new RuleName("name2", "description2", "json2", "template2", "sql2", "sqlPArt2");
        ratingList.add(rating1);
        ratingList.add(rating2);
    }

    // FIND RULE NAME BY ID TEST //

    @Test
    void findRuleNameByIdExistsTest() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(rating1));
        ruleNameService.findRuleNameById(1);
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void findRuleNameByIdNotExistsTest() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> ruleNameService.findRuleNameById(1));
    }

    // FIND ALL RULE NAME TEST //

    @Test
    void findAllRuleNameTest() {
        when(ruleNameRepository.findAll()).thenReturn(ratingList);
        ruleNameService.findAllRuleName();
        verify(ruleNameRepository, times(1)).findAll();
    }

    // SAVE RULE NAME TEST //

    @Test
    void saveRuleNameTest() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rating1);
        ruleNameService.saveRuleName(rating1);
        verify(ruleNameRepository, times(1)).save(rating1);
    }

    // DELETE RULE NAME TEST //

    @Test
    void deleteRuleNameTest() {
        doNothing().when(ruleNameRepository).delete(any(RuleName.class));
        ruleNameService.deleteRuleName(rating1);
        verify(ruleNameRepository, times(1)).delete(rating1);
    }
}
