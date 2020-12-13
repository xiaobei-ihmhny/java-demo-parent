package com.xiaobei.mybatis.demo.plugins;

import com.xiaobei.mybatis.demo.domain.User;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/12 21:24
 */
@Intercepts({
      @Signature(
          type = Executor.class,
          method = "update",
          args = {MappedStatement.class, Object.class}
      )
})
public class ExamplePlugin implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // implement pre-processing if needed
        // 参数中若包含 User 对象，则将其出生地改为洛阳
        Arrays.stream(invocation.getArgs())
                .filter(arg -> User.class.equals(arg.getClass()))
                .findFirst()
                .ifPresent(arg -> {
                    User user = (User) arg;
                    user.setBirthPlace("洛阳666");
                });
        Object returnObject = invocation.proceed();
        // implement post-processing if needed
        return returnObject;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
