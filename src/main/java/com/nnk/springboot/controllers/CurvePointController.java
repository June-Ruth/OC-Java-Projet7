package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(CurvePointController.class);
    /**
     * @see CurvePointService
     */
    private CurvePointService curvePointService;

    /**
     * Autowired constructor.
     * @param pCurvePointService .
     */
    public CurvePointController(final CurvePointService pCurvePointService) {
        curvePointService = pCurvePointService;
    }

    /**
     * Get all the curve point.
     * @param model .
     * @return all curve point.
     */
    @RequestMapping("/curvePoint/list")
    public String home(final Model model) {
        List<CurvePoint> result = curvePointService.findAllCurvePoint();
        model.addAttribute("curvePointList", result);
        LOGGER.info("Find all curve point, size = " + result.size());
        return "curvePoint/list";
    }

    /**
     * Prepare the form to add a curve point.
     * @param curvePoint .
     * @return the form.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(final BidList curvePoint) {
        LOGGER.info("Show form to add curve point");
        return "curvePoint/add";
    }

    /**
     * Add a new curve point.
     * @param curvePoint to add.
     * @param result .
     * @param model .
     * @return the new list of curve point.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid final CurvePoint curvePoint,
                           final BindingResult result,
                           final Model model) {
        LOGGER.info("Try to save new curve point : " + curvePoint);
        if (!result.hasErrors()) {
            CurvePoint curvePoint1 = curvePointService.saveCurvePoint(curvePoint);
            LOGGER.info("Save curve point : " + curvePoint1);
            return "redirect:/curvePoint/list";
        }
        LOGGER.error("Can't save following bid list, "
                + "must be invalid data :" + curvePoint);
        return "curvePoint/add";
    }

    /**
     * Get the form to update curve point.
     * @param id of the bid list to update.
     * @param model .
     * @return form.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        LOGGER.info("Show the form to update curve point with id " + id);
        model.addAttribute("curvePoint", curvePointService.findCurvePointById(id));
        return "curvePoint/update";
    }

    /**
     * Update a curve point.
     * @param id of the curve point to update.
     * @param curvePoint with updated information.
     * @param result .
     * @param model .
     * @return list with updated bid list.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") final Integer id,
                            @Valid final CurvePoint curvePoint,
                            final BindingResult result,
                            final Model model) {
        LOGGER.info("Try to update bid list with id " + id);
        if (!result.hasErrors()) {
            CurvePoint curvePoint1 = curvePointService.findCurvePointById(id);
            curvePoint1.setCurveId(curvePoint.getCurveId());
            curvePoint1.setTerm(curvePoint.getTerm());
            curvePoint1.setValue(curvePoint.getValue());
            curvePointService.saveCurvePoint(curvePoint1);
            LOGGER.info("Succes to update curve point " + curvePoint1);
            return "redirect:/curvePoint/list";
        }
        curvePoint.setId(id);
        LOGGER.error("Can't update following curve point, "
                + "must be invalid data " + curvePoint);
        return "curvePoint/update";
    }

    /**
     * Delete a curve point.
     * @param id of curve point to delete.
     * @param model .
     * @return the new list of curve point.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") final Integer id,
                            final Model model) {
        LOGGER.info("Try to delete bid list with id : " + id);
        CurvePoint curvePoint = curvePointService.findCurvePointById(id);
        curvePointService.deleteCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }
}
