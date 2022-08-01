## 项目说明
1. 项目框架搭建
- SpringBoot环境搭建
- 集成Thymeleaf,RespBean
- MyBatis
2. 分布式会话
- 用户登录
  - 设计数据库
  - 明文密码二次MD5加密
  - 参数校验+全局异常处理
- 共享Session
  - SpringSession
  - Redis

3. 功能开发
- 商品列表
- 商品详情
- 秒杀
- 订单详情
  
4. 系统压测
- JMeter
- 自定义变量模拟多用户
- JMeter命令行的使用
- 正式压测
  - 商品列表
  - 秒杀

5. 页面优化
- 页面缓存+URL缓存+对象缓存
- 页面静态化，前后端分离
- 静态资源优化
- CDN优化

6. 接口优化
- Redis预减库存减少数据库的访问
- 内存标记减少Redis的访问
- RabbitMQ异步下单
  - SpringBoot整合RabbitMQ
  
7. 安全优化
- 秒杀接口地址隐藏
- 算术验证码
- 接口限流，使用ThreadLocal保证线程安全

## 使用说明

服务器部署 登录页面：http://124.220.207.187:15327/login/toLogin

本地 登录页面：http://localhost:8080/login/toLogin

登录之后，每个用户只能秒杀一件商品，暂时没有实现支付功能

代码生成器：https://gitee.com/guizhizhe/code-generator.git


## 坑

1. 在本地测试正常，但是部署到服务器时，登录之后，页面中的user信息无法传递
经过排查，发现是部署在服务器上时，登陆时不生成cookie

- [解决方案1,thymleaf页面映射](https://www.bbsmax.com/A/6pdDNyoGJw/)
- [解决方案2,服务器和本地机器时间不一致，导致cookie设置时间错误](https://blog.csdn.net/weixin_34255938/article/details/114946717?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-114946717-blog-114148662.pc_relevant_sortByAnswer&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-114946717-blog-114148662.pc_relevant_sortByAnswer&utm_relevant_index=1)

最终的问题解决:

原来的代码是：
```java 
if (!"localhost".equals(domainName)) {
    cookie.setDomain(domainName);
    }else{
        cookie.setDomain("localhost");
}
```
这样会造成一个问题，服务器的完整ip地址是192.168.0.0，而这段代码只能获取到168.0.0

解决方案：
```java 
Pattern pattern = Pattern.compile("[0-9]+.*");
Matcher matcher = pattern.matcher((CharSequence) serverName);
boolean result = matcher.matches();
if(result)
domainName=serverName;
```
