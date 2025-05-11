package pl.patrykkukula.Product.Mapper;

import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionDto;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionForCategoryDto;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionUpdateDto;
import pl.patrykkukula.Product.Model.AttributeDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttributeDefinitionMapper {

    public static AttributeDefinition mapAttributeDefinitionDtoToAttributeDefinition(AttributeDefinitionDto attributeDefinitionDto){
        AttributeDefinition attributeDefinition = new AttributeDefinition();
        attributeDefinition.setName(attributeDefinitionDto.getName());
        attributeDefinition.setDataType(attributeDefinitionDto.getDataType());
        return attributeDefinition;
    }
    public static AttributeDefinitionDto mapAttributeDefinitionToAttributeDefinitionDto(AttributeDefinition attributeDefinition){
        AttributeDefinitionDto attributeDefinitionDto = new AttributeDefinitionDto();
        attributeDefinitionDto.setId(attributeDefinition.getId());
        attributeDefinitionDto.setName(attributeDefinition.getName());
        attributeDefinitionDto.setDataType(attributeDefinition.getDataType());
        List<String> categoryNames = new ArrayList<>();
        attributeDefinition.getCategories().forEach(
                cat -> categoryNames.add(cat.getName())
        );
        attributeDefinitionDto.setCategoryNames(categoryNames);
        return attributeDefinitionDto;
    }
    public static AttributeDefinitionForCategoryDto mapAttributeDefinitionForCategoryDto(AttributeDefinition attributeDefinition){
        AttributeDefinitionForCategoryDto attributeDefinitionDto = new AttributeDefinitionForCategoryDto();
        attributeDefinitionDto.setName(attributeDefinition.getName());
        attributeDefinitionDto.setDataType(attributeDefinition.getDataType());
        return attributeDefinitionDto;
    }
    public static AttributeDefinition mapAttributeDefinitionUpdateDtoToAttributeDefinition(
        AttributeDefinitionUpdateDto attributeDefinitionUpdateDto, AttributeDefinition attributeDefinition){
        Optional.ofNullable(attributeDefinitionUpdateDto.getName()).ifPresent(attributeDefinition::setName);
        Optional.ofNullable(attributeDefinitionUpdateDto.getDataType()).ifPresent(attributeDefinition::setDataType);
        return attributeDefinition;
    }
}
