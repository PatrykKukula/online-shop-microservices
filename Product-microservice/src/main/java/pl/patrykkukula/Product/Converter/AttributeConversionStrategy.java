package pl.patrykkukula.Product.Converter;

import pl.patrykkukula.Product.Model.ProductAttribute;

public interface AttributeConversionStrategy {
    void apply(ProductAttribute attribute, String value);
}
