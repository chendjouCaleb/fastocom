package app.controller;

import app.exception.ConstraintException;
import app.exception.ElementAlreadyUsed;
import app.exception.EntityError;
import app.exception.FileTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerErrorAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleEntityNotFoundException(NoSuchElementException e, HttpServletRequest request){
        EntityError error = new EntityError();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle("Element Not Found");
        error.setDetail(e.getMessage());
        error.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<Object>(error, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementAlreadyUsed.class)
    public ResponseEntity<?> handleEntityAlreadyException(ElementAlreadyUsed e, HttpServletRequest request){
        EntityError error = new EntityError();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.IM_USED.value());
        error.setTitle("Entity already exist");
        error.setDetail(e.getMessage());
        error.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<Object>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileTypeException.class)
    public ResponseEntity<?> handleFileTypeException(FileTypeException e, HttpServletRequest request){
        EntityError error = new EntityError();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        error.setTitle("This file format is not acceptable");
        error.setDetail(e.getMessage());
        error.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<Object>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request){
        EntityError error = new EntityError();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        error.setTitle("Max file size reached");
        error.setDetail("Le fichier envoyé depasse la taille maximale admise qui est de " + e.getMaxUploadSize()/1024/1024 + " Mo");
        error.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<Object>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException e, HttpServletRequest request){
        EntityError error = new EntityError();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTitle("Error occuring during the handling of the file");
        error.setDetail(e.getMessage());
        error.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<Object>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintException.class)
    public ResponseEntity<?> handleConstraintException(ConstraintException e, HttpServletRequest request){
        EntityError error = new EntityError();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTitle("Violation d'intégrité");
        error.setDetail(e.getMessage());
        error.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<Object>(error, null, HttpStatus.BAD_REQUEST);
    }
}
