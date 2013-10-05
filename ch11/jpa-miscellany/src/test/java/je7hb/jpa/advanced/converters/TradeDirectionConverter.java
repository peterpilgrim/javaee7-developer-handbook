package je7hb.jpa.advanced.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

enum Direction { BUY, SELL }

/**
 * The type TradeDirectionConverter
 *
 * @author Peter Pilgrim (peter)
 */
@Converter(autoApply = true)
public class TradeDirectionConverter
    implements AttributeConverter<Direction,String>
{
    @Override
    public String convertToDatabaseColumn(Direction attribute) {
        switch (attribute) {
            case BUY: return "P";
            default: return "S";
        }
    }

    @Override
    public Direction convertToEntityAttribute(String dbData) {
        dbData = dbData.trim().toLowerCase();
        if ( dbData.equals("P"))
            return Direction.BUY;
        else
            return Direction.SELL;
    }
}
