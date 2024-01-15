# IEEE-Tempate
IEEE System Requirements Specification Template

# Software Requirements Specification
## For  <project name>
Version 1.0 approved
Prepared by <author>
<organization>
<date created>

Table of Contents
=================
  * [Revision History](#revision-history)
  * [Introduction](#1-introduction)
    * 1.1 [Purpose](#11-purpose)
    * 1.2 [Document Conventions](#12-document-conventions)
    * 1.3 [Intended Audience and Reading Suggestions](#13-intended-audience-and-reading-suggestions)
    * 1.4 [Product Scope](#14-product-scope)
    * 1.5 [References](#15-references)
  * [Overall Description](#overall-description)
    * 2.1 [Product Perspective](#21-product-perspective)
    * 2.2 [Product Functions](#22-product-functions)
    * 2.3 [User Classes and Characteristics](#23-user-classes-and-characteristics)
    * 2.4 [Operating Environment](#24-operating-environment)
    * 2.5 [Design and Implementation Constraints](#25-design-and-implementation-constraints)
    * 2.6 [User Documentation](#26-user-documentation)
    * 2.7 [Assumptions and Dependencies](#27-assumptions-and-dependencies)
  * [External Interface Requirements](#external-interface-requirements)
    * 3.1 [User Interfaces](#31-user-interfaces)
    * 3.2 [Hardware Interfaces](#32-hardware-interfaces)
    * 3.3 [Software Interfaces](#33-software-interfaces)
    * 3.4 [Communications Interfaces](#34-communications-interfaces)
  * [System Features](#system-features)
    * 4.1 [Authentication](#41-authentication)
    * 4.2 [Dashboard](#42-dashboard)
        * 4.2.1 [User Profile](#421-user-profile)
        * 4.2.2 [Upload and View Submissions](#422-upload-and-view-submissions)
        * 4.2.3 [Interaction With Reviewers](#423-interaction-with-reviewers)
        * 4.2.4 [Notification System](#424-notification-system)
  * [Other Nonfunctional Requirements](#other-nonfunctional-requirements)
    * 5.1 [Performance Requirements](#51-performance-requirements)
    * 5.2 [Safety & Security Requirements](#52-safety-and-security-requirements)
    * 5.3 [Software Quality Attributes](#53-software-quality-attributes)
        * 5.3.1 [Reliability](#531-reliability)
        * 5.3.2 [Adaptability](#532-adaptability)
        * 5.3.3 [Maintainability](#533-maintainability)
        * 5.3.4 [Portability](#534-portability)
        * 5.3.5 [Cost Effectiveness](#535-cost-effectiveness)

## Revision History
| Name | Date    | Reason For Changes  | Version   |
| ---- | ------- | ------------------- | --------- |
|      |         |                     |           |
|      |         |                     |           |
|      |         |                     |           |

## 1. Introduction
### 1.1 Purpose 
Identify the product whose software requirements are specified in this document, including the revision or release number. Describe the scope of the product that is covered by this SRS, particularly if this SRS describes only part of the system or a single subsystem.

### 1.2 Document Conventions
Describe any standards or typographical conventions that were followed when writing this SRS, such as fonts or highlighting that have special significance. For example, state whether priorities  for higher-level requirements are assumed to be inherited by detailed requirements, or whether every requirement statement is to have its own priority.
### 1.3 Intended Audience and Reading Suggestions
Describe the different types of reader that the document is intended for, such as developers, project managers, marketing staff, users, testers, and documentation writers. Describe what the rest of this SRS contains and how it is organized. Suggest a sequence for reading the document, beginning with the overview sections and proceeding through the sections that are most pertinent to each reader type.
### 1.4 Product Scope
Provide a short description of the software being specified and its purpose, including relevant benefits, objectives, and goals. Relate the software to corporate goals or business strategies. If a separate vision and scope document is available, refer to it rather than duplicating its contents here.
### 1.5 References
List any other documents or Web addresses to which this SRS refers. These may include user interface style guides, contracts, standards, system requirements specifications, use case documents, or a vision and scope document. Provide enough information so that the reader could access a copy of each reference, including title, author, version number, date, and source or location.

## Overall Description
### 2.1 Product Perspective
The NITCONF website is an integral part of the NITCONF conference management system. It serves as a platform for authors to submit papers for evaluation. The product is a self-contained system designed specifically for handling the submission and review process of conference papers. It does not replace any existing systems but rather enhances the efficiency of the conference management process.

The major components of the overall system include the Author Module (Module 1), Reviewer Module (Module 2), Program Committee Module (Module 3), and the Notification System. The interaction between these modules will be facilitated through well-defined interfaces.
### 2.2 Product Functions
* User registration and login functionality for authors.
* Submission of abstracts (papers in PDF format) by registered authors.
* Deadline enforcement to restrict abstract submission after the specified date.
### 2.3 User Classes and Characteristics
1. Authors: Users who submit papers for evaluation.
    * Characteristics:
    Authors may vary in technical expertise.
    All authors must register and log in to the system.
    The primary interaction for authors involves submitting abstracts.
### 2.4 Operating Environment
The software will operate in a web-based environment.

* Hardware Platform:
The system should be compatible with standard web browsers on various devices.
* Operating System:
Compatible with major operating systems like Windows, macOS, Android, iOS and Linux.
* Software Components:
The system should coexist peacefully with common web technologies (e.g., Apache Tomcat).
### 2.5 Design and Implementation Constraints
* Development must adhere to the Spring Boot framework using Java.
* The system must comply with corporate policies regarding data security and privacy.
* Utilization of specific technologies, such as Spring Security for authentication.
### 2.6 User Documentation
User documentation components will include:

* User manuals for authors.
* On-line help resources.
* Tutorials guiding authors through the submission process.
### 2.7 Assumptions and Dependencies
1. Assumptions:
    * Authors have access to a device with internet connectivity for submission.
    * Users will follow the registration and submission guidelines provided.
2. Dependencies:
    * Dependency on external libraries and frameworks compatible with Spring Boot.
    * Integration with a notification system for sending alerts to authors.
## External Interface Requirements
### 3.1 User Interfaces
1. Logical Characteristics:
The user interface for the NITCONF website's Author Module will be a web-based application accessible through standard web browsers. Key elements of the user interface include:
    * User Registration and Login Screens: Simple forms for user registration and login.
    * Dashboard: A personalized dashboard displaying submission status, deadlines, and other relevant information.
    * Abstract Submission Form: An intuitive form for authors to submit papers in PDF format.
    * Deadline Notification: Clear notification regarding the submission deadline.
2. GUI Standards and Constraints:
    * The user interface will follow standard web design principles.
    * GUI will be responsive, adapting to different screen sizes.
    * Submission forms will enforce PDF format for paper submissions.
### 3.2 Hardware Interfaces
The NITCONF website will interact with standard hardware components, ensuring compatibility with various devices. Key hardware interfaces include:
* Supported Devices: The system should be compatible with desktops, laptops, tablets, and smartphones.
* Internet Connectivity: Authors require internet access for registration and paper submission.

### 3.3 Software Interfaces
Connections with Other Software Components:
The NITCONF website will interface with specific software components, ensuring seamless integration and data exchange.
* Database: Interaction with a relational database system for storing user data and submitted papers.
* Spring Boot Framework: Utilization of the Spring Boot framework for Java-based application development.
* Spring Security: Integration for secure user authentication and authorization.
* User Registration Data: Transfer of user registration information to the database.
* Submitted Papers: Storage of papers in PDF format in the database.
Communication Nature:
* REST APIs: Communication between frontend and backend components through RESTful APIs.
* Data Encryption: Use of HTTPS for secure data transfer.
### 3.4 Communications Interfaces
#### Communication Functions:
The NITCONF website requires specific communication functions to facilitate its operation.

* Email Notifications: Sending notifications, including registration confirmation and deadline reminders via email.
* Web Browser Communications: Interaction with web browsers for user access and data submission.
#### Communication Standards:
* SMTP Protocol: For sending email notifications.
* HTTP/HTTPS Protocols: For web browser communications.
* Data Transfer Rates: Standard internet data transfer rates.
#### Security and Encryption:
* HTTPS: Ensuring secure communication with encryption.
* Data Synchronization: Real-time synchronization for timely notifications.
## System Features
The following features are made available to the users (authors).

### 4.1 Authentication
* The user has the ability to create an account, and login with their credentials.
* The user can request for a password reset, via the `forgot password` option, if required.

### 4.2 Dashboard
The following features are available as part of the main dashboard presented to the user.

#### 4.2.1 User Profile
* The user can view / update their details in the profile section

#### 4.2.2 Upload And View Submissions
* The user is provided with the current status of their latest submission, if any.
* The user is then able to re-submit their abstract, with the changes suggested by reviewers.

#### 4.2.3 Interaction With Reviewers
* The user is given a list of comments provided by the reviewer assigned.
* The user is also provided with contact information of the reviewer
* The comments are listed along with the version of the submission, the comment was targeted to.

#### 4.2.4 Notification System
* The user is sent urgent notifications via Email in events such as a new comment, acceptance / rejection of a submission, etc.


## Other Nonfunctional Requirements
### 5.1 Performance Requirements
This system's purpose is to give users the ability to upload their abstracts in the form of PDF documents. The ssytem should be able to handle large number of simultaneously logged in users and support high speed data transfers for swift and error-free upload of data.

### 5.2 Safety and Security Requirements
The system ensures data security through a dedicated database. Regular users can view information but lack the authorization to make any changes. 

In terms of security, the system is meticulously designed to shield users from any potential harm. It employs strict access controls and user permissions, guaranteeing the safety and integrity of data. Users can interact with the system with confidence, as it effectively safeguards their information against any threats or unauthorized access.

### 5.3 Software Quality Attributes
#### 5.3.1 Reliability
* The system will not lag and will provide instant and accurate results to all the users.

#### 5.3.2 Adaptability
* The system is open source. The system can be extended to other organizations as well.

#### 5.3.3 Maintainability
* In case of errors, it can be rectified by any developer due to the ease of maintenance.

#### 5.3.4 Portability
* The system can be accessed on any smartphone or laptop.

#### 5.3.5 Cost-effectiveness
* This system is less in cost and bearable to any organization.
