package com.mplus.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StopWatch;

import java.util.Properties;

@Intercepts({
        @Signature(
                type= Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        ),
        @Signature(
                type= Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
        )
})
@Slf4j
public class ExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StopWatch stopWatch = new StopWatch();stopWatch.start();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String id = mappedStatement.getId();
        Object proceed = null;
        try {
            proceed =  invocation.proceed();
        }finally {
            stopWatch.stop();
            log.debug("sqlId:"+id+" 执行时间为:"+stopWatch.getTotalTimeMillis()+" ms");
        }
        return proceed;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
