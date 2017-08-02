package com.szw.utils;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

import com.szw.textviewbuilder.Text;
import com.szw.textviewbuilder.TextViewBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_test);

        TextViewBuilder textViewBuilder = new TextViewBuilder(MainActivity.this);
        textViewBuilder
                .add(new Text("默认字体样式").color(Color.BLACK).br())
                .add(new Text("百度链接，点击可以自动跳转百度").strikethroughLine().color(Color.RED).bgColor(Color.YELLOW).bold().size(60).italic().underLine().webLink("http://www.baidu.com").br())
                .add(new Text("自动跳转拨打电话界面").color(Color.BLUE).bold().tell("18301069161").sizeRe(3f).br())
                .add(new Text(MainActivity.this,R.mipmap.ic_launcher,Text.ResType.IMAGE).sms("18301069161","测试发个短信"));

        textViewBuilder.build(textView);


    }
}
