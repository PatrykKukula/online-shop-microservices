package pl.patrykkukula.Product.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.patrykkukula.Common.CommonUtils;
import pl.patrykkukula.Exception.ResourceNotFoundException;
import pl.patrykkukula.Product.Dto.Category.CategoryDto;
import pl.patrykkukula.Product.Mapper.CategoryMapper;
import pl.patrykkukula.Product.Model.Category;
import pl.patrykkukula.Product.Repository.CategoryRepository;
import pl.patrykkukula.Product.Service.CategoryService;

import java.util.List;
import java.util.Optional;

import static pl.patrykkukula.Validation.ValidationUtils.validateSortDirection;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findByNameIgnoreCase(categoryDto.getName());
        if (optionalCategory.isPresent()) throw new IllegalArgumentException("Category already exists");

        Category savedCategory = categoryRepository.save(CategoryMapper.mapCategoryDtoToCategory(categoryDto));
        return CategoryMapper.mapCategoryToCategoryDto(savedCategory);
    }
    @Override
    public void removeCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category ID: " + categoryId));
        categoryRepository.deleteById(categoryId);
    }
    @Override
    public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category ID: " + categoryId));
        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.mapCategoryToCategoryDto(updatedCategory);
    }
    @Override
    public List<CategoryDto> findAllCategories(String sortDirection) {
        String validSortDirection = validateSortDirection(sortDirection);
        Sort sort = CommonUtils.setSort(validSortDirection, "name");
        return categoryRepository.findAll(sort).stream()
                .map(CategoryMapper::mapCategoryToCategoryDto)
                .toList();
    }
}
