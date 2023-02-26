package net.codejava.dto;

import java.io.Serializable;

public interface Report {
    Serializable getLabel();
    Double getSum();
    Long getCount();
}
