package com.example.vinifyveneto.entity;

public class ResponseEntity<T> {
    private int code;
    private String message;
    private T entity;

    public ResponseEntity(T entity, String message, int code){
        this(message,code);
        this.entity=entity;
    }

    public ResponseEntity(String messsage, int code){
        this.message=messsage;
        this.code=code;
        this.entity=null;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

    public T getEntity(){
        return entity;
    }
}
