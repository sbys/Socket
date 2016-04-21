package com.example.socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
/**
 * Created by 袁帅 on 2016/4/18.
 */
public class ClientThread implements Runnable{
    private Socket s;
    private Handler handler;
    public Handler revHandler;
    BufferedReader br=null;
    OutputStream os=null;
    public ClientThread(Handler hander){
        this.handler=hander;
    }
    public void run() {

        try {
            s = new Socket("121.250.222.56", 6001);
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=s.getOutputStream();
            new Thread(){
                public void run(){
                    String content=null;
                    try {
                        while((content=br.readLine())!=null)
                        {
                            Message mes=new Message();
                            mes.what=0x123;
                            mes.obj=content;
                            handler.sendMessage(mes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            Looper.prepare();
            revHandler=new Handler() {


                public void  handleMessage(Message mes){
                    if (mes.what==0x345){
                        try {
                            os.write((mes.obj.toString()+"\n").getBytes("utf-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            };
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
