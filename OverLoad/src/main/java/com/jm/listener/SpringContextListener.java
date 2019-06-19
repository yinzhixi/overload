package com.jm.listener;

import com.jm.service.PreviewService;
import com.jm.service.StaticWeightService;
import org.iot.client.common.ClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jm.service.SystemSetService;
//import com.jm.serviceImpl.out.FXCNodeService;
import com.jm.websocket.ClientService;
import com.jm.websocket.MessageQueueHandler;

@Component
public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private ClientService clientService;
    @Autowired
    private PreviewService previewService;
    @Autowired
    private SystemSetService systemSetService;
    @Autowired
    private StaticWeightService staticWeightService;
    
    // ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用
    /**
     * 当一个ApplicationContext被初始化或刷新触发
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("spring容易初始化完毕,启动连接到服务器");
        ClientFactory clientFactory = new ClientFactory();
        this.clientService.setClientFactory(clientFactory);
        MessageQueueHandler queueHandler = new MessageQueueHandler(clientFactory);
        queueHandler.setSystemSetService(systemSetService);
        queueHandler.setStaticWeightService(staticWeightService);
        queueHandler.setPreviewService(previewService);
        this.clientService.setMessageQueueHandler(queueHandler);
        this.clientService.start();
        //this.fxcNodeService.start();
        System.out.println("spring容易初始化完毕，已启动连接到服务器");
    }
}
