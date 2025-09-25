package com.springBoot.MbakaraBlogApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        //The next line will pass messages to the super constructor using super keyword
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)); // Dynamically, this line will be;
        // Post not found id 1 (that's the ID no, this will be the custom message)
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName(){
        return resourceName;
    }

    public String getFieldName(){
        return fieldName;
    }

    public String getFieldValue(){
        return fieldValue;
    }
}
