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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type FXSpotTradeBuilder
 *
 * @author Peter Pilgrim (peter)
 */
public class FXSpotTradeBuilder {

    private Map<String,Object> properties = new HashMap<>();
    private boolean created;

    private FXSpotTradeBuilder() {
        super();
    }

    public static FXSpotTradeBuilder create() {
        return new FXSpotTradeBuilder();
    }

    public FXSpotTradeBuilder id( Integer id ) {
        properties.put("id", id);
        return this;
    }

    public FXSpotTradeBuilder ccy1buy( Boolean v ) {
        properties.put("ccy1buy", v );
        return this;
    }

    public FXSpotTradeBuilder ccy1( String ccy ) {
        properties.put("ccy1", ccy);
        return this;
    }

    public FXSpotTradeBuilder ccy2( String ccy ) {
        properties.put("ccy2",ccy);
        return this;
    }

    public FXSpotTradeBuilder amount1( BigDecimal a ) {
        properties.put("amount1", a );
        return this;
    }

    public FXSpotTradeBuilder amount2( BigDecimal a ) {
        properties.put("amount2", a );
        return this;
    }

    public FXSpotTradeBuilder spotRate( BigDecimal sr ) {
        properties.put("spotRate", sr );
        return this;
    }

    public FXSpotTradeBuilder valueDate( Date v ) {
        properties.put("valueDate", v );
        return this;
    }

    public FXSpotTradeBuilder counterparty( String c ) {
        properties.put("counterparty", c );
        return this;
    }

    public FXSpotTradeBuilder portfolio( String p ) {
        properties.put("portfolio", p );
        return this;
    }

    public synchronized FXSpotTrade build() {
        if ( created ) {
            throw new IllegalStateException("builder already used");
        }
        else {
            FXSpotTrade t = new FXSpotTrade();
            t.setId((Integer) properties.get("id"));
            t.setCcy1Buy((Boolean) properties.get("ccy1buy"));
            t.setCcy1((String) properties.get("ccy1"));
            t.setCcy2((String) properties.get("ccy2"));
            t.setAmount1((BigDecimal) properties.get("amount1"));
            t.setAmount2((BigDecimal) properties.get("amount2"));
            t.setSpotRate((BigDecimal) properties.get("spotRate"));
            t.setValueDate((Date) properties.get("valueDate"));
            t.setCounterparty((String) properties.get("counterparty"));
            t.setPortfolio((String) properties.get("portfolio"));

            return t;
        }
    }
}
