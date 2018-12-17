# e-learning
## API Samples
### Login example
>http://localhost:8080/api/auth/login?email=muhammed@gmail.com&password=12345678

### Register example
>http://localhost:8080/api/auth/register?email=muhammed@gmail.com&password=12345678&name=%22muhammed%22&type=1

### Logout example
>http://localhost:8080/api/auth/logout?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw

### Create classroom
>http://localhost:8080/classrooms?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&classtype=3&category=math&passcode=mah67u

### Retrieve classrooms for students,teacher or childs
>http://localhost:8080/classrooms?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw

### Update the classroom info by it's id
>http://localhost:8080/classrooms?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&classtype=3&category=math&passcode=mah67u&classroom_id=2

### Delete classroom by it's id
>http://localhost:8080/classrooms?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&classroom_id=2

### Find classroom by it's id
>http://localhost:8080/classroom?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&classroom_id=2

### Enroll student in classroom with it's id
>http://localhost:8080/enroll?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&classroom_id=35&passcode=mah67u

### Add child for a parent
>http://localhost:8080/parent/addchildaccess_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&email=mohamed@hotmail.com&name=habebbaba&password=12345678

### join child into classroom
>http://localhost:8080/parent/parentenroll?access_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ0ODk0MzQ2LCJleHAiOjE1NDU0OTkxNDZ9.xXlBHxIs-mjublF-vMEgAAoP-18t7Hfmg3AXEM0BRBJ4L7aI6BT0qUNkXVlIgJ_Y0OcnM4jVeYF6jNoGi81Vlw&user_id=2&classroom_id=7&passcode=mah67j
