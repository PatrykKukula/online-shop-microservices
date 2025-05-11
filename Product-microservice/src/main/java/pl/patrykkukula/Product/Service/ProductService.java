package pl.patrykkukula.Product.Service;

import pl.patrykkukula.Product.Dto.Product.ProductDto;
import pl.patrykkukula.Product.Dto.Product.ProductUpdateDto;
import pl.patrykkukula.Product.Dto.ProductAttribute.ProductAttributeDto;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Integer productId, ProductUpdateDto productUpdateDto);
    String setNewPrice(Integer productId, Double newPrice);
    void setAvailableAmount(Integer productId, Integer availableAmount);
    ProductDto findProductById(Integer productId);
    List<ProductDto> findAllProducts(String sortDirection, String sortProperty);
    List<ProductDto> findProductsByName(String name, String sortDirection, String sortProperty);
    List<ProductDto> findProductsByCategory(String category, String sortDirection, String sortProperty, String propertyValue);
    void deleteProduct(Integer productId);
    void addProductAttributeToProduct(Integer productId, ProductAttributeDto productAttributeDto);
    void removeProductAttributeFromProduct(Integer productId, String productAttributeName);
}
