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
    Returns all the sessions pertaining to the user

## 4.2 POST/api/session
    Input: {token, String:title, String:description, String:language, Level:level, Status:status, List<String>:tags}
    Output: {<Session>}
    Creates a new session for the user

## 4.3 PUT/api/session/{id}
    Input: {token, String:id, String:title, String:description, String:language, Level:level, Status:status, List<String>:tags}
    Output: {<Session>}
    Updates the details for the session with given id 

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

## 4.7 PUT/api/session/status/accepted/{id}
    Input: {token, String:id}
    Output: {"UPDATED STATUS TO ACCEPTED"}
    Sets the status of session with given id as ACCEPTED

## 4.8 PUT/api/session/doc/rejected/{id}
    Input: {token, String:id}
    Output: {"UPDATED STATUS TO REJECTED"}
    Sets the status of session with given id as REJECTED

# 5-TAG

## 4.1 GET/api/tags/{title}
    Input: {String:title}
    Output: {List<Session>}
    Returns all the sessions containing the title with given title

## 4.2 GET/api/tags
    Input: {}
    Output: {List<Tag>}
    Lists all the pre-defined tags

## 5.3 POST/api/tags
    Input: {String:title}
    Output: {<Tag>}
    Creates the tag with the given title


