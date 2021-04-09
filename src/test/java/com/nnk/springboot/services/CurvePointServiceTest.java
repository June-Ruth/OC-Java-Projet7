package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceTest {

    @Mock
    private static CurvePointRepository curvePointRepository;

    private static CurvePointService curvePointService;

    private CurvePoint curvePoint1;
    private CurvePoint curvePoint2;

    private List<CurvePoint> curvePointList;

    @BeforeEach
    void beforeEach() {
        curvePointService = new CurvePointServiceImpl(curvePointRepository);
        curvePointList = new ArrayList<>();
        curvePoint1 = new CurvePoint(1, 11.00d, 11.00d);
        curvePoint2 = new CurvePoint(2, 20.00d, 22.00d);
        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);
    }

    // FIND CURVE POINT BY ID TEST //

    @Test
    void findCurvePointByIdExistsTest() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint1));
        curvePointService.findCurvePointById(1);
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void findCurvePointByIdNotExistsTest() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> curvePointService.findCurvePointById(1));
    }

    // FIND ALL CURVE POINT TEST //

    @Test
    void findAllCurvePointTest() {
        when(curvePointRepository.findAll()).thenReturn(curvePointList);
        curvePointService.findAllCurvePoint();
        verify(curvePointRepository, times(1)).findAll();
    }

    // SAVE CURVE POINT TEST //

    @Test
    void saveCurvePointTest() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1);
        curvePointService.saveCurvePoint(curvePoint1);
        verify(curvePointRepository, times(1)).save(curvePoint1);
    }

    // DELETE CURVE POINT TEST //

    @Test
    void deleteCurvePointTest() {
        doNothing().when(curvePointRepository).delete(any(CurvePoint.class));
        curvePointService.deleteCurvePoint(curvePoint1);
        verify(curvePointRepository, times(1)).delete(curvePoint1);
    }
}
