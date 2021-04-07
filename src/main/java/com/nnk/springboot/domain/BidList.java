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

import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;
import static com.nnk.springboot.constants.Number.SIX;
import static com.nnk.springboot.constants.Number.THIRTY;
import static com.nnk.springboot.constants.Number.TWO;

@Entity
@Table(name = "bid_list")
public class BidList {
    /**
     * Public constructor.
     * @param pAccount .
     * @param pType .
     * @param pBidQuantity .
     */
    public BidList(final String pAccount,
                   final String pType,
                   final Double pBidQuantity) {
        account = pAccount;
        type = pType;
        bidQuantity = pBidQuantity;
    }

    /**
     * Private empty constructor.
     */
    private BidList() {

    }

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
    @NotBlank(message = "Account is mandatory")
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String account;
    /**
     * Type.
     */
    @Column(name = "type")
    @NotBlank(message = "Type is mandatory")
    @Size(max = THIRTY, message = TOO_MUCH_CHARACTERS)
    private String type;
    /**
     * Bid quantity.
     */
    @Column(name = "bid_quantity")
    @Digits(integer = SIX, fraction = TWO)
    private Double bidQuantity;
    /**
     * Ask quantity.
     */
    @Column(name = "ask_quantity")
    @Digits(integer = SIX, fraction = TWO)
    private Double askQuantity;
    /**
     * Bid.
     */
    @Column(name = "bid")
    @Digits(integer = SIX, fraction = TWO)
    private Double bid;
    /**
     * Ask.
     */
    @Column(name = "ask")
    @Digits(integer = SIX, fraction = TWO)
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
    @Column(name = "source_list_id")
    private String sourceListId;
    /**
     * Side.
     */
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    @Column(name = "side")
    private String side;

    /**
     * Get id.
     * @return id.
     */
    public Integer getBidListId() {
        return bidListId;
    }

    /**
     * Set id.
     * @param pBidListId .
     */
    public void setBidListId(final Integer pBidListId) {
        bidListId = pBidListId;
    }

    /**
     * Get account.
     * @return account.
     */
    public String getAccount() {
        return account;
    }

    /**
     * Set account.
     * @param pAccount .
     */
    public void setAccount(final String pAccount) {
        account = pAccount;
    }

    /**
     * Get type.
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set type.
     * @param pType .
     */
    public void setType(final String pType) {
        type = pType;
    }

    /**
     * Get bid quantity.
     * @return bid quantity.
     */
    public Double getBidQuantity() {
        return bidQuantity;
    }

    /**
     * Set bid quantity.
     * @param pBidQuantity .
     */
    public void setBidQuantity(final Double pBidQuantity) {
        bidQuantity = pBidQuantity;
    }

    /**
     * Get ask quantity.
     * @return ask quantity.
     */
    public Double getAskQuantity() {
        return askQuantity;
    }

    /**
     * Set ask quantity.
     * @param pAskQuantity .
     */
    public void setAskQuantity(final Double pAskQuantity) {
        this.askQuantity = pAskQuantity;
    }

    /**
     * Get bid.
     * @return bid.
     */
    public Double getBid() {
        return bid;
    }

    /**
     * Set bid.
     * @param pBid .
     */
    public void setBid(final Double pBid) {
        bid = pBid;
    }

    /**
     * Get ask.
     * @return .
     */
    public Double getAsk() {
        return ask;
    }

    /**
     * Set ask.
     * @param pAsk .
     */
    public void setAsk(final Double pAsk) {
        ask = pAsk;
    }

    /**
     * Get benchmark.
     * @return benchmark.
     */
    public String getBenchmark() {
        return benchmark;
    }

    /**
     * Set benchmark.
     * @param pBenchmark .
     */
    public void setBenchmark(final String pBenchmark) {
        benchmark = pBenchmark;
    }

    /**
     * Get bid list date.
     * @return bid list date.
     */
    public Timestamp getBidListDate() {
        return bidListDate;
    }

    /**
     * Set bid list date.
     * @param pBidListDate
     */
    public void setBidListDate(final Timestamp pBidListDate) {
        bidListDate = pBidListDate;
    }

    /**
     * Get commentary.
     * @return commentary.
     */
    public String getCommentary() {
        return commentary;
    }

    /**
     * Set commentary.
     * @param pCommentary .
     */
    public void setCommentary(final String pCommentary) {
        commentary = pCommentary;
    }

    /**
     * Get security.
     * @return security.
     */
    public String getSecurity() {
        return security;
    }

    /**
     * Set security.
     * @param pSecurity .
     */
    public void setSecurity(final String pSecurity) {
        security = pSecurity;
    }

    /**
     * Get status.
     * @return status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status.
     * @param pStatus .
     */
    public void setStatus(final String pStatus) {
        status = pStatus;
    }

    /**
     * Get trader.
     * @return trader.
     */
    public String getTrader() {
        return trader;
    }

    /**
     * Set trader.
     * @param pTrader .
     */
    public void setTrader(final String pTrader) {
        trader = pTrader;
    }

    /**
     * Get book.
     * @return book.
     */
    public String getBook() {
        return book;
    }

    /**
     * Set book.
     * @param pBook .
     */
    public void setBook(final String pBook) {
        book = pBook;
    }

    /**
     * Get creation name.
     * @return creation name.
     */
    public String getCreationName() {
        return creationName;
    }

    /**
     * Set creation name.
     * @param pCreationName .
     */
    public void setCreationName(final String pCreationName) {
        creationName = pCreationName;
    }

    /**
     * Get creation date.
     * @return creation date.
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Set creation date.
     * @param pCreationDate .
     */
    public void setCreationDate(final Timestamp pCreationDate) {
        creationDate = pCreationDate;
    }

    /**
     * Get revision name.
     * @return revision name.
     */
    public String getRevisionName() {
        return revisionName;
    }

    /**
     * Set revision name.
     * @param pRevisionName .
     */
    public void setRevisionName(final String pRevisionName) {
        revisionName = pRevisionName;
    }

    /**
     * Get revision date.
     * @return revision date.
     */
    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    /**
     * Set revision date.
     * @param pRevisionDate .
     */
    public void setRevisionDate(final Timestamp pRevisionDate) {
        revisionDate = pRevisionDate;
    }

    /**
     * Get deal name.
     * @return deal name.
     */
    public String getDealName() {
        return dealName;
    }

    /**
     * Set deal name.
     * @param pDealName .
     */
    public void setDealName(final String pDealName) {
        dealName = pDealName;
    }

    /**
     * Get deal type.
     * @return deal type.
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * Set deal type.
     * @param pDealType .
     */
    public void setDealType(final String pDealType) {
        dealType = pDealType;
    }

    /**
     * Get source list id.
     * @return source list id.
     */
    public String getSourceListId() {
        return sourceListId;
    }

    /**
     * Set source list id.
     * @param pSourceListId .
     */
    public void setSourceListId(final String pSourceListId) {
        sourceListId = pSourceListId;
    }

    /**
     * Get side.
     * @return side.
     */
    public String getSide() {
        return side;
    }

    /**
     * Set side.
     * @param pSide .
     */
    public void setSide(final String pSide) {
        side = pSide;
    }
}
