package com.samton.common.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.samton.common.config.CommonConfig;
import com.samton.common.keygen.KeyGenerator;

import net.spy.memcached.internal.OperationFuture;

/**
 * @author yangpeng
 * redis操作工具类
 */
public class RedisPool {

    private static RedisPool instance = null;
    private JedisPool pool = null;

    private int dbIndex;
    
	private static final int DEFAULT_EXPIRE = 60 * 60;

    protected RedisPool() { }

    public static void main(String[] args){
    	getRedisPool().set("123", "12434");
    	getRedisPool().get("123");
    }
    
    public static RedisPool getRedisPool() {
        if (instance == null) {
            synchronized (RedisPool.class) {
                if (instance == null) {
                    instance = new RedisPool();
                    instance.initPool();
                }
            }
        }

        return instance;
    }

    private void initPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        CommonConfig config = CommonConfig.getInstance();
        poolConfig.setMaxTotal(config.getRedisMaxTotal());
        poolConfig.setMaxIdle(config.getRedisMaxIdle());
        poolConfig.setMaxWaitMillis(config.getRedisMaxWaitMillis());
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        
        if(StringUtils.isEmpty(config.getRedisPWD())){
        	this.pool = new JedisPool(poolConfig, config.getRedisIp(), config.getRedisPort(), config.getRedisTimeout());
        }else{
        	this.pool = new JedisPool(poolConfig, config.getRedisIp(), config.getRedisPort(), config.getRedisTimeout(),
    	            config.getRedisPWD());
        }
        
        //this.pool = new JedisPool(poolConfig, config.getRedisIp(), config.getRedisPort(), config.getRedisTimeout(),config.getRedisPWD());
        this.dbIndex = config.getRedisDbIndex();
    }

    /**
     * 查询key值下的排序Set集合中value的下标
     *
     * @param key
     * @param value
     * @return
     */
    public long getValueIndex(String key, String value, String rev) {
        try (Jedis conn = this.getConnection()) {
            if ("rev".equals(rev)) {
                //降序下标
                return conn.zrevrank(key, value);
            } else {
                //升序下标
                return conn.zrank(key, value);
            }
        }
    }

    public long setExpire(String key, int expire) {
        try (Jedis conn = this.pool.getResource()) {
            return conn.expire(key, expire);
        }
    }

    public boolean exists(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.exists(key);
        }
    }


    public void set(String key, String value, int expire) {
        try (Jedis conn = this.getConnection()) {
            conn.set(key, value);
            if (expire != -1) {
                conn.expire(key, expire);
            }
        }
    }

    public void setList(String key, String value, int expire) {
        try (Jedis conn = this.getConnection()) {
            conn.lpush(key, value);
            if (expire != -1) {
                conn.expire(key, expire);
            }
        }
    }
    
    public void setSet(String key, String value, int expire) {
    	
        try (Jedis conn = this.getConnection()) {
            conn.sadd(key, value);
            if (expire != -1) {
                conn.expire(key, expire);
            }
        }
    }
    
    //增加列表，不设置过期时间
    public void setSet(String key, String value){
    	 try (Jedis conn = this.getConnection()) {
             conn.sadd(key, value);
         }
    }

    public void setSortedSet(String key, double score, String value, int expire) {
        try (Jedis conn = this.getConnection()) {
            conn.zadd(key, score, value);
            if (expire != -1) {
                conn.expire(key, expire);
            }
        }
    }

    public void incrSortedSet(String key, double score, String value, int expire) {
        try (Jedis conn = this.getConnection()) {
            conn.zincrby(key, score, value);
            if (expire != -1) {
                conn.expire(key, expire);
            }
        }
    }

    public void clearDB() {
        try (Jedis conn = this.getConnection()) {
            conn.flushDB();
        }
    }

    public int clearByPattern(String pattern) {
        try (Jedis conn = this.getConnection()) {
            Set<String> keys = conn.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                for (String key : keys) {
                    conn.del(key);
                }

                return keys.size();
            }

            return 0;
        }
    }

    public Set allKeys(String patternstr) {
        try (Jedis conn = this.getConnection()) {
            return conn.keys(patternstr);
        }
    }

    public void set(String key, String value) {
        this.set(key, value, -1);
    }

    public void addList(String key, String value) {
        this.setList(key, value, -1);
    }

    public String get(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.get(key);
        }
    }

    public List<String> mget(String... keys) {
        try (Jedis conn = this.getConnection()) {
            return conn.mget(keys);
        }
    }

    public List<String> getList(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.lrange(key, 0, -1);
        }
    }

    public Set<String> getSet(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.smembers(key);
        }
    }

    /**
     * 取出排序Set中的Set集合信息
     *
     * @param key      Set的Key值
     * @param rev      Set取出后的排序方式   值为‘rev’倒序，其他为降序
     * @param pageSize 分页参数  页面大小
     * @return
     */
    public Set<String> getSortedSet(String key, String rev, Integer currentPage, Integer pageSize) {
        //处理分页参数
        int index = 0;
        int endIndex = -1;
        if (currentPage != null && pageSize != null && currentPage > 0 && pageSize > 0) {
            index = (currentPage - 1) * pageSize;
            endIndex = index + pageSize - 1;
        }

        try (Jedis conn = this.getConnection()) {
            if ("rev".equals(rev)) {
                return conn.zrevrange(key, index, endIndex);
            } else {
                return conn.zrange(key, index, endIndex);
            }
        }
    }

    /**
     * 取出排序Set中的Set集合信息
     *
     * @param key      Set的Key值
     * @param rev      Set取出后的排序方式   值为‘rev’倒序，其他为降序
     * @param pageSize 分页参数  页面大小
     * @return
     */
    public Set<redis.clients.jedis.Tuple> getSortedSetWithScores(String key, String rev, Integer currentPage, Integer pageSize) {
        //处理分页参数
        int index = 0;
        int endIndex = -1;
        if (currentPage != null && pageSize != null && currentPage > 0 && pageSize > 0) {
            index = (currentPage - 1) * pageSize;
            endIndex = index + pageSize - 1;
        }

        try (Jedis conn = this.getConnection()) {
            if ("rev".equals(rev)) {
                return conn.zrevrangeWithScores(key, index, endIndex);
            } else {
                return conn.zrangeWithScores(key, index, endIndex);
            }
        }
    }

    //按升序获取
    public Set<String> getIncSortedSet(String key) {
        Set<String> ret = null;
        try (Jedis conn = this.getConnection()) {
            //从大到小
            return conn.zrange(key, 0, -1);
        }
    }

    public Set<redis.clients.jedis.Tuple> getSortedSetWithScore(String key) {
        Set<Tuple> ret = null;
        try (Jedis conn = this.getConnection()) {
            //从大到小
            return conn.zrevrangeWithScores(key, 0, -1);
        }
    }

    public void del(String key) {
        try (Jedis conn = this.getConnection()) {
            conn.del(key);
        }
    }

    public long delFromSet(String key, String member) {
        try (Jedis conn = this.getConnection()) {
            return conn.srem(key, member);
        }
    }

    public long delFromSortedSet(String key, String member) {
        try (Jedis conn = this.getConnection()) {
            return conn.zrem(key, member);
        }
    }

    public long delFromSortedSet(String key, Double min, Double max) {
        try (Jedis conn = this.getConnection()) {
        	conn.zremrangeByScore(key, min, max);
            return conn.zremrangeByScore(key,1,3);
        }
    }

    public boolean isInSet(String key, String member) {
        try (Jedis conn = this.getConnection()) {
            return conn.sismember(key, member);
        }
    }

    public long getValueSize(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.scard(key);
        }
    }

    public long getSortedSetValueSize(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.zcard(key);
        }
    }

    public long IncValue(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.incr(key);
        }
    }

    public long DecValue(String key) {
        try (Jedis conn = this.getConnection()) {
            return conn.decr(key);
        }
    }

    public boolean isInSortedSet(String key, String member) {
        try (Jedis conn = this.getConnection()) {
            Long value = conn.zrank(key, member);
            if (value != null && value >= 0) {
                return true;
            }
        }

        return false;
    }

    private Jedis getConnection() {
        Jedis conn = this.pool.getResource();
        conn.select(this.dbIndex);

        return conn;
    }

    /**
     * key的值在原有基础增加num
     * @param key
     * @param num
     * @return
     */
	public long IncByValue(String key, int num) {
		try (Jedis conn = this.getConnection()) {
	            return conn.incrBy(key, num);
	      }
	}
	
	public long decByValue(String key, int num) {
        try (Jedis conn = this.getConnection()) {
                return conn.decrBy(key, num);
          }
    }
	
	//设置值
	public long Hset(String Mainkey,String field,String value) {
        try (Jedis conn = this.getConnection()) {
                return conn.hset(Mainkey, field, value);
          }
    }
	
	//设置值
	public long Hset(String Mainkey,String field,String value,int expire) {
		try (Jedis conn = this.getConnection()) {
			long rs = conn.hset(Mainkey, field, value);
			if (expire != -1) {
				conn.expire(Mainkey, expire);
			}
			return rs;
		}
    }
	//根据key和field获取单个值
	public String Hget(String Mainkey,String field) {
        try (Jedis conn = this.getConnection()) {
                return conn.hget(Mainkey, field);
          }
    }
	
	//根据key获取所有的field和value
	public Map<String, String> Hget(String Mainkey) {
        try (Jedis conn = this.getConnection()) {
                return conn.hgetAll(Mainkey);
          }
    }
	
	//根据key和Field删除值
	public Long Hdel(String Mainkey,String Field) {
        try (Jedis conn = this.getConnection()) {
                return conn.hdel(Mainkey, Field);
          }
    }
	
	public Set<String> getAllSortedSet(String key, String rev) {
		// 处理分页参数
		int index = 0;
		int endIndex = -1;
		Jedis conn = null;
		try {
			conn = this.getConnection();
			if ("rev".equals(rev)) {
				return conn.zrevrange(key, index, endIndex);
			} else {
				return conn.zrange(key, index, endIndex);
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (conn != null) {
				this.pool.returnResource(conn);
			}
		}
	}

	/** get Object */
	public Object getObject(String key) {

		Jedis conn = null;
		try {
			conn = this.getConnection();
			byte[] value = conn.get(key.getBytes());
			return SerializeUtil.unserialize(value);
		} catch (Exception e) {
			return null;
		} finally {
			if (conn != null) {
				this.pool.returnResource(conn);
			}
		}
	}

	public void setObject(byte[] key, byte[] value, int expire) {

		Jedis conn = null;
		try {
			conn = this.getConnection();
			conn.setex(key, expire, value);
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				this.pool.returnResource(conn);
			}
		}
	
	}

	public void setObject(String key, Object obj) {
		Jedis conn = null;
		try {
			conn = this.getConnection();
			if (obj instanceof String) {
				String objValue = (String) obj;
				this.set(key, objValue, DEFAULT_EXPIRE);
			} else {
				this.setObject(key.getBytes(), SerializeUtil.serialize(obj), DEFAULT_EXPIRE);
			}
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				this.pool.returnResource(conn);
			}
		}
	}

	public void setObject(String key, Object obj, int expire) {
		Jedis conn = null;
		try {
			conn = this.getConnection();
			if (obj instanceof String) {
				String objValue = (String) obj;
				this.set(key, objValue, expire);
			} else {
				this.setObject(key.getBytes(), SerializeUtil.serialize(obj), expire);
			}
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				this.pool.returnResource(conn);
			}
		}
	}
	
	public void rpush(String key, String value, int expire) {

		Jedis conn = null;
		try {
			conn = this.getConnection();
			conn.rpush(key, value);			
			if (expire != -1) {
				conn.expire(key, expire);
			}
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				this.pool.returnResource(conn);
			}
		}
	}

	public OperationFuture<Boolean> delete(String key) {
		this.del(key);
		return null;
	}
	
}
