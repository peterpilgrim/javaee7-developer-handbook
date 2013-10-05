package je7hb.jpa.advanced.converters;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * The type ForexTrade
 *
 * @author Peter Pilgrim (peter)
 */
@Entity @Table(name = "FXTRADE")
public class ForexTrade {
    /* ... */

    @Convert(converter=TradeDirectionConverter.class)
    Direction direction;

    /* ... */
}
