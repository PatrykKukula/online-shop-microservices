package pl.patrykkukula.Product.Converter;

import org.springframework.stereotype.Component;
import pl.patrykkukula.Constants.DataType;
import pl.patrykkukula.Product.Converter.Impl.DoubleConversion;
import pl.patrykkukula.Product.Converter.Impl.StringConversion;

import java.util.EnumMap;
import java.util.Map;

@Component
public class AttributeConversionStrategyFactory {

    private final Map<DataType, AttributeConversionStrategy> strategyMap = new EnumMap<>(DataType.class);

    public AttributeConversionStrategyFactory() {
        strategyMap.put(DataType.STRING, new StringConversion());
        strategyMap.put(DataType.DOUBLE, new DoubleConversion());
    }

    public AttributeConversionStrategy getStrategy(DataType dataType) {
        return strategyMap.get(dataType);
    }

}
