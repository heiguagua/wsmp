package com.chinawiserv;

import com.chinawiserv.wsmp.websocket.WSClient;

public class AppTest {

    public static void main(String[] args) throws Exception {
        WSClient ws = new WSClient("ws://172.16.7.75:8080/test");
        ws.connectToServer();
        for (int i = 0 ; i< 10000000; i ++) {
            ws.sendMessage("{\"dom\":\""+i+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+System.currentTimeMillis()+"\"}");
        }
    }
}
