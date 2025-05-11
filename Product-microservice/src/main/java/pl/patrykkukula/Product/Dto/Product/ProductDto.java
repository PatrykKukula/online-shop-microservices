package pl.patrykkukula.Product.Dto.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Product.Dto.ProductAttribute.ProductAttributeDto;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    private Integer id;
    @NotEmpty(message = "Product name cannot be null or empty")
    private String name;
    @Positive(message = "Product price must be more than 0.00")
    private Double price;
    @Min(value = 0, message = "Available amount cannot be less than 0")
    private Integer availableAmount;
    @NotEmpty(message = "Category cannot be null or empty")
    private String category;
    private String description;
    private List<ProductAttributeDto> productAttributes;
}
