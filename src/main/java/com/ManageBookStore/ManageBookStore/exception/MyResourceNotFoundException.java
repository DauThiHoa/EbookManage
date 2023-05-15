package com.ManageBookStore.ManageBookStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MyResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public MyResourceNotFoundException() {
        super();
    }
    public MyResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MyResourceNotFoundException(String message) {
        super(message);
    }

//    Ngoại lệ tài nguyên không tìm thấy của tôi (Nguyên nhân có thể ném) {
    public MyResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
