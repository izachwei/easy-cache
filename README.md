<p align="center">
	<strong>一个基于 SpringBoot 的缓存组件（starter）</strong>
</p>

<p align="center">
    <a>
        <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" >
    </a>
    <a>
        <img src="https://img.shields.io/badge/springBoot-2.6.7.svg" >
    </a>
    <a href="https://www.jetbrains.com">
        <img src="https://img.shields.io/badge/IntelliJ%20IDEA-support-blue.svg" >
    </a>
    <a>
        <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" >
    </a>
</p>

# easy-cache
## 介绍
* 支持 Springboot，基于注解的、可扩展的、即开即用的通用缓存组件。
* 支持本地内存缓存、分布式缓存、多级缓存架构。
* 基于切面与业务代码解耦，添加缓存、删除缓存简单优雅。

## 支持功能

* 注解方式写入与刷新缓存
* 支持本地内存缓存（目前实现参考`MemoryCacheManager`,支持自定义）
* 支持自定义缓存实现，实现接口：`CacheManage`
* 支持远程缓存

## 后续功能

* 增加多级缓存架构实现
* 实现多种缓存移除策略
* 支持 SpEl 表达式
  ...

## 使用说明

* 引入依赖

```pom

```

* SpringBoot 应用启动类上添加注解 `@EnableCache`，开启缓存功能

```java
import com.easycache.cacheapi.starter.enable.EnableCache;

@SpringBootApplication
@EnableCache
public class StudyApplication {
    ...
}
}
```

* 添加缓存
* 需要缓存的方法：添加 `@Cached` 注解，可选参数有 key 、expire 、type
> * key: 缓存键值，默认取方法名+返回参数类型+方法参数类型 MD5 以后的值作为 Key
> * expire：缓存的过期时间，删除策略（默认本地缓存实现 `MemoryCacheManager`）：
> > * 主动删除，后台启动一个周期线程实时扫描当前缓存中的值，过期则删除
> > * 被动删除，获取值时，缓存值过期则直接删除
> * type：使用的缓存类型，MEMORY、DISTRIBUTED、MULTISTEP，注：默认实现了MEMORY，后续会实现其余类型，也可自定义

```java

import com.easycache.cacheapi.core.annotation.CacheSourceType;
import com.easycache.cacheapi.core.annotation.Cached;

public class CachedDemo {
  @Cached(expire = 5, timeUnit = TimeUnit.MINUTES, type = CacheSourceType.MEMORY)
  public User queryUser(String userId) {
    return new User();
  }
}
```
