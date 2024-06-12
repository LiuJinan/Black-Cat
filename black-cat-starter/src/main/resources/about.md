# Black-Cat

BlackCat是一个工具箱项目，以插件的方式集成各种工具。

有兴趣开发插件的大佬可以查看GitHub源码

version：v1

author：liujinnan

GitHub：https://github.com/LiuJinan/Black-Cat

打包
# 查看依赖
bin\\jdeps -s BlackCat.jar

# 精简jie
bin\\jlink --output out --add-modules java.base,java.logging,java.compiler,java.desktop,java.datatransfer,java.instrument,java.naming,java.prefs,java.sql,java.xml,jdk.compiler,jdk.unsupported

# 打包
bin\\jpackage --type app-image --input blackCat --runtime-image out --name BlackCat --main-jar BlackCat.jar --icon logo.png --app-version 1.0.0 --vendor liujinnan --copyright liujinnan.cn --description tools --dest exe





