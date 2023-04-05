package com.example.elibrary.service.mapper;

public interface CommonMapper <D, E>{
    E toEntity(D d);
    D toDto(E e);
}
