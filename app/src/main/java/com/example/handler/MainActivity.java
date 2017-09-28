package com.example.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int UPDATE_TEXT=1;
    private TextView textView;
    private Handler handler=new Handler(){
        //这边就可以进行处理ui组件了，因为这边的handler是主线程的
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    textView.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text);
        Button button=(Button)findViewById(R.id.change);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
