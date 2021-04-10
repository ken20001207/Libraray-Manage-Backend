# Library-Manage-Backend

A simple library management system RESTful API server built
with [Javalin](https://github.com/tipsy/javalin) framework, as my database
course homework.

一个简单的图书管理系统 RESTful API
服务器，作为数据库课程作业，用 [Javalin](https://github.com/tipsy/javalin) 框架搭建。

## Build the `.jar` file

This project using [Maven](https://maven.apache.org) as the dependencies
management tool. If you want to build the `.jar` file of this project on your
local machine, please make sure you have installed maven:

```bash
$ mvn --version

Apache Maven 3.8.1 (05c21c65bdfed0f71a2f2ada8b84da59348c4c5d)
Maven home: /Users/yuanlin/apache-maven-3.8.1
Java version: 15.0.2, vendor: Oracle Corporation, runtime: /Users/yuanlin/Library/Java/JavaVirtualMachines/openjdk-15.0.2/Contents/Home
Default locale: en_TW, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
```

Once you have installed maven, you can build the `.jar` file with this command:

```bash
$ mvn clean compile assembly:single

[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------< com.linyuanlin:LibraryManagement >------------------
[INFO] Building LibraryManagement 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ LibraryManagement ---
[INFO] Deleting /Users/yuanlin/Projects/Libraray-Manage-Backend/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ LibraryManagement ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/yuanlin/Projects/Libraray-Manage-Backend/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ LibraryManagement ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 7 source files to /Users/yuanlin/Projects/Libraray-Manage-Backend/target/classes
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.726 s
[INFO] Finished at: 2021-04-11T02:29:13+08:00
[INFO] ------------------------------------------------------------------------
```

The output `.jar` file will be in the `target` folder with
name `LibraryManagement-1.0-SNAPSHOT-jar-with-dependencies.jar`, you can use
following commend to execute it (make sure you have java installed on your
machine):

```bash
$ java -jar target/LibraryManagement-1.0-SNAPSHOT-jar-with-dependencies.jar

[main] INFO io.javalin.Javalin - 
           __                      __ _
          / /____ _ _   __ ____ _ / /(_)____
     __  / // __ `/| | / // __ `// // // __ \
    / /_/ // /_/ / | |/ // /_/ // // // / / /
    \____/ \__,_/  |___/ \__,_//_//_//_/ /_/

        https://javalin.io/documentation

[main] INFO org.eclipse.jetty.util.log - Logging initialized @86ms to org.eclipse.jetty.util.log.Slf4jLog
[main] INFO io.javalin.Javalin - Starting Javalin ...
[main] INFO io.javalin.Javalin - Listening on http://localhost:7000/
[main] INFO io.javalin.Javalin - Javalin started in 102ms \o/

正在连接至数据库 ...

java.sql.SQLException: The url cannot be null
	at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:660)
	at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:228)
	at com.linyuanlin.LibraryManagement.Main.main(Main.java:27)

数据库连线失败，请检查环境变数是否有正确配置 MySQL 连线资料。
查看 readme.md 或其他文件来取得设定说明。
```

You will get error message if you run the jar file without setting the
environment variables, see the next chapter about some environment variable that
you have to set.

## Environment Variables

```dotenv
MYSQL_URL=jdbc:mysql://...
MYSQL_USER=
MYSQL_PASS=
```

## MySQL Schema

Please use
the [`init.sql`](https://github.com/ken20001207/Libraray-Manage-Backend/blob/main/init.sql)
to import some table and column definitions for this service.
