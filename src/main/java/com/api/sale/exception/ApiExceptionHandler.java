package com.api.sale.exception;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice 
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final String MSG_ERRO_GENERICA_USER_FINAL = "Ocorreu um erro interno inesperado no sistema. "
	        + "Tente novamente e se o problema persistir, entre em contato "
	        + "com o administrador do sistema.";

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch(
	                (MethodArgumentTypeMismatchException) ex, headers, status, request);
	    }

		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof NoHandlerFoundException) {
	        return handleMethodNoHandlerFoundException(
	                (NoHandlerFoundException) ex, headers, status, request);
	    }
		
		return super.handleNoHandlerFoundException(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String recurso = ex.getRequestURL();
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso '%s', que você tentou acessar é inexistente", recurso);
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USER_FINAL)
				.timestamp(OffsetDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String typeRequired = ex.getRequiredType().getSimpleName();
		Object urlInformada = ex.getValue();
		String url = ex.getName();
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor de '%s', que é um tipo inválido."
				+ "Corrija e informe um valor compatível com o tipo %s", url, urlInformada, typeRequired);
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USER_FINAL)
				.timestamp(OffsetDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(QuantidadeInsuficienteException.class)
	public ResponseEntity<?> handleQuantidadeInsuficienteException(QuantidadeInsuficienteException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
		ProblemType problemType = ProblemType.QUANTIDADE_INSUFICIENTE;
		String detail = ex.getMessage();
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USER_FINAL)
				.timestamp(OffsetDateTime.now()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(VendaNaoEncontradaException.class)
	public ResponseEntity<?> handleVendaNaoEncontradaException(VendaNaoEncontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USER_FINAL)
				.timestamp(OffsetDateTime.now()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					
					.title(status.getReasonPhrase())
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.build();
		} else if (body instanceof String){
			body = Problem.builder()
					.title((String)body)
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
		return Problem.builder().status(status.value())
								.type(problemType.getUri())
								.title(problemType.getTitle())
								.detail(detail)
								.timestamp(OffsetDateTime.now());
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
	    String detail = MSG_ERRO_GENERICA_USER_FINAL;

	    ex.printStackTrace();
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(MSG_ERRO_GENERICA_USER_FINAL)
	    		.timestamp(OffsetDateTime.now()).build();

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	} 
		
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		        
		    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		    
		    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
		            .map(objectError -> {
		                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
		                
		                String name = objectError.getObjectName();
		                
		                if (objectError instanceof FieldError) {
		                    name = ((FieldError) objectError).getField();
		                }
		                
		                return Problem.Object.builder()
		                    .name(name)
		                    .userMessage(message)
		                    .build();
		            })
		            .collect(Collectors.toList());
		    
		    Problem problem = createProblemBuilder(status, problemType, detail)
		        .userMessage(detail)
		        .objects(problemObjects)
		        .build();
		    
		    return handleExceptionInternal(ex, problem, headers, status, request);
		}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
	
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
}