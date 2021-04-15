package com.slashmobility.taxly.dto.request;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiParam;

public class PaginationWithoutDefaultRequest {
	
	@ApiParam(value = "(pagination) Page's number - 0 is the first page  (default: first Page)")
	@PositiveOrZero
	private Integer page;

	@ApiParam(value = "(pagination) Page's size (default: all)")
	@Positive
	private Integer pageSize;
	
	public PaginationWithoutDefaultRequest() {
		super();
	}

	public PaginationWithoutDefaultRequest(@PositiveOrZero Integer page, @Positive Integer pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public boolean valid() {
		return (page == null && pageSize == null) || (page != null && pageSize != null);
	}
	
	public boolean pagination() {
		return (page != null && pageSize != null);
	}
}
