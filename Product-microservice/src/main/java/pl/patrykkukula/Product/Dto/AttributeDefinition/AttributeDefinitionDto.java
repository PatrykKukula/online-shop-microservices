package pl.patrykkukula.Product.Dto.AttributeDefinition;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Constants.DataType;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AttributeDefinitionDto {
    Integer id;
    @NotEmpty(message = "Attribute definition name cannot be null or empty")
    private String name;
    private DataType dataType;
    @Size(min = 1, message = "Provide at least one category")
    List<String> categoryNames;
}
