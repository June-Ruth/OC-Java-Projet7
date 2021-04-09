package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {
    /**
     * Find a curve point by its id.
     * @param id .
     * @return curve point found - if not found, throws ElementNotFoundException
     * @see com.nnk.springboot.domain.CurvePoint
     */
    CurvePoint findCurvePointById(Integer id);

    /**
     * Find all Curve Point.
     * @return a list with all curve point.
     * @see CurvePoint
     */
    List<CurvePoint> findAllCurvePoint();

    /**
     * Save a new Curve Point or update an existing Curve Point.
     * @param curvePoint to save.
     * @return curve point saved.
     * @see CurvePoint
     */
    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    /**
     * Delete an existing curve point.
     * @param curvePoint to delete.
     */
    void deleteCurvePoint(CurvePoint curvePoint);
}
