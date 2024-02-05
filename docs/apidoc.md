# NITCONF - SE LAB MODULE 1 

# API DOCUMENTATION

TABLE OF CONTENTS
-------------------

* 1-AUTHENTICATION
    * 1.1 Login
    * 1.2 Register
* 2-PROFILE
    * 2.1 Get current profile 
    * 2.2 Update profile 
    * 2.3 Get any user profile
    * 2.4 Verify email
    * 2.5 Resend email verification code
    * 2.6 Request forgot password mail
    * 2.7 Update password
* 3-REVIEW
    * 3.1 Get Reviews for Document
    * 3.2 Get Review
    * 3.3 Create Review
* 4-SESSION
    * 4.1 Get all sessions
    * 4.2 Create new session
    * 4.3 Get session
    * 4.4 Update session
    * 4.5 Upload PDF
    * 4.6 Get all files of the session
    * 4.7 Update status to ACCEPTED
    * 4.8 Update status to REJECTED
* 5-TAG
    * 5.1 Get sessions with given tag
    * 5.2 Get all tags
    * 5.3 Create new tag


# 1 AUTHENTICATION

## 1.1 Login
    [POST] /api/auth/login 
    To login in to the user account
    Body: { email: string, password: string }
    Response: { token: string }

## 1.2 Register
    [POST] /api/auth/register 
    To create an account    
    Body: { firstName:string,  lastName:string, phoneNumber: string, email: string, password: string }
    Response: { token: string }

# 2 PROFILE

## 2.1 Get current profile 
    [GET] /api/profile
    To display the profile details of the current user
    Headers: { Authorization: "Bearer <token>" }
    Body: { check ProfileRequest.java }
    Response: success message

## 2.2 Update profile 
    [PUT] /api/auth/profile 
    To update the profile details of the current user
    Headers: { Authorization: "Bearer <token>" }
    Body: { email: string, password: string }
    Response: { token: string }

## 2.3 Get any user profile
    [GET] /api/profile/<id:string>
    To display the profile details of any user
    (Accessible only to users with Roles of either REVIEWER or PROGRAM_COMMITTEE)
    Headers: { Authorization: "Bearer <token>" }
    Body: { None }
    Response: { Refer user object}

## 2.4 Verify email
    [GET] /api/email/verify/<token:string>
    To verify the email of the user
    Headers: None
    Body: None
    Response: "Email verified successfully"

## 2.5 Resend email verification code
    [POST] /api/email/resend
    To resend email verification code to given email id
    Headers: None
    Body: { email: string }
    Response: "Email sent successfully"

## 2.6 Request forgot password mail
    [POST] /api/email/reset
    To reset password(FORGOT password)
    Headers: None
    Body: { email: string }
    Response: "Email sent successfully"

## 2.7 Update password
    [PUT] /api/email/reset/<token: string>
    To update the password
    Headers: None
    Body: { password: string }
    Response: "Password reset successfully"

# 3 REVIEW

## 3.1 Get Reviews for Document
    [GET] /api/review/doc/<id:string>
    To obtain reviews for document with given doc id
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string }
    Response: List of reviews of document doc ID

## 3.2 Get Review
    [GET] /api/review/<id:string>
    To obtain review with given id
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string }
    Response: Review pertaining to the ID

## 3.3 Create Review
    [POST] /api/review/<id:string>
    To create a review for ducument with given id
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string }
    Response: "Review created"

# 4 SESSION

## 4.1 Get all sessions
    [GET] /api/session
    Returns all the sessions pertaining to the user
    Headers: { Authorization: "Bearer <token>" }
    Body: { None }
    Response: { List of sessions(Refer session object) }

## 4.2 Create new session
    [POST] /api/session    
    Creates a new session for the user
    Headers: { Authorization: "Bearer <token>" }
    Body: { check SessionRequest.java }
    Response: { Refer session object }

## 4.3 Get session
    [GET] /api/session/<id:string>
    Returns the session with given id
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string }
    Response: {Refer session object}

## 4.4 Update session
    [PUT] /api/session/<id:string>
    Updates the details for the session with given id 
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string, check SessionRequest.java }
    Response: { Refer session object }

## 4.5 Upload PDF
    [PUT] /api/session/doc/<id:string>
    Uploads the PDF file to the session with given id
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string, file: PDF }
    Response: { None }

## 4.6 Get all files of the session
    [GET] /api/session/doc/<id:string>
    Returns all the PDF files of the session with given id
    Headers: { Authorization: "Bearer <token>" }
    Body: { id: string }
    Response: { List of files }

## 4.7 Update status to ACCEPTED
    [PUT] /api/session/status/accepted/<id:string>
    Sets the status of session with given id as ACCEPTED
    Headers: { Authorization: "Bearer <token>" }
    Body: {  id: string}
    Response: "UPDATED STATUS TO ACCEPTED"

## 4.8 Update status to REJECTED
    [PUT] /api/session/doc/rejected/<id:string>
    Sets the status of session with given id as REJECTED
    Headers: { Authorization: "Bearer <token>" }
    Body: {  id: string}
    Response: "UPDATED STATUS TO REJECTED"

# 5 TAG

## 5.1 Get sessions with given tag
    [GET] /api/tags/<title:string>
    Returns all the sessions containing the tag with given title
    Headers: { Authorization: "Bearer <token>" }
    Body: { title: string }
    Response: { List of sessions(Refer session object) }

## 5.2 Get all tags
    [GET] /api/tags
    Lists all the pre-defined tags
    Headers: { Authorization: "Bearer <token>" }
    Body: { None }
    Response: { List of tags(Refer tag object) }

## 5.3 Create new tag
    [POST] /api/tags
    Creates the tag with the given title
    Headers: { Authorization: "Bearer <token>" }
    Body: { check TagRequest.java }
    Response: { Refer tag object }


