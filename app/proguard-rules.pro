# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


# 不优化输入的类文件
-dontoptimize
# 代码混淆压缩比，在0~7之间，默认为5,一般不下需要修改
-optimizationpasses 5

# 混淆时不使用大小写混合，混淆后的类名为小写
# windows下的同学还是加入这个选项吧(windows大小写不敏感)
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库的类
# 默认跳过，有些情况下编写的代码与类库中的类在同一个包下，并且持有包中内容的引用，此时就需要加入此条声明
-dontskipnonpubliclibraryclasses

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

# 不做预检验，preverify是proguard的四个步骤之一
# Android不需要preverify，去掉这一步可以加快混淆速度
-dontpreverify

# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 也可以使用printmapping指定映射文件的名称，不指定使用默认
-verbose

# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保护代码中的Annotation不被混淆
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

# 避免混淆泛型
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes InnerClasses,LineNumberTable

#忽略警告
#-ignorewarning


# 保留了继承自Activity、Application这些类的子类
# 因为这些子类有可能被外部调用
# 比如第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.mula.mode.order.DomesticOrder


# 保留Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会影响
-keepclassmembers class * extends android.app.Activity {
   public void * (android.view.View);
}


# 枚举类不能被混淆
-keepclassmembers enum * {
   public static **[] values();
   public static ** valueOf(java.lang.String);
}


# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
   public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
   public <init>(android.content.Context, android.util.AttributeSet, int);
   public void set*(***); *** get* ();
}

# 保留Parcelable序列化的类不能被混淆
-keep class * implements android.os.Parcelable{
    public static final android.os.Parcelable$Creator *;
}


# 对R文件下的所有类及其方法，都不能被混淆
-keep class **.R$* {
    *;
}
# 对于带有回调函数onXXEvent的，不能混淆
-keepclassmembers class * {
    void *(**On*Event);
}

# 构造函数从xml构造的类不混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 对WebView的处理
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}

#######################################移除日志打印##############################################

# Remove debugging - All logging API calls. Remove all invocations of the
# logging API whose return values are not used.
-assumenosideeffects public class java.util.logging.* {
    <methods>;
}

# Remove debugging - All Log4j API calls. Remove all invocations of the
# Log4j API whose return values are not used.
-assumenosideeffects public class org.apache.log4j.** {
    <methods>;
}

#对第三方的一些处理
#retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#glid
 -keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
     public *;
}

#eventBus,目前de版本的，org版需要修改
-keepclassmembers class ** {
    @de.greenrobot.event.Subscribe <methods>;
}
-keep enum de.greenrobot.event.ThreadMode { *; }
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#gson
-keep class sun.misc.Unsafe { *; }
-keep public class com.google.gson.stream.** { *; }

#JPush
-keepattributes  EnclosingMethod,Signature
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-keepclassmembers class ** {
    public void onEvent*(**);
}
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

#微信支付
-keep class com.tencent.mm.opensdk.** { *; }
-keep class com.tencent.wxop.** { *; }
-keep class com.tencent.mm.sdk.** { *; }

#greenDao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.greenrobot.greendao.database.**
-dontwarn rx.**

#RxJava
-keep class rx.** { *; }

#高德地图
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
-keep   class com.amap.api.services.**{*;}

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

#router
-keep class com.lzh.nonview.router.** { *; }
-keep class com.mula.comm.user.RouterRuleCreator { *; }
-keep class com.mula.laren.user.RouterRuleCreator { *; }
-keep class com.mula.lahuo.user.RouterRuleCreator { *; }
-keep class com.domestic.laren.user.RouterRuleCreator { *; }

#sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

#本应用内不被混淆的类，（枚举，反射等）
-keep class * implements java.io.Serializable { *; }
-keep class com.mula.mode.bean.** { *; }
-keep class com.yanzhenjie.nohttp.** { *; }
-keep class com.mula.overseas.comm.bean.** { *; }
-keep class com.mula.comm.user.mode.bean.** { *; }
-keep class com.domestic.laren.user.mode.bean.** { *; }
-keep class com.mula.lahuo.user.mode.bean.** { *; }
-keep class com.mula.laren.user.mode.bean.** { *; }
-keep class com.mula.lbsloc.** { *; }
-keep class com.mula.retrofit.ApiResult { *; }
-keep class * extends com.mula.base.fragment.BaseFragment { *; }
-keep public class com.tdft.user.BuildConfig
-keep public class com.megvii.**{*;}
