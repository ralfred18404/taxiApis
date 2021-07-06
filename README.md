## Challenge - Simple Job Application
- This challenge is contain with java backend  and angular frontend design that powers simple job application 
(BK Techouse Coding exercise).
- Below are API used to test data from server side

## Technology used
-	Backend : Spring boot framework 
-	Frontend : angular 12
-	Database: Mysql 
-	Docker server for building and running application
- Integration testing using JUNIT

## Available end points
### Job Listed (GET)
- Link: http://localhost:8080/api/job/jobListed
### Find Job Detail (GET)
- Link: http://localhost:8080/api/job/jobDetails/{jobId}
### Send application (POST)
- Required params are: 
- User: (firstName, lastName, email, phoneNumber), Cv, JobId
- Link: http://localhost:8080/api/job/applyWithoutLogin
### List Of applicants (GET)
- Link: http://localhost:8080/api/job/applicants
### View applicant By Id: (GET)
- Link: http://localhost:8080/api/job/applicantDetails/{id}
### Drop Application: (PUT)
- Required: status is Dropped, Application Id and Comment
- Link: http://localhost:8080/api/job/dropApplication/{applicationId}
### Pass Application (PUT)
- Required: status is Passed and Application Id 
- Link: http://localhost:8080/api/job/passApplication/{applicationId}
### Download Cv(GET)
- Required: file Name
- Link: http://localhost:8080/api/job/download/{fileName}

#### That the main activities needed on the project and others are non functional requirement like authentication, authorization or user account creation, etc

