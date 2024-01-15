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
    * 4.1 [System Feature 1](#41-system-feature-1)
    * 4.2 [System Feature 2 (and so on)](#42-system-feature-2-and-so-on)
  * [Other Nonfunctional Requirements](#other-nonfunctional-requirements)
    * 5.1 [Performance Requirements](#51-performance-requirements)
    * 5.2 [Safety Requirements](#52-safety-requirements)
    * 5.3 [Security Requirements](#53-security-requirements)
    * 5.4 [Software Quality Attributes](#54-software-quality-attributes)
    * 5.5 [Business Rules](#55-business-rules)
  * [Other Requirements](#other-requirements)
* [Appendix A: Glossary](#appendix-a-glossary)
* [Appendix B: Analysis Models](#appendix-b-analysis-models)
* [Appendix C: To Be Determined List](#appendix-c-to-be-determined-list)




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
Compatible with major operating systems like Windows, macOS, and Linux.
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
This template illustrates organizing the functional requirements for the product by system features, the major services provided by the product. You may prefer to organize this section by use case, mode of operation, user class, object class, functional hierarchy, or combinations of these, whatever makes the most logical sense for your product.
### 4.1 System Feature 1
Don’t really say “System Feature 1.” State the feature name in just a few words.
4.1.1   Description and Priority
 Provide a short description of the feature and indicate whether it is of High, Medium, or Low priority. You could also include specific priority component ratings, such as benefit, penalty, cost, and risk (each rated on a relative scale from a low of 1 to a high of 9).
4.1.2   Stimulus/Response Sequences
 List the sequences of user actions and system responses that stimulate the behavior defined for this feature. These will correspond to the dialog elements associated with use cases.
4.1.3   Functional Requirements
 Itemize the detailed functional requirements associated with this feature. These are the software capabilities that must be present in order for the user to carry out the services provided by the feature, or to execute the use case. Include how the product should respond to anticipated error conditions or invalid inputs. Requirements should be concise, complete, unambiguous, verifiable, and necessary. Use “TBD” as a placeholder to indicate when necessary information is not yet available.
 
 Each requirement should be uniquely identified with a sequence number or a meaningful tag of some kind.

### 4.2 System Feature 2 (and so on)

## Other Nonfunctional Requirements
### 5.1 Performance Requirements
If there are performance requirements for the product under various circumstances, state them here and explain their rationale, to help the developers understand the intent and make suitable design choices. Specify the timing relationships for real time systems. Make such requirements as specific as possible. You may need to state performance requirements for individual functional requirements or features.
### 5.2 Safety Requirements
Specify those requirements that are concerned with possible loss, damage, or harm that could result from the use of the product. Define any safeguards or actions that must be taken, as well as actions that must be prevented. Refer to any external policies or regulations that state safety issues that affect the product’s design or use. Define any safety certifications that must be satisfied.
### 5.3 Security Requirements
Specify any requirements regarding security or privacy issues surrounding use of the product or protection of the data used or created by the product. Define any user identity authentication requirements. Refer to any external policies or regulations containing security issues that affect the product. Define any security or privacy certifications that must be satisfied.
### 5.4 Software Quality Attributes
Specify any additional quality characteristics for the product that will be important to either the customers or the developers. Some to consider are: adaptability, availability, correctness, flexibility, interoperability, maintainability, portability, reliability, reusability, robustness, testability, and usability. Write these to be specific, quantitative, and verifiable when possible. At the least, clarify the relative preferences for various attributes, such as ease of use over ease of learning.
### 5.5 Business Rules
List any operating principles about the product, such as which individuals or roles can perform which functions under specific circumstances. These are not functional requirements in themselves, but they may imply certain functional requirements to enforce the rules.

## Other Requirements
Define any other requirements not covered elsewhere in the SRS. This might include database requirements, internationalization requirements, legal requirements, reuse objectives for the project, and so on. Add any new sections that are pertinent to the project.
### Appendix A: Glossary
Define all the terms necessary to properly interpret the SRS, including acronyms and abbreviations. You may wish to build a separate glossary that spans multiple projects or the entire organization, and just include terms specific to a single project in each SRS.
### Appendix B: Analysis Models
Optionally, include any pertinent analysis models, such as data flow diagrams, class diagrams, state-transition diagrams, or entity-relationship diagrams.
### Appendix C: To Be Determined List
Collect a numbered list of the TBD (to be determined) references that remain in the SRS so they can be tracked to closure. 