# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep WebView classes
-keepclassmembers class * extends android.webkit.WebViewClient {
    public <methods>;
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public <methods>;
}

# Keep WebView JavaScript interface if used
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Google AdMob ProGuard rules
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.ads.** { *; }
-dontwarn com.google.android.gms.ads.**

# Keep AdMob classes
-keep class * extends com.google.android.gms.ads.mediation.MediationAdapter {
    public <init>(android.content.Context, ...);
}
-keep class * extends com.google.android.gms.ads.mediation.MediationServerParameters {
    public <fields>;
}

# Keep AndroidX classes
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-dontwarn androidx.**

# Keep ViewBinding
-keep class * extends androidx.viewbinding.ViewBinding {
    public static ** bind(android.view.View);
    public static ** inflate(android.view.LayoutInflater);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep MainActivity
-keep class com.logspace.dynamic_flui_x.MainActivity { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}