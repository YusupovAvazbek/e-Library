package com.example.elibrary.repository;

import com.example.elibrary.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl {
    private final EntityManager entityManager;
    public Page<Book> universalSearch(Map<String, String> params){
        int page = 0, size = 10;
        if(params.containsKey("page")){
            page = Integer.parseInt(params.get("page"));
        }
        if(params.containsKey("size")){
            size = Math.max(Integer.parseInt(params.get("size")), 1);
        }

        String sqlQuery = "select p from Book p where 1 = 1 ";
        String sqlCount = "select count(1) from Book p where 1 = 1 ";

        StringBuilder queryCondition = new StringBuilder();
        generateQueryCondition(queryCondition, params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition);
        Query countQuery = entityManager.createQuery(sqlCount + queryCondition);

        setParam(query, params);
        setParam(countQuery, params);
        long count = (long) countQuery.getSingleResult();

        if(count > 0 && count/size <= page){
            if(count % size == 0){
                page = (int) count/size - 1;
            }else {
                page = (int) count/size;
            }
        }

        query.setFirstResult(size * page);
        query.setMaxResults(size);

        return new PageImpl<>(query.getResultList(), PageRequest.of(page, size), count);

    }

    private void generateQueryCondition(StringBuilder queryCondition , Map<String , String> params){
        if(params.containsKey("title")){
            queryCondition.append(" and upper(p.title ) like :title ");
        }
        if(params.containsKey("amount")){
            queryCondition.append(" AND p.amount = :amount ");
        }
        if(params.containsKey("description")){
            queryCondition.append(" AND upper(p.description) like :description ");
        }
        if(params.containsKey("price")){
            queryCondition.append(" AND p.price = :price ");
        }
        if(params.containsKey("author")){
            queryCondition.append(" AND upper(p.bookDetail.author) like :author");
        }
    }
    private void setParam(Query query , Map<String , String> params){
        if(params.containsKey("title")){
            query.setParameter("title","%"+params.get("title").toUpperCase()+"%");
        }
        if(params.containsKey("amount")){
            query.setParameter("amount",Integer.parseInt(params.get("amount")));
        }
        if(params.containsKey("description")){
            query.setParameter("description","%"+params.get("description").toUpperCase()+"%");
        }
        if(params.containsKey("price")){
            query.setParameter("price",Integer.parseInt(params.get("price")));
        }
        if (params.containsKey("author")){
            query.setParameter("author","%"+params.get("author").toUpperCase()+"%");
        }
    }
}
