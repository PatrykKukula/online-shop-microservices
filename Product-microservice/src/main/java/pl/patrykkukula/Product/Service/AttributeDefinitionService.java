package pl.patrykkukula.Product.Service;

import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionDto;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionUpdateDto;

import java.util.List;

public interface AttributeDefinitionService {

    AttributeDefinitionDto addAttributeDefinition(AttributeDefinitionDto attributeDefinitionDto);
    void removeAttributeDefinition(String attributeName);
    AttributeDefinitionDto updateAttributeDefinition(String attributeName, AttributeDefinitionUpdateDto attributeDefinitionUpdateDto);
    List<AttributeDefinitionDto> findAllAttributesSortedByCategory(String sortDirection, String categoryName);
    void addCategoryToAttributeDefinition(String attributeName, String categoryName);
    void removeCategoryFromAttributeDefinition(String attributeName, String categoryName);
}
