
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
(some test cases are not covered due to time constraints)

- User Can login successfully
- password will be masked
- If invalid credentials, show error message
- List displays correctly from api response
- card displays all the data returned from list api
- can click on a contact card
- once clicked on a card will route to view details
- card shows an edit button if authenticated only
- once edit button clicked will route to edit screen
- user will be able to edit name, website, phone number
- accessing edit through url will be blocked and will route to login page 
- validations triggered accordingly
- once submitted will show correct success/error messages
- once successfull will return to list page
- list page shows a create button
- once clicked will be routed to create page
- correct validations applied for required fields and email
- once submitted will show correct error/success messages
- once successfull will return to list page
- routes are correctly blocked
- test if api calls are correctly fires with correct inputs
- Check filter by name triggers only after 3 characters

