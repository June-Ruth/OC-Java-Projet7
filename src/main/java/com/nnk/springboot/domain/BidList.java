package com.nnk.springboot.domain;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;
import static com.nnk.springboot.constants.Number.THIRTY;

@Entity
@Table(name = "bid_list")
public class BidList {

    // TODO: Map columns in data table BIDLIST with corresponding java fields

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bid_list_id")
    private Integer bidListId;
    /**
     * Account.
     */
    @Column(name = "account")
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String account;
    /**
     * Type.
     */
    @Column(name = "type")
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String type;
    /**
     * Bid quantity.
     */
    @Column(name = "bid_quantity")
    private Double bidQuantity;
    /**
     * Ask quantity.
     */
    @Column(name = "ask_quantity")
    private Double askQuantity;
    /**
     * Bid.
     */
    @Column(name = "bid")
    private Double bid;
    /**
     * Ask.
     */
    @Column(name = "ask")
    private Double ask;
    /**
     * Benchmark.
     */
    @Column(name = "benchmark")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String benchmark;
    /**
     * Bid list date.
     */
    @Column(name = "bid_list_date")
    private Timestamp bidListDate;
    /**
     * Commentary.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "commentary")
    private String commentary;
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
