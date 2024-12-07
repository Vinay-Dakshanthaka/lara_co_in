insert into cb_course_packages(course_package_name, course_package_desc, course_package_duration, course_package_price, bg_class)
values('College to Corporate',
'This package is specially designed for the freshers. In this package 8 skills are covering which are very much essential to enter into the industry as a Java full stack developer. 1. Core Java Basics, 2. Core Java Advanced, 3. Logical Coding, 4. Data Structures, 5. Algorithms, 6. SQL, 7. Java Script, 8. Angular',
 '5 months', '5000', 'cs-header');

insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Core Java Basics', '3 months', 'Very basics of Core Java', 
'We are covering right from the scratch. Any Graduate can able to understand. More practical way we have covered. Non computer student also can able to understand very easily. ', 
'50', 'corejava.png');

insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Core Java Advanced', '3 months', 'All Advanced topics of Core Java', 
'We are covering every advanced topic of Core Java. More attention is very much required. For every concept several examples are developed.  These topics of very much required for the development and also for an interviews.', '40', 'corejavaadv.png');

insert into cb_course_requirements (course_requirement_desc,course_id) values ('No pre-requisites are required. No need of knowing any programming languages like a C or C++ to understand this course.', '1');

insert into cb_course_requirements (course_requirement_desc,course_id) values ('If you know Core Java Basics, Then only you can understand this course. First finish the Core Java Basics then only start this course.', '2');

insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'Core Java Basics'));
insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'Core Java Advanced'));

insert into lov_education (education_name, education_desc) values ('BE/BTECH','Bachelor Of Engineering/Technology');
insert into lov_education (education_name, education_desc) values ('ME/MTECH','Master Of Engineering/Technology');
insert into lov_education (education_name, education_desc) values ('BA','Bachelor Of Arts');
insert into lov_education (education_name, education_desc) values ('BSC','Bachelor Of Science');
insert into lov_education (education_name, education_desc) values ('MA','Master Of Arts');
insert into lov_education (education_name, education_desc) values ('MSC','Master Of Science');

insert into lov_education_specialization(education_id, education_specialization_name, education_specialization_desc) values ('1', 'IT','InformationTechnology');
insert into lov_education_specialization(education_id, education_specialization_name, education_specialization_desc) values ('1', 'CS','Computer Science');
insert into lov_education_specialization(education_id, education_specialization_name, education_specialization_desc) values ('2', 'EE','Electrical & Electronics');
insert into lov_education_specialization(education_id, education_specialization_name, education_specialization_desc) values ('2', 'ME','Mechanical');

insert into lov_skills (skill_name, skill_desc) values ('Java', 'Java');
insert into lov_skills (skill_name, skill_desc) values ('Angular', 'Angular');
insert into lov_skills (skill_name, skill_desc) values ('Servlets', 'Servlets');
insert into lov_skills (skill_name, skill_desc) values ('Jsp', 'Jsp');
insert into lov_skills (skill_name, skill_desc) values ('MySql', 'MySql');

insert into lov_skill_level (skill_level_name) values ('Beginners');
insert into lov_skill_level (skill_level_name) values ('Intermediate');
insert into lov_skill_level (skill_level_name) values ('Expert');

insert into lov_topics (course_id, topic_name, topic_duration) values ('1', 'Core Java Basics', '20h:10min');
insert into lov_topics (course_id, topic_name, topic_duration) values ('2', 'Core Java Advanced', '20h:10min');


INSERT INTO roles(name) VALUES('ROLE_LEARNER');
INSERT INTO roles(name) VALUES('ROLE_EMPLOYER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');