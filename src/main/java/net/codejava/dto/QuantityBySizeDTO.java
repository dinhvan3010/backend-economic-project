package net.codejava.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityBySizeDTO {

    private int size;
    private int quantity;
}
