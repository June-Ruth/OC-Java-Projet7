package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static com.nnk.springboot.constants.ErrorMessage.FIELD_IS_MANDATORY;
import static com.nnk.springboot.constants.ErrorMessage.INVALID_NUMBER;
import static com.nnk.springboot.constants.Number.SIX;
import static com.nnk.springboot.constants.Number.TWO;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
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
    @Digits(integer = SIX , fraction = TWO, message = INVALID_NUMBER)
    private Double term;
    /**
     * Value.
     */
    @Column(name = "value")
    @Digits(integer = SIX , fraction = TWO, message = INVALID_NUMBER)
    @NotNull(message = FIELD_IS_MANDATORY)
    private Double value;
    /**
     * Creation date.
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;
}
