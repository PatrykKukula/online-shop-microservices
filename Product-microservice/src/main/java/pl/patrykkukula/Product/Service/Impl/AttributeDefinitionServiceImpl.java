package pl.patrykkukula.Product.Service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.patrykkukula.Common.CommonUtils;
import pl.patrykkukula.Exception.ResourceNotFoundException;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionDto;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionUpdateDto;
import pl.patrykkukula.Product.Mapper.AttributeDefinitionMapper;
import pl.patrykkukula.Product.Model.AttributeDefinition;
import pl.patrykkukula.Product.Model.Category;
import pl.patrykkukula.Product.Repository.AttributeDefinitionRepository;
import pl.patrykkukula.Product.Repository.CategoryRepository;
import pl.patrykkukula.Product.Service.AttributeDefinitionService;

import java.util.List;
import java.util.Optional;

import static pl.patrykkukula.Product.Mapper.AttributeDefinitionMapper.*;
import static pl.patrykkukula.Validation.ValidationUtils.validateSortDirection;

@Service
@RequiredArgsConstructor
public class AttributeDefinitionServiceImpl implements AttributeDefinitionService {

    private final AttributeDefinitionRepository attributeDefinitionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public AttributeDefinitionDto addAttributeDefinition(AttributeDefinitionDto attributeDefinitionDto) {
        attributeDefinitionRepository.findByNameIgnoreCase(attributeDefinitionDto.getName())
                .ifPresent(attr -> {throw new IllegalArgumentException("Attribute already exists");});

        List<String> categories = attributeDefinitionDto.getCategoryNames();
        AttributeDefinition attribute = mapAttributeDefinitionDtoToAttributeDefinition(attributeDefinitionDto);
        attribute = attributeDefinitionRepository.save(attribute);

        for (String categoryName : categories) {
            Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", categoryName));
            attribute.getCategories().add(category);
            category.getAttributeDefs().add(attribute);
        }

        AttributeDefinition savedAttributeDefinition = attributeDefinitionRepository.save(attribute);
        return mapAttributeDefinitionToAttributeDefinitionDto(savedAttributeDefinition);
    }
    @Override
    @Transactional
    public void removeAttributeDefinition(String attributeName) {
        AttributeDefinition attribute = attributeDefinitionRepository.findByAttributeNameWithCategories(attributeName)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute definition", attributeName));

        List<Category> categories = attribute.getCategories();
        for (Category category : categories) {
            category.getAttributeDefs().remove(attribute);
        }

        attributeDefinitionRepository.delete(attribute);
    }
    @Override
    @Transactional
    public AttributeDefinitionDto updateAttributeDefinition(String attributeName, AttributeDefinitionUpdateDto attributeDefinitionUpdateDto) {
        Optional<AttributeDefinition> optionalAttribute = attributeDefinitionRepository.findByNameIgnoreCase(attributeDefinitionUpdateDto.getName());
        if (optionalAttribute.isPresent()) throw new IllegalArgumentException("Attribute: " + attributeDefinitionUpdateDto.getName() + " already exists");

        AttributeDefinition attribute = findAttributeByName(attributeName);

        AttributeDefinition updatedAttribute = attributeDefinitionRepository
                .save(mapAttributeDefinitionUpdateDtoToAttributeDefinition(attributeDefinitionUpdateDto, attribute));
        return mapAttributeDefinitionToAttributeDefinitionDto(updatedAttribute);
    }
    @Override
    public List<AttributeDefinitionDto> findAllAttributesSortedByCategory(String sortDirection, String categoryName) {
        String validSortDirection = validateSortDirection(sortDirection);
        Sort sort = CommonUtils.setSort(validSortDirection, "name");
        return attributeDefinitionRepository.findAllByCategory(categoryName, sort)
                .stream()
                .map(AttributeDefinitionMapper::mapAttributeDefinitionToAttributeDefinitionDto)
                .toList();
    }
    @Override
    @Transactional
    public void addCategoryToAttributeDefinition(String attributeName, String categoryName) {
        AttributeDefinition attribute = findAttributeByName(attributeName);
        Category category = findCategoryByName(categoryName);
        if (attribute.hasCategory(category.getName())) throw new IllegalArgumentException("Category already belongs to AttributeDefinition");
        attribute.getCategories().add(category);
        attributeDefinitionRepository.save(attribute);
    }
    @Override
    @Transactional
    public void removeCategoryFromAttributeDefinition(String attributeName, String categoryName) {
        AttributeDefinition attribute = findAttributeByName(attributeName);
        Category category = findCategoryByName(categoryName);
        if (attribute.hasCategory(category.getName())){
            attribute.getCategories().remove(category);
            attributeDefinitionRepository.save(attribute);
        }
        else throw new IllegalArgumentException("Category does not belong to AttributeDefinition");
    }

    private AttributeDefinition findAttributeByName(String attributeName) {
        return attributeDefinitionRepository.findByAttributeNameWithCategories(attributeName)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute definition", attributeName));
    }
    private Category findCategoryByName(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryName));
    }
}
