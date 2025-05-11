package pl.patrykkukula.Product.Converter.Impl;

import jakarta.ws.rs.BadRequestException;
import pl.patrykkukula.Product.Converter.AttributeConversionStrategy;
import pl.patrykkukula.Product.Model.ProductAttribute;

public class DoubleConversion implements AttributeConversionStrategy {
    @Override
    public void apply(ProductAttribute attribute, String value) {
        try{
        attribute.setNumericValue(Double.parseDouble(value));
    }
        catch(NumberFormatException ex){
            throw new BadRequestException("Not a valid numeric value");
        }
    }
}
