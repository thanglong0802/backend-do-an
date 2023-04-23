package com.api.base.repository;

import com.api.base.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "WITH RECURSIVE cte(id, created_at, created_by, updated_at, updated_by, name, parent_id) AS ( SELECT id, created_at, created_by, updated_at, updated_by, name, parent_id FROM tbl_category WHERE id = :id UNION ALL SELECT tb.id, tb.created_at, tb.created_by, tb.updated_at, tb.updated_by, tb.name, tb.parent_id FROM cte ct, tbl_category tb WHERE ct.id = tb.parent_id) SELECT id, created_at, created_by, updated_at, updated_by, name, parent_id FROM cte", nativeQuery = true)
    List<Category> directoryList(@Param("id") Long id);

    List<Category> findCategoryByParentIdIsNull();
}
