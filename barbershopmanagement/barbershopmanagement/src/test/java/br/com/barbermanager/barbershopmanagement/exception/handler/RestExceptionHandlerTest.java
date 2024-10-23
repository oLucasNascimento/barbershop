package br.com.barbermanager.barbershopmanagement.exception.handler;

import br.com.barbermanager.barbershopmanagement.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestExceptionHandlerTest {

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private RestExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void runTimeException() {
        RuntimeException ex = new RuntimeException("Runtime error");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.runTimeException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Runtime error", response.getBody().getMessage());
        assertEquals("RUNTIME_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void nullPointerException() {
        NullPointerException ex = new NullPointerException("Null Pointer error");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.nullPointerException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Null Pointer error", response.getBody().getMessage());
        assertEquals("NULL_POINTER_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void notFoundException() {
        NotFoundException ex = new NotFoundException("Not Found resource");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.notFoundException(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Not Found resource", response.getBody().getMessage());
        assertEquals("NOT_FOUND_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void alreadyExistsException() {
        AlreadyExistsException ex = new AlreadyExistsException("Already Exists resource");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.alreadyExistsException(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Already Exists resource", response.getBody().getMessage());
        assertEquals("ALREADY_EXISTS_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void badRequestException() {
        BadRequestException ex = new BadRequestException("Bad Request resource");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.badRequestException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Bad Request resource", response.getBody().getMessage());
        assertEquals("BAD_REQUEST_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void alreadyActiveException() {
        AlreadyActiveException ex = new AlreadyActiveException("Already Exits resource");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.alreadyActiveException(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Already Exits resource", response.getBody().getMessage());
        assertEquals("ALREADY_ACTIVE_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void inactiveException() {
        InactiveException ex = new InactiveException("Inactive resource");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.inactiveException(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Inactive resource", response.getBody().getMessage());
        assertEquals("INACTIVE_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void businessException() {
        BusinessException ex = new BusinessException("Business error");
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<RestErrorMessage> response = this.handler.businessException(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        RestErrorMessage body = response.getBody();
        assertNotNull(response.getBody());
        assertEquals("Business error", response.getBody().getMessage());
        assertEquals("BUSINESS_ERROR", response.getBody().getErrorCode());
        assertEquals("/some-path", response.getBody().getPath());
    }

    @Test
    void handleHttpMessageNotReadable() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        HttpHeaders headers = new HttpHeaders();
        WebRequest webRequest = mock(WebRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(new FieldError("station", "name", "An error occurred while processing the information. Please ensure all data is correct and try again.")));
//        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<Object> response = this.handler.handleHttpMessageNotReadable(exception,headers,HttpStatus.BAD_REQUEST, webRequest);

        RestErrorMessage body = (RestErrorMessage) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getStatus());
        assertNotNull(body);
        assertEquals("An error occurred while processing the information. Please ensure all data is correct and try again.", body.getMessage());
        assertEquals("DESERIALIZE_ERROR", body.getErrorCode());
        assertEquals("/some-path", body.getPath());
    }

    @Test
    void handleMethodArgumentNotValid() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        HttpHeaders headers = new HttpHeaders();
        WebRequest webRequest = mock(WebRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(new FieldError("station", "name", "Should not be empty")));
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(webRequest.getDescription(false)).thenReturn("/some-path");

        ResponseEntity<Object> response = this.handler.handleMethodArgumentNotValid(exception,headers,HttpStatus.BAD_REQUEST, webRequest);

        RestErrorMessage body = (RestErrorMessage) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getStatus());
        assertNotNull(body);
        assertEquals("Should not be empty", body.getMessage());
        assertEquals("VALIDATION_ERROR", body.getErrorCode());
        assertEquals("/some-path", body.getPath());
    }
}