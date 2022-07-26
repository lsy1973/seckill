
## 遇到的问题


1. 在本地测试正常，但是部署到服务器时，登录之后，页面中的user信息无法传递
经过排查，发现是部署在服务器上时，登陆时不生成cookie

- [解决方案1](https://www.bbsmax.com/A/6pdDNyoGJw/)
- [解决方案2](https://blog.csdn.net/weixin_34255938/article/details/114946717?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-114946717-blog-114148662.pc_relevant_sortByAnswer&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-114946717-blog-114148662.pc_relevant_sortByAnswer&utm_relevant_index=1)

问题解决：                
原来的代码是：
```java 
if (!"localhost".equals(domainName)) {
    cookie.setDomain(domainName);
    }else{
        cookie.setDomain("localhost");
}
```
这样会造成一个问题，服务器的完整ip地址是124.220.207.187，而这段代码只能获取到124.220.207.187
临时解决方案：
```java
if (!"localhost".equals(domainName)) {
cookie.setDomain("124.220.207.187");
}else{
cookie.setDomain("localhost");
}
```

