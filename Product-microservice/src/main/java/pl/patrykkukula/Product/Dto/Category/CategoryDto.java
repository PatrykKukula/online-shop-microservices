package pl.patrykkukula.Product.Dto.Category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionDto;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionForCategoryDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotEmpty(message = "Category name cannot be null or empty")
    private String name;
    List<AttributeDefinitionForCategoryDto> attributes = new ArrayList<>();
}
