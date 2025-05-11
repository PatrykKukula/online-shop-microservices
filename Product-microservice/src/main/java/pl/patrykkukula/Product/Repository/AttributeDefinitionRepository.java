package pl.patrykkukula.Product.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykkukula.Product.Model.AttributeDefinition;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition, Integer> {

    @Query("SELECT ad FROM AttributeDefinition ad JOIN FETCH ad.categories c WHERE LOWER(c.name) = LOWER(:categoryName)")
    List<AttributeDefinition> findAllByCategory(String categoryName, Sort sort);
    @Query("SELECT ad FROM AttributeDefinition ad JOIN FETCH ad.categories c WHERE LOWER(ad.name) = LOWER(:attributeName)")
    Optional<AttributeDefinition> findByAttributeNameWithCategories(String attributeName);
    Optional<AttributeDefinition> findByNameIgnoreCase(String name);
}
