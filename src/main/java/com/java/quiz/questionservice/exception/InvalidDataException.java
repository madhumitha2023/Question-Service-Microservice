package com.java.quiz.questionservice.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String questionNotFound) {
        super(questionNotFound);
    }
}
