package pl.patrykkukula.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykkukula.Product.Model.ProductAttribute;


@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
}
