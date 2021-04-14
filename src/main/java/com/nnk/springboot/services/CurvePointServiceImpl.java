package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(BidListServiceImpl.class);
    /**
     * @see CurvePointRepository
     */
    private final CurvePointRepository curvePointRepository;

    /**
     * Autowired constructor.
     * @param pCurvePointRepository .
     */
    public CurvePointServiceImpl(
            final CurvePointRepository pCurvePointRepository) {
        curvePointRepository = pCurvePointRepository;
    }

    /**
     * @see CurvePointService .
     * @param id .
     * @return curve point id
     */
    @Override
    public CurvePoint findCurvePointById(final Integer id) {
        LOGGER.info("Try to find curve point with id : " + id);
        CurvePoint result = curvePointRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No curve point found for id : " + id));
        LOGGER.info("Get curve point with id : "
                + id + "\n result : " + result);
        return result;
    }

    /**
     * @see CurvePointService .
     * @return list of curve point
     */
    @Override
    public List<CurvePoint> findAllCurvePoint() {
        LOGGER.info("Try to get all curve point");
        List<CurvePoint> result = curvePointRepository.findAll();
        LOGGER.info("Get all curve point :" + result);
        return result;
    }

    /**
     * @see CurvePointService .
     * @param curvePoint to save.
     * @return curve point.
     */
    @Transactional
    @Override
    public CurvePoint saveCurvePoint(final CurvePoint curvePoint) {
        LOGGER.info("Try to save curve point : " + curvePoint);
        CurvePoint result = curvePointRepository.save(curvePoint);
        LOGGER.info("Save curve point : " + result);
        return result;
    }

    /**
     * @see CurvePointService .
     * @param curvePoint to delete.
     */
    @Transactional
    @Override
    public void deleteCurvePoint(final CurvePoint curvePoint) {
        LOGGER.info("Try to delete curve point : " + curvePoint);
        curvePointRepository.delete(curvePoint);
        LOGGER.info("Delete curve point");
    }
}
