package pl.patrykkukula.Product.Service.Impl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.patrykkukula.Common.CommonUtils;
import pl.patrykkukula.Constants.DataType;
import pl.patrykkukula.Exception.ResourceNotFoundException;
import pl.patrykkukula.Product.Converter.AttributeConversionStrategy;
import pl.patrykkukula.Product.Converter.AttributeConversionStrategyFactory;
import pl.patrykkukula.Product.Dto.Product.ProductDto;
import pl.patrykkukula.Product.Dto.Product.ProductUpdateDto;
import pl.patrykkukula.Product.Dto.ProductAttribute.ProductAttributeDto;
import pl.patrykkukula.Product.Mapper.ProductMapper;
import pl.patrykkukula.Product.Model.AttributeDefinition;
import pl.patrykkukula.Product.Model.Category;
import pl.patrykkukula.Product.Model.Product;
import pl.patrykkukula.Product.Model.ProductAttribute;
import pl.patrykkukula.Product.Repository.AttributeDefinitionRepository;
import pl.patrykkukula.Product.Repository.CategoryRepository;
import pl.patrykkukula.Product.Repository.ProductRepository;
import pl.patrykkukula.Product.Service.ProductService;
import java.util.List;
import java.util.Optional;
import static pl.patrykkukula.Product.Mapper.ProductMapper.*;
import static pl.patrykkukula.Validation.ValidationUtils.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AttributeDefinitionRepository attributeDefinitionRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeConversionStrategyFactory factory;

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        Category category = findCategoryByName(productDto.getCategory());

        List<AttributeDefinition> attributes = attributeDefinitionRepository
                .findAllByCategory(category.getName(), Sort.by("name"));

        Product product = mapProductDtoToProduct(productDto, attributes, category);
        Product savedProduct = productRepository.save(product);

        return mapProductToProductDto(savedProduct);
    }
    @Override
    @Transactional
    public ProductDto updateProduct(Integer productId, ProductUpdateDto productUpdateDto) {
        Product product = fetchProductById(productId);

        if (productUpdateDto.getCategory() != null) {
            Category category = findCategoryByName(productUpdateDto.getCategory());
            product.setCategory(category);
        }

        Product updatedProduct = productRepository.save(mapProductUpdateDtoToProduct(productUpdateDto, product));
        return mapProductToProductDto(updatedProduct);
    }
    @Override
    @Transactional
    public String setNewPrice(Integer productId, Double newPrice) {
        if (newPrice == null || newPrice<=0) throw new IllegalArgumentException("Price must be greater than 0");
        Product product = fetchProductById(productId);
        product.setPrice(newPrice);
        productRepository.save(product);
        return "Price updated successfully: " + product.getPrice() + "for product: " + product.getName();
    }
    @Override
    @Transactional
    public void setAvailableAmount(Integer productId, Integer availableAmount) {
        if (availableAmount == null || availableAmount<0) throw new IllegalArgumentException("Amount must be greater at least 0");
        Product product = fetchProductById(productId);
        product.setAvailableAmount(availableAmount);
        productRepository.save(product);
    }
    @Override
    public ProductDto findProductById(Integer productId) {
        Product product = fetchProductById(productId);
        return mapProductToProductDto(product);
    }
    @Override
    public List<ProductDto> findAllProducts(String sortDirection, String sortProperty) {
        Sort sort = setSort(sortDirection, sortProperty);
        return productRepository.findAll(sort).stream()
                .map(ProductMapper::mapProductToProductDto)
                .toList();
    }
    @Override
    public List<ProductDto> findProductsByName(String name, String sortDirection, String sortProperty) {
        Sort sort = setSort(sortDirection, sortProperty);
        return productRepository.findByNameContainingIgnoreCase(name, sort).stream()
                .map(ProductMapper::mapProductToProductDto)
                .toList();
    }
    @Override
    public List<ProductDto> findProductsByCategory(String categoryName, String sortDirection, String sortProperty, String propertyValue) {
        AttributeDefinition validAttribute = validateAttribute(categoryName, sortProperty);
        Sort sort = setSortBySortProperty(sortDirection, sortProperty);

        return validAttribute.getDataType().equals(DataType.DOUBLE) ?
                productRepository.findByCategorySortByNumericProperty(categoryName, sort, Integer.parseInt(propertyValue))
                        .stream()
                        .map(ProductMapper::mapProductToProductDto)
                        .toList() :
                productRepository.findByCategorySortByStringProperty(categoryName, sort, propertyValue)
                        .stream()
                        .map(ProductMapper::mapProductToProductDto)
                        .toList();
    }
    @Override
    public void deleteProduct(Integer productId) {
        Product product = fetchProductById(productId);
        productRepository.deleteById(product.getId());
    }
    @Override
    public void addProductAttributeToProduct(Integer productId, ProductAttributeDto productAttributeDto) {
        Product product = fetchProductByIdWithAttributes(productId);

        Optional<ProductAttribute> optionalAtt = product.getAttributes().stream()
                .filter(att -> att.getName().equalsIgnoreCase(productAttributeDto.getName()))
                .findFirst();
        if (optionalAtt.isPresent()) throw new IllegalArgumentException("Product already has attribute: " + productAttributeDto.getName());

        AttributeDefinition attribute = attributeDefinitionRepository
                .findAllByCategory(product.getCategory().getName(), Sort.by("name"))
                .stream()
                .filter(att -> att.getName().equalsIgnoreCase(productAttributeDto.getName()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Attribute", productAttributeDto.getName()));

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setAttributeDefinition(attribute);

        AttributeConversionStrategy strategy = factory.getStrategy(attribute.getDataType());
        strategy.apply(productAttribute, productAttributeDto.getValue());

        product.getAttributes().add(productAttribute);
        productRepository.save(product);
    }
    @Override
    public void removeProductAttributeFromProduct(Integer productId, String productAttributeName) {
        Product product = fetchProductByIdWithAttributes(productId);

       ProductAttribute optionalAtt = product.getAttributes().stream()
                .filter(att -> att.getName().equalsIgnoreCase(productAttributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + productAttributeName));

            product.getAttributes().remove(optionalAtt);
            productRepository.save(product);
    }

    private Sort setSort(String sortDirection, String sortProperty) {
        String validSortDirection = validateSortDirection(sortDirection);
        String validSortProperty = validateSortProperty(sortProperty);
        return CommonUtils.setSort(validSortDirection, validSortProperty);
    }
    private Product fetchProductById(Integer productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product ID: " + productId));
    }
    private Product fetchProductByIdWithAttributes(Integer productId){
        return productRepository.findByIdWithAttributes(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product ID: " + productId));
    }
    private Category findCategoryByName(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryName));
    }
    private String validateSortProperty(String sortProperty) {
        if (sortProperty.equalsIgnoreCase("price")) return "price";
        else return "name";
    }
    private Sort setSortBySortProperty(String sortDirection, String sortProperty) {
        String validSortDirection = validateSortDirection(sortDirection);
        return Sort.by(validSortDirection, sortProperty);
    }
    private AttributeDefinition validateAttribute(String categoryName, String sortProperty) {
        List<AttributeDefinition> attributes = attributeDefinitionRepository.findAllByCategory(categoryName, Sort.by("name"));
        return attributes.stream()
                .filter(att -> att.getName().equalsIgnoreCase(sortProperty))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Attribute", sortProperty));
    }
}
