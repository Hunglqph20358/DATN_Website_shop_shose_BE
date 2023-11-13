package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAdminRepository extends JpaRepository<Product,Long> {
//    @Query(value = "select prd.id,prd.code,prd.name,prd.createDate,prd.updateDate,prd.createName,prd.updateName,br.id,ct.id,mt.id,prd.description,prd.status,prd.idel,sl.id from Product prd join Brand br on prd.idBrand = br.id join Category ct on prd.idCategory = ct.id join " +
//            "Material  mt on prd.idMaterial = mt.id join Sole sl on prd.idSole = sl.id")
//    List<Product> getall();
//@Query("SELECT prd.id,prd.code,prd.name,prd.createDate,prd.updateDate,prd.createName,prd.updateName,prd.idBrand,prd.idCategory,prd.idMaterial,prd.description,prd.status,prd.idel,prd.idSole FROM Product prd JOIN prd.idBrand br JOIN prd.idCategory ct JOIN prd.idMaterial mt JOIN prd.idSole sl")
//    List<Product> getall();
}