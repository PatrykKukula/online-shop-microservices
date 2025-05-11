package pl.patrykkukula.Product.Controller;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.patrykkukula.Common.CommonUtils;
import pl.patrykkukula.Product.Dto.Category.CategoryDto;
import pl.patrykkukula.Product.Service.CategoryService;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.addCategory(categoryDto);
        URI location = CommonUtils.setLocation(createdCategory.getId(), "categoryId");
        return ResponseEntity.created(location).body(createdCategory);
    }
    @DeleteMapping("/remove/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@Min(value = 1, message = "Id cannot be less than 1") @PathVariable Integer categoryId) {
        categoryService.removeCategory(categoryId);
        return ResponseEntity.accepted().build();
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@Min(value = 1, message = "Id cannot be less than 1") @PathVariable Integer categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.accepted().body(categoryService.updateCategory(categoryId, categoryDto));
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok().body(categoryService.findAllCategories(sortDirection));
    }

}
