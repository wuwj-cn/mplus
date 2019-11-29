package com.mplus.common.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * JpaRepository满足基本的增删改查、分页等需求
 * JpaSpecificationExecutor满足多可选条件查询需求
 * @author wuwj
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaSpecificationExecutor<T>,JpaRepository<T, ID> {

}
