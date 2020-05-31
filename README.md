# DT
Distributed Transaction Framework


使用教程


### 从业务服务

#### POM引入

```xml
        <dependency>
            <groupId>cn.blogxin</groupId>
            <artifactId>dt-client-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

#### 提供二阶段服务

```java
public interface AccountDubboService {

    @TwoPhaseCommit(name = "transferOutAccount", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(AccountDTO accountDTO);

    void commit(DTParam dtParam, AccountDTO accountDTO);

    void unfreeze(DTParam dtParam, AccountDTO accountDTO);

}
```

接口的一阶段方法上添加二阶段提交注解`@TwoPhaseCommit`，设置分支事务名称以及对应的`confirmMethod`和`cancelMethod`方法名称，`confirmMethod`和`cancelMethod`方法第一个参数设置为`DTParam`，包含了分布式事务ID以及事务开始时间等分布式事务上下文信息，后面的参数与一阶段方法参数相同，二阶段方法调用时会将一阶段的参数重新传进来。


### 主业务服务

#### POM引入

```xml
        <dependency>
            <groupId>cn.blogxin</groupId>
            <artifactId>dt</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

#### 添加mybatis配置

```java
@MapperScan({"cn.blogxin.pay.mapper"/*服务本身的mapper路径*/, "cn.blogxin.dt.log.repository.mybatis.mapper"})
```

扫描dt组件的mapper

```
mybatis.mapper-locations=classpath*:dt-mybatis/mapper/*.xml
```

添加dt组件的mapper.xml配置

#### 添加配置

```java
dt.enable=true	//启动dt组件
dt.name=pay_test	//dt服务名称
dt.job.serverList=127.0.0.1:2181	//jobzk地址
dt.job.namespace=pay_test_job	//job的zk路径namespace
```

#### 使用

```java
    @Resource
    private TransactionManager dtTransactionManager;

    @Transactional(rollbackFor = Exception.class)
    public boolean execute(Xxx xxx) {
        dtTransactionManager.start();
      	//执行本地事务
      	//执行分支事务
        return true;
    }
```

引入`TransactionManager`，在`Transactional`本地事务中，需要执行`dtTransactionManager.start();`开启分布式事务。


### DEMO

使用DT分布式事务组件的demo： https://github.com/kris-liu/DT/tree/master/dt-demo

1. 执行初始化SQL并初始化数据：https://github.com/kris-liu/DT/blob/master/dt-demo/sql/init.sql
2. 启动`DemoAccountApplication`，`DemoCouponApplication`两个分支事务提供方。
3. 启动`DemoPayApplication`分布式事务发起方，实现了一个同时使用余额和券两种渠道组合支付的接口demo。
4. 请求测试接口http://127.0.0.1:8082/pay
5. 参数：{"uid":"000001","orderId":"ORDER000002","amount":"200","channels":[{"channelId":"10","amount":"100","assetsId":""},{"channelId":"11","amount":"100","assetId":"COUPON000001"}]}
6. 可以在分布式事务执行过程中的各个环节模拟异常，观察分布式事务会通过补偿达到最终一致。






