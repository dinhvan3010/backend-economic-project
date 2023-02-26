package net.codejava.model;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@Indexed
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int unitPrice;
    private String description;
    private float discount;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
    @OneToMany( mappedBy = "product")
    private List<ProductImage> images;
    @OneToMany(mappedBy = "product")
    private List<Inventory> inventories;
    @Column(updatable = false)
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;

}
