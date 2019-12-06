package com.mplus.system.repo;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Module;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends BaseRepository<Module, String> {

}
