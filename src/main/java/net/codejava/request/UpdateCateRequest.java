package net.codejava.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCateRequest {

    private int id;
	@NotNull
    private String name;

}
