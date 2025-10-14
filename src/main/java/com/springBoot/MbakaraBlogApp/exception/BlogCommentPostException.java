package com.springBoot.MbakaraBlogApp.exception;

import org.springframework.http.HttpStatus;

public class BlogCommentPostException extends RuntimeException{
    private HttpStatus status;
    private String messageBody;

    public BlogCommentPostException(String message, HttpStatus status, String messageBody) {
        super(message);
        this.status = status;
        this.messageBody = messageBody;
    }

    public BlogCommentPostException(HttpStatus status, String messageBody) {
        this.status = status;
        this.messageBody = messageBody;
    }

    public HttpStatus getStatus(){
        return status;

    }

    public String getMessageBody(){
        return messageBody;
    }
}
