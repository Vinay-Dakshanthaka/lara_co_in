

insert into cb_courses(course_name, course_duration, course_description1, course_description2, no_of_articles, course_thumbnail) values
('Logical coding', '3 months', 'All types of Logical coding', 'We are covering every section of Logical coding. For example Number System, Display patterns, Strings, Strings with File handling and Arrays', '50', 'Java-icon.png');

update cb_courses set course_thumbnail = 'logicalcoding.png' where course_name = 'Logical coding';

insert into cb_course_requirements (course_requirement_desc,course_id) values ('No pre-requisites are required. Anybody can understand', '4');


insert into lov_topics (course_id, topic_name, topic_duration) values ('4', 'Logical coding', '20h:10min');

