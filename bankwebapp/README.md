# SUTD-bank Webapp

Contents

- [SUTD-bank Webapp](#sutd-bank-webapp)
  * [Pre-requisites](#pre-requisites)
    + [Java Development Kit (JDK) 1.8 or later](#java-development-kit-jdk-1-8-or-later)
    + [Eclipse IDE for Java EE Developers (*Optional* but *recommended*)](#eclipse-ide-for-java-ee-developers-optional-but-recommended-)
    + [Apache Tomcat 8.0 or later](#apache-tomcat-8-0-or-later)
    + [MySQL 5.7 or later](#mysql-5-7-or-later)
    + [MySQL WorkBench (*Optional*)](#mysql-workbench-optional-)
  * [Deployment guide](#deployment-guide)
    + [Step 1. Clone or download the source code from this repository](#step-1-clone-or-download-the-source-code-from-this-repository)
    + [Step 2. Import the web application project into Eclipse](#step-2-import-the-web-application-project-into-eclipse)
    + [Step 3. Update your MySQL server configurations](#step-3-update-your-mysql-server-configurations)
    + [Step 4. Start MySQL server](#step-4-start-mysql-server)
    + [Step 5. Generate default MySQL tables](#step-5-generate-default-mysql-tables)
    + [Step 6. Add a JDBC connector binary to Apache Tomcat server](#step-6-add-a-jdbc-connector-binary-to-apache-tomcat-server)
    + [Step 7. Deploy your web application from Eclipse (*Optional*)](#step-7-deploy-your-web-application-from-eclipse-optional-)
      - [Step 7a. Add Apache Tomcat server as a web server for Eclipse](#step-7a-add-apache-tomcat-server-as-a-web-server-for-eclipse)
      - [Step 7b. Modify the attached Apache Tomcat server's configurations](#step-7b-modify-the-attached-apache-tomcat-server-s-configurations)
      - [Step 7c. Run your web application from Eclipse](#step-7c-run-your-web-application-from-eclipse)
    + [Step 8. Deploy your web application in Apache Tomcat (*Optional*)](#step-8-deploy-your-web-application-in-apache-tomcat-optional-)
      - [Step 8a. Modify Apache Tomcat server's configurations](#step-8a-modify-apache-tomcat-server-s-configurations)
      - [Step 8b. Clean your Apache Tomcat `webapps` directory](#step-8b-clean-your-apache-tomcat-webapps-directory)
      - [Step 8c. Expore your web application project as a `war` file](#step-8c-expore-your-web-application-project-as-a-war-file)
      - [Step 8d. Run your web application from Apache Tomcat](#step-8d-run-your-web-application-from-apache-tomcat)

## Pre-requisites

### Java Development Kit (JDK) 1.8 or later

See [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/howto/JDK_HowTo.html) for installation instructions.

### Eclipse IDE for Java EE Developers (*Optional* but *recommended*) 

Install Eclipse IDE *for Java EE Developers* (not to be confused with Eclipse IDE for Java Developers). You may choose the [Oxygen release](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2) or any recent releases.

Make sure that you have selected Eclipse's default JRE to be JDK 1.8 or later (see [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html#zz-2.)).

See [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html#zz-1) for installation instructions.

### Apache Tomcat 8.0 or later

See [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/howto/Tomcat_HowTo.html#zz-2.) for installation instructions.

We shall refer to the Apache Tomcat installation directory as `<TOMCAT_HOME>`.

### MySQL 5.7 or later

See [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/sql/MySQL_HowTo.html#zz-3.1) for installation instructions. A default MySQL server account with the username `root` will be created and you will be prompted to create a password. 

*Do not forget* your password!

### MySQL WorkBench (*Optional*) 

[MySQL Workbench](https://dev.mysql.com/downloads/workbench/) will be useful for debugging your web application's database.

## Deployment guide

This guide assumes that you are using Eclipse IDE *for Java EE Developers*.

### Step 1. Clone or download the source code from this repository

Download [this repository](https://github.com/sunjun-group/bankwebapp) as a zip file and extract it into your Eclipse workspace.

Alternatively, navigate to your Eclipse workspace using your terminal or command prompt and run the following code to clone the repository into your Eclipse workspace:

```bash
$ git clone https://github.com/sunjun-group/bankwebapp.git
```

We will refer to the project's root directory as `<BANKWEBAPP>`.

The project structure is as follows:

    + src/main/java [Java code]
    + src/main/resources [configuration for database and email]
        + database.properties
        + email.properties
        + create.sql [MySQL script to create tables]

### Step 2. Import the web application project into Eclipse

* Click on `File --> Import...`
* Select `Existing Projects into Workspace`
* Select the project's root directory `<BANKWEBAPP>` and click `Finish`

### Step 3. Update your MySQL server configurations

Edit `<BANKWEBAPP>/src/main/resources/database.properties` and modify:
* `jdbc.password` -- replace with the password of your `root` MySQL server account

### Step 4. Start MySQL server

See [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/sql/MySQL_HowTo.html#zz-3.2) for instructions.

### Step 5. Generate default MySQL tables

Right-click on `<BANKWEBAPP>/src/test/java/sutdbank/DbCreator.java` and select `Run As --> Java Application`.

This step creates/overwrites the default schema `bankwebapp`.

### Step 6. Add a JDBC connector binary to Apache Tomcat server

*Copy* `<BANKWEBAPP>/libs/mysql-connector-java-5.1.35.jar` into `<TOMCAT_HOME>/lib`.

### Step 7. Deploy your web application from Eclipse (*Optional*) 

In this step, we will guide you to attach Apache Tomcat to Eclipse so that you can run and debug your web application from within Eclipse. If you do not wish to do so, skip ahead to [Step 8](#step-8-deploy-your-web-application-in-apache-tomcat---optional--).

#### Step 7a. Add Apache Tomcat server as a web server for Eclipse

Follow *Steps 3 to 5* of [this online tutorial](http://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/).

Do not start your web server yet!

#### Step 7b. Modify the attached Apache Tomcat server's configurations

After the previous step, you should see a new project folder named `Servers` in your Eclipse workspace.

Open `Servers/Tomcat vX.X Server at localhost-config/server.xml` (where `vX.X` refers to your Apache Tomcat version, e.g., `v8.5`) and add the following lines between the `<Engine> ... </Engine>` tags:

```xml
    <Realm 
        className="org.apache.catalina.realm.JDBCRealm"
        driverName="org.gjt.mm.mysql.Driver"
        connectionURL="jdbc:mysql://localhost:3306/bankwebapp"
        connectionName="root" 
        connectionPassword="INSERT_YOUR_PASSWORD_HERE"
        userTable="user" 
        userNameCol="user_name" 
        userCredCol="password"
        userRoleTable="user_role" 
        roleNameCol="role" 
    />
```

Replace the `connectionPassword` field with the password of your `root` MySQL server account.

#### Step 7c. Run your web application from Eclipse

Right-click on your web application project in Eclipse and select `Run As --> Run on server`.

Navigate your web browser to http://localhost:8080/sutdbank.

You should be able to log in with the default account `staff_1/123456`.

### Step 8. Deploy your web application in Apache Tomcat (*Optional*)

#### Step 8a. Modify Apache Tomcat server's configurations

Edit `<TOMCAT_HOME>/conf/server.xml` and add the following lines between the `<Engine> ... </Engine>` tags:

```xml
    <Realm 
        className="org.apache.catalina.realm.JDBCRealm"
        driverName="org.gjt.mm.mysql.Driver"
        connectionURL="jdbc:mysql://localhost:3306/bankwebapp"
        connectionName="root" 
        connectionPassword="INSERT_YOUR_PASSWORD_HERE"
        userTable="user" 
        userNameCol="user_name" 
        userCredCol="password"
        userRoleTable="user_role" 
        roleNameCol="role" 
    />
```

Replace the `connectionPassword` field with the password of your `root` MySQL server account.

#### Step 8b. Clean your Apache Tomcat `webapps` directory

Delete (if it exists) the folder `<TOMCAT_HOME>/webapps/bankwebapp/`.

#### Step 8c. Expore your web application project as a `war` file

Right-click on your web application project in Eclipse and select `Export...`. Select the `WAR file` wizard and:
* Give your web project the title `bankwebapp`.
* Select the destination to be `<TOMCAT_HOME>/webapps`.
* Un-check the box for `Optimize for a specific server runtime`.
* Check the box for `Overwrite existing file`.

This creates the file `<TOMCAT_HOME>/webapps/bankwebapp.war`.

#### Step 8d. Run your web application from Apache Tomcat

Start Apache Tomcat (see [instructions](https://www.ntu.edu.sg/home/ehchua/programming/howto/Tomcat_HowTo.html#zz-2.4)) and navigate your web browser to http://localhost:8080/sutdbank.

You should be able to log in with the default account `staff_1/123456`.
