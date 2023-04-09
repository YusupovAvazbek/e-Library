package com.example.elibrary.rest;

import com.example.elibrary.dto.BookDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.model.Book;
import com.example.elibrary.service.BookService;
import com.example.elibrary.service.serviceImpl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookResources {
    private final BookService bookService;
    @GetMapping
    public ResponseDto<Page<EntityModel<BookDto>>> getAllBooks(@RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "1") Integer size){
        return bookService.getAllBooks(page, size);
    }
    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public ResponseDto<BookDto> add(@RequestPart BookDto bookInfo, @RequestPart MultipartFile file) throws IOException {
        return bookService.add(bookInfo, file);

    }
    @PatchMapping
    public ResponseDto<BookDto> edit(@RequestBody BookDto bookDto) {
        return bookService.edit(bookDto);
    }
    @GetMapping("/{id}")
    public ResponseDto<BookDto> getById(@PathVariable Integer id){
        return bookService.getById(id);
    }
    @GetMapping("universal-search")
    public ResponseDto<Page<BookDto>> universalSearch(@RequestParam Map<String,String> params){
        return bookService.universalSearch(params);
    }
    @GetMapping("expensive-by-genre")
    public ResponseDto<List<BookDto>> getExpensiveBooks(){
        return bookService.getExpensiveBooks();
    }

}
