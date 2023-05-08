package com.example.elibrary.rest;

import com.example.elibrary.dto.BookDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.model.Book;
import com.example.elibrary.service.BookService;
import com.example.elibrary.service.serviceImpl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(
            method = "get all books",
            summary = "get all books",
            description = "get all active books",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json"))
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseDto<Page<EntityModel<BookDto>>> getAllBooks(@RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "1") Integer size){
        return bookService.getAllBooks(page, size);
    }
    @Operation(
            method = "add new book",
            summary = "add new book",
            description = "Need to send BookDto to this end point to add new book",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "send BookDto",
                    content = @Content(mediaType = "application/json"))
    )
    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public ResponseDto<BookDto> add(@RequestPart BookDto bookInfo, @RequestPart MultipartFile file) throws IOException {
        return bookService.add(bookInfo, file);

    }
    @Operation(
            method = "edit book",
            summary = "edit book",
            description = "Need to send book fields to this end point to update book",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json"))
    )
    @PatchMapping
    public ResponseDto<BookDto> edit(@RequestBody BookDto bookDto) {
        return bookService.edit(bookDto);
    }
    @Operation(
            method = "get book by id",
            summary = "get book by id",
            description = "Need to send book id to this end point to get book",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Integer book_id",
                    content = @Content(mediaType = "application/json"))
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseDto<BookDto> getById(@PathVariable Integer id, HttpServletRequest request){
        return bookService.getById(id);
    }
    @Operation(
            method = "universal search",
            summary = "search",
            description = "Need to send any book fields to this end point to found book",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "bookDto",
                    content = @Content(mediaType = "application/json"))
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("universal-search")
    public ResponseDto<Page<BookDto>> universalSearch(@RequestParam Map<String,String> params, ServletRequest request){
        System.out.println(request);
        return bookService.universalSearch(params);
    }
    @Operation(
            method = "get expensive books",
            summary = "get expensive books",
            description = "expensive books",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Expensive books info",
                    content = @Content(mediaType = "application/json"))
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("expensive-by-genre")
    public ResponseDto<List<BookDto>> getExpensiveBooks(){
        return bookService.getExpensiveBooks();
    }

}
