package com.example.backend.core.admin.repository.impl;


import com.example.backend.core.admin.repository.DiscountAdminCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DiscountAdminCustomRepositoryImpl implements DiscountAdminCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void deleteAllDiscountDetailByDiscount(Long id) {
        try {
            Query query = entityManager.createQuery("delete from DiscountDetail where idDiscount = :idDiscount");
            query.setParameter("idDiscount", id);
            query.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
