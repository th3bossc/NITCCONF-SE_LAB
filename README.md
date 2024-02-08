## SE LAB PROJECT 1 - NITCONF (Author Module)
- To see the working prototype, [click here](https://nitconf-se-lab.vercel.app/)

### Overview
- NITCONF is a conferencing website. This repository contains the implementation of the first module of the complete site, which is the author module.
- The Author module is the interface provided to any user who wants to create and submit their abstracts and take part in the conference
- This module, although interlinked with the reviewer module and the program committee module, doesn't provide implementation for either of them

### Functionality
**Authentication** - The user is able to login or register using their email, password as the authentication credentails. 
**Create, delete and update abstracts** - The user is able to create abstracts, upload the data in `pdf` format, view the reviewers comments on their abstracts and make adjustments accordingly
**View abstract status** - The user is able to see if the reviewer has `ACCEPTED`, `REJECTED` or if the review is `PENDING` along with their abstract details directly in their dashboard
**Edit Profile** - The user is able to edit data in their profile, except for the email
**Email Notification** - All important activities done by the user, as well as communication between reviewer and the user is done through the help of email notifications


### Documentation & Reference
- [System Requirement Specification](./docs/SRS.md)
- [Api Documentation](./docs/apidoc.md)
- [Try out the Api](https://nitconf-backend.ashyforest-8ec21cce.australiaeast.azurecontainerapps.io/api/docs/swagger-ui/index.html) 
- [Backend installtion](./backend/nitconf/README.md)
- [Frontend installtion](./frontend/README.md)


### Authors
- [Diljith P D](https://th3bossc.github.io/Portfolio)
- [Ashwin Suresh Babu](https://github.com/Xolinnax)
- [Sreeshma Sangesh](https://github.com/Sreeshu123)
