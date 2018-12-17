# e-learning
## API Samples
### Login example (GET method)
>http://localhost:8080/api/auth/login?email=muhammed@gmail.com&password=12345678

### Register example (POST method)
>http://localhost:8080/api/auth/register?email=muhammed@gmail.com&password=12345678&name=%22muhammed%22&type=1

### Logout example (GET method)
>http://localhost:8080/api/auth/logout?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Create classroom (POST method)
>http://localhost:8080/classrooms?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classtype=3&category=math&passcode=mah67u

### Retrieve classrooms for students,teacher or childs (GET method)
>http://localhost:8080/classrooms?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}

### Update the classroom info by it's id (PUT method)
>http://localhost:8080/classrooms?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classtype=3&category=math&passcode=mah67u&classroom_id=2

### Delete classroom by it's id (DELETE method)
>http://localhost:8080/classrooms?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classroom_id=2

### Find classroom by it's id (GET method)
>http://localhost:8080/classroom?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classroom_id=2

### Enroll student in classroom with it's id (POST method)
>http://localhost:8080/enroll?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&classroom_id=35&passcode=mah67u

### Add child for a parent (POST method)
>http://localhost:8080/parent/addchildaccess_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&email=mohamed@hotmail.com&name=habebbaba&password=12345678

### join child into classroom (POST method)
>http://localhost:8080/parent/parentenroll?access_token={eyJhbGciOiJIUzUxMiJ9.eyJzdWIi...}&user_id=2&classroom_id=7&passcode=mah67j
