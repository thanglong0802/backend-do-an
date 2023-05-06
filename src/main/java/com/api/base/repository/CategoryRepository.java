package com.api.base.repository;

import com.api.base.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "WITH RECURSIVE cte(id, created_at, created_by, updated_at, updated_by, name_category, parent_id) AS ( SELECT id, created_at, created_by, updated_at, updated_by, name_category, parent_id FROM tbl_category WHERE id = :id UNION ALL SELECT tb.id, tb.created_at, tb.created_by, tb.updated_at, tb.updated_by, tb.name_category, tb.parent_id FROM cte ct, tbl_category tb WHERE ct.id = tb.parent_id) SELECT id, created_at, created_by, updated_at, updated_by, name_category, parent_id FROM cte", nativeQuery = true)
    List<Category> directoryList(@Param("id") Long id);

    List<Category> findCategoryByParentIdIsNull();

    @Query(value = "WITH RECURSIVE cte AS (SELECT id, created_at, created_by, updated_at, updated_by, name_category, parent_id FROM tbl_category WHERE id = :id UNION ALL SELECT c.id, c.created_at, c.created_by, c.updated_at, c.updated_by, c.name_category, c.parent_id FROM tbl_category c JOIN cte ON c.parent_id = cte.id) SELECT p.id, p.category_id, p.status, p.price, p.quantity, p.description, p.use, p.producer, p.where_production, p.name, p.created_at, p.created_by, p.updated_at, p.updated_by, c.id, c.name_category, c.parent_id FROM tbl_product p JOIN cte c ON p.category_id = c.id", nativeQuery = true)
    List<Category> getAllProductsInTheCategory(@Param("id") Long id);
}
