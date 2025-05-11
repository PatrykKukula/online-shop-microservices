package pl.patrykkukula.Product.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.patrykkukula.Constants.DataType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class AttributeDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @ManyToMany(mappedBy = "attributeDefs", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "attributeDefinition", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    public List<ProductAttribute> productAttributes = new ArrayList<>();

    public boolean hasCategory(String categoryName) {
        return categories
                .stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(categoryName));
    }
}
