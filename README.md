# Secure Banking Application  with Java Servlet

[![Build Status](https://travis-ci.org/DarrenAscione/bankwebapp.svg?branch=master)](https://travis-ci.org/DarrenAscione/bankwebapp) [![Coverage Status](https://coveralls.io/repos/github/DarrenAscione/bankwebapp/badge.svg?branch=master)](https://coveralls.io/github/DarrenAscione/bankwebapp?branch=master)
  
## Contents  
  
- [Pre-requisites](#pre-requisites)
- [Secure Banking Application](#secure-banking-application)  
  * [Introduction](#introduction)  
	  + [Application Overview](#application-overview)
		  + [Use Cases](#use-cases)
		  + [Use Case Diagram](#use-case-diagram)
  * [Deployment guide](#deployment-guide)  
   * [Project Structure](#project-structure)
	   + [Project Architecture](#project-architecture)
	   + [Features Implementation](#features)
		   + [Onboarding New User](#onboarding-new-user)
		   + [New Transaction](#new-transaction)
			   + [Single Transaction](#single-transaction)
			   + [Batch Upload](#batch-upload)
	  + [Security Enhancement](#security-enhancement)
		  + [Client-Side Validation](#client-side-validation)
			  + [Input Sanitisation](#input-sanitisation)
			  + [JavaScript Validation Methods](#javascript-validation-methods)
		  + [Server-Side (Back-End) Validation](#server-side-validation)
			  + [SQL Injection Prevention](#sql-injection-prevention)
			  + [XSS Injection Prevention](#xss-injection-prevention)
		  + [Race-Condition Prevention](#race-condition-prevention)
 + [Testing](#testing)
	 + [Overview](#overview)
	 + [Black-Box Test Case](#black-box-test-case)
	 + [JUnit](#junit)
	 + [Mocking Objects for Isolation Testing](#mocking-objects-for-isolation-testing)
	 +  [Penetration Testing](#penetration-testing)
 + [Static Analysis Tool](#static-analysis-tool)
	 + [Coverity Scan Analysis](#coverity-scan-analysis)
		 + [Other Tools for Integration Testing](#other-tools-for-integration-testing)
		 + [Travis CI](#travis-ci)
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

This report documents our work for the **50.531 Secure Software Engineering** final project. In this project, we were distributed a running but incomplete web application for a bank - _bankwebapp_. This project is coded in _java_, using the _IntelliJ_ environment on the _Apache Tomcat 8.0_ server as _localhost_. The distributed project allows users to _register_ accounts and _login_. Additionally, there is a default username, _staff_1_ with administrative privileges. The key objectives for this project are

1.  Completion of the functionalities including transactions and batch transactions
2.  Implementation of security features
3.  Testing the project's source codes for defects
4.  Testing the project's source codes for vulnerabilities.

Additionally, we will apply USE cases and USE case diagrams to show demonstrate our direction and logic flow.

### [](#use-cases)Use Cases

| Name| Register|
-------| -----
| Objective | To register account in SUTD Bank|
|Pre-conditions | User must have an existing email address. Password must consist of >8 characters, >1 uppercase, >1 lowercase, 1 symbol, 1 digit. User must have a valid FIN. |
|Post-conditions|Success:	User creates a new account with unique username, password, personal and contact details that is stored in the database. Failure:Error message explaining why user was unable to create a new account. |
|Actors|Primary: Users with the intention of using the SUTD Bank App.|
|Trigger| User wants to store, transfer or receive money with SUTD Bank.|
|Normal flow|User clicks on register button at main page > User enters full name > User enters FIN > User enters date of birth > User enters his/her occupation > User enters mobile contact > User enters mailing address > User enters valid email address > User enters username > User enters valid password > User clicks on _Register_ to register account > Staff_1 login > Staff_1 approve > Account successfully created > User receives a message saying his/her account is successfully created|
|Alternative flow|User clicks on register button at main page > User enters full name > User enters FIN > User enters date of birth > User enters his/her occupation > User enters mobile contact ? User enters mailing address > User enters valid email address > User enters username > User enters password > User clicks on _Register_ to register account > Server rejects any of his/her entered fields > User receives a message saying any of his/her entered fields were not allowed, and the reason is displayed|
|Interacts with| Staff Login (Extends)|
|Open issues|
------------
| Name | Login |
|------| -----|
|Objective|To access the Bank App services in SUTD Bank
|Pre-conditions|User must have a registered account with credentials inside SQL Database
|Post-conditions|Success: Login Main Page showing account balance and transaction history. Failure: Message saying “Login failed”
| Actors|Primary: User
|Trigger|User wants to send or receive money or check account balance through SUTD Bank
|Normal flow|User enters valid login username in SQL Database > User enters valid login password in SQL Database > User sees Main Page
|Alternative flow|User enters valid/invalid login username > User enters invalid login password > User sees login error message
|Interacts with|SQL Database, Main Page (includes)
|Open issues
-------
| Name | Staff Login |
|------| -----|
|Objective|To gain administrative privileges.
|Pre-conditions|User must have a registered account with credentials inside SQL Database
|Post-conditions|Success: Login Staff Main Page showing account balance and transaction history. Failure: Message saying “Login failed”
|Actors|Primary: Staff
|Trigger|User wants to send or receive money or check account balance through SUTD Bank
|Normal flow|Staff enters valid login username in SQL Database > Staff enters valid login password in SQL Database > Staff sees > Staff Main Page
|Alternative flow|Staff enters valid/invalid login username > Staff enters invalid login password > Staff sees login error message
|Interacts with|SQL Database, Staff Main Page (includes)
|Open issues
-----
| Name | Main Page|
|------| -----|
|Objective|To access the Bank App services in SUTD Bank
|Pre-conditions|User must have a registered account with credentials inside SQL Database
|Post-conditions|Success: Login Main Page showing account balance and transaction history. Message saying “Login failed”
|Actors| Primary: User
|Trigger|User wants to send or receive money or check account balance through SUTD Bank
|Normal flow|User enters valid login username in SQL Database > User enters valid login password in SQL Database > User sees Main Page
|Alternative flow|User enters valid/invalid login username > User enters invalid login password > User sees login error message
|Interacts with|SQL Database, Login (includes)
--------
| Name |Staff Main Page|
|------| -----|
|Objective|To approve/decline user requests.
|Pre-conditions|Staff must be logged in.
|Post-conditions|Success: Staff Main Page showing pending approvals, Staff Main Page showing null if no pending approvals. Failure: Message saying “Login failed”
|Actors|Primary: Staff
|Trigger|Staff wants to approve/decline user requests
|Normal flow|Staff enters valid login username in SQL Database > Staff enters valid login password in SQL Database > Staff sees Main Page > Staff approves request OR Staff declines request. > Staff logs out. 
|Alternative flow|Staff enters valid/invalid login username > Staffenters invalid login password > Staff sees login error message
|Interacts with|SQL Database, StaffLogin (includes)
|Open issues
----------
| Name | Transfer Money|
|------| -----|
|Objective|To transfer money from one account to another in SUTD Bank
|Pre-conditions|User must be logged in, User must have sufficient funds, Payee must have an account with SUTD Bank
|Post-conditions|Success: Money is successfully transferred from one account to another, User receives a messaging indicating that the transaction is successful. ransaction history updated. Failure: User has insufficient credits in account
|Payee does not exist in database. System under maintenance
|Actors|Primary: User_1 (Payer), User_2 (Payee)
|Trigger|User wants to transfer money
|Normal flow|User logs in with correct username and password > User enters prompt for money transfer > User enters payee account number > User enters amount of money to transfer > User confirms transfer > Staff_1 to login, approve transaction > User receives message of successful transfer > Transaction history updated
|Alternative flow|User notified of insufficient credits > User is returned Transfer money window
|Interacts with | SQL Database, Main Page (Includes), Staff Login (Extends)
|Open issues| Implement time-out notification, Implement fail-safe for connection issues with notification to user
------------
| Name | Batch Transfer|
|---------|---------|
|Objective|To transfer money from one account to multiple accounts in SUTD Bank
|Pre-conditions|User must have a registered account, User must have sufficient funds, Payee must have a registered account with SUTD Bank, User must have a completed form consisting of all details needed by the bank app for funds transfer (“the textfile”)
|Post-conditions|Success: Message indicating file upload is successful. Transaction history updated. Failure: error message for insufficient funds or error in field(s).
|Actors|Primary: User_1 (Payer). Secondary:  User_3 (Payee), User_4 (Payee)
|Trigger|User wants to transfer money to multiple payees with SUTD Bank.
|Normal flow|User fills up the textfile correctly > User selects Browse > User determines file path where the textfile was stored >  User selects Upload > User receives a message indicating the file upload was successful > Batch Transfer reads the textfile > Batch Transfers enters information from the textfile into Transfer Money, manually creating transactions > Staff_1 login, approve all transactions > Transaction history updated 
|Alternative flow | User notified of insufficient credits > User is returned to the upload file window
|Interacts with
|SQL Database|Staff Login (Extends), Main Page (Includes), The textfile (Extends)
|Open issues| Implement time-out notification, Implement fail-safe for connection issues with notification to user

### [Use Case Diagram](#use-case-diagram)

![](https://imageshack.com/a/img923/6210/eAJMjV.png)

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
|   |       |   |   └── welcome.jsp
|   |       |   └── web.xml
|   |       └── index.jsp
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

![Imgur](https://i.imgur.com/bVnrK2V.png)
**Figure 1.1: Invalid password and email due to regular expression check in the Javascript**

![Imgur](https://i.imgur.com/ZfIjFxO.png)
**Figure 1.2: Successful Registration of new user**

Once the user has successfully registered his/her account, the application will redirect the user to the main page with th eabove shown message. The user will also receive an email notification based on the email address used to register the account to notify the user that a successful registration has been completed.

![Imgur](https://i.imgur.com/eJj0Q4S.png)
**Figure 1.3: Successful Email Received**

When a user has been registered, the staff/admin must approve this account in order to activate it. Only when the staff has approved the account would the user be then allowed to login to his account and make transactions. He would also receive an email notifying him that the account has been approved together with a list of transaction codes that he can use to make transactions.

![Imgur](https://i.imgur.com/09vmbuS.png)
**Figure 1.4: Staff Dashboard should display any pending request for client registration**

![Imgur](https://i.imgur.com/1Fh13Xb.png)
**Figure 1.5: Email received by the user to notify him that the account has been approved.**

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

Moreover, the `TransactionCodesDAO` interface has been updated to include the `validCode()` and `updateUsage()` . These methods ensure that the transaction code used is valid.

```java
@Override  
public Boolean validCode(String code, int userId) throws ServiceException {  
  Connection conn = connectDB();  
  PreparedStatement ps = null;  
  ResultSet rs = null;  
  String acode = "\"" + code + "\"";  
 try {  
  String query = String.format("SELECT * FROM transaction_code WHERE code= %s AND user_id = %s AND used = 0", acode, userId);  
  ps = prepareStmt(conn, query);  
  rs = ps.executeQuery();  
 if (!rs.isBeforeFirst()) {  
  throw new SQLException("Your Code is invalid or has expired, please use another valid transaction code emailed to your account. Thank you");  
  }  
 } catch (SQLException e) {  
  throw ServiceException.wrap(e);  
  } finally {  
  closeDb(conn, ps, null);  
  }  
  return true;  
}
```

Under the clientTransactionDAO model, a new method `validTransaction()` is written to return if the user has succifient balance before submitting the request. If a user has more than enough balance, the transaction is allowed and the user will be able to successfully make that transaction.

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
```

As part of the requirements, the user must transfer at least $10.00 whenever a transaction is to be made. This is checked on the client side through the use of javascript ``.

![Imgur](https://i.imgur.com/z9b5JsK.png)
**Figure 2.1: Error Shown when the amount is not valid and user can only key in numeric inputs**

![Imgur](https://i.imgur.com/bAw6HEW.png)
**Figure 2.1: Error Shown when the code is invalid or has been used (expired)**

![Imgur](https://i.imgur.com/guWhEJQ.png)
**Figure 2.2: Upon Successful Transaction, dashboard is updated to reflect changes**


#### Batch Upload

Batch upload allows the user to submit a `.txt` file with multiple transactions. The user will have to follow a strict file format, with variables separated with single spaces. The fields to be submitted must be written in the following order:

1. Transaction Code
2. Amount (to be sent)
3. Account Number (where to send to)

``` bash
# transaction code # amount # toAcc
234a9f84:162b932fad7:-7f68 20 25
234a9f84:162b932fad7:-7f67 20 25
```

Should if the user decides to input an invalid transaction code in any of the transactions in the `.txt` file, the entire batch upload will be rejected. 

The file will also be stored in a location off from the main source code of the project for security reasons. It will be stored under the `webapps/data` directory. If a user submits the same file twice, it will not be processed and the file will be deleted. 

To perform the file upload functionality, the `FileUploadServlet` was written. It uses the apache commons `fileupload` module and upon a `doPost()` request, the servlet first tries to read each of the multiple files uploaded in a single instance. It first parses the request and checks if it is `null`.

Several key security measurements were also used to prevent possible attacks through the limitation of the file type as well as the size of the uploaded file. These preventions will be explained in detail under the security enhancement feature. 

Once the file has been successfully parsed and the contents are deemed non malicious, the servlet reads the file line by line and executes the request by creating individual `clientTransactionDAO` objects similar to how a single transaction is carried out. The same `Helper` class functions are used to sanitise the user input. 

Upon completion, the user will be redirected to the main dashboard page and the new list of transactions will appear in the table view with the status pending. 

![Imgur](https://i.imgur.com/l8vbADs.png)
**Figure 3.1: Uploading correct file format**

![Imgur](https://i.imgur.com/VzYI5jv.png)
**Figure 3.2: Table updated to reflect 3 new transaction request (batch) in the .txt file**

#### Staff Approval

As an admin/staff user, the staff's primary responsibility is to approve or to reject any new transaction or client registration that do not have a current `APPROVED` or `DECLINED` status. These pending requests will be reflected in the `staff` page where the staff user can then make the appropriate decision to update the request. Once the request has been updated, the respective client accounts should reflect these changes. For example, for pending transaction requests, once the staff has approved the request, both the sender and receiver account should be updated to reflect the new balance amount changes due to the approved request.

![Imgur](https://i.imgur.com/qGqFjYS.png)
**Figure 4.1: Staff Dashboard view with pending transaction request**

![Imgur](https://i.imgur.com/firrSCd.png)
**Figure 4.2: Sender account balance updated**

Once the transaction request has been updated, an approved and declined transaction would turnbe green and red in colour respectively. This ins turno allow clientusers to have a clearer view of their respective account transaction statuses. 

![Imgur](https://i.imgur.com/VfXYAie.png)
**Figure 4.3: Receiver account balance updated**

### Security Enhancement

#### Client-Side Validation

A helper class is written to hold common methods of input sanitation that is used throughout the application whenever an input is processed. This helper class helps to prevent the possible SQL and XSS injections into the application. Because user inputs are thought to be insecure and are used throughout the application whenever a `DAO` object needs to either update or select from the database, placing these methods in a common module allows it to be used throughout the application.

On top of that, javascript is used as a client-side validation to prevent the user from inputting certain values. The javascript class contains methods such as `validateAmount` and `validateEmail` to ensure that the user input allows a restricted pattern of regular expression. 

##### Input Sanitisation

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

#### XSS Injection Prevention

We have to assume that the user input is always not secure and therefore a common and proper way to sanitise these user inputs must be carried out. Since these user inputs are used throughout the application, the best place and way to define these methods is to store them in a common `Helper` class which is placed in the `commons` module. With this common helper class, anywhere a user input is used, one of these methods in this class can be called upon to sanitise the input.

```java
public static String input_normalizer(String input) {  
  input = Normalizer.normalize(input, Normalizer.Form.NFKC);  
 return input.replaceAll("[^\\p{ASCII}]", "");  
}
```

The `intput_normalizer()` function normalises the string and replaces all non ascii characters with nothing. This helps to prevent injection attacks by limiting the scope of what character is allowed.

```java
public static Boolean xss_match(String input) {  
  Pattern pattern = Pattern.compile("<script>");  
  Matcher matcher = pattern.matcher(input);  
 return matcher.find();  
}
```

Next, the `xss_match()` method uses regular expression to find if an existing user input matches the pattern that is in placed. In this case, it checks for any matching pattern to `"<script>"`. The method simply returns a boolean value of `true` or `false` if detected. 



### Race-Condition Prevention

Because the user accounts and account information are maintained by a central database, which serializes all transactions, in these cases no race condition attack is feasible as the database has checks and restrictions in place to prevent such race conditions. However, it is important to note that Coverity Scan did show these areas as vulnerabilities and thus a `synchronised` is added to remove this error. 

## Testing

### Overview

**unit testing** is a software testing method by which individual units of source code, sets of one or more computer program modules together with associated control data, usage procedures, and operating procedures, are tested to determine whether they are fit for use.

A unit test should test functionality in isolation. Side effects from other classes or the system should be eliminated for a unit test, if possible. Hence as such, with this definition in mind, the proper way of writing unit test cases should be applied. Code coverage provides a good insight as to how much testing has been done throughout the application. Several various unit testing techniques were applied, including parameterised testing, etc. 

Although it would be ideal to have complete test coverage, but due to limited time only a few selected test cases were written. However, they were carefully chosen to show how certain test cases should be written. A total of 7 classes were covered. 

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

### Testing for Exception

In the example for the class, `TransactionStatusTest`, to obtain a 100% coverage the following branch conditions must be tested:

1. When input is `null`
2. When input is `APPROVED`
3. When input is `DECLINED`

However, when the input is none of the 3 above mentioned variables, an `IllegalArgumentException` is thus thrown and we have to simulate this in another test case. 

```java
@Test(expected = IllegalArgumentException.class)  
public void of_Error() {  
  TransactionStatus value = TransactionStatus.of("Error");  
}
```

### Mocking Objects for Isolation Testing

When certain methods have dependencies on others, we need to find a way to mock these dependencies so that our unit testing would work as intended. Therefore, in this project, the module `mockito` is used for such purposes. The framework allows the creation of test objects in unit testing. Mock testing frameworks such as `mockito` effectively fakes some external dependencies so that the object that is being tested has a consistent interaction with its outside dependencies. 

On top of that, `mockito` comes with additional features such as the ability to verify the behaviour of the system under test. This is also known as SUT without establisihng the expectations beforehand. Mockito does so by removing the specification of expectations, thus the coupling between test code to system under testing is reduced. Resulting in a simpler way to maintain and implement the unit test cases. For an example, the unit test case using the `mockito` framework has been written for the `DefaultServlet()` java class. 

Before each test methods, a common `run()` instantiates all common declarations. This is to prevent repetitive code writing.

```java 
  @Before  
  public void run() {  
  defaultServlet = new DefaultServlet();  
  request = mock(HttpServletRequest.class, Mockito.RETURNS_DEEP_STUBS);  
  session = mock(HttpSession.class);  
  response = mock(HttpServletResponse.class, Mockito.RETURNS_DEEP_STUBS);  
  }  
```
The objects to be mocked will then be initialised with the mock methods like the ones shown above. 

```java
@Test  
public void sendError() {  
  String msg = "error";  
  when(request.getSession()).thenReturn(session);  
  defaultServlet.sendError(request, msg);  
  verify(request.getSession(), times(1)).setAttribute("req_error",msg);  
}
```

In this unit test case for `sendError()`, we can see that the method `request.getSession()` is called within the function and hence would need to be subjected to mocking. the `thenReturn` method returns a result from mocking that function, i.e. a fake call. When the method to be tested is called, we can then simply verify through the use of `mockito`'s `verify` key function that the `request.getSession().setAttribute(msg)` is called 1 time which means the code has been executed correctly for this method.

```java
@Test  
public void getUserId() {  
  when(request.getSession()).thenReturn(session);  
  when(request.getSession().getAttribute("user_id")).thenReturn(12);  
  
  // calling function to test  
  int result = defaultServlet.getUserId(request);  
  assertEquals(result, 12);  
}
```

In the above test, the `getSession()` method is first mocked followed by the `getUserId()` and once that is done, we can call the function to be tested and `assertEquals` that the resulting output is as expected.

### Black-Box Test Case

| TestID                 | Description                                                                                                                                                                                                                            | Expected Results                                                                                                                                      | Actual Results |
|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| LoginUser1             | Precondition:  User at login Page of Web Application. Currently no user logged in. Waiting for user to key in username and password. User1: guest Password1: Password!12345                                                                     | BankAccount                                                                                                                                           |                |
| LoginUser1.1           | Precondition:  Login Failed. User keys in wrong password and/or username once                                                                                                                                                          | Savings, Investments                                                                                                                                  |                |
| LoginUser1.2           | Precondition: Login Successful. User keys in correct password and username combination.                                                                                                                                                | User is redirected to main dashboard page.                                                                                                            |                |
| Transaction2           | Precondition: User has successfully logged into his/her account. User is at transaction tab. User1: guest Password1: guest Account Balance: 100                                                                                        | User is at the transaction tab of the dashboard page.                                                                                                 |                |
| Transaction2.1         | Precondition:  Transaction2 has passed User1. User1 makes a transaction of -$50.                                                                                                                                                       | User account balance updates to $50. User transaction history updates to include latest transaction. User is brought back to transaction history tab. |                |
| Transaction2.2         | Precondition:  Transaction2 has passed User1. User1 makes a transaction of -$120 and fails.                                                                                                                                            | User receives alert message to state balance not enough. User is taken back to transaction tab.                                                       |                |
| TextFileTransaction3   | Precondition: User has successfully logged into his/her account. User is at transaction tab. User selects text file upload option.  User1: guest Password1: guest Account Balance: 100 Text File Format: .txt                          | User is at the transaction tab of the dashboard page and uploads a .txt file                                                                          |                |
| TextFileTransaction3.1 | Precondition:TextFileTransaction3 has passed User1. User1 uploads a .txt file and is parsed into the browser successfully of transaction -$50.  User1: guest Password1: guestAccount  Balance: 100 Text File Format: .txt Amount: -$50 | User account balance updates to $50. User transaction history updates to include latest transaction. User is brought back to transaction history tab. |                |
| TextFileTransaction3.2 | Precondition:TextFileTransaction3 has passed User1. User1 uploads a .txt file and is parsed into the browser unsuccessfully.  User1: guest Password1: guest Account Balance: 100 Text File Format: .txt Amount: sdfkdjfsdf             | User receives alert message that parsing of .txt file failed. User is brought back to transaction tab.                                                |                

### Penetration Testing
Penetration testing (pen testing) refers to an authorized cyber attack on a given system for the purpose of reporting on security vulnerabilities or code defects. Typically, a pen testing process involves many steps. The first of which is reconnaisance, which could be some form of social engineering on a target group of personnel associated with a certain network or system. The reconnaisance team's objective is to gain personal information about the target personnel, such as birthdates, wedding anniversary dates, childrens' names or the town in which that target personnel grew up in - such information typically could be used for passwords. Once a certain node or workstation has been compromised, the pen testers would scan the network to find out the IP addresses of all devices within the network. The next step is to determine what the server's IP address is. The attackers would then attempt to compromise the server by finding out exploits. In today's context, we have a lot of open source tools available for pen testing.

For this project, we have adopted _OWASP Zap_, a free and user-friendly tool. From their website, we download their installer, https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project and install the software accordingly. Once done, we run a session and enter a url address which we would like to pen test. We then enter http://localhost:8080/sutdbank/login. Zap performs fuzz pen testing and reports vulnerabilities to us.

## Static Analysis Tool

Static analysis refers to the analysis of computer programs without actual execution. Typically, the uses of static analysis range from detecting coding errors to verifying formal methods. For our purposes, static analysis aids us in finding code vulnerabilities and security defects. We have adopted _Coverity Scan_ as our static analysis tool.

### Coverity Scan Analysis

To use Coverity Scan, we download their _zip_ package _cov-analysis-win64-2017.07.zip_ from [https://scan.coverity.com/download?tab=java](https://scan.coverity.com/download?tab=java). The procedure requires two main steps:

1.  Register an account and the project at their website [https://scan.coverity.com/](https://scan.coverity.com/). Users may also login with their _Github_ user credentials.
2.  Build the project log via the said zip package above. This step less trivial, and requires more time to set up. The coverity website instructs us to first to add the \bin directory in the zip package to our _path_. To do this, we run the _Edit the system environment variables_ utility in the _Windows Control Panel_. We would also need to add the _Apache Maven_ tool and _Java Development Kit_(JDK (not JRE)) compiler to to system path. Finally, we access the bankwebapp project directory with a _cmd_ shell, and use `mvn clean` to clean up undesirable units or steps and `cov-build --dir cov-int mvn -DskipTests=true compile` to build logs. A folder _cov-int_ will be added to the project directory. We compress this folder, and upload it to [https://scan.coverity.com/projects/project_link?tab=overview](https://scan.coverity.com/projects/project_link?tab=overview) .

We would then obtain the following summary. [![https://imgur.com/abf1xow](https://imageshack.com/a/img923/9932/iQW5ZD.png)](/DarrenAscione/bankwebapp/blob/master/bankwebapp/image.png) 

The bugs that were discovered are mostly serve bugs that are caused by the opening connection of the databases in the DAO classes. To remedy this, we have to ensure that the connection is closed wherever a database is used. This can be done through the use of the `finally` clause.

```java
finally {  
  closeDb(conn, ps, null);  
  }
```

In the following, we will show some of the summarized defect reports.  
  
![https://imgur.com/MIeAxl8](https://imgur.com/MIeAxl8.png)Amongst our outstanding defects, 3 were considered _high_ severity, and 3 were considered _medium_ severity.  
  
Of the 3 high severity defects, 2 were resource leaks and 1 was a security impact.  
  
![https://imgur.com/MT3i727](https://imgur.com/MT3i727.png)  
  
The following figures demonstrate our code defects.  
  
![https://imgur.com/QnFrTh8](https://imgur.com/QnFrTh8.png)  
  
![https://imgur.com/Qsfz1f7](https://imgur.com/Qsfz1f7.png)  
  
Our medium severity defects were issues pertinent to _null pointer dereferences_.   
  
![https://imgur.com/KZTaN5t](https://imgur.com/KZTaN5t.png)  
  
The following figures would show the actual codes  
  
![https://imgur.com/sLaRaXg](https://imgur.com/sLaRaXg.png)  
  
![https://imgur.com/JQRs7t6](https://imgur.com/JQRs7t6.png)  
  
![https://imgur.com/c0AoFgr](https://imgur.com/c0AoFgr.png)  
  
Once again, we made an attempt to solve even more of these defects. We submitted a new build once again to yield  
  
![https://imgur.com/5ztfMVl](https://imgur.com/5ztfMVl.png)  
  
Our final submission has 2 defects left. Unfortunately, due to the limitation of time, the full report would only be out after the submission deadline of this project. This remaining 2 defects are mentioned here for reporting purposes.  

### Other Tools for Integration Testing

#### Travis CI

![Imgur](https://i.imgur.com/pq2TZ7p.png)

[Travis CI](https://travis-ci.org/profile/DarrenAscione) is a hosted, distributed continuous integration service used for automating build runs and test runs for projects hosted on GitHub. 

Through the inclusion of a `.travis.yml` file to the root directory, Travis CI will check out the relevant branch and run the specific commands specified in `.travis.yml`. When that process has been completed, the developer will then be notified if a build has been successful or is in failure. Travis CI is a useful tool that updates automatically and can be ran on the mobile phone without the need of a computer. It also runs automatically whenever a new `git push` has been initiated. With Travis CI, developers will know if the code can be build successfully and proper integration has been done with Unit testing.

The badge at the top of the `readme.md` file shows that it is build successful currently and will change depending on the current build status.

[![Build Status](https://travis-ci.org/DarrenAscione/bankwebapp.svg?branch=master)](https://travis-ci.org/DarrenAscione/bankwebapp)
  
#### Coveralls Test Coverage

To show case how much test coverage was done for the project, coveralls.io was used. _Coveralls_ takes the pain out of tracking your code coverage. Know where you stand with your untested code. 

Cove coverage is done through the use of Cobertura. To retrieve the list:

```bash
mvn cobertura:cobertura coveralls:report
```



## Conclusion

We have worked on the distributed SUTD Bank Webapp, completed the main requisite functionalities and have implemented security features to a reasonable. Level. We have also shown our USE Case and USE diagrams. We have also reduced our defects down from 11 to 2.


