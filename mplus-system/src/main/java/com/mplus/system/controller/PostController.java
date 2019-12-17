/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mplus.system.controller;

import com.mplus.common.enums.DataState;
import com.mplus.common.exception.GenericException;
import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.system.entity.Org;
import com.mplus.system.entity.Post;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.repo.PostRepository;
import com.mplus.system.vo.PostVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

/**
 * PostController
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class PostController {
    private PostRepository postRepository;
    private OrgRepository orgRepository;

    @PostMapping(value = "/orgs/{orgCode}/posts")
    @SneakyThrows
    public Result<String> addPost(@PathVariable String orgCode, @RequestBody PostVo postVo) {
        Org org = new Org();
        org.setOrgCode(orgCode);
        org.setDataState(DataState.NORMAL.code());
        org = orgRepository.findOne(Example.of(org)).get();
        if(!org.getOrgCode().equals(postVo.getOrgCode())) {
            throw new GenericException(String.format("this object is not belong to [%s]", orgCode));
        }

        Post post = new Post();
        MBeanUtils.copyPropertiesIgnoreNull(postVo, post);
        post.setOrg(org);
        postRepository.save(post);
        return Result.success(post.getId());
    }
}
