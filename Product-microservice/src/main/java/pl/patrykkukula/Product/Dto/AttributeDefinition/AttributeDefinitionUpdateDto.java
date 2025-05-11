package pl.patrykkukula.Product.Dto.AttributeDefinition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Constants.DataType;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AttributeDefinitionUpdateDto {

    private String name;
    private DataType dataType;
}
