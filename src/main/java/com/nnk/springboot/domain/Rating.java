package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;

@Entity
@Table(name = "rating")
public class Rating {
    /**
     * Public constructor.
     * @param moodysRating .
     * @param sandPRating .
     * @param fitchRating .
     * @param orderNumber .
     */
    public Rating(final String moodysRating,
                  final String sandPRating,
                  final String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    /**
     * Private empty constructor.
     */
    private Rating() { }

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    /**
     * Moodys rating.
     */
    @Column(name = "moodys_rating")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String moodysRating;
    /**
     * Sand P rating.
     */
    @Column(name = "sand_p_rating")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String sandPRating;
    /**
     * Fitch rating.
     */
    @Column(name = "fitch_rating")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

    /**
     * Getter.
     * @return id.
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
     * @return moodys rating.
     */
    public String getMoodysRating() {
        return moodysRating;
    }

    /**
     * Setter.
     * @param pMoodysRating .
     */
    public void setMoodysRating(final String pMoodysRating) {
        moodysRating = pMoodysRating;
    }

    /**
     * Getter.
     * @return sand p rating.
     */
    public String getSandPRating() {
        return sandPRating;
    }

    /**
     * Setter.
     * @param pSandPRating .
     */
    public void setSandPRating(final String pSandPRating) {
        sandPRating = pSandPRating;
    }

    /**
     * Getter.
     * @return fitch rating.
     */
    public String getFitchRating() {
        return fitchRating;
    }

    /**
     * Setter.
     * @param pFitchRating .
     */
    public void setFitchRating(final String pFitchRating) {
        fitchRating = pFitchRating;
    }

    /**
     * Getter.
     * @return order number.
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * Setter.
     * @param pOrderNumber .
     */
    public void setOrderNumber(final Integer pOrderNumber) {
        orderNumber = pOrderNumber;
    }
}
