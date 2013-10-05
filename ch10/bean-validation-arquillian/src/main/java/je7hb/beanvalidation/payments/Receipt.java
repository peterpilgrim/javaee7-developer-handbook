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

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The type Receipt
 *
 * @author Peter Pilgrim
 */
public class Receipt {
    @NotEmpty private final String message;
    @NotEmpty private final String counterparty;
    @Valid private final Account account;
    @NotNull private final Date purchaseDate;

    public Receipt(String message, String counterparty,
                   Account account, Date purchaseDate) {
        this.message = message;
        this.counterparty = counterparty;
        this.account = account;
        this.purchaseDate = purchaseDate;
    }

    public String getMessage() {
        return message;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public Account getAccount() {
        return account;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
}
