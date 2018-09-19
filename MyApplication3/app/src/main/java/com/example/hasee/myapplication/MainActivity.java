package com.example.hasee.myapplication;//使用前注意manifest添加的权限uses-permission 以及app/build.grale添加的lottie依赖；
import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public TextView txt0;//文本
    public LottieAnimationView line;//动画控件
    public WindowManager.LayoutParams pr0;//文本框布局
    public WindowManager.LayoutParams pr3;
    public WindowManager wm;
    public Button button;
    public int width,height;
    public boolean flag;//动画加载完成标记
    public boolean click_on;
    public boolean click_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化

        flag=false;
        click_on=true;
        click_off=false;
        txt0 = new TextView(this);

        line = new LottieAnimationView(this);
        line.setAnimation("penguin.json");
        line.loop(true);

        wm = getWindowManager();

        //文本位置信息
        pr0 = new WindowManager.LayoutParams(500,500,0,0, PixelFormat.TRANSPARENT);
        pr0.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        pr0.type=99;
        pr0.x=300;
        pr0.y=300;


        //动画位置信息
        pr3 = new WindowManager.LayoutParams(500,500,0,0, PixelFormat.TRANSPARENT);
        pr3.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        pr3.type=99;
        pr3.x=300;
        pr3.y=300;
        //设置关闭按钮
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_off) {
                    wm.removeView(line);
                    flag = false;
                    click_on = true;
                    click_off=false;
                }
            }
        });
        //设置启动按钮
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click_on) {
                    click_on=false;
                    click_off=true;
                    wm.addView(line, pr3);
                    line.playAnimation();
                    handler0.sendEmptyMessage(0);

                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            flag = true;
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 5000);
                }
            }
        });

        //获取屏幕大小
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        height=dm.heightPixels;

        //设置触摸拖拽
        line.setOnTouchListener(new View.OnTouchListener() {
            float dx,dy;
            float movex, movey;
            boolean moveon;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case  MotionEvent.ACTION_DOWN:
                        flag=false;
                        dx=event.getRawX();
                        dy=event.getRawY();
                        moveon=false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x= Math.abs((int)(event.getRawX()-dx));
                        int y= Math.abs((int)(event.getRawY()-dy));
                        if(x>5||y>5){
                            moveon = true;
                            pr3.x = (int)event.getRawX()-width/2;
                            pr3.y = (int)event.getRawY()-height/2;
                            wm.updateViewLayout(line, pr3);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        flag=true;
                        return moveon;
                    default:break;
                }
                return false;
            }
        });

        //子线程
        new Thread(){
            @Override
            public  void run(){
                while(true){
                    int random=(int) (Math.random()*14+1);//随机数选择文本，增添文本数量需增大14
                    try{
                        Thread.sleep(12000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(flag){
                        pr0.x=pr3.x;
                        pr0.y=pr3.y;
                        handler0.sendEmptyMessage(random);}
                }
            }
        }.start();
    }

    //runnable
    final Runnable run_fg = new Runnable() {
        @Override
        public void run() {
        wm.removeView(txt0);
        }
    };
    //handler 监控子线程
    @SuppressLint("HandlerLeak")
    final Handler handler0 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //设置文本
            switch (msg.what) {
                case 0:
                    txt0.setText(R.string.fg_hello);
                    break;
                case 1:
                    txt0.setText(R.string.fg_weather);
                    break;
                case 2:
                    txt0.setText(R.string.fg_chat);
                    break;
                case 3:
                    txt0.setText(R.string.fg_strange);
                    break;
                case 4:
                    txt0.setText(R.string.fg_split);
                    break;
                case 5:
                    txt0.setText(R.string.fg_joke);
                    break;
                case 6:
                    txt0.setText(R.string.fg_cloth);
                    break;
                case 7:
                    txt0.setText(R.string.fg_think);
                    break;
                case 8:
                    txt0.setText(R.string.fg_music);
                    break;
                case 9:
                    txt0.setText(R.string.fg_article);
                    break;
                case 10:
                    txt0.setText(R.string.fg_number);
                    break;
                case 11:
                    txt0.setText(R.string.fg_data);
                    break;
                case 12:
                    txt0.setText(R.string.fg_work);
                    break;
                case 13:
                    txt0.setText(R.string.fg_peek);
                    break;
                case 14:
                    txt0.setText(R.string.fg_erro);
                    break;
                case 15:
                    txt0.setText(R.string.fg_repeater);
                    break;
                default:
                    break;
            }
            wm.addView(txt0, pr0);
            //3S删除文本
            this.postDelayed(run_fg, 3000);
        }
    };

}
