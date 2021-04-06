package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;
import static com.nnk.springboot.constants.Number.THIRTY;


@Entity
@Table(name = "trade")
public class Trade {
    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trade_id")
    private Integer tradeId;
    /**
     * Account.
     */
    @Column(name = "account")
    @NotBlank(message = "account is mandatory")
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String account;
    /**
     * Type.
     */
    @Column(name = "type")
    @NotBlank(message = "type is mandatory")
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String type;
    /**
     * Buy quantity.
     */
    @Column(name = "buy_quantity")
    private Double buyQuantity;
    /**
     * Sell quantity.
     */
    @Column(name = "sell_quantity")
    private Double sellQuantity;
    /**
     * Buy price.
     */
    @Column(name = "buy_price")
    private Double buyPrice;
    /**
     * Sell price.
     */
    @Column(name = "sell_price")
    private Double sellPrice;
    /**
     * Benchmark.
     */
    @Column(name = "benchmark")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String benchmark;
    /**
     * Trade date.
     */
    @Column(name = "trade_date")
    private Timestamp tradeDate;

    /**
     * Security.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "security")
    private String security;
    /**
     * Status.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "status")
    private String status;
    /**
     * Trader.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "trader")
    private String trader;
    /**
     * Book.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "book")
    private String book;
    /**
     * Creation name.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "creation_name")
    private String creationName;
    /**
     * Creation date.
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;
    /**
     * Revision name.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "revision_name")
    private String revisionName;
    /**
     * Revision date.
     */
    @Column(name = "revision_date")
    private Timestamp revisionDate;
    /**
     * Deal name.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "deal_name")
    private String dealName;
    /**
     * Deal type.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "deal_type")
    private String dealType;
    /**
     * Source list id.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "source_list°id")
    private String sourceListId;
    /**
     * Side.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "side")
    private String side;
}
