package com.example.elibrary.security.userrole;

public enum UserAuthorities {
    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private String name;
    UserAuthorities(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
