## Navigation 介绍与使用


### 前言

在2018年的Google IO上谷歌推出了jetpack 架构组件, 他是一个旨在减少重复的样板代码并加速开发的组件库，Navigation作为jetpack的一部分,笔者认为更好的解决了谷歌之前推荐的Single Activity multi fragments（一个Activity和多个Fragments） 的解决方案。另外整个视图通过类似IOS里的StoryBoard方式以xml配置导航面板的方式展现出来，在页面管理上也简易的许多。

![navigation1](http://leonblog.lelionnews.com/163725145e8eb370.jpg)

### 为什么要使用Navigation
我们为什么要使用Navigation来管理Fragment与Activity 以及Fragment与Fragment之间的交互逻辑，通过已有fragmentTransaction 不是一样也可以吗？

1. fragemnt 难以管理的页面重叠和回退栈
2. 提供了fragment页面转场的动画
3. 通过DeepLink可以直接绕过Activity直接跳转到目标Fragment
4. 页面之间传递参数多样化也更加安全(argument参数可以通过代码传递，也可以通过xml里argument标签来配置)
5. 强大而直观的导航面板（导航编辑器）

### 开始使用

#### 1、名词解释

- NavHost: 
- NavController: 管理Navigation页面跳转的Destination
- NavigationUI: 
- Navigation Graph: Navigation的导航面板，类似IOS里的StoryBoard
- Destination: 字面意思为目的地，我们可以理解为页面跳转的路由（Activity,Fragment），一下简称路由
- Actions: 定义每个Destination之间跳转和返回的行为

#### 2、添加依赖

```
# app/build.gradle

implementation 'android.arch.navigation:navigation-fragment:1.0.0-alpha11'
implementation 'android.arch.navigation:navigation-ui:1.0.0-alpha11'

```

#### 3、创建Navigation Graph导航面板
首先我们在res目录下创建navigation 文件夹，在创建好的navigation下创建名为test_nav_graph.xml文件   

![test_nav_graph](http://leonblog.lelionnews.com/navigation_one.png)

初始状态下的test_nav_graph.xml页面布局


```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/test_nav_graph">

    //TODO
    ...
</navigation>
```

### 4、配置activity layout
由于我们的页面呈现方式全部用fragment来展现出来，而fragment则需要activity来作为容器，所以我们需要在activity layout 布局中加载Navigation Graph。

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

    <fragment
            android:id="@+id/nav_host_fragment"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            android:layout_height="0dp"
            android:layout_width="0dp"
            app:defaultNavHost="true"
            app:navGraph="@navigation/test_nav_graph"
            android:name="androidx.navigation.fragment.NavHostFragment"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

我们看到布局里面是一个NavHostFragment, 他通过```navGraph```属性来加载指定的导航面板，另外他通过Navigation Controller，管理所有页面的跳转。

```
//加载指定的导航面板
app:navGraph="@navigation/test_nav_graph"
//确保NavHostFragment拦截系统的返回按钮，使其不会直接退出Activity，而是退回到上一个fragment，也就是说给了fragment做了回退栈处理
app:defaultNavHost="true"
```

### 5、Navigation Graph 添加Fragment

```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:id="@+id/test_nav_graph"
            app:startDestination="@+id/test1">

    <fragment
            android:id="@+id/test1"
            android:name="com.navigationdemo.app.Test1Fragment"
            android:label="fragment_test_1"
            tools:layout="@layout/fragment_test_1">

        <action android:id="@+id/action_test2"
                app:destination="@id/test2"
                app:enterAnim="@anim/slide_in_right"
                app:exitAnim="@anim/slide_out_left"
                app:popEnterAnim="@anim/slide_in_left"
                app:popExitAnim="@anim/slide_out_right"/>

        <action android:id="@+id/action_test3"
                app:destination="@id/test3"/>
        
    </fragment>

    <fragment
            android:id="@+id/test2"
            android:name="com.navigationdemo.app.Test2Fragment"
            android:label="fragment_test_2"
            tools:layout="@layout/fragment_test_2"/>

    <fragment
            android:id="@+id/test3"
            android:name="com.navigationdemo.app.Test3Fragment"
            android:label="fragment_test_3"
            tools:layout="@layout/fragment_test_3"/>

</navigation>
```

- Navigation导航编辑器

![image](http://leonblog.lelionnews.com/navigation_two.png)

1、destination 路由列表，它会列出当前所有的路由  
2、视图预览编辑   
3、destination 路由相关属性

- destination 页面跳转路由

![image](http://leonblog.lelionnews.com/navigation_four.gif)


```
<fragment 
            android:id="@+id/test3Fragment3" 
            android:name="com.navigationdemo.app.Test3Fragment"
            android:label="Test3Fragment"
            tools:layout="@layout/fragment_test_3"/>
```

自动创建的话我们只需要点击导航预览视图左上角的New Destination 就可以加载自己想要指定的路由，由于自动生成无法指定预览，因此需要我们手动添加预览```tools:layout="@layout/fragment_test_3"```
navigation 里面有个startDestination 属性他是用来指定默认加载的视图, 我们只需要选中对应的目标路由，在视图预览里面点击右键，然后选择Set Start Destination 就可以了。

- destination 路由相关属性

1. Type：以指定目标路由为Fragment或Activity等其他加载方式（不是说仅仅只能加载Fragment）
2. Label：路由的XML布局文件的名称
3. ID：用于在代码中引用目标路由的资源ID
4. Class：指定目标类的名称


- Actions: 连接每个Destination之间跳转和返回的行为

![image](http://leonblog.lelionnews.com/navigation_five.gif)


```
<fragment
            android:id="@+id/test1"
            android:name="com.navigationdemo.app.Test1Fragment"
            android:label="fragment_test_1"
            tools:layout="@layout/fragment_test_1">

        <action android:id="@+id/action_test2"
                app:destination="@id/test2"
                app:enterAnim="@anim/slide_in_right"
                app:exitAnim="@anim/slide_out_left"
                app:popEnterAnim="@anim/slide_in_left"
                app:popExitAnim="@anim/slide_out_right"/>

        <action android:id="@+id/action_test1_to_test3"
                app:destination="@id/test3"/>

</fragment>
```
自动创建时我们要选择目标路由然后拖拽，这时候会有有个箭头目标指向，我们指向目标路由就可以了。

相关属性:
1. destination 设置跳转目标路由的资源id
2. enterAnim、exitAnim、popEnterAnim、popExitAnim 添加自定义跳转动画


### 6、实现路由之间的跳转

在Navigation中，我们使用NavController工具来实现跳转到某个指定的路由。我们可以通过以下三种方法来获取NavController：
1. NavHostFragment.findNavController(Fragment)
2. Navigation.findNavController(Activity, @IdRes int viewId)
3. Navigation.findNavController(View)

<img width="30%" src="http://leonblog.lelionnews.com/navigation_six.gif" />


```
val btnStartTest2 = view.findViewById<AppCompatButton>(R.id.btn)
btnStartTest2.setOnClickListener {
            Navigation.findNavController(btnStartTest2).navigate(R.id.action_test2)
//            NavHostFragment.findNavController(this).navigate(R.id.action_test2)
}
```
我们使用NavController的navigate方法来导航到指定的destination，此方法接收一个资源ID，当然这个ID可以是 navigation 导航面板中的:

- action的ID
- destination的ID

这里我们推荐使用Action的ID是因为我们可以绑定一些我们需要的转场动画以及指定回退的任务栈

### 7、参数传递  

destination路由传递参数官方为我们提供了两种方式：

- Bundle方式
- Safe Args 方式

#### Bundle方式

1. 传统方式代码中添加bundle 参数


```
val btnStartTest2 = view.findViewById<AppCompatButton>(R.id.btn)
btnStartTest2.setOnClickListener {
    val bundle = bundleOf("paramsA" to "大王叫我来巡山")
    //Navigation跳转时传入bundle参数
    Navigation.findNavController(btnStartTest2).navigate(R.id.action_test2_to_test3, bundle)
}
```
代码中其实很简单，就是使用nativgate()方法发送数据。

2. 接收数据


```
 val tvArgument = view.findViewById<AppCompatTextView>(R.id.tv_argument)
 //接收数据
 arguments?.also {
    val paramsA = it.getString("paramsA")
    tvArgument.text = paramsA
 }
```

#### Safe Args 方式

nativgate为我们提供了一个Safe Args 插件的方式传递参数，当然我们首先需要添加相关的gradle的依赖。

```

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        ...
        //add safe-args-gradle-plugin
        classpath "android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha11"
    }
}

```

然后我们需要在app的 ```build.gardle```里面定义好safeargs的插件：

```
apply plugin: 'androidx.navigation.safeargs.kotlin'
```

1. navigation 导航面板中中给destination添加arguments

![image](http://leonblog.lelionnews.com/navigation_seven.gif)

```
 <fragment
            android:id="@+id/test2"
            android:name="com.navigationdemo.app.Test2Fragment"
            android:label="fragment_test_2"
            tools:layout="@layout/fragment_test_2">

        <argument android:name="paramsA"
                  app:argType="string"
                  app:nullable="true"
                  android:defaultValue="Hello World !!!"/>

    </fragment>
```


在Navigation 面板的 Attributes 一栏点击arguments 就弹出了添加bundle参数选项，当然你也可以在xml中手动添加。  
> 这种方式的好处在于我们重新```rebuild```项目后, 该插件会帮我们自动帮我们在对应Destination中生成一个名为 [Destination Fragment Name]Args 的参数类，另外他会在Destination中action跳转属性中查找目标的Destination是否有argument属性传递参数，如果有的话就会自动生成[Destination Fragment Name]Directions类来绑定bundle的相关put方法，使我们的bundle参数传递一目了然，降低开发的维护成本。


```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:id="@+id/test_nav_graph"
            app:startDestination="@+id/test1">

    <fragment
            android:id="@+id/test1"
            android:name="com.navigationdemo.app.Test1Fragment"
            android:label="fragment_test_1"
            tools:layout="@layout/fragment_test_1">

        <action android:id="@+id/action_test1_to_test2"
                app:destination="@id/test2"
                app:enterAnim="@anim/slide_in_right"
                app:exitAnim="@anim/slide_out_left"
                app:popEnterAnim="@anim/slide_in_left"
                app:popExitAnim="@anim/slide_out_right"/>

        <action android:id="@+id/action_test1_to_test3"
                app:destination="@id/test3"/>

    </fragment>

    <fragment
            android:id="@+id/test2"
            android:name="com.navigationdemo.app.Test2Fragment"
            android:label="fragment_test_2"
            tools:layout="@layout/fragment_test_2">

        <action android:id="@+id/action_test2_to_test3"
                app:destination="@id/test3"/>

        <argument android:name="paramsB"
                  app:argType="string"
                  app:nullable="true"
                  android:defaultValue="Hello World !!!"/>

    </fragment>

    <fragment
            android:id="@+id/test3"
            android:name="com.navigationdemo.app.Test3Fragment"
            android:label="fragment_test_3"
            tools:layout="@layout/fragment_test_3">

        <argument android:name="paramsC"
                  app:argType="string"
                  app:nullable="true"
                  android:defaultValue="Hello World !!!"/>

    </fragment>


</navigation>
```

自动生成的 [Destination Fragment Name]Args 类

![image](http://leonblog.lelionnews.com/navigation_9.png)


自动生成的[Destination Fragment Name]Directions 类

![image](http://leonblog.lelionnews.com/navigation_8.png)


传递参数：

通过自动生成的[Destination Fragment Name]Directions 类，他会根据不同的action生成不同的Destination路由的NavDirections类，这个NavDirections类其实里面方法很简单，只包含ActionId 和Bundle ，我们知道NavController的navigate方法的参数也有直接传入NavDirections参数。
```
val btnStartTest2 = view.findViewById<AppCompatButton>(R.id.btn)
 btnStartTest2.setOnClickListener {
//            val bundle = bundleOf("paramsA" to "大王叫我来巡山")
//            Navigation.findNavController(btnStartTest2).navigate(R.id.action_test2_to_test3)
    val directions = Test2FragmentDirections.actionTest2ToTest3("大王叫我来巡山")
    Navigation.findNavController(btnStartTest2).navigate(directions)
}
```

接收参数：


```
 val tvArgument = view.findViewById<AppCompatTextView>(R.id.tv_argument)
        //接收数据
//        arguments?.also {
//            val paramsC = it.getString("paramsC")
//            tvArgument.text = paramsC
//        }
        arguments?.also {
            val bundle = Test3FragmentArgs.fromBundle(it)
            tvArgument.text = bundle.paramsC
        }
```

#### 8、Deep Links

Navigation 为我们提供的深链支持web urls 和自定义 scheme URIs，比如我们在目标destination路由的fragmnet添加支持 web url为 https://www.github.com/kaixueio 的话 我们只需要写成 www.github.com/kaixueio 就可以了，另外对于自定义的scheme URI的话， 我们只需要写成 kaixueio-android://path/subpath/{data}这样，对于data参数的话 我们只需要通过fragment中的arguments bundle来接收这个key为data的参数就ok了。

- destination添加 deepLink

![image](http://leonblog.lelionnews.com/navigation_10.png)


```
 <fragment
            android:id="@+id/test3"
            android:name="com.navigationdemo.app.Test3Fragment"
            android:label="fragment_test_3"
            tools:layout="@layout/fragment_test_3">

        <argument android:name="paramsC"
                  app:argType="string"
                  app:nullable="true"
                  android:defaultValue="Hello World !!!"/>
        
        <!--选中"autoVerify"以要求Google验证你是URI的所有者-->
        <deepLink android:id="@+id/deepLink"
                  app:uri="www.github.com/kaixueio"
                  android:autoVerify="true"/>

        <deepLink android:id="@+id/deepLink2"
                  app:uri="aixueio-android://path/subpath/{data}"/>

    </fragment>
```
- 添加intent过滤器

1. ~~对于Android Studio 3.1之前，必须手动添加intent-filter 元素~~  
2. 对于Android Studio 3.2+，直接在Manifest的Activity元素中添加nav-graph标签


```
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
    </intent-filter>
    //添加nav-graph
    <nav-graph android:value="@navigation/test_nav_graph" />
</activity>
```

#### 9、遗留问题

1. startActivityForResult回调处理，目前Navigation还没看到有好的解决方案，但是我们可以通过ViewModel和LiveData来处理，或者通过EventBus 、使用Activity回调处理。
2. 另外网络上反映每次切换destination我们会发现Fragment会被重复创建，其实我觉得这个不是问题，每次切换destination就是一种重新压栈出栈的过程，但是在我们常见的场景下app首页，底部有四个tab分别对应不同的fragment的话，这样会导致每次切换fragment的时候都会重新onCreate,目前还没找到合适的解决方案。


### 项目实战

待续...





