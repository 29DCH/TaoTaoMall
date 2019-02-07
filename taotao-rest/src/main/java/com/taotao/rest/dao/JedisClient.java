package com.taotao.rest.dao;

public interface JedisClient {

	String get(String key);
	String set(String key, String value);
	long incr(String key);
	Long hset(String hkey, String key, String value);
	String hget(String hkey, String key);
	Long del(String key);
	Long hdel(String hkey, String key);
	Long expire(String key, int second);
}
