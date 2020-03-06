package com.example.demo.repository;

import com.example.demo.model.Details;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Service
public class DetailsRepositoryImpl{
    @PersistenceContext
    private EntityManager em;
    public List<Object[]> getAllDetails(){
        StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("getAllProduct");
        return storedProcedureQuery.getResultList();
    }

    public List<Object[]> getDetailsById(Long id){
        StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("getProductById");
        storedProcedureQuery.registerStoredProcedureParameter("id", Long.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("id", id);
        return storedProcedureQuery.getResultList();
    }


}
