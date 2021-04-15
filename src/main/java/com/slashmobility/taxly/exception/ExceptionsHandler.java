package com.slashmobility.taxly.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.slashmobility.taxly.dto.response.Response;

@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String firstCode = error.getCodes()[0];
			String[] splits = firstCode.split("\\.");
			String field = (splits.length > 0) ? splits[splits.length-1] : error.getField();

			errors.add(field + " " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(/*error.getObjectName() + ": " + */ error.getDefaultMessage());
		}

		return buildResponseEntity(status, errors.get(0));
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildResponseEntity(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(/*error.getObjectName() + ": " + */ error.getDefaultMessage());
		}
		
		return buildResponseEntity(status, errors.get(0));
	}
	

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
	
	@ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }
	
	@ExceptionHandler({ ResourceNotFoundException.class })
	protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	

	@ExceptionHandler(InternalErrorException.class)
	protected ResponseEntity<Object> handleInternalErrorException(InternalErrorException ex) {
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<Object> handleResourceNotFound(UnauthorizedException ex) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}
	
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleResourceNotFound(BadRequestException ex) {
		return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	@ExceptionHandler(ForbiddenException.class)
	protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
		return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
	}

	@ExceptionHandler(NotAcceptableException.class)
	protected ResponseEntity<Object> handleNotAcceptableException(NotAcceptableException ex) {
		return buildResponseEntity(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
	}
	
	@ExceptionHandler({ ResourceAlreadyExistsException.class })
    public ResponseEntity<Object> handleResourceAlreadyExistsException(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }
	
	@ExceptionHandler({ MissingRequestHeaderException.class })
    public ResponseEntity<Object> handleMissingRequestHeaderException(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

	
	@ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

	@ExceptionHandler({ ClientAbortException.class })
    public void handleClientAbortException(ClientAbortException ex, WebRequest request) {
        return;
    }
	
	private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, Object data) {
		Response response = new Response(status.value(), message, data);
		return new ResponseEntity<>(response, status);
	}
	
	private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
		return buildResponseEntity(status, message, null);
	}
}
