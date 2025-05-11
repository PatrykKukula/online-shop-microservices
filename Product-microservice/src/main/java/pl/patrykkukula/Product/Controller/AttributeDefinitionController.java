package pl.patrykkukula.Product.Controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.patrykkukula.Common.CommonUtils;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionDto;
import pl.patrykkukula.Product.Dto.AttributeDefinition.AttributeDefinitionUpdateDto;
import pl.patrykkukula.Product.Service.AttributeDefinitionService;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attributes")
@Validated
public class AttributeDefinitionController {

    private final AttributeDefinitionService attributeDefinitionService;

    @PostMapping("/add")
    public ResponseEntity<AttributeDefinitionDto> createAttributeDefinition(@Valid @RequestBody AttributeDefinitionDto attributeDefinitionDto) {
        AttributeDefinitionDto createdAttribute = attributeDefinitionService.addAttributeDefinition(attributeDefinitionDto);
        URI location = CommonUtils.setLocation(createdAttribute.getId(), "attributeId");
        return ResponseEntity.created(location).body(createdAttribute);
    }
    @DeleteMapping("/remove")
    public ResponseEntity<Void> deleteAttributeDefinition(@RequestParam String attributeName) {
        attributeDefinitionService.removeAttributeDefinition(attributeName);
        return ResponseEntity.accepted().build();
    }
    @PatchMapping
    public ResponseEntity<AttributeDefinitionDto> updateAttributeDefinition(@RequestParam String attributeName,
                                                          @Valid @RequestBody AttributeDefinitionUpdateDto attributeDefinitionDto) {
        return ResponseEntity.accepted().body(attributeDefinitionService.updateAttributeDefinition(attributeName, attributeDefinitionDto));
    }
    @GetMapping
    public ResponseEntity<List<AttributeDefinitionDto>> getAllAttributeDefinitionsByCategory(
                                                @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                @RequestParam String categoryName) {
        return ResponseEntity.ok().body(attributeDefinitionService.findAllAttributesSortedByCategory(sortDirection, categoryName));
    }
    @PostMapping("/addCategory")
    public ResponseEntity<Void> addCategoryToAttributeDefinition(@RequestParam String attributeName,
                                                                 @RequestParam String categoryName) {
        attributeDefinitionService.addCategoryToAttributeDefinition(attributeName, categoryName);
        return ResponseEntity.accepted().build();
    }
    @DeleteMapping("/removeCategory")
    public ResponseEntity<Void> removeCategoryFromAttributeDefinition(@RequestParam String attributeName,
                                                                      @RequestParam String categoryName) {
        attributeDefinitionService.removeCategoryFromAttributeDefinition(attributeName, categoryName);
        return ResponseEntity.accepted().build();
    }
}
