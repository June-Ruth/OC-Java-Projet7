package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.sql.Timestamp;

import static com.nnk.springboot.constants.ErrorMessage.INVALID_NUMBER;
import static com.nnk.springboot.constants.Number.SIX;
import static com.nnk.springboot.constants.Number.TWO;


@Entity
@Table(name = "curve_point")
public class CurvePoint {
    /**
     * Public constructor.
     * @param pCurveId .
     * @param pTerm .
     * @param pValue .
     */
    public CurvePoint(final Integer pCurveId,
                      final Double pTerm,
                      final Double pValue) {
        curveId = pCurveId;
        term = pTerm;
        value = pValue;
    }

    /**
     * Private empty constructor.
     */
    private CurvePoint() { }

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    /**
     * Curve id.
     */
    @Column(name = "curve_id")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
    private Integer curveId;
    /**
     * As of date.
     */
    @Column(name = "as_of_date")
    private Timestamp asOfDate;
    /**
     * Term.
     */
    @Column(name = "term")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
    private Double term;
    /**
     * Value.
     */
    @Column(name = "value")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
    private Double value;
    /**
     * Creation date.
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;

    /**
     * Getter.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter.
     * @param pId .
     */
    public void setId(final Integer pId) {
        id = pId;
    }

    /**
     * Getter.
     * @return id.
     */
    public Integer getCurveId() {
        return curveId;
    }

    /**
     * Setter.
     * @param pCurveId .
     */
    public void setCurveId(final Integer pCurveId) {
        curveId = pCurveId;
    }

    /**
     * Getter.
     * @return as of date.
     */
    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    /**
     * Setter.
     * @param pAsOfDate .
     */
    public void setAsOfDate(final Timestamp pAsOfDate) {
        asOfDate = pAsOfDate;
    }

    /**
     * Getter.
     * @return term.
     */
    public Double getTerm() {
        return term;
    }

    /**
     * Setter.
     * @param pTerm .
     */
    public void setTerm(final Double pTerm) {
        term = pTerm;
    }

    /**
     * Getter.
     * @return value.
     */
    public Double getValue() {
        return value;
    }

    /**
     * Setter.
     * @param pValue .
     */
    public void setValue(final Double pValue) {
        value = pValue;
    }

    /**
     * Getter.
     * @return creation date.
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Setter.
     * @param pCreationDate .
     */
    public void setCreationDate(final Timestamp pCreationDate) {
        creationDate = pCreationDate;
    }
}
