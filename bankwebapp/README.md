# Secure Banking Application  with Java Servlet
  
## Contents  
  
- [Pre-requisites](#pre-requisites)
- [Secure Banking Application](#secure-banking-application)  
  * [Introduction](#introduction)  
	  + [Application Overview](#application-overview)
		  + [Use Cases](#use-cases)
		  + [Use Case Diagram](#use-case-diagram)
  * [Deployment guide](#deployment-guide)  
    + [Step 1. Clone or download the source code from this repository](#step-1-clone-or-download-the-source-code-from-this-repository)  
    + [Step 2. Import the web application project into Eclipse](#step-2-import-the-web-application-project-into-eclipse)  
    + [Step 3. Update your MySQL server configurations](#step-3-update-your-mysql-server-configurations)  
    + [Step 4. Start MySQL server](#step-4-start-mysql-server)  
    + [Step 5. Generate default MySQL tables](#step-5-generate-default-mysql-tables)  
    + [Step 6. Add a JDBC connector binary to Apache Tomcat server](#step-6-add-a-jdbc-connector-binary-to-apache-tomcat-server)  
   * [Project Structure](#project-structure)
	   + [Project Architecture](#project-architecture)
	   + [Features Implementation](#features)
		   + [Onboarding New User](#onboarding-new-user)
		   + [New Transaction](#new-transaction)
			   + [Single Transaction](#single-transaction)
	  + [Security Enhancement](#security-enhancement)
		  + [Client-Side Validation](#client-side-validation)
			  + [Input Sanitization](#input-sanitization)
			  + [HTML Escaping](#html-escaping)
		  + [Server-Side (Back-End) Validation](#server-side-validation)
			  + [SQL Injection Prevention](#sql-injection-prevention)
			  + [XSS Injection Prevention](#xss-injection-prevention)
		  + [Race-Condition Prevention](#race-condition-prevention)
  + [Coverity Scan Analysis](#coverity-scan-analysis)
	 + [Results](#results)
	 + [Testing Analysis](#testing-analysis)
 + [Conclusion](#conclusion)

## Pre-requisites  
  
1. Java Development Kit (JDK) 1.8 or later  
  
2. Eclipse IDE for Java EE Developers (*Optional* but *recommended*)   
	- Install Eclipse IDE *for Java EE Developers* (not to be confused with Eclipse IDE for Java Developers). You may choose the [Oxygen release](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2) or any recent releases.  
  
	- Make sure that you have selected Eclipse's default JRE to be JDK 1.8 or later (see [yet another insignificant programming notes](https://www.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html#zz-2.)).  
  
3. Apache Tomcat 8.0 or later  

	  - We shall refer to the Apache Tomcat installation directory as `<TOMCAT_HOME>`.  
  
4. MySQL 5.7 or later  
	  -  A default MySQL server account with the username `root` will be created and you will be prompted to create a password.   
  
	- *Do not forget* your password!  
  
  
## Deployment guide  
  
This guide assumes that you are using Eclipse IDE *for Java EE Developers*.  
  
### Step 1. Clone or download the source code from this repository  
  
Download [this repository](https://github.com/sunjun-group/bankwebapp) as a zip file and extract it into your Eclipse workspace.  
  
Alternatively, navigate to your Eclipse workspace using your terminal or command prompt and run the following code to clone the repository into your Eclipse workspace:  
  
```bash  
$ git clone https://github.com/DarrenAscione/bankwebapp.git  
```  

We will refer to the project's root directory as `<BANKWEBAPP>`.  

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
  
## Introduction

### Application Overview

### Use Cases

### Use Case Diagram

## Project Structure

### Project Architecture

```
.
├── _apache-tomcat-9.0.7
|   └── webapps
|       └── data
├── _src
|   ├── main
|   |   ├── java
|   |   |   ├── commons
|   |   |   |   ├── Contants.java
|   |   |   |   ├── Helper.java
|   |   |   |   ├── ServiceException.java
|   |   |   |   └── StringUtils.java
|   |   |   ├── model
|   |   |   |   ├── AbstractIdEntity.java
|   |   |   |   ├── ClientAccount.java
|   |   |   |   ├── ClientInfo.java
|   |   |   |   ├── ClientTransaction.java
|   |   |   |   ├── Role.java
|   |   |   |   ├── TransactionStatus.java
|   |   |   |   ├── User.java
|   |   |   |   ├── UserRole.java
|   |   |   |   └── UserStatus.java
|   |   |   ├── service
|   |   |   |   ├── AbstractDAOImpl.java
|   |   |   |   ├── ClientAccountDAO.java
|   |   |   |   ├── ClientAccountDAOImpl.java
|   |   |   |   ├── ClientInfoDAO.java
|   |   |   |   ├── ClientInfoDAOImpl.java
|   |   |   |   ├── ClientTransactionDAO.java
|   |   |   |   ├── ClientTransactionDAOImpl.java
|   |   |   |   ├── EmailService.java
|   |   |   |   ├── EmailServiceImp.java
|   |   |   |   ├── TransactionCodesDAO.java
|   |   |   |   ├── TransactionCodesDAOImpl.java
|   |   |   |   ├── UserDAO.java
|   |   |   |   ├── UserDAOImpl.java
|   |   |   |   ├── UserRoleDAO.java
|   |   |   |   └── UserRoleDAOImpl.java
|   |   |   └── servlet
|   |   |       ├── ClientDashboardServlet.java
|   |   |       ├── DefaultServlet.java
|   |   |       ├── FileUploadServlet.java
|   |   |       ├── LoginServlet.java
|   |   |       ├── NewTransactionServlet.java
|   |   |       ├── RegisterServlet.java
|   |   |       ├── ServletPaths.java
|   |   |       ├── StaffDashboardServlet.java
|   |   |       └── TransactionCodeGenerator.java          
|	|   ├── _resources
|	|   └── _webapp
|	|		├── resources
|	|		|   └── js
|	|		|       ├── login.js
|	|		|       ├── register.js
|	|		|       └── transaction.js
|	|		├── WEB-INF
|	|		|   ├── jsp
|	|		|   |   ├── clientDashboard.jsp
|	|		|   |   ├── errorMessage.jsp
|	|		|   |   ├── header.jsp
|	|		|   |   ├── login.jsp
|	|		|   |   ├── newTransaction.jsp
|	|		|   |   ├── pageHeader.jsp
|	|		|   |   ├── register.jsp
|	|		|   |   ├── staffDashboard.jsp
|	|		|   |   └── welcome.jsp
|	|		|   └── web.xml
|	|		└── index.jsp
| └── test
├── pom.xml
└── README.md
```

