package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CategoryRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CategoryRepository extends CrudRepository<CategoryRecord, String> {
}
