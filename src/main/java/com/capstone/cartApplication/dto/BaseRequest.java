package com.capstone.cartApplication.dto;

import java.io.Serializable;

public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
	private Integer _id;

	public Integer getId() {
		return _id;
	}

	public void setId(Integer id) {
		this._id = id;
	}
}