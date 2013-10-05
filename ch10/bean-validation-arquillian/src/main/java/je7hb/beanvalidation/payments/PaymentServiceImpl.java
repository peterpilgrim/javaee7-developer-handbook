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

package je7hb.beanvalidation.payments;

import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The type PaymentServiceImpl
 *
 * @author Peter Pilgrim
 */
@Stateless
public class PaymentServiceImpl  {

    @NotNull
    @SecureReceipt
    public Receipt payEntity(
            @NotNull @Valid Account account,
            @NotNull @Size(min = 5, max = 32) String counterparty,
            @NotNull @DecimalMin("0.0") BigDecimal amount)
    {
        System.out.printf("%s.payEntity( %s, %s, %.2f )\n",
                getClass().getSimpleName(), account.getAccount(),
                counterparty, amount.doubleValue() );
        String msg;
        if ( counterparty.contains("Bridgetown"))
            msg = "SEC123";
        else
            msg = "Boo! Hoo!";
        Account acct2 = new Account(
                account.getAccount(),
                account.getAmount().subtract(amount));
        return new Receipt(msg,counterparty,acct2, new Date());
    }
}
