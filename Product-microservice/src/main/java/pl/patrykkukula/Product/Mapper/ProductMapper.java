package pl.patrykkukula.Product.Mapper;

import jakarta.ws.rs.BadRequestException;
import pl.patrykkukula.Exception.ResourceNotFoundException;
import pl.patrykkukula.Product.Converter.AttributeConversionStrategy;
import pl.patrykkukula.Product.Converter.AttributeConversionStrategyFactory;
import pl.patrykkukula.Product.Dto.Product.ProductDto;
import pl.patrykkukula.Product.Dto.Product.ProductUpdateDto;
import pl.patrykkukula.Product.Dto.ProductAttribute.ProductAttributeDto;
import pl.patrykkukula.Product.Model.AttributeDefinition;
import pl.patrykkukula.Product.Model.Category;
import pl.patrykkukula.Product.Model.Product;
import pl.patrykkukula.Product.Model.ProductAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductMapper {

    private static final AttributeConversionStrategyFactory factory = new AttributeConversionStrategyFactory();

    public static Product mapProductDtoToProduct(ProductDto productDto, List<AttributeDefinition> attributes, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setAvailableAmount(productDto.getAvailableAmount());
        product.setCategory(category);


        List<ProductAttribute> productAttributes = new ArrayList<>();

        for (ProductAttributeDto productAttributeDto : productDto.getProductAttributes()) {
            AttributeDefinition attDefinition = attributes.stream()
                    .filter(att -> att.getName().equalsIgnoreCase(productAttributeDto.getName()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Product attribute", productAttributeDto.getName()));

            ProductAttribute productAttribute = new ProductAttribute();
            productAttribute.setAttributeDefinition(attDefinition);
            productAttribute.setProduct(product);
            AttributeConversionStrategy strategy = factory.getStrategy(attDefinition.getDataType());
            strategy.apply(productAttribute, productAttributeDto.getValue());
            productAttributes.add(productAttribute);
        }

        product.setAttributes(productAttributes);

        return product;
    }
    public static ProductDto mapProductToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setAvailableAmount(product.getAvailableAmount());

        List<ProductAttributeDto> attributes = product.getAttributes().stream()
                .map(att -> mapProductAttributeToProductAttributeDto(att))
                .toList();
        productDto.setProductAttributes(attributes);
        return productDto;
    }
    public static Product mapProductUpdateDtoToProduct(ProductUpdateDto productUpdateDto, Product product){
        Optional.ofNullable(productUpdateDto.getName()).ifPresent(product::setName);
        Optional.ofNullable(productUpdateDto.getDescription()).ifPresent(product::setDescription);
        return product;
    }
    private static ProductAttributeDto mapProductAttributeToProductAttributeDto(ProductAttribute productAttribute){
        ProductAttributeDto productAttributeDto = new ProductAttributeDto();
        productAttributeDto.setName(productAttribute.getName());

        switch (productAttribute.getAttributeDefinition().getDataType()){
            case STRING -> productAttributeDto.setValue(productAttribute.getValue());
            case DOUBLE -> productAttributeDto.setValue(String.valueOf(productAttribute.getNumericValue()));
            default -> throw new BadRequestException("Illegal data type: " + productAttribute.getAttributeDefinition().getDataType());
        }
        return productAttributeDto;
    }
}
