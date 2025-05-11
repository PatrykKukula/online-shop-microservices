package pl.patrykkukula.Product.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.patrykkukula.Common.CommonUtils;
import pl.patrykkukula.Product.Dto.Product.ProductDto;
import pl.patrykkukula.Product.Dto.Product.ProductUpdateDto;
import pl.patrykkukula.Product.Dto.ProductAttribute.ProductAttributeDto;
import pl.patrykkukula.Product.Service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.addProduct(productDto);
        URI location = CommonUtils.setLocation(savedProduct.getId(), "productId");
        return ResponseEntity.created(location).body(savedProduct);
    }
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto,
                                                    @Min(value = 1, message = "Id cannot be less than 1") @PathVariable Integer productId){
        return ResponseEntity.accepted().body(productService.updateProduct(productId, productUpdateDto));
    }
    @PostMapping("/setPrice/{productId}")
    public ResponseEntity<String> setNewPrice(@Min(value = 1, message = "Id cannot be less than 1") @PathVariable Integer productId,
                                              @RequestBody Double newPrice){
        return ResponseEntity.accepted().body(productService.setNewPrice(productId, newPrice));
    }
    @PostMapping("/setAmount/{productId}")
    public ResponseEntity<Void> setAvailableAmount(@PathVariable Integer productId, @RequestBody Integer amount){
        productService.setAvailableAmount(productId, amount);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@Min(value = 1, message = "Id cannot be less than 1")@PathVariable Integer productId){
        return ResponseEntity.ok(productService.findProductById(productId));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                           @RequestParam(required = false, defaultValue = "name") String sortProperty) {
        return ResponseEntity.ok(productService.findAllProducts(sortDirection, sortProperty));
    }
    @GetMapping("/findByName")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@RequestParam() String name,
                                                            @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                            @RequestParam(required = false, defaultValue = "name") String sortProperty) {
        return ResponseEntity.ok(productService.findProductsByName(name, sortDirection, sortProperty));
    }
    @GetMapping("/findByCategory")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@RequestParam() String categoryName,
                                                                  @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                                  @RequestParam(required = false, defaultValue = "name") String sortProperty,
                                                                      @RequestParam(required = false) String propertyValue) {
        return ResponseEntity.ok(productService.findProductsByCategory(categoryName, sortDirection, sortProperty, propertyValue));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/addAttribute/{productId}")
    public ResponseEntity<Void> addAttributeToProduct(@Min(value = 1, message = "Id cannot be less than 1")@PathVariable Integer productId,
                                                      @Valid @RequestBody ProductAttributeDto productAttributeDto) {
        productService.addProductAttributeToProduct(productId, productAttributeDto);
        return ResponseEntity.accepted().build();
    }
    @DeleteMapping("/removeAttribute/{productId}")
    public ResponseEntity<Void> removeAttributeFromProduct(@Min(value = 1, message = "Id cannot be less than 1") @PathVariable Integer productId,
                                                            @RequestParam() String attributeName){
        productService.removeProductAttributeFromProduct(productId,attributeName);
        return ResponseEntity.accepted().build();
    }
}
