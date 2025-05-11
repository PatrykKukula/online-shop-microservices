package pl.patrykkukula.Product.Dto.AttributeDefinition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Constants.DataType;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AttributeDefinitionForCategoryDto {
    private String name;
    private DataType dataType;
}
