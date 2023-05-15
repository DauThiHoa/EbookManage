package com.ManageBookStore.ManageBookStore.exception;

public class FileStorageException extends RuntimeException {
// thông tin cụ thể về ngoại lệ hoặc lý do lỗi lưu trữ tệp.
	private static final long serialVersionUID = 1L;

	public FileStorageException(String message) {
		super(message);
	}
	
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
