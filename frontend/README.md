
# Contact Hub Front End

## Run Locally

Clone the project

```bash
  git clone https://github.com/rumesha89/contact-hub.git
```

Go to the project directory

```bash
  cd frontend
```

Install dependencies

```bash
  yarn install
```

Start the server

```bash
  yarn dev
```


## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)


## Assumptions and User Stories

### User Story 1 - View All Existing Contacts
- Can be accessed with a public url
- All the contacts in the system will be shown
- Pagination is yet to be added (skipping due to time constraints)
- Name, email, company name, website, phone number will be displayed


### User Story 2 - Filter Contacts
- Can accessed by a public url
- Once typed 3 or more characters in the search box search will be triggered to backend api
- Only filterable by name
- Once cleared all the contacts will be shown


### User Story 3 - Create Contacts
- Can be accessed with the button in the list UI called ‘Create New’
- New page with form to add email, name, company name, website and phone number will be displayed
- Email, company name and name is required
- Have validations for required fields and emal
- After submit will call POST api 
- Success/Error messages shown
- Upon success will be routed back to list page


### User Story 4 - Update Contacts
- Cannot be accessed publicly
- Unauthenticated users will be thrown to Login page
- Once successfully logged in will be able to view/edit contact
- Can only edit name, website and phone number only
- Have validations for required fields and emal
- Once submitted PUT request will be triggered with token, success/error messages will be shown 
- Upon success will be routed back to list page



## Test Scenarios

* Have written front end tests for below scenarios

- when get All contacts returns all the contacts merged from partner and database
- when get All contacts if partner api fails send all from database
- when get All contacts if datanase contacts empty send all from partner
- when get All contacts nothing in partner and Database return empty list
- when get contact by id send correct contact for that id
- when get contact by id if partner api fails send contact from database
- when get contact by id if partner api sends empty then send contact from database
- when get contact by id if a contact not present under that id send EntityNotFound
- when create contact then create in partner api then database
- when create contact if partner api fails then return error
- when create contact if already in the system return error
- when create contact if validations fails send error with error description
- when update contact then update in partner api then database
- when update contact if partner api fails then return error
- when update contact if not found in the system return error
- when update contact if validations fails send error with error description

- test third party client 
    - convert to correct entity and to request
    - handle errors from 3rd part api
- Cannot be accessed by a public unauthenticated user
- Have to call with a token obtained from /login api in the header
- Can only edit name, website and phone number at the moment
- If the contact requested is not present in remote, will accessed in database
- If not in database too, will return entity not found exception with a detailed error message
- Contact entity validations will apply to request body