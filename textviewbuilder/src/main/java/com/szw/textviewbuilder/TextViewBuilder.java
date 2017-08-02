package com.szw.textviewbuilder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义文字 构造器
 * @author lfish
 */
public class TextViewBuilder {
    private List<Text> texts = new LinkedList<Text>();
    private Context context;

    public TextViewBuilder(Context context) {
        this.context = context;
    }

    public TextViewBuilder add (Text text){
        texts.add(text);
        return  this;
    }

    public SpannableStringBuilder build(TextView textView){
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannableStringBuilder;
        StringBuilder stringBuilder = new StringBuilder();
        int startTextPos = 0;
        int endTextPos = 0;
        for(Text text:texts){
            stringBuilder.append(text.getText());
        }
        spannableStringBuilder = new SpannableStringBuilder(stringBuilder.toString());

        for(final Text text:texts){
            endTextPos = startTextPos +text.getText().length();

            //文字超链接
            if(text.getLinkUrl()!=null && text.getLinkUrl().length()>0){
                switch (text.getLinkType()){
                    case WebUrl:
                        spannableStringBuilder.setSpan(new URLSpan(text.getLinkUrl()), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        break;
                    case Tel:
                        spannableStringBuilder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Intent intent=new Intent();
                                intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
                                intent.setData(Uri.parse("tel:"+text.getLinkUrl()));
                                context.startActivity(intent);
                            }
                        }, startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        break;
                    case Sms:
                        spannableStringBuilder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+text.getLinkUrl()));
                                intent.putExtra("sms_body", text.getSmsBody());
                                context.startActivity(intent);
                            }
                        }, startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        break;
                    case Mms:
                        spannableStringBuilder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+text.getLinkUrl()));
                                intent.putExtra("sms_body", "");
                                context.startActivity(intent);
                            }
                        }, startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        break;
                    case Mail:
                        spannableStringBuilder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Intent intent=new Intent();
                                intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
                                intent.setData(Uri.parse("mailto:"+text.getLinkUrl()));
                                context.startActivity(intent);
                            }
                        }, startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        break;
                }
            }


            //字体变斜
            if(text.isItalic()) {
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.ITALIC), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            //字体加粗
            if(text.isBold()) {
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            //字体下划线
            if(text.isUnderline()) {
                if(text.getUnderLineColor()==0) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }else{
                    spannableStringBuilder.setSpan(new ColorUnderlineSpan(text.getUnderLineColor()), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }else{
                spannableStringBuilder.setSpan(new NoUnderlineSpan(), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            //字体颜色
            spannableStringBuilder.setSpan(new ForegroundColorSpan(text.getColor()),startTextPos,endTextPos,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            //字体背景颜色
            spannableStringBuilder.setSpan(new BackgroundColorSpan(text.getBgColor()),startTextPos,endTextPos,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            //字体大小px
            if(text.getSizePx()>0) {
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(text.getSizePx()), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            //字体大小绝对
            if(text.getSzieRe()>0){
                spannableStringBuilder.setSpan(new RelativeSizeSpan(text.getSzieRe()), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            //删除线
            if(text.isStrikethrough()){
                spannableStringBuilder.setSpan(new StrikethroughSpan(), startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }




            //文字中增加图标
            if(text.getImageDrawabe()!=null){
                ImageSpan span = new ImageSpan(text.getImageDrawabe(), ImageSpan.ALIGN_BASELINE);
                spannableStringBuilder.setSpan(span, startTextPos, endTextPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }






            startTextPos = startTextPos + text.getText().length();
        }
        textView.setText(spannableStringBuilder);
        return spannableStringBuilder;
    }

    public class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            //设置可点击文本的字体颜色
            ds.setUnderlineText(false);
        }
    }

    public class ColorUnderlineSpan extends UnderlineSpan {
        private int colorRes;
        public ColorUnderlineSpan(int colorRes) {
            this.colorRes = colorRes;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(colorRes);
            //设置可点击文本的字体颜色
            ds.setUnderlineText(true);
        }
    }

}
