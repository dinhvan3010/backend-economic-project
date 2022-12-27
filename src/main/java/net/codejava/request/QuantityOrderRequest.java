package net.codejava.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class QuantityOrderRequest {
    private int size;
    private int quantity;

}
