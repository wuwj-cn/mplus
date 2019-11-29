package com.mplus.common.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.mplus.common.entity.BaseEntity;
import com.mplus.common.enums.DataStatus;
import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.BaseService;

public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements BaseService<T, ID> {

	public abstract BaseRepository<T, ID> getRepository();
	
	@Override
	public T save(T t){
		if(StringUtils.isNotBlank(t.getId())) {
			throw new RuntimeException("object id is not null");
		}
//		User user = UserUtils.getCurrentUser();
		Date now = new Date();
//		t.setCreateBy(user.getId());
		t.setCreateDate(now);
//		t.setUpdateBy(user.getId());
		t.setUpdateDate(now);
		t.setDataStatus(DataStatus.NORMAL.getCode());
		return getRepository().save(t);
	}
	
	@Override
	public Iterable<T> save(Iterable<T> entities){
//		User user = UserUtils.getCurrentUser();
		Date now = new Date();
		for (T t : entities) {
			if(StringUtils.isNotBlank(t.getId())) {
				throw new RuntimeException("object id is not null");
			}
//			t.setCreateBy(user.getId());
			t.setCreateDate(now);
//			t.setUpdateBy(user.getId());
			t.setUpdateDate(now);
			t.setDataStatus(DataStatus.NORMAL.getCode());
		}
		return getRepository().saveAll(entities);
	}
	
	@Override
	public T update(T t) {
		if (StringUtils.isBlank(t.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
//		User user = UserUtils.getCurrentUser();
		Date now = new Date();
//		t.setUpdateBy(user.getId());
		t.setUpdateDate(now);
		return getRepository().save(t);
	}

	@Override
	public Iterable<T> update(Iterable<T> entities) {
//		User user = UserUtils.getCurrentUser();
		Date now = new Date();
		for (T t : entities) {
			if(StringUtils.isBlank(t.getId())) {
				throw new RuntimeException("object id is null or empty");
			}
//			t.setUpdateBy(user.getId());
			t.setUpdateDate(now);
		}
		return getRepository().saveAll(entities);
	}
	
	@Override
	public void delete(ID id){
		Optional<T> t = findById(id);
		if(t == null){
			return;
		}
		delete(t.get());
	}
	
	@Override
	public void delete(T t) {
//		User user = UserUtils.getCurrentUser();
		Date now = new Date();
//		t.setUpdateBy(user.getId());
		t.setUpdateDate(now);
		t.setDataStatus(DataStatus.DELETED.getCode());
		getRepository().save(t);
	}
	
	@Override
	public Optional<T> findById(ID id) {
		return getRepository().findById(id);
	}
	
	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}
	
	@Override
	public Page<T> findAll(Pageable pageable){
		return getRepository().findAll(pageable);
	}
	
	@Override
	public List<T> find(@Nullable final Map<String, Object> params) {
		Specification<T> spec = getSpecification(params);
		List<T> list = getRepository().findAll(spec);
		return list;
	}
	
	@Override
	public Page<T> findPage(@Nullable final Map<String, Object> params,Pageable pageable){
		Specification<T> spec = getSpecification(params);
		Page<T> page = getRepository().findAll(spec, pageable);
		return page;
	}
	
	public List<T> find(@Nullable Map<String, Object> params, Sort sort) {
		Specification<T> spec = getSpecification(params);
		List<T> list = getRepository().findAll(spec, sort);
		return list;
	}
	
	private Specification<T> getSpecification(@Nullable Map<String, Object> params) {
		@SuppressWarnings("serial")
		Specification<T> spec = new Specification<T>() {  
			@Override
			public Predicate toPredicate(Root<T> root,CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				Predicate predicate = null;
				boolean containStatusKey = false;
				for(Entry<String, Object> entry : params.entrySet()){
					Object value = entry.getValue();
					if(value == null || StringUtils.isBlank(value.toString())){
						continue;
					}
					String key = entry.getKey();
					String[] arr = key.split(":");
					predicate = getPredicate(arr,value,root,cb);
					list.add(predicate);
					if(key.startsWith("dataStatus")) containStatusKey = true;
				}
				//查询条件默认增加dataStatus的条件，默认dataStatus="0"
				if(!containStatusKey) {
					predicate = getPredicate("dataStatus:eq".split(":"), DataStatus.NORMAL.getCode(), root, cb);
				}
				list.add(predicate);
			    Predicate[] p = new Predicate[list.size()];  
			    return cb.and(list.toArray(p));  
			}
		};  
		return spec;
	}
	
	
	private Predicate getPredicate(String[] arr, Object value,
			Root<T> root, CriteriaBuilder cb) {
		if(arr.length == 1 || QueryTypeEnum.eq.name().equals(arr[1])){
			return cb.equal(root.get(arr[0]).as(value.getClass()), value);  
		}
		if(QueryTypeEnum.like.name().equals(arr[1])){
			return cb.like(root.get(arr[0]).as(String.class), String.format("%%%s%%", value));
		}
		if(QueryTypeEnum.ne.name().equals(arr[1])){
			return cb.notEqual(root.get(arr[0]).as(value.getClass()), value);
		}
		if(QueryTypeEnum.lt.name().equals(arr[1])){
			return getLessThanPredicate(arr,value,root,cb);
		}
		if(QueryTypeEnum.lte.name().equals(arr[1])){
			return getLessThanOrEqualToPredicate(arr,value,root,cb);
		}
		if(QueryTypeEnum.gt.name().equals(arr[1])){
			return getGreaterThanPredicate(arr,value,root,cb);
		}
		if(QueryTypeEnum.gte.name().equals(arr[1])){
			return getGreaterThanOrEqualToPredicate(arr,value,root,cb);
		}
		throw new UnsupportedOperationException(String.format("不支持的查询类型[%s]",arr[1]));
	}

	private Predicate getLessThanPredicate(String[] arr, Object value,
			Root<T> root, CriteriaBuilder cb) {
		if(Integer.class == value.getClass()){
			return cb.lessThan(root.get(arr[0]).as(Integer.class), (int)value);
		}
		if(Long.class == value.getClass()){
			return cb.lessThan(root.get(arr[0]).as(Long.class), (long)value);
		}
		if(Double.class == value.getClass()){
			return cb.lessThan(root.get(arr[0]).as(Double.class), (double)value);
		}
		if(Float.class == value.getClass()){
			return cb.lessThan(root.get(arr[0]).as(Float.class), (float)value);
		}
		if(Timestamp.class == value.getClass()){
			return cb.lessThan(root.get(arr[0]).as(Timestamp.class), (Timestamp)value);
		}
		if(Date.class == value.getClass()){
			return cb.lessThan(root.get(arr[0]).as(Date.class), (Date)value);
		}
		return cb.lessThan(root.get(arr[0]).as(String.class), String.valueOf(value));
	}

	private Predicate getLessThanOrEqualToPredicate(String[] arr,
			Object value, Root<T> root, CriteriaBuilder cb) {
		if(Integer.class == value.getClass()){
			return cb.lessThanOrEqualTo(root.get(arr[0]).as(Integer.class), (int)value);
		}
		if(Long.class == value.getClass()){
			return cb.lessThanOrEqualTo(root.get(arr[0]).as(Long.class), (long)value);
		}
		if(Double.class == value.getClass()){
			return cb.lessThanOrEqualTo(root.get(arr[0]).as(Double.class), (double)value);
		}
		if(Float.class == value.getClass()){
			return cb.lessThanOrEqualTo(root.get(arr[0]).as(Float.class), (float)value);
		}
		if(Timestamp.class == value.getClass()){
			return cb.lessThanOrEqualTo(root.get(arr[0]).as(Timestamp.class), (Timestamp)value);
		}
		if(Date.class == value.getClass()){
			return cb.lessThanOrEqualTo(root.get(arr[0]).as(Date.class), (Date)value);
		}
		return cb.lessThanOrEqualTo(root.get(arr[0]).as(String.class), String.valueOf(value));
	}

	private Predicate getGreaterThanPredicate(String[] arr,
			Object value, Root<T> root, CriteriaBuilder cb) {
		if(Integer.class == value.getClass()){
			return cb.greaterThan(root.get(arr[0]).as(Integer.class), (int)value);
		}
		if(Long.class == value.getClass()){
			return cb.greaterThan(root.get(arr[0]).as(Long.class), (long)value);
		}
		if(Double.class == value.getClass()){
			return cb.greaterThan(root.get(arr[0]).as(Double.class), (double)value);
		}
		if(Float.class == value.getClass()){
			return cb.greaterThan(root.get(arr[0]).as(Float.class), (float)value);
		}
		if(Timestamp.class == value.getClass()){
			return cb.greaterThan(root.get(arr[0]).as(Timestamp.class), (Timestamp)value);
		}
		if(Date.class == value.getClass()){
			return cb.greaterThan(root.get(arr[0]).as(Date.class), (Date)value);
		}
		return cb.greaterThan(root.get(arr[0]).as(String.class), String.valueOf(value));
	}

	private Predicate getGreaterThanOrEqualToPredicate(String[] arr,Object value,
			Root<T> root, CriteriaBuilder cb) {
		if(Integer.class == value.getClass()){
			return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Integer.class), (int)value);
		}
		if(Long.class == value.getClass()){
			return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Long.class), (long)value);
		}
		if(Double.class == value.getClass()){
			return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Double.class), (double)value);
		}
		if(Float.class == value.getClass()){
			return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Float.class), (float)value);
		}
		if(Timestamp.class == value.getClass()){
			return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Timestamp.class), (Timestamp)value);
		}
		if(Date.class == value.getClass()){
			return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Date.class), (Date)value);
		}
		return cb.lessThanOrEqualTo(root.get(arr[0]).as(String.class), String.valueOf(value));
	}  

	enum QueryTypeEnum {
		like,
		eq,
		ne,
		lt,
		lte,
		gt,
		gte
	}
}
