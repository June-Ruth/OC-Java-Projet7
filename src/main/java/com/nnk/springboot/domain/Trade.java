package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.nnk.springboot.constants.ErrorMessage.FIELD_IS_MANDATORY;
import static com.nnk.springboot.constants.ErrorMessage.INVALID_NUMBER;
import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;
import static com.nnk.springboot.constants.Number.SIX;
import static com.nnk.springboot.constants.Number.THIRTY;
import static com.nnk.springboot.constants.Number.TWO;


@Entity
@Table(name = "trade")
public class Trade {
    /**
     * Public constructor.
     * @param pAccount .
     * @param pType .
     * @param pBuyQuantity .
     */
    public Trade(final String pAccount,
                 final String pType,
                 final Double pBuyQuantity) {
        account = pAccount;
        type = pType;
        buyQuantity = pBuyQuantity;
    }

    /**
     * Private empty constructor.
     */
    private Trade() { }

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
    @NotBlank(message = FIELD_IS_MANDATORY)
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String account;
    /**
     * Type.
     */
    @Column(name = "type")
    @NotBlank(message = FIELD_IS_MANDATORY)
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String type;
    /**
     * Buy quantity.
     */
    @Column(name = "buy_quantity")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
    private Double buyQuantity;
    /**
     * Sell quantity.
     */
    @Column(name = "sell_quantity")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
    private Double sellQuantity;
    /**
     * Buy price.
     */
    @Column(name = "buy_price")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
    private Double buyPrice;
    /**
     * Sell price.
     */
    @Column(name = "sell_price")
    @Digits(integer = SIX, fraction = TWO, message = INVALID_NUMBER)
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
    @Column(name = "source_list_id")
    private String sourceListId;
    /**
     * Side.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "side")
    private String side;

    /**
     * Getter.
     * @return id.
     */
    public Integer getTradeId() {
        return tradeId;
    }

    /**
     * Setter.
     * @param pTradeId .
     */
    public void setTradeId(final Integer pTradeId) {
        tradeId = pTradeId;
    }

    /**
     * Getter.
     * @return account.
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter.
     * @param pAccount .
     */
    public void setAccount(final String pAccount) {
        account = pAccount;
    }

    /**
     * Getter.
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter.
     * @param pType .
     */
    public void setType(final String pType) {
        type = pType;
    }

    /**
     * Getter.
     * @return buy quantity.
     */
    public Double getBuyQuantity() {
        return buyQuantity;
    }

    /**
     * Setter.
     * @param pBuyQuantity .
     */
    public void setBuyQuantity(final Double pBuyQuantity) {
        buyQuantity = pBuyQuantity;
    }

    /**
     * Getter.
     * @return sell quantity.
     */
    public Double getSellQuantity() {
        return sellQuantity;
    }

    /**
     * Setter.
     * @param pSellQuantity .
     */
    public void setSellQuantity(final Double pSellQuantity) {
        sellQuantity = pSellQuantity;
    }

    /**
     * Getter.
     * @return buy price.
     */
    public Double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Setter.
     * @param pBuyPrice .
     */
    public void setBuyPrice(final Double pBuyPrice) {
        buyPrice = pBuyPrice;
    }

    /**
     * Getter.
     * @return sell price.
     */
    public Double getSellPrice() {
        return sellPrice;
    }

    /**
     * Setter.
     * @param pSellPrice .
     */
    public void setSellPrice(final Double pSellPrice) {
        this.sellPrice = pSellPrice;
    }

    /**
     * Getter.
     * @return benchmark.
     */
    public String getBenchmark() {
        return benchmark;
    }

    /**
     * Setter.
     * @param pBenchmark .
     */
    public void setBenchmark(final String pBenchmark) {
        benchmark = pBenchmark;
    }

    /**
     * Getter.
     * @return trade date.
     */
    public Timestamp getTradeDate() {
        return tradeDate;
    }

    /**
     * Setter.
     * @param pTradeDate .
     */
    public void setTradeDate(final Timestamp pTradeDate) {
        tradeDate = pTradeDate;
    }

    /**
     * Getter.
     * @return security.
     */
    public String getSecurity() {
        return security;
    }

    /**
     * Setter.
     * @param pSecurity .
     */
    public void setSecurity(final String pSecurity) {
        this.security = pSecurity;
    }

    /**
     * Getter.
     * @return security.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter.
     * @param pStatus .
     */
    public void setStatus(final String pStatus) {
        status = pStatus;
    }

    /**
     * Getter.
     * @return trader.
     */
    public String getTrader() {
        return trader;
    }

    /**
     * Setter.
     * @param pTrader .
     */
    public void setTrader(final String pTrader) {
        this.trader = pTrader;
    }

    /**
     * Getter.
     * @return book.
     */
    public String getBook() {
        return book;
    }

    /**
     * Setter.
     * @param pBook .
     */
    public void setBook(final String pBook) {
        book = pBook;
    }

    /**
     * Getter.
     * @return creation name.
     */
    public String getCreationName() {
        return creationName;
    }

    /**
     * Setter.
     * @param pCreationName .
     */
    public void setCreationName(final String pCreationName) {
        creationName = pCreationName;
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

    /**
     * Getter.
     * @return revision name.
     */
    public String getRevisionName() {
        return revisionName;
    }

    /**
     * Setter.
     * @param pRevisionName .
     */
    public void setRevisionName(final String pRevisionName) {
        revisionName = pRevisionName;
    }

    /**
     * Getter.
     * @return revision date.
     */
    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    /**
     * Setter.
     * @param pRevisionDate .
     */
    public void setRevisionDate(final Timestamp pRevisionDate) {
        revisionDate = pRevisionDate;
    }

    /**
     * Getter.
     * @return deal name.
     */
    public String getDealName() {
        return dealName;
    }

    /**
     * Setter.
     * @param pDealName .
     */
    public void setDealName(final String pDealName) {
        dealName = pDealName;
    }

    /**
     * Getter.
     * @return deal type.
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * Setter.
     * @param pDealType .
     */
    public void setDealType(final String pDealType) {
        dealType = pDealType;
    }

    /**
     * Getter.
     * @return source List id.
     */
    public String getSourceListId() {
        return sourceListId;
    }

    /**
     * Setter.
     * @param pSourceListId .
     */
    public void setSourceListId(final String pSourceListId) {
        sourceListId = pSourceListId;
    }

    /**
     * Getter.
     * @return side.
     */
    public String getSide() {
        return side;
    }

    /**
     * Setter.
     * @param pSide .
     */
    public void setSide(final String pSide) {
        side = pSide;
    }
}
