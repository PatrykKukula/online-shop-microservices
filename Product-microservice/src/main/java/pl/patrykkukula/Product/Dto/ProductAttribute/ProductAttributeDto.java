package pl.patrykkukula.Product.Dto.ProductAttribute;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductAttributeDto {
    @NotEmpty(message = "Attribute name cannot be null or empty")
    private String name;
    private String value;
}
