package com.szw.textviewbuilder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * 座位textview builder 的一个单独的单元
 * @author lfish
 */
public class Text {
    private int id;
    private int underLineColor = 0;
    private String text,smsBody;
    private Drawable imageDrawabe;
    private int color,bgColor,sizePx;
    private float szieRe;
    private String linkUrl;
    private LinkType linkType;
    private boolean bold,italic,underline,strikethrough = false;
    private TextClickListener textClickListener;

    /**
     * 构造 方法
     * @param text 要包装的文字
     */
    public Text(String text) {
        this.text = text;
    }

    /**
     * 构造 方法
     * @param text 要包装的文字
     * @param id 设置用于区别包装文字的id（用于点击，等操作时区分）
     */
    public Text(String text,int id) {
            this.text  =text;
            this.id = id;
    }

    /**
     * 构造 方法
     * @param res 要包装的文字资源id,或者图片的资源id
     */
    public Text(Context context, int res,ResType resType) {
        Resources resources = context.getResources();
        switch (resType){
            case STRING:
                this.text = resources.getString(res);
                break;
            case IMAGE:
                this.text = "th";
                Drawable d = resources.getDrawable(res);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                this.imageDrawabe = d;
                break;
        }

    }

    /**
     * 设置单元文字的颜色
     * @param resColor res文件中引用的color
     * @return 返回该字体单元，用于链式编程
     */
    public Text color(int resColor){
        color = resColor;
        return this;
    }

    /**
     * 设置单元文字的背景颜色
     * @param resColor res文件中引用的color
     * @return 返回该字体单元，用于链式编程
     */
    public Text bgColor(int resColor){
        this.bgColor = resColor;
        return this;
    }

    /**
     * 设置单元文字的大小
     * @param sizePx 文字的绝对大小
     * @return 返回该字体单元，用于链式编程
     */
    public Text size(int sizePx){
        this.sizePx = sizePx;
        return this;
    }

    /**
     * 设置单元文字的大小 (几何倍数增长 例如2=2倍)
     * @param szieRe 文字的大小倍数 如0.5 =二分之一原字体大小，2=原字体的二倍
     * @return 返回该字体单元，用于链式编程
     */
    public Text sizeRe(float szieRe){
        this.szieRe = szieRe;
        return this;
    }

    /**
     * 设置单元文字的颜色
     * @param rgbColor RGB 颜色（例如：#FFFFF）
     * @return 返回该字体单元，用于链式编程
     */
    public Text color(String rgbColor){
        color = Color.parseColor(rgbColor);
        return this;
    }

    /**
     * 设置文字 链接 动向
     * @param linkUrl 要跳转的url地址
     * @return 返回该字体单元，用于链式编程
     */
    private Text link(String linkUrl,LinkType linkType){
        this.linkUrl = linkUrl;
        this.linkType = linkType;
        return this;
    }

    /**
     * 设置文字 点击用浏览器跳转链接
     * @param url 要跳转的url地址
     * @return 返回该字体单元，用于链式编程
     */
    public Text webLink(String url){
        link(url,LinkType.WebUrl);
        return this;
    }

    /**
     * 设置文字 点击可拨打电话
     * @param phoneNumber 要拨打的电话号码
     * @return 返回该字体单元，用于链式编程
     */
    public Text tell(String phoneNumber){
        link(phoneNumber,LinkType.Tel);
        return this;
    }

    /**
     * 设置文字 点击发送短信
     * @param phoneNumber 要拨打的电话号码
     * @return 返回该字体单元，用于链式编程
     */
    public Text sms(String phoneNumber){
        link(phoneNumber,LinkType.Sms);
        return this;
    }

    /**
     * 设置文字 点击发送短信
     * @param phoneNumber 要拨打的电话号码
     * @param smsBody 要发送的短信内容
     * @return 返回该字体单元，用于链式编程
     */
    public Text sms(String phoneNumber,String smsBody){
        this.smsBody = smsBody;
        link(phoneNumber,LinkType.Sms);
        return this;
    }



    /**
     * 加粗
     * @return 返回该字体单元，用于链式编程
     */
    public Text bold(){
        this.bold = true;
        return this;
    }

    /**
     * 斜体
     * @return 返回该字体单元，用于链式编程
     */
    public Text italic(){
        this.italic = true;
        return this;
    }

    /**
     * 下划线
     * @return 返回该字体单元，用于链式编程
     */
    public Text underLine(){
        this.underline = true;
        return this;
    }

    /**
     * 删除线
     * @return 返回该字体单元，用于链式编程
     */
    public Text strikethroughLine(){
        this.strikethrough = true;
        return this;
    }

    /**
     * 换行
     * @return 返回该字体单元，用于链式编程
     */
    public Text br(){
        this.text = getText()+"\n";
        return this;
    }

//    /**
//     * 下划线
//     * @param colorRes 下划线的颜色
//     * @return 返回该字体单元，用于链式编程
//     */
//    public Text underLine(int colorRes){
//        this.underline = true;
//        this.underLineColor = colorRes;
//        return this;
//    }

    /**
     * 字体
     * @param resFont 资源文件中的字体 （内部会自动设置文字字体缓存，避免多次加载造成内存溢出）
     * @return 返回该字体单元，用于链式编程
     */
    public Text font(int resFont){
        return this;
    }



    /**
     * 设置文字单元的点击事件（注：如果你设置weblink自动跳转为true，那么该方法会覆盖webLink的跳转行为）
     * @param textClickListener
     * @return
     */
    public Text click(TextClickListener textClickListener){
        this.textClickListener = textClickListener;
        return this;
    }


    //---------------------------------interface
    public interface TextClickListener{
        void onClick(Text text);
    }

    /**
     * 标记资源类型 ，包含string 类型，和image 类型
     */
    public enum ResType{
        STRING,IMAGE;
    }

    /**
     * 如果该文本标记为链接文本 ，那么它需要有类型，系统会根据此判断它将要如何自动跳转
     */
    public enum LinkType{
        /**
         * 网络超链接，自动跳转浏览器
         */
        WebUrl,
        /**
         * 自动拨打电话
         */
        Tel,
        /**
         * 跳转至短信界面
         */
        Sms,
        /**
         * 跳转至彩信发送界面
         */
        Mms,
        /**
         * 跳转发送邮件界面
         */
        Mail;
    }


    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public TextClickListener getTextClickListener() {
        return textClickListener;
    }

    public Drawable getImageDrawabe() {
        return imageDrawabe;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public int getUnderLineColor() {
        return underLineColor;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public int getBgColor() {
        return bgColor;
    }

    public int getSizePx() {
        return sizePx;
    }

    public float getSzieRe() {
        return szieRe;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }
}
