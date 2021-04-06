package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


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
    private Double term;
    /**
     * Value.
     */
    @Column(name = "value")
    private Double value;
    /**
     * Creation date.
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;
}
