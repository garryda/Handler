package com.example.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    static final String UPPER_NUM="upper";
    EditText etNum;
    CalThread calThread;

    class CalThread implements Runnable{
        public Handler mHandler;

        public void run(){
            Looper.prepare();
            mHandler=new Handler(){
                //定义处理消息的方法
                public void handleMessage(Message msg)
                {
                    if(msg.what==0x123) {
                        //获取存放在message的数据
                        int upper = msg.getData().getInt(UPPER_NUM);

                        List<Integer> nums = new ArrayList<Integer>();
                        //计算从2 开始，到upper的所有质数
                        outer:

                        for (int i = 2; i <= upper; i++) {
                            // 用i处于从2开始、到i的平方根的所有数
                            for (int j = 2; j <= Math.sqrt(i); j++) {
                                // 如果可以整除，表明这个数不是质数
                                if (i != 2 && i % j == 0) {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        // 使用Toast显示统计出来的所有质数
                        Toast.makeText(Main2Activity.this , nums.toString()
                                , Toast.LENGTH_LONG).show();

                    }

                }
            };
            Looper.loop();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etNum=(EditText)findViewById(R.id.etNum);
        calThread=new CalThread();
        Thread thread=new Thread(calThread);
        thread.start();
    }

    //为按钮的点击事件提供时间处理函数，这是一个特殊的监听方法，在布局中设立按钮onclick属性进行设置监听
    public void cal(View source)
    {
        //创建消息
        Message message=new Message();
        message.what=0x123;
        Bundle bundle=new Bundle();
        bundle.putInt(UPPER_NUM,
                Integer.parseInt(etNum.getText().toString()));
        message.setData(bundle);
        calThread.mHandler.sendMessage(message);
    }
}
