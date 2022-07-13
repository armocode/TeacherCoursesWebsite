
 Guidelines for localhost
1. Clone this repository
2. Open project -> application.properties -> change your data:
a. spring.datasource.url=jdbc:mysql://localhost:3306/yourDatabaseName
b. Turn on your database server (I using XAMPP)
c. Open your browser, go to localhost/phpmyadmin -> SQL
d. 'CREATE DATABASE yourDatabaseName'
e. Back to the project, uncomment autocreate rows and start the project.
(In the terminal you can get an exception saying you couldn't create a 'course reviews' table.
Restarting project should fix this issue.(PostgreSQL))
3. Go to localhost/phpmyadmin -> yourDatabaseName -> SQL
4. Open MySQL.txt, insert all rows which contain INSERT..
5. Go to localhost:8080 in your browser
6. After these steps you can test the application.



Admin user:
username: admin123
password: admin123

