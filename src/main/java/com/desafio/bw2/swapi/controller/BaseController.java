package com.desafio.bw2.swapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import netscape.javascript.JSObject;
import org.hibernate.validator.internal.xml.binding.PropertyType;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class BaseController {

    /**
     * função para preparar as mensagens de error na validação nos campos do model
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HashMap handleValidationExceptions(MethodArgumentNotValidException ex) {
        HashMap errors = new HashMap<>();
        errors.put("success", false);

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) objectError;
            String message = objectError.getDefaultMessage();
            errors.put(fieldError.getField(), message);
        }
        return errors;
    }

    /**
     * função para preparar a mensagem de error na validação do JSON
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException.class)
    public HashMap<String, ?>  handleJSONExceptions(JsonParseException ex)  {
        HashMap errors = new HashMap<>();
        errors.put("success", false);
        errors.put("message", ex.getOriginalMessage());
        return errors;
    } /**
     * função para preparar a mensagem de error caso não seja mandado um body
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HashMap<String, ?>  handleBodyExceptions()  {

        HashMap errors = new HashMap<>();
        errors.put("success", false);
        errors.put("message", "required request body is missing");
        return errors;
    }
}
