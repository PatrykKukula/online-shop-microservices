package pl.patrykkukula.Product.Converter.Impl;

import pl.patrykkukula.Product.Converter.AttributeConversionStrategy;
import pl.patrykkukula.Product.Model.ProductAttribute;

public class StringConversion implements AttributeConversionStrategy {
    @Override
    public void apply(ProductAttribute attribute, String value) {
        attribute.setValue(value);
    }
}
