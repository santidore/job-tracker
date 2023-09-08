
# Job Tracker API

Job Tracker is an API that allows you to manage job applications and associated companies. With this API, you can track job applications, update their status, create new applications, and manage company information.


# Endpoints


## Base URL

https://job-tracker-production-c069.up.railway.app/

You can append the specific endpoints below to this base URL to access different functionalities.

## Job Applications

### Create New Job Application

**URL:** `POST /jobapplication/create`

**Description:** Creates a new job application.

**Request Parameters:** You must provide job application details in the request body in JSON format, including position, company name, and recruiter.

**Successful Response:** Returns a success message.

**Error Response:** Returns an error message if the request data is invalid or if an issue occurs during creation.

---
### Delete Job Application

**URL:** `DEL /jobapplication/delete/{id}`

**Description:** Deletes a job application.

**Request Parameters:** You must provide the ID of the job application you want to delete in the URL.

**Successful Response:** Returns a success message.

**Error Response:** Returns an error message if the ID is invalid or if the job application was not found.

---

### Update Job Application

**URL:** `PATCH /jobapplication/update/{id}`

**Description:** Updates an existing job application with new details.

**Request Parameters:** You must provide the application ID in the URL and the fields you want to update in the request body in JSON format.

**Successful Response:** Returns a success message.

**Error Response:** Returns an error message if the request data is invalid or if an issue occurs during the update.

---

### Change Job Application Status

**URL:** `PUT /jobapplication/changestatus/{id}`

**Description:** Changes the status of a specific job application.

**Request Parameters:** You must provide the application ID in the URL and the new status in the request body in JSON format.

**Successful Response:** Returns a success message.

**Error Response:** Returns an error message if the status cannot be changed or if the request data is invalid.

---

### List All Job Applications

**URL:** `GET /jobapplication/list`

**Description:** Fetches a list of all job applications registered in the database.

**Successful Response:** Returns a list of job applications in JSON format.

**Error Response:** Returns an error message if no job applications are found.

---

### List Job Applications by Company

**URL:** `POST /jobapplication/listbycompany/`

**Description:** Retrieves a list of job applications associated with a specific company.

**Request Parameters:** You must provide the company name in the request body in JSON format.

**Successful Response:** Returns a list of job applications in JSON format.

**Error Response:** Returns an error message if no job applications are found for the specified company.

---

## Companies

### Create New Company

**URL:** `POST /company/create`

**Description:** Creates a new company.

**Request Parameters:** You must provide the company name in the request body in JSON format.

**Successful Response:** Returns a success message.

**Error Response:** Returns an error message if the request data is invalid or if an issue occurs during creation.

---
### Delete Company

**URL:** `DEL /company/delete/{id}`

**Description:** Deletes a company.

**Request Parameters:** You must provide the ID of the company you want to delete in the URL.

**Successful Response:** Returns a success message.

**Error Response:** Returns an error message if the ID is invalid or if the company was not found.

---
### List All Companies

**URL:** `GET /company/list`

**Description:** Retrieves a list of all companies registered in the database.

**Successful Response:** Returns a list of companies in JSON format.

**Error Response:** Returns an error message if no companies are found.








# Request and Response Examples

#### Example of Creating a Job Application:

**Request:**

```json
POST /jobapplication/create

{
    "position": "Software Developer",
    "companyName": "My Company",
    "recruiter": "John Recruiter"
}

```

Successful Response:
```json
Job application created successfully.
```
#### Example of Updating a Job Application:

**Request:**
```json
PATCH /jobapplication/update/131ead8d-0e52-48dc-bb9f-4144769da67f

{
    "position": "Updated Position",
    "recruiter": "Updated Recruiter"
}
```

Successful Response:

```json
Job application updated successfully.
```
## Common Errors
If a request contains incorrect or incomplete data, the API will respond with a detailed error message.

If you try to change the status of a job application with an invalid status, the API will respond with an error message.

If an internal error occurs during the execution of a request, the API will respond with a server error message.
## Future Development

I am committed to continuously improving the Job Tracker API. In the future, I plan to implement user authentication and login features to enhance security and provide user-specific functionality. This will enable users to securely access and manage their job applications and company data.

Stay tuned for updates and new features as I work to make Job Tracker even more useful.
