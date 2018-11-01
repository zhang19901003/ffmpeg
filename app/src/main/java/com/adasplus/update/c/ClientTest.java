package com.adasplus.update.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by zhangyapeng on 18-11-1.
 */

public class ClientTest {
    public static void main(String arg0[]){
        BufferedReader brc = null;
        PrintWriter pwc = null;
        try {
            Socket socket = new Socket("172.16.0.121",8000);
            brc = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pwc = new PrintWriter(socket.getOutputStream(),true);
            pwc.println("Hello");
            String sc = null;
            while (true) {
                sc = brc.readLine();
                if (sc!=null) {
                    break;
                }
            }
            System.out.println(sc);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
