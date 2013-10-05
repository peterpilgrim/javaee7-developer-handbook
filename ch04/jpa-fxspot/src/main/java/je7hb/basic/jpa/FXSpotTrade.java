/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package je7hb.basic.jpa;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The type FXSpotTrade
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class FXSpotTrade implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private boolean ccy1Buy;

    @Column(nullable = false)
    private String ccy1;

    @Column(nullable = false)
    private String ccy2;

    @Column(nullable = false)
    private BigDecimal amount1;

    @Column(nullable = false)
    private BigDecimal amount2;

    @Column(nullable = false)
    private BigDecimal spotRate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;

    @Column(nullable = false)
    private String counterparty;

    @Column(nullable = false)
    private String Portfolio;

    public FXSpotTrade() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCcy1Buy() {
        return ccy1Buy;
    }

    public void setCcy1Buy(boolean ccy1Buy) {
        this.ccy1Buy = ccy1Buy;
    }

    public String getCcy1() {
        return ccy1;
    }

    public void setCcy1(String ccy1) {
        this.ccy1 = ccy1;
    }

    public String getCcy2() {
        return ccy2;
    }

    public void setCcy2(String ccy2) {
        this.ccy2 = ccy2;
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public BigDecimal getSpotRate() {
        return spotRate;
    }

    public void setSpotRate(BigDecimal spotRate) {
        this.spotRate = spotRate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getPortfolio() {
        return Portfolio;
    }

    public void setPortfolio(String portfolio) {
        Portfolio = portfolio;
    }

    @Override
    public String toString() {
        return "FXSpotTrade{" +
                "id=" + id +
                ", ccy1Buy=" + ccy1Buy +
                ", ccy1='" + ccy1 + '\'' +
                ", ccy2='" + ccy2 + '\'' +
                ", amount1=" + String.format("%.2f", amount1 ) +
                ", amount2=" + String.format("%.2f", amount2 ) +
                ", spotRate=" + String.format("%.4f", spotRate ) +
                ", valueDate=" + valueDate +
                ", counterparty='" + counterparty + '\'' +
                ", Portfolio='" + Portfolio + '\'' +
                '}';
    }

    public boolean semanticallyEquals(FXSpotTrade that) {
        if (that == null ) return false;

        if (ccy1Buy != that.ccy1Buy) return false;
        if (Portfolio != null ? !Portfolio.equals(that.Portfolio) : that.Portfolio != null) return false;
        if (amount1 != null ? !amount1.equals(that.amount1) : that.amount1 != null) return false;
        if (amount2 != null ? !amount2.equals(that.amount2) : that.amount2 != null) return false;
        if (ccy1 != null ? !ccy1.equals(that.ccy1) : that.ccy1 != null) return false;
        if (ccy2 != null ? !ccy2.equals(that.ccy2) : that.ccy2 != null) return false;
        if (counterparty != null ? !counterparty.equals(that.counterparty) : that.counterparty != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (spotRate != null ? !spotRate.equals(that.spotRate) : that.spotRate != null) return false;
        if (valueDate != null ? !valueDate.equals(that.valueDate) : that.valueDate != null) return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FXSpotTrade)) return false;

        FXSpotTrade that = (FXSpotTrade) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
