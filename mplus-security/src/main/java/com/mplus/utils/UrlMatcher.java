package com.mplus.utils;

/**
 * url匹配接口
 * @author wuwj
 *
 */
public interface UrlMatcher {

	Object compile(String paramString);
    boolean pathMatchesUrl(Object paramObject, String paramString);
    String getUniversalMatchPattern();
    boolean requiresLowerCaseUrl();
    
}
