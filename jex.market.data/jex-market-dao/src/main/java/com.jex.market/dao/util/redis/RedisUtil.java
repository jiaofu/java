package com.jex.market.dao.util.redis;



import com.alibaba.fastjson.JSON;
import com.jex.market.dao.util.SpringContextUtil;


import com.jex.market.util.StringUtil;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by wanghui on 2018/6/8.
 */
public class RedisUtil {
    private static RedisUtil redisUtil = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static RedisUtil getRedisUtil() {
        if (null != redisUtil) {
            return redisUtil;
        }
        Object object = SpringContextUtil.getBean("redisUtil");
        if (null == object) {
            return null;
        }
        redisUtil = (RedisUtil) object;
        return redisUtil;
    }

    private RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> getKeyOfList(String key, Class<T> clazz) {
        String value = this.get(key);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return JSON.parseArray(value, clazz);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(String key, Object value) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForValue().set(key, valueStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean setObject(String key, Object value, long time) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForValue().set(key, valueStr, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过key获取对象
     *
     * @param key
     * @param clazz
     * @param
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        if (!StringUtil.isEmpty(key)) {
            String objJson = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtil.isEmpty(objJson)) {
                return JSON.parseObject(objJson, clazz);
            }
        }
        return null;
    }

    /**
     * 发送消息
     *
     * @param channel
     * @param message
     */
    public void publish(String channel, String message) {
        if (!StringUtil.isEmpty(channel)) {
            redisTemplate.convertAndSend(channel, message);
        }
    }

    /**
     * 订阅发布方法
     *
     * @param obj
     * @param channel 发布到的频道
     * @return
     */
    public static boolean publishObject(String channel, Object obj) {
        if (obj != null) {
            String objJson = JSON.toJSONString(obj);
            RedisUtil.getRedisUtil().publish(channel, objJson);
            return true;
        }
        return false;
    }

    public List<Object> executePipelined(RedisCallback<String> callback) {
        return redisTemplate.executePipelined(callback);
    }

    public List<Object> executePipelinedSession(SessionCallback<String> callback) {
        return redisTemplate.executePipelined(callback);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public String hget(String key, String item) {
        return (String) redisTemplate.opsForHash().get(key, item);
    }

    public <T> T hGetObject(String key, String item, Class<T> tClass) {
        Object object = redisTemplate.opsForHash().get(key, item);
        if (null == object) {
            return null;
        }
        String jsonStr = (String) object;
        return JSON.parseObject(jsonStr, tClass);
    }

    public <T> List<T> hmListObject(String key, Class<T> tClass) {
        List<Object> objectList = redisTemplate.opsForHash().values(key);
        if (CollectionUtils.isEmpty(objectList)) {
            return null;
        }
        List<T> list = objectList.stream().map(item -> {
            String jsonstr = (String) item;
            return JSON.parseObject(jsonstr, tClass);
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<? extends Object, ? extends Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, ? extends Object> map) {
        Map<String, String> param = map.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> JSON.toJSONString(entry.getValue())));
        try {
            redisTemplate.opsForHash().putAll(key, param);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, ? extends Object> map, long time) {
        Map<String, String> param = map.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> JSON.toJSONString(entry.getValue())));
        try {
            redisTemplate.opsForHash().putAll(key, param);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForHash().put(key, item, valueStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            String vlaueStr = JSON.toJSONString(value);
            redisTemplate.opsForHash().put(key, item, vlaueStr);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, String... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<String> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            String valueStr = JSON.toJSONString(value);
            return redisTemplate.opsForSet().isMember(key, valueStr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, String... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, String... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, String... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public <T> List<T> lGet(String key, long start, long end, Class<T> tClass) {
        try {
            List<String> strList = redisTemplate.opsForList().range(key, start, end);
            if (CollectionUtils.isEmpty(strList)) {
                return null;
            }
            return strList.stream().map(str -> {
                return JSON.parseObject(str, tClass);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public <T> T lGetIndex(String key, long index, Class<T> tClass) {
        try {
            String value = redisTemplate.opsForList().index(key, index);
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return JSON.parseObject(value, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForList().rightPush(key, valueStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForList().rightPush(key, valueStr);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean llSet(String key, List value) {
        try {
            if (CollectionUtils.isEmpty(value)) {
                return false;
            }
            List<String> list = (List<String>) value.stream().map(v -> JSON.toJSONString(v)).collect(Collectors.toList());
            redisTemplate.opsForList().rightPushAll(key, list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean llSet(String key, List<Object> value, long time) {
        try {
            if (CollectionUtils.isEmpty(value)) {
                return false;
            }
            List<String> list = value.stream().map(v -> JSON.toJSONString(v)).collect(Collectors.toList());
            redisTemplate.opsForList().rightPushAll(key, list);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForList().set(key, index, valueStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //===============================sorted set=================================

    /**
     * 获取有序set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long zsGetZSetSize(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回所有成员，按score逆序排列
     *
     * @param key 键
     * @return
     */
    public Set<String> zsGetRever(String key) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回所有成员，按score正序排列
     *
     * @param key 键
     * @return
     */
    public Set<String> zsGet(String key) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>向有序集合添加一个或多个成员</p>
     * <p>或者更新已存在成员的分数</p>
     *
     * @param key    键
     * @param values 插入的value数组
     * @param scores 与value相对应的score
     * @return
     */
    public long zsSet(String key, String[] values, double[] scores) {
        try {
            Set<TypedTuple<String>> zSetTupleSet = new HashSet<>();
            for (int i = 0; i < values.length; i++) {
                zSetTupleSet.add(new DefaultTypedTuple<>(values[i], scores[i]));
            }
            return redisTemplate.opsForZSet().add(key, zSetTupleSet);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public long zsRemove(String key, String... value) {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * <p>对有序集合中指定成员的分数加上增量 delta</p>
     * 当 key 不存在或value不是zset的成员，创建value，并默认为0
     *
     * @param key   键
     * @param value 值
     * @param delta 增量
     * @return
     */
    public double zsIncrBy(String key, String value, double delta) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Long zsGetValueRank(String key, String value) {
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean zsContainValue(String key, String value) {
        try {
            if (null == zsGetValueRank(key, value)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setnx(String key, String value) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
