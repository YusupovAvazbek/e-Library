package com.example.elibrary.service.serviceImpl;

import com.example.elibrary.dto.BookDetailDto;
import com.example.elibrary.dto.BookDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.model.Book;
import com.example.elibrary.model.BookDetail;
import com.example.elibrary.repository.BookDetailRepository;
import com.example.elibrary.repository.BookRepository;
import com.example.elibrary.repository.BookRepositoryImpl;
import com.example.elibrary.rest.BookResources;
import com.example.elibrary.service.BookService;
import com.example.elibrary.service.FileService;
import com.example.elibrary.service.mapper.BookMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.example.elibrary.service.validator.AppStatusCode.*;
import static com.example.elibrary.service.validator.AppStatusMessages.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookDetailRepository bookDetailRepository;
    private final BookRepositoryImpl bookRepositoryImpl;
    private final FileService fileService;
    @Override
    public ResponseDto<Page<EntityModel<BookDto>>> getAllBooks(Integer page, Integer size) {
        size = Math.max(size, 1);
        page = Math.max(page, 0);
        Long count = bookRepository.count();
        PageRequest pageRequest = PageRequest.of((count / size) <= page ?
                (count % size == 0) ? (int) (count / size) - 1 : (int) (count / size)
                : page, size);
        pageRequest.withSort(Sort.by("price").descending());

        Page<EntityModel<BookDto>> all = bookRepository
                .findAll(pageRequest)
                .map(p-> {
                    EntityModel<BookDto> entityModel = EntityModel.of(bookMapper.toDto(p));
                    try {
                        entityModel.add(linkTo(BookResources.class
                                .getDeclaredMethod("getById", Integer.class, HttpServletRequest.class))
                                .withSelfRel()
                                .expand(p.getId()));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;

                });

        return ResponseDto.<Page<EntityModel<BookDto>>>builder()
                .code(OK_CODE)
                .success(true)
                .message(OK)
                .data(all)
                .build();
    }
    @Override
    public ResponseDto<BookDto> add(BookDto bookDto, MultipartFile file) {
        Optional<Book> book = bookRepository.findByTitle(bookDto.getTitle());
        if(!book.isEmpty()){
            return ResponseDto.<BookDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }try {
            Book newBook = bookMapper.toEntity(bookDto);
            ResponseDto<Integer> integerResponseDto = fileService.fileUpload(file);
            newBook.setFileId(integerResponseDto.getData());

            bookRepository.save(newBook);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseDto.<BookDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(bookDto)
                .build();
    }
    @Override
    public ResponseDto<BookDto> edit(BookDto bookDto) {
        if(bookDto.getId() == null){
            return ResponseDto.<BookDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }
        Optional<Book> optional = bookRepository.findByIdAndIsAvailable(bookDto.getId(), (short) 1);
        if(optional.isEmpty()){
            return ResponseDto.<BookDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        try{
            Book book = optional.get();
            if(bookDto.getBookDetail() == null) {
                bookRepository.save(editBook(book, bookDto));
            }else {
                bookRepository.save(editBook(book, bookDto));
                bookDetailRepository.save(editBookDetails(book.getBookDetail(), bookDto.getBookDetail()));
            }
            return ResponseDto.<BookDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(bookMapper.toDto(book))
                    .build();
        }catch (Exception e){
            return ResponseDto.<BookDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> delete(Integer id) {
        Optional<Book> byId = bookRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseDto.<BookDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        try {
            Book book = byId.get();
            book.setIsAvailable((short) 0);
            bookRepository.save(book);
            return ResponseDto.<BookDto>builder()
                    .success(true)
                    .data(bookMapper.toDto(book))
                    .code(OK_CODE)
                    .message(OK)
                    .build();

        }catch (Exception e){
            return ResponseDto.<BookDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> getById(Integer id) {
        return bookRepository.findById(id)
                .map(b -> ResponseDto.<BookDto>builder()
                        .success(true)
                        .data(bookMapper.toDto(b))
                        .code(OK_CODE)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<BookDto>builder()
                        .message(NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build());
    }

    @Override
    public ResponseDto<Page<BookDto>> universalSearch(Map<String, String> params) {
        Page<Book> products = bookRepositoryImpl.universalSearch(params);
        if(products.isEmpty()){
            return ResponseDto.<Page<BookDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .success(false)
                    .message(NOT_FOUND)
                    .build();
        }

        return ResponseDto.<Page<BookDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(products.map(bookMapper::toDto))
                .build();

    }
    @Override
    public ResponseDto<List<BookDto>> getExpensiveBooks() {
        List<Book> expensiveBooks = bookRepository.getExpensiveBooks();
        List<BookDto> list = expensiveBooks.stream()
                .map(book -> bookMapper.toDto(book))
                .toList();
        return ResponseDto.<List<BookDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(list)
                .build();
    }


    private BookDetail editBookDetails(BookDetail bookDetail, BookDetailDto bookDetailDto) {
    if(bookDetailDto.getId() != null){
        bookDetail.setId(bookDetailDto.getId());
    }
    if(bookDetailDto.getAuthor() != null){
        bookDetail.setAuthor(bookDetailDto.getAuthor());
    }
    if(bookDetailDto.getPages() != null){
        bookDetail.setPages(bookDetailDto.getPages());
    }
    if(bookDetailDto.getLanguage() != null){
        bookDetail.setLanguage(bookDetailDto.getLanguage());
    }
    if(bookDetailDto.getReleaceDate() !=null){
        bookDetail.setReleaceDate(bookDetailDto.getReleaceDate());
    }
    return bookDetail;
    }

    private Book editBook(Book book, BookDto bookDto){
        if(bookDto.getTitle() != null){
            book.setTitle(bookDto.getTitle());
        }
        if(bookDto.getAmount() != null){
            book.setAmount(bookDto.getAmount());
        }
        if(bookDto.getDescription() != null){
            book.setDescription(bookDto.getDescription());
        }
        if(bookDto.getPrice() != null){
            book.setPrice(bookDto.getPrice());
        }

        return book;
    }
}
