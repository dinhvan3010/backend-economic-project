package net.codejava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String deliveryAddress;
    private int paymentMethod;
    private String notes;
    private int status;
    private Date createdDate;
    @ManyToOne
    private User user;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
