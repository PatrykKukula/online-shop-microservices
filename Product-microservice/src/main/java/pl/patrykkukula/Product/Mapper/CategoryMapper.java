package pl.patrykkukula.Product.Mapper;

import pl.patrykkukula.Product.Dto.Category.CategoryDto;
import pl.patrykkukula.Product.Model.Category;

public class CategoryMapper {

    public static Category mapCategoryDtoToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
    public static CategoryDto mapCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        category.getAttributeDefs().forEach(
                attr -> categoryDto.getAttributes().add(AttributeDefinitionMapper.mapAttributeDefinitionForCategoryDto(attr))
        );
        return categoryDto;
    }
}
