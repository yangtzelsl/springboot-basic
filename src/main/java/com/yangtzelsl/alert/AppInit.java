package com.yangtzelsl.alert;

import com.github.jaemon.dinger.DingerSender;
import com.github.jaemon.dinger.core.entity.DingerRequest;
import com.github.jaemon.dinger.core.entity.enums.MessageSubType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: AppInit
 * @Author luis.liu
 * @Date: 2021/7/15 10:30
 * @Version 1.0
 */
@Component
public class AppInit implements InitializingBean {
    @Autowired
    private DingerSender dingerSender;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 发送text类型消息
        dingerSender.send(
                MessageSubType.TEXT,
                DingerRequest.request("Hello World, Hello Dinger")
        );

        // 发送markdown类型消息
        dingerSender.send(
                MessageSubType.MARKDOWN,
                DingerRequest.request("Hello World, Hello Dinger", "启动通知")
        );
    }
}
