package net.codejava.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddBrandRequest {
    @NotNull
    private String name;
}
