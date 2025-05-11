package pl.patrykkukula.Product.Repository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import pl.patrykkukula.Product.Model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

        List<Product> findByNameContainingIgnoreCase(String name, Sort sort);
        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.attributes WHERE p.id = :productId")
        Optional<Product> findByIdWithAttributes(@Param(value = "productId") Integer productId);
        @Query("SELECT p FROM Product p JOIN FETCH p.attributes a WHERE p.category.name = LOWER(:categoryName) " +
                "AND (:npropertyValue IS NULL OR a.numericValue >= :propertyValue)")
        List<Product> findByCategorySortByNumericProperty(@Param(value = "categoryName") String categoryName
                ,Sort sort,@Param(value = "propertyValue") Integer propertyValue);
        @Query("SELECT p FROM Product p JOIN FETCH p.attributes a WHERE p.category.name = LOWER(:categoryName) " +
                "AND (:propertValue IS NULL OR a.value = :propertyValue)")
        List<Product> findByCategorySortByStringProperty(@Param(value = "categoryName") String categoryName
                ,Sort sort,@Param(value = "propertyValue") String propertyValue);

}
