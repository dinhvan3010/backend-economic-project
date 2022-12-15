package net.codejava.services;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import net.codejava.dto.DataPagingResp;

public interface IListConverter {
	<T, R> DataPagingResp<R> toPageResponse(Page<T> page, Function<T, R> function);

	<T, R> List<R> toListResponse(List<T> list, Function<T, R> function);

	public <R> DataPagingResp<R> toPageResponse(Page<R> page);

}