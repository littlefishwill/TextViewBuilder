# TextViewBuilder
@Author : suzhiwei-Lfish 
> 这个项目可以快速的帮你构建Textview中的内容，如字体颜色，字体背景，样式，删除线，下滑线，文本超链接，文本跳转电话，文本跳转短信等功能。
>
![例图](https://github.com/littlefishwill/TextViewBuilder/blob/master/des.jpg)

### 用法示例：

       TextView textView = (TextView) findViewById(R.id.tv_test);

        TextViewBuilder textViewBuilder = new TextViewBuilder(MainActivity.this);
        textViewBuilder
                .add(new Text("默认字体样式").color(Color.BLACK).br())
                .add(new Text("百度链接，点击可以自动跳转百度").strikethroughLine().color(Color.RED).bgColor(Color.YELLOW).bold().size(60).italic().underLine().webLink("http://www.baidu.com").br())
                .add(new Text("自动跳转拨打电话界面").color(Color.BLUE).bold().tell("18301069161").sizeRe(2f).br())
                .add(new Text("点击右侧发短信").color(Color.BLACK))
                .add(new Text(MainActivity.this,R.mipmap.ic_launcher,Text.ResType.IMAGE).sms("18301069161","测试发个短信"));

        textViewBuilder.build(textView);
