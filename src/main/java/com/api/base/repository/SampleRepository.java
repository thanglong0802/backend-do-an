package com.api.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.base.entity.Sample;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

}
