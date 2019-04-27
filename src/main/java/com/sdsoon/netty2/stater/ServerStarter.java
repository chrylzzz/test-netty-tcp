package com.sdsoon.netty2.stater;


import com.sdsoon.netty2.config.TcpConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sdsoon.netty2.service.NettyServer.doPublisher;


/**
 * Created By Chr on 2019/4/25.
 */
public class ServerStarter {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(
                () -> {
                    try {
                        doPublisher(TcpConfig.HOST, TcpConfig.PORT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

    }
}
