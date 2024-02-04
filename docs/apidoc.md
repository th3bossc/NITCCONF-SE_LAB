# NITCONF - SE LAB MODULE 1 

# API DOCUMENTATION

TABLE OF CONTENTS
-------------------

*1-AUTHENTICATION
*2-PROFILE
*3-REVIEW
*4-SESSION
*5-TAG

# 4-SESSION

## 4.1 GET/api/session
    Input: {token}
    Output: {List<Session>}
    Returns all the sessions pertaining to the user.

## 4.2 POST/api/session
    Input: {token, String:title, String:description, String:language, Level:level, Status:status, List<String>:tags}
    Output: {<Session>}
    Creates and returns a new session to the user.

## 4.3 PUT/api/session/{id}
    Input: {token, String:id, String:title, String:description, String:language, Level:level, Status:status, List<String>:tags}
    Output: {<Session>}
    Returns an updated session for the session with given id to the user.

## 4.4 PUT/api/session/doc/{id}
    Input: {token, String:id, Byte:File}
    Output: {}
    Uploads the PDF file to the session with given id

## 4.5 GET/api/session/doc/{id}
    Input: {token, String:id}
    Output: {List<File>}
    Returns all the PDF files of the session with given id

## 4.6 GET/api/session/{id}
    Input: {token, String:id}
    Output: {<Session>}
    Returns the session with given id



