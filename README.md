## Challenge - Simple Job Application
This backend that powers Simple Job Application (BK Techouse Coding exercise).
## Technologies
-	Backend : Spring boot framework 
-	Frontend : angular 12
-	Database: Mysql 
-	Docker server for building and running application
- Integration testing using JUNIT
## Available end points
### Job Listed (GET)
Link: http://localhost:8080/api/job/jobListed
### Find Job Detail (GET)
Link: http://localhost:8080/api/job/jobDetails/{jobId}
### Send application (POST)
Required params are: 
User: (firstName, lastName, email, phoneNumber), Cv, JobId
Link: http://localhost:8080/api/job/applyWithoutLogin
### List Of applicants (GET)
Link: http://localhost:8080/api/job/applicants
### View applicant By Id: (GET)
Link: http://localhost:8080/api/job/applicantDetails/{id}
### Drop Application: (PUT)
Required: status is Dropped, Application Id and Comment
Link: http://localhost:8080/api/job/dropApplication/{applicationId}
### Pass Application (PUT)
Required: status is Passed and Application Id 
Link: http://localhost:8080/api/job/passApplication/{applicationId}
### Download Cv(GET)
Required: file Name
Link: http://localhost:8080/api/job/download/{fileName}

