package pl.patrykkukula.Product.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "categories_attributes_def",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "attribute_def_id", referencedColumnName = "id", nullable = false)
    )
    private List<AttributeDefinition> attributeDefs;
}
