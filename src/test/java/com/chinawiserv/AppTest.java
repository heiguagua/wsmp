package com.chinawiserv;

import com.chinawiserv.wsmp.websocket.WSClient;

public class AppTest {

    public static void main(String[] args) throws Exception {
        WSClient ws = new WSClient();
        ws.connectToServer("ws://172.16.7.75:8080/Wifi/test");
        for (int i = 0 ; i< 100; i ++) {
            ws.sendMessage("{dom:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA}");
        }
    }
}
