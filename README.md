# Wishlist Service

## Participants:
- Mentor: Suraj Singh
- Deepika Alavala
- Vaishali Walia
- Manisha Sinha

## Details:
Two users, namely, creator and fullfiller.
- Creator: Can create a wishlist and share it to his friends.
- Fullfiller: Can buy the products from his friend's(creator) wishlist.

## Setup Instructions:
- Download and install the IDE, [Eclipse for Java EE Developers](https://www.eclipse.org/downloads/).
- Create a maven project in the IDE.
- Add the required dependencies in the file 'pom.xml'.
- The dependencies added are: mysql-connector-java (for jdbc driver), jersey (for RESTful service development), javax (for a package of standard Java extensions), hiberante (for hibernate extensions). These dependencies scripts can be obtained from [the website](https://mvnrepository.com/).
- Download the [Apache Tomcat v7.0 Server](https://tomcat.apache.org/download-70.cgi): Preferences -> Server -> Runtime Environment -> Add -> Select Apache/Apache Tomcat v7.0.
- Setup the tomcat server for the project: Build Path -> Configure Build Path -> Server -> Select Tomcat v7.0 Server.
- Place the Backend API services in src/main/java/org/acms/WishlistService/services.
- Place the Backend Data Access Object files in src/main/java/org/acms/WishlistService/dao.
- Place the Backend Model Class files in src/main/java/org/acms/WishlistService/model.
- Place the mapping (model class to respective table mapping) files in src/main/resources.
- Place the Front-end HTML scripts in src/main/webapp.
- Place the corresponding jQuery scripts in src/main/webapp/assets/js.
- Place the corresponding CSS scripts in src/main/webapp/assets/css.
- Place the corresponding catalog images in src/main/webapp/images/catalog with name as <product_id>.jpeg.
- Some required icon images are located in src/main/webapp/images/Extras.
- Finally, you can run the project on the server.

## Technologies:
- Database: MySQL
- Backend: Java, Hibernate
- Rest API Calls: AJAX
- Front-end: Bootstrap

## Deadlines:
- 2-Feb  : Database low-level design and schema, API break down
- 23-Feb : Low-Level APIs implementation
- 16-Mar : Front-end development
- 30-Mar : Full front-end to low-level APIs implementation
- 11-Apr : UI improvements
- 16-Apr  : Internal demo to mentor
