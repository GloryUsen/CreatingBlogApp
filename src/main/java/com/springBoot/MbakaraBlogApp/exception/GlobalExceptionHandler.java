package com.springBoot.MbakaraBlogApp.exception;

import com.springBoot.MbakaraBlogApp.dtos.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /** Handling specific Exceptions as well as GlobalExceptions, So the specific Exception here is the ResourceNotFound
     Exception and BlogCommentPostException
     * @ControllerAdvice annotation is used to handle exception globally
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlingResourceNotFoundException(ResourceNotFoundException exception,
                                                                          WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BlogCommentPostException.class)
     public ResponseEntity<ErrorDetails> handlingBlogCommentPostException(BlogCommentPostException exception,
                                                                         WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handlerBadCredentialsException(BadCredentialsException exception,
                                                                       WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Invalid username or password",

                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalExceptionHandler(Exception exception,
                                                                     WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }




            @Override
            protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request) {
                /** Add my own customized logic for the return statement below.
                 So a map is created and all the validations for all the fields put inside.
                 **/
                Map<String, String> errors = new HashMap<>();
                // Get all the validation errors from the exception object that is(MethodArgumentNotValidException ex)
                // and al does errors will be kept inside the error variable.
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                     String fieldName = ((FieldError)error).getField();
                     String message = error.getDefaultMessage();
                     errors.put(fieldName, message); // using mapObject here

                });

                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

            }

            // Second approached of validating an Update request.

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
//                                                            WebRequest webRequest){
//        Map<String, String> newStep = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach((error)->{
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            newStep.put(fieldName, message);
//        });
//
//        return new ResponseEntity<>(newStep, HttpStatus.BAD_REQUEST);
//    }

}
