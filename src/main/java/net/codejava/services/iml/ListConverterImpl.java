package net.codejava.services.iml;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import net.codejava.services.IListConverter;
import net.codejava.dto.DataPagingResp;

@Component
public class ListConverterImpl implements IListConverter {

	public <T, R> List<R> toListResponse(List<T> list, Function<T, R> function) {
		List<R> result = new ArrayList<>();
		for (T entity : list) {
			result.add(function.apply(entity));
		}
		return result;
	}

	public <T, R> DataPagingResp<R> toPageResponse(Page<T> page, Function<T, R> function) {
		DataPagingResp<R> result = new DataPagingResp<>();

		for (T entity : page) {
			result.getList().add(function.apply(entity));
		}
		result.setCurrentPage(page.getNumber());
		result.setTotalPages(page.getTotalPages());
		result.setTotalElements(page.getTotalElements());
		return result;
	}

	public <R> DataPagingResp<R> toPageResponse(Page<R> page) {
		DataPagingResp<R> result = new DataPagingResp<>();
		result.setCurrentPage(page.getNumber());
		result.setTotalPages(page.getTotalPages());
		result.setList(page.toList());
		result.setTotalElements(page.getTotalElements());
		return result;

	}
}