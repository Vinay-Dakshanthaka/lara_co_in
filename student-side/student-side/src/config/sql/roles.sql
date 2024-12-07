'LR190918044256956', 'kP5UcnaNQxLvhoPazLV7RYQVWy0yff', 0, '2019-09-18 04:42:57', 'riteshkr.rt@gmail.com', 'ritesh', 'yyyyyyyyyyyyyyyyy', '2019-09-18 04:42:57', '708806::2019-09-18 04:42:56.798', '$2a$10$XKW70Sf/PphjthhzXFCileza8yONtzG3nxhExZZH6zozfMsIxT9Ba', 2

insert into cb_tpos (user_id, college_id, created, email, first_name, mobile_no, modified, password, status, otp) values (
    'TP190918044256956', '1', '2019-09-18 04:42:57', 'riteshkr.rt@gmail.com', 'ritesh','7899815050', '2019-09-18 04:42:57','$2a$10$XKW70Sf/PphjthhzXFCileza8yONtzG3nxhExZZH6zozfMsIxT9Ba','2','708806::2019-09-18 04:42:56.798'
);


insert into user_roles('TP190918044256956','4');

update cb_learner_courses set enrollment_date = '2019-09-18 04:42:57';
update cb_learner_courses set status = 1;