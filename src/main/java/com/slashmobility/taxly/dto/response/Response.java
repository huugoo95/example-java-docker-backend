package com.slashmobility.taxly.dto.response;

import org.springframework.http.HttpStatus;

public class Response {
	
	private int status;
    private String message;
    private Object data;

    public Response(int status, String message, Object data) {
    	this.status = status;
    	this.message = message;
        this.data = data;
    }
    
    public Response(String message, Object data) {
        this(HttpStatus.OK.value(), message, data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
