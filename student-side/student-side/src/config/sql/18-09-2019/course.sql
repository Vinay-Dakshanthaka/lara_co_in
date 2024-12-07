

insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('SQL', '3 months', 'SQL features with Oracle 11g', 'We are covering all features of SQL. Right from the installation of Oracle 11g and SQL Developer. We cover all features of DDL, DML and DQL. We also focus more on joins and real time challenges of SQL.', '50', 'Java-icon.png');

update cb_courses set course_thumbnail = 'mysql.jpg' where course_name = 'SQL';



insert into lov_topics (course_id, topic_name, topic_duration) values ('3', 'Sql', '20h:10min');

insert into lov_sub_topics (topic_id, sub_topic_name, sub_topic_duration) values ('3', 'Setup', '1h:20min');
insert into lov_sub_topics (topic_id, sub_topic_name, sub_topic_duration) values ('3', 'Constraints', '1h:20min');
insert into lov_sub_topics (topic_id, sub_topic_name, sub_topic_duration) values ('3', 'Mappings and Joins', '1h:20min');
insert into lov_sub_topics (topic_id, sub_topic_name, sub_topic_duration) values ('3', 'Real time usage', '1h:20min');
insert into lov_sub_topics (topic_id, sub_topic_name, sub_topic_duration) values ('3', 'Normal Forms', '1h:20min');
insert into lov_sub_topics (topic_id, sub_topic_name, sub_topic_duration) values ('3', 'ASSIGNMENT', '1h:20min');

insert into map_courses_under_package (course_id, course_package_id) values ('3', '1');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Setup'), 'Oracle 11g Installation', '1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Setup'), 'SQL Developer installation', '1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Setup'), 'Oracle 11g un installation', '1h:20min');

insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Basics'),'DDL and DML', '1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Basics'),'DQL', '1h:20min');

insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Constraints'), 'NOT NULL, UNIQUE AND PRIMARY','1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Constraints'), 'FOREIGN KEY','1h:20min');

insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Mappings and Joins'), 'ONE-TO-ONE','1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Mappings and Joins'), 'ONE-TO-MANY','1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Mappings and Joins'), 'MANY-TO-MANY','1h:20min');

insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Real time usage'), 'DATE AND TIMESTAMP','1h:20min');
insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Real time usage'), 'DESIGNING EMPLOYEE DATABASE','1h:20min');

insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'Normal Forms'), '1NF, 2NF and 3NF','1h:20min');

insert into lov_micro_topics (sub_topic_id, micro_topic_name, micro_topic_duration) values ((select sub_topic_id from lov_sub_topics where sub_topic_name = 'ASSIGNMENT'), 'Designing Car database','1h:20min');