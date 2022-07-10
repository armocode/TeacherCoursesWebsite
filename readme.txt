
Guidelines for localhost
1. Clone this repository
2. Open project -> application.properties -> change your data:
a. spring.datasource.url=jdbc:mysql://localhost:3306/yourDatabaseName 
b. Turn on your database server (I using XAMPP) 
c. Open your browser, go to localhost/phpmyadmin -> SQL
d. 'CREATE DATABASE yourDatabaseName'
e. Back to project, uncomment autocreate rows and start the project.
(In terminal you can get exception saying couldn't create 'course reviews' table.
Restarting project should fix this issue.(PostgreSQL))
3. Go to localhost/phpmyadmin -> yourDatabaseName -> SQL 
4. Open postgresql.txt, insert all rows which contains INSERT..
5. Go to localhost:8080 in your browser
6. After this steps you can test application.

Admin user:
username: admin123
password: admin123

Application is ready to use on postgresql, if you would like to use on mysql, 
you must change all rows which contain boolean expression in query.
Example : false to 0, true to 1. (postgresql: true/false, mysql: 0/1)

