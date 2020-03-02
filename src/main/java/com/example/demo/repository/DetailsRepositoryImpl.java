package com.example.demo.repository;

import com.example.demo.model.Details;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Service
public class DetailsRepositoryImpl{
    @PersistenceContext
    private EntityManager em;
    public List<Details> getAllDetails(){
        StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("getAllProduct");
        return storedProcedureQuery.getResultList();
    }

}
