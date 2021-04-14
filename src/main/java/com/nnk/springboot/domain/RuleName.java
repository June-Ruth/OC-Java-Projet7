package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;

@Entity
@Table(name = "rule_name")
public class RuleName {
    /**
     * Public constructor.
     * @param pName .
     * @param pDescription .
     * @param pJson .
     * @param pTemplate .
     * @param pSqlStr .
     * @param pSqlPart .
     */
    public RuleName(final String pName,
                    final String pDescription,
                    final String pJson,
                    final String pTemplate,
                    final String pSqlStr,
                    final String pSqlPart) {
        name = pName;
        description = pDescription;
        json = pJson;
        template = pTemplate;
        sqlStr = pSqlStr;
        sqlPart = pSqlPart;
    }

    /**
     * Private empty constructor.
     */
    private RuleName() { }

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    /**
     * Name.
     */
    @Column(name = "name")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String name;
    /**
     * Description.
     */
    @Column(name = "description")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String description;
    /**
     * Json.
     */
    @Column(name = "json")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String json;
    /**
     * Template.
     */
    @Column(name = "template")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String template;
    /**
     * Sql string.
     */
    @Column(name = "sql_str")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String sqlStr;
    /**
     * Sql part.
     */
    @Column(name = "sql_part")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String sqlPart;

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
     * @return name .
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     * @param pName .
     */
    public void setName(final String pName) {
        name = pName;
    }

    /**
     * Getter.
     * @return name.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter.
     * @param pDescription .
     */
    public void setDescription(final String pDescription) {
        description = pDescription;
    }

    /**
     * Getter.
     * @return json.
     */
    public String getJson() {
        return json;
    }

    /**
     * Setter.
     * @param pJson .
     */
    public void setJson(final String pJson) {
        json = pJson;
    }

    /**
     * Getter.
     * @return template.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Setter.
     * @param pTemplate .
     */
    public void setTemplate(final String pTemplate) {
        template = template;
    }

    /**
     * Getter.
     * @return sqlStr.
     */
    public String getSqlStr() {
        return sqlStr;
    }

    /**
     * Setter.
     * @param pSqlStr .
     */
    public void setSqlStr(final String pSqlStr) {
        sqlStr = pSqlStr;
    }

    /**
     * Getter.
     * @return sqlPart.
     */
    public String getSqlPart() {
        return sqlPart;
    }

    /**
     * Setter.
     * @param pSqlPart .
     */
    public void setSqlPart(final String pSqlPart) {
        sqlPart = pSqlPart;
    }
}
