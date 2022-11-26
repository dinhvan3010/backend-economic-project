package net.codejava.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DataPagingResp<T> {
	private List<T> list;
	private long currentPage;
	private long totalPages;
	private long totalElements;

	public DataPagingResp() {
		this.list = new ArrayList<>();
	}
}