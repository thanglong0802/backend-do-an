package com.api.base.repository;

import com.api.base.entity.ProductValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductValueRepository extends JpaRepository<ProductValue, Long> {
}
