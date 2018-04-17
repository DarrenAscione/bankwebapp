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
			   + [Batch Upload](#batch-upload)
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
		 + [Overview](#overview)
		 + [Black-Box Test Case](#black-box-test-case)
		 + [JUnit](#junit)
		 + [Mocking Objects for Isolation Testing](#mocking-objects-for-isolation-testing)
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

This report documents our work for the **50.531 Secure Software Engineering ** final project. In this project, we were distributed a running but incomplete web application for a bank - _bankwebapp_. This project is coded in _java_, using the _IntelliJ_ environment on the _Apache Tomcat 8.0_ server as _localhost_. The distributed project allows users to _register_ accounts and _login_. Additionally, there is a default username, _staff_1_ with administrative privileges. The key objectives for this project are

1. Completion of the functionalities including transactions and batch transactions
2. Implementation of security features
3. Testing the project's source codes for defects
4. Testing the project's source codes for vulnerabilities.

Additionally, we will apply USE cases and USE case diagrams to show demonstrate our direction and logic flow.

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
|   └── test
├── pom.xml
└── README.md
```

### Features Implementation

#### Onboarding New User

One of the key features we have implemented beyond the scope of the deliverables and rubrics we have added is a stringent requirement for registration credentials. We would require that for registration, users

1. Use a password with at least one uppercase, one digit (0-9), one symbol and at least 8 characters long. 
2. Use a valid email address.

Users would require a valid username, password and email address for login. As these are standard security implementations adopted by most public platforms, we opine that it was a good practice to implement this for our project as well. You may refer to the _Security Implementations_ section below for a full discussion.

#### Single Transaction

For the single transaction functionality, the `clientTransactionDAO`interface has been modified to include the following methods:

```java
public interface ClientTransactionDAO {  
  
  void create(ClientTransaction clientTransaction) throws ServiceException;  
  
  List<ClientTransaction> load(User user) throws ServiceException;  
  List<ClientTransaction> loadWaitingList() throws ServiceException;  
  
 void updateDecision(List<ClientTransaction> transactions) throws ServiceException;  
  
 void updateReceiver(ClientTransaction transaction) throws ServiceException;  
  
 void updateSender(ClientTransaction transaction) throws ServiceException;  
  
  Boolean validTransaction(ClientTransaction transaction) throws ServiceException;  
  
}
```

The method `validTransaction()` serves as a check to see if the transaction made by the user is a valid transaction **if and only if the user has enough money to transfer**. This is done by comparing the amount to be transferred against the user's current balance.

```java
@Override  
  public synchronized Boolean validTransaction(ClientTransaction transaction) throws ServiceException {  
  Connection conn = connectDB();  
  PreparedStatement ps = null;  
  ResultSet rs = null;  
 try {  
  ps = prepareStmt(conn, "SELECT amount FROM client_account WHERE user_id = ?");  
 int idx = 1;  
  ps.setString(idx++, String.valueOf(transaction.getUser().getId()));  
  rs = ps.executeQuery();  
 if (rs.next()) {  
  BigDecimal current_amount = new BigDecimal(rs.getInt(1));  
 return transaction.getAmount().compareTo(current_amount) < 0;  
  } else {  
  throw new SQLException("no data found");  
  }  
  
 } catch (SQLException e) {  
  throw ServiceException.wrap(e);  
  } finally {  
  closeDb(conn, ps, rs);  
  }  
 }
```

Moreover, the `TransactionCodesDAO` interface has been updated to include the `validCode()` and `updateUsage()` 


#### Batch Upload

### Security Enhancement

#### Client-Side Validation

A helper class is written to hold common methods of input sanitation that is used throughout the application whenever an input is processed. This helper class helps to prevent the possible SQL and XSS injections into the application. Because user inputs are thought to be insecure and are used throughout the application whenever a `DAO` object needs to either update or select from the database, placing these methods in a common module allows it to be used throughout the application.

On top of that, javascript is used as a client-side validation to prevent the user from inputting certain values. The javascript class contains methods such as `validateAmount` and `validateEmail` to ensure that the user input allows a restricted pattern of regular expression. 

##### User Input Limitations

A great way to prevent injection attacks is to limit the input space of the user. In the provided skeleton application, we can see that the user is able to key in alphanumeric characters into the amount section of the new transaction page. This vulnerability allows users to key in irrelevant characters that may allow some form of an injection attack. To prevent this, the form used in the `.jsp` classes need to be sanitised to only allow its intended set of possible inputs.

In the `newTransaction.jsp`, the form type has been change to limit the user to only key in numeric characters.

```html
<div id="input-group-toAccount" class="form-group">  
 <label for="toAccountNum" class="control-label">To (account number)</label>  
 <input type="number" class="form-control" id="toAccountNum" name="toAccountNum" placeholder="To Account Number">  
</div>  
<div id="input-group-amount" class="form-group">  
 <label for="amount" class="control-label">Amount</label>  
 <input type="number" class="form-control" id="amount" name="amount" placeholder="amount">  
</div>
```

##### JavaScript Validation Methods

Javascript is used as a form of client-side validation to prevent the user from injecting malicious code through an input form. The javascipt functions serve as a first line of defence in sanitising and limiting the input space of the user. Methods such as `validateAmount`  prevents the user from setting a value too small while methods such as `validatePassword` ensures that the user follows a strict set of regular expression.

```javascript
function validatePassword(password) {  
  var re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/;  
 return re.test(password)  
}
```
 In this case, the password of the user is set to be at least 8 characters long with at least one numeric, one symbol and one capital character in order for it to be a valid password. This increases the security of the user's account from brute force attack on password trials.





#### Server-Side Validation 

##### SQL Injection Prevention

In the project, SQL queries are used in the following files, `ClientTransactionDAO`, `ClientTransactionDAOImpl`, `UserDAOImpl`, `ClientInfoDAOImpl`, `TransactionCodesDAOImp`, `UserRoleDAOImpl`. In these Database Access Object java classes, the `PreparedStaement`object is used. This represents a precompiled SQL statement that can be executed multiple times without having to recompile for every execution. The code is not vulnerable to SQL injection because it does not use dynamic queries to concatenate malicious data to the query itself. In the code snippet of the java class `UserRoleAOImpl`, we can see that it correctly uses parameterised queries. 

``` java
try {
			ps = prepareStmt(conn, "INSERT INTO user_role(user_name, role) VALUES(?,?)");
			int idx = 1;
			ps.setString(idx++, userRole.getUser().getUserName());
			ps.setString(idx++, userRole.getRole().name());
			executeInsert(userRole, ps);
		}
```

The project utilises Java's `PreparedStatement` class and binds variables (i.e '?') and the corresponding `setString` methods, SQL injection can be easily prevented.

## Testing

### Overview

**unit testing** is a software testing method by which individual units of source code, sets of one or more computer program modules together with associated control data, usage procedures, and operating procedures, are tested to determine whether they are fit for use.

A unit test should test functionality in isolation. Side effects from other classes or the system should be eliminated for a unit test, if possible. Hence as such, with this definition in mind, the proper way of writing unit test cases should be applied. Code coverage provides a good insight as to how much testing has been done throughout the application. Several various unit testing techniques were applied, including parameterised testing, etc. 

### JUnit

As mentioned above, a unit test should test functionality in isolation. As such setter and getter methods are often if not always incorrectly written. A unit test method for a setter function should not call its corresponding getter method when applying assertion. This is also true for a test on the getter function. Because of this, the following test case has been written to demonstrate how proper unit testing should be carried out. 

```java
// How to properly test a getter function without calling a setter
@Test  
public void getUser() throws NoSuchFieldException, IllegalAccessException {  
  final Field field = clientAccount.getClass().getDeclaredField("user");  
  field.setAccessible(true);  
  field.set(clientAccount, new User(12));  
 final User result = clientAccount.getUser();  
 final int id = result.getId();  
  assertEquals("field wasn't retrieved properly", id, 12);  
}
```

In the `getUser()` test class, we first set a variable `field` where we are getting the field value `user` which is a private variable in the `ClientAccount` model and is accessed and set through it's `getUser()` and `setUser()` methods. Because we are testing on the getter method, based on the principles of unit testing, it would not be correct to call a `setUser()` method in this test case. We can however se this field `field.setAccesible(true)` as `true` and this would allow us to set the corresponding field which in this case is `user` as ` field.set(clientAccount, new User(12)); `. From here, we can finally apply the method we are testing for which is `clientAccount.getUser()` and apply an assertion to see if these values matches.

```java
// How to properly test a setter function without calling a getter
@Test  
public void setUser() throws NoSuchFieldException, IllegalAccessException {  
  //when  
  clientAccount.setUser(new User(12));  
  
  //then  
  final Field field = clientAccount.getClass().getDeclaredField("user");  
  field.setAccessible(true);  
  assertEquals("Fields didn't match", field.get(clientAccount), 12);  
}
```

Likewise, the above code shows how a proper test case for a setter function should be written.

Each of the Java classes in the Model module has a corresponding getter and setter method that must be tested as shown above. These test cases have been written and you can refer them in the following directory: `<BANKWEBAPP>/src/test/java/sg.edu.sutd.bank.webapp/model`.


### Black-Box Test Case

| TestID                 | Description                                                                                                                                                                                                                            | Expected Results                                                                                                                                      | Actual Results |
|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| LoginUser1             | Precondition:  User at login Page of Web Application. Currently no user logged in. Waiting for user to key in username and password. User1: guest Password1: Password!12345                                                                     | BankAccount                                                                                                                                           |                |
| LoginUser1.1           | Precondition:  Login Failed. User keys in wrong password and/or username once                                                                                                                                                          | Savings, Investments                                                                                                                                  |                |
| LoginUser1.2           | Precondition: Login Failed thrice. 
User keys in wrong password and/or username 3 times.                                                                                                                                                | Pop-up showing warning is displayed. User unable to key in username/password for the next 1 minute.                                                   |                |
| LoginUser1.3           | Precondition: Login Successful. User keys in correct password and username combination.                                                                                                                                                | User is redirected to main dashboard page.                                                                                                            |                |
| Transaction2           | Precondition: User has successfully logged into his/her account. User is at transaction tab. User1: guest Password1: guest Account Balance: 100                                                                                        | User is at the transaction tab of the dashboard page.                                                                                                 |                |
| Transaction2.1         | Precondition:  Transaction2 has passed User1. User1 makes a transaction of -$50.                                                                                                                                                       | User account balance updates to $50. User transaction history updates to include latest transaction. User is brought back to transaction history tab. |                |
| Transaction2.2         | Precondition:  Transaction2 has passed User1. User1 makes a transaction of -$120 and fails.                                                                                                                                            | User receives alert message to state balance not enough. User is taken back to transaction tab.                                                       |                |
| TextFileTransaction3   | Precondition: User has successfully logged into his/her account. User is at transaction tab. User selects text file upload option.  User1: guest Password1: guest Account Balance: 100 Text File Format: .txt                          | User is at the transaction tab of the dashboard page and uploads a .txt file                                                                          |                |
| TextFileTransaction3.1 | Precondition:TextFileTransaction3 has passed User1. User1 uploads a .txt file and is parsed into the browser successfully of transaction -$50.  User1: guest Password1: guestAccount  Balance: 100 Text File Format: .txt Amount: -$50 | User account balance updates to $50. User transaction history updates to include latest transaction. User is brought back to transaction history tab. |                |
| TextFileTransaction3.2 | Precondition:TextFileTransaction3 has passed User1. User1 uploads a .txt file and is parsed into the browser unsuccessfully.  User1: guest Password1: guest Account Balance: 100 Text File Format: .txt Amount: sdfkdjfsdf             | User receives alert message that parsing of .txt file failed. User is brought back to transaction tab.                                                |                |
| TextFileTransaction3.3 | Precondition:TextFileTransaction3 has passed User1. User1 uploads a .pdf file and is parsed into the browser unsuccessfully.  User1: guest Password1: guest Account Balance: 100 Text File Format: .pdf                                | User receives alert message that parsing of .txt file failed. User is brought back to transaction tab.                                                |                |
|                        |                                                                                                                                                                                                                                        |                                                                                                                                                       |                |



## Analysis

Static analysis refers to the analysis of computer programs without actual execution. Typically, the uses of static analysis range from detecting coding errors to verifying formal methods. For our purposes, static analysis aids us in finding code vulnerabilities and security defects. We have adopted _Coverity Scan_ as our static analysis tool.

### Coverity Report

To use Coverity Scan, we download their _zip_ package _cov-analysis-win64-2017.07.zip_ from https://scan.coverity.com/download?tab=java. The procedure requires two main steps:

1. Register an account and the project at their website https://scan.coverity.com/. Users may also login with their _Github_ user credentials.
2. Build the project log via the said zip package above. This step less trivial, and requires more time to set up. The coverity website instructs us to first to add the \bin directory in the zip package to our _path_. To do this, we run the _Edit the system environment variables_ utility in the _Windows Control Panel_. We would also need to add the _Apache Maven_ tool and _Java Development Kit_(JDK (not JRE)) compiler to to system path. Finally, we access the bankwebapp project directory with a _cmd_ shell, and use `mvn clean` to clean up undesirable units or steps and `cov-build --dir cov-int mvn -DskipTests=true compile` to build logs. A folder _cov-int_ will be added to the project directory. We compress this folder, and upload it to https://scan.coverity.com/projects/project_link?tab=overview . 

We would then obtain the following summary.
![https://imgur.com/abf1xow](image.png)
//still figuring out how to upload images. 
It will require up to 48 business hours to receive the full report.