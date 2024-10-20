```java
<dependency>
    <groupId>io.github.fenbaitiao</groupId>
    <artifactId>ftbapi-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```





在[http://www.fenbaitiao.asia](http://www.fenbaitiao.asia/admin)注册用户

在关于我的中获取公钥和密钥

![](https://cdn.nlark.com/yuque/0/2024/png/39176486/1729434064938-23b1c2f2-730f-44fa-bab8-ba9c2084a48d.png)





在yml文件中添加自己的公钥和密钥

![](https://cdn.nlark.com/yuque/0/2024/png/39176486/1729434103147-30038f19-f94f-4f3e-b180-745c7ef4c10d.png)





```java
@SpringBootTest
public class test1 {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private FbtApiClient fbtApiClient;

    @Test
    public void testt(){
        String anser = fbtApiClient.getAnser("你是谁sss");
        System.out.println(anser);
    }
}
```

```json
{"code":0,"data":"您好，我是科大讯飞研发的认知智能大模型，我的名字叫讯飞星火认知大模型。我可以和人类进行自然交流，解答问题，高效完成各领域认知智能需求。","message":"ok","description":""}
```

