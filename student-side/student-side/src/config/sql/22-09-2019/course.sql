


insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Data Structures', '3 months', 'desc1', 'This is basic dummy course Jdbc with image on top, title, description and button.', '50', 'Java-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Algorithms', '3 months', 'desc1', 'This is basic dummy course MySql with image on top, title, description and button.', '40', 'sql-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('JDBC', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Servlets', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('JSP', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Hibernate', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Spring', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Spring Boot', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Spring Microservice', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('JavaScript', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Angular', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Webservices', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');
insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Project Development', '1 month','desc1',  'This is basic dummy course Oracle with image on top, title, description and button.', '20', 'ocle-icon.png');


update cb_courses set course_thumbnail = 'corejava.png' where course_name = 'Core Java Basics';
update cb_courses set course_thumbnail = 'corejavaadv.png' where course_name = 'Core Java Advanced';
update cb_courses set course_thumbnail = 'logicalcoding.png' where course_name = 'Logical coding';
update cb_courses set course_thumbnail = 'ds.png' where course_name = 'Data Structures';
update cb_courses set course_thumbnail = 'algo.png' where course_name = 'Algorithms';
update cb_courses set course_thumbnail = 'mysql.jpg' where course_name = 'SQL';
update cb_courses set course_thumbnail = 'jdbc.jpg' where course_name = 'JDBC';
update cb_courses set course_thumbnail = 'servlets.jpg' where course_name = 'Servlets';
update cb_courses set course_thumbnail = 'jsp.jpg' where course_name = 'JSP';
update cb_courses set course_thumbnail = 'hibernate.jpg' where course_name = 'Hibernate';
update cb_courses set course_thumbnail = 'spring.png' where course_name = 'Spring';
update cb_courses set course_thumbnail = 'springboot.png' where course_name = 'Spring Boot';
update cb_courses set course_thumbnail = 'springmcr.png' where course_name = 'Spring Microservice';
update cb_courses set course_thumbnail = 'javascript.jpg' where course_name = 'JavaScript';
update cb_courses set course_thumbnail = 'angular.jpg' where course_name = 'Angular';
update cb_courses set course_thumbnail = 'webservice.png' where course_name = 'Webservices';
update cb_courses set course_thumbnail = 'projdev.png' where course_name = 'Project Development';

update cb_courses set is_active = 'Y' where course_name = 'Core Java Basics';
update cb_courses set is_active = 'Y'  where course_name = 'Core Java Advanced';
update cb_courses set is_active = 'N'  where course_name = 'Logical coding';
update cb_courses set is_active = 'N'  where course_name = 'Data Structures';
update cb_courses set is_active = 'N' where course_name = 'Algorithms';
update cb_courses set is_active = 'Y' where course_name = 'SQL';
update cb_courses set is_active = 'N' where course_name = 'JDBC';
update cb_courses set is_active = 'N' where course_name = 'Servlets';
update cb_courses set is_active = 'N' where course_name = 'JSP';
update cb_courses set is_active = 'N' where course_name = 'Hibernate';
update cb_courses set is_active = 'N' where course_name = 'Spring';
update cb_courses set is_active = 'N' where course_name = 'Spring Boot';
update cb_courses set is_active = 'N' where course_name = 'Spring Microservice';
update cb_courses set is_active = 'N' where course_name = 'JavaScript';
update cb_courses set is_active = 'N' where course_name = 'Angular';
update cb_courses set is_active = 'N' where course_name = 'Webservices';
update cb_courses set is_active = 'N' where course_name = 'Project Development';

insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'Logical coding'));
insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'Data Structures'));
insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'Algorithms'));
insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'SQL'));
insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'JavaScript'));
insert into map_courses_under_package (course_package_id, course_id) values ('1', (select course_id from cb_courses where course_name = 'Angular'));