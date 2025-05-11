package pl.patrykkukula.Product.Service;

import pl.patrykkukula.Product.Dto.Category.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    void removeCategory(Integer categoryId);
    CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);
    List<CategoryDto> findAllCategories(String sortDirection);
}
