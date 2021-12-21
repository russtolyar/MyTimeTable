use school;

insert into days (name) values 
('MONDAY'),
('TUESDAY'),
('WEDNESDAY'),
('THURSDAY'),
('FRIDAY'),
('SATURDAY');

 insert into lesson_numbers (time_name) values
 ('FIRST'),
 ('SECOND'),
 ('THIRD'),
 ('FOURTH'),
 ('FIFTH'),
 ('SIXTH'),
 ('SEVENTH');
 
insert into complexity_per_day (day_id, complexity) values
(1, 50),
(2, 55),
(3, 50),
(4, 55),
(5, 50),
(6, 50);

insert into teachers (first_name,last_name) values
('Marina', 'Sergeevna'),
('Natalia', 'Vasilevna'),
('Natalia', 'Nikolaevna'),
('Alexandra', 'Sergeevna'),
('Elena', 'Igorevna'),
('Uriy', 'Vladimirovich'),
('Vladimir', 'Vladimirovich'),
('Vera', 'Prokopfievna'),
('Pesenka', 'Pesenkova'),
('Kartina', 'Kartinovna'),
('Nepomny', 'Kakzvatevich'),
('Evgeniy', 'Jakovlevich');

insert into teacher_wishes (teacher_id, lesson_number_id) values 
(1, 2), (1, 3), (1, 4), 
(2, 1), (2, 2), (2, 5), (2, 6), 
(3, 3), (3, 4), (3, 5), (3, 6), 
(4, 5), (4, 6), (4, 7),
(5, 1), (5, 2), (5, 3), (5, 4), 
(6, 4), (6, 5), (6, 6), (6, 7),
(7, 4), (7, 5), (7, 6), (7, 7),
(8, 5), (8, 6), (8, 7),
(9, 4), (9, 5), (9, 6), (9, 7), 
(10, 1), (10, 2), (10, 5), (10, 6),
(11, 3), (11, 4), (11, 5), (11, 6),
(12, 3), (12, 5), (12, 6), (12, 7);

insert into subjects (name, room_type_needed) values
('Russian Language', 'ALL'),
('Russian Literature', 'ALL'),
('English Language', 'ALL'),
('Algebra', 'ALL'),
('Geometry', 'ALL'),
('Informatics', 'COMPUTER'),
('Chemistry', 'LAB'),
('Biology', 'LAB'),
('Physics', 'LAB'),
('Geography', 'MAP'),
('History', 'MAP'),
('Social Science', 'ALL'),
('Music', 'ALL'),
('Physical Culture', 'GYM'),
('O.B.G.', 'ALL'),
('Art', 'ALL');

insert into teacher_subjects (subject_id, teacher_id) values
(1,3),(2,3), (3,4), (4,5), (5,5), (6,6), (7,7), (8,8), (9,7),
(10,2), (11,1), (12,1), (13,9), (14,12), (15,11), (16,10);

insert into areas (name) values 
("Philological"),
("Mathematical"),
("Social"),
("Natural"),
("Art"),
("Physical");

insert into subject_area (area_id, subject_id) values
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5), (2, 6),
(3, 10), (3, 11), (3, 12),
(4, 7), (4, 8), (4, 9),
(5, 16), (5, 13),
(6, 14), (6, 15);

insert into subject_positions (subject_id, lesson_number_id) values
(1, 2), (1, 3), (1, 5), (1, 6),
(2, 1), (2, 4), (2, 5),
(3, 1), (3, 2), (3, 6), (3, 7),
(4, 2), (4, 3), (4, 4), (4, 5), (4, 6),
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5),
(6, 1), (6, 2), (6, 3), (6, 6), (6, 7),
(7, 1), (7, 2), (7, 5), (7, 6),
(8, 4), (8, 5), (8, 6), (8, 7),
(9, 2), (9, 3), (9, 4), (9, 5), (9, 6),
(10, 1), (10, 5), (10, 6), (10, 7),
(11, 2), (11, 3), (11, 4), (11, 5),
(12, 2), (12, 3), (12, 4), (12, 5), (12, 6),
(13, 4), (13, 5), (13, 6), (13, 7),
(14, 1), (14, 2), (14, 3), (14, 4), (14, 5),
(15, 1), (15, 2), (15, 4), (15, 5),
(16, 4), (16, 5), (16, 6), (16, 7);

insert into subject_complexities (subject_id, complexity) values
(1, 7), (2, 4), (3, 8), (4, 9), (5, 10),
(6, 7), (7, 10), (8, 7), (9, 9), (10, 6),
(11, 8), (12, 10), (13, 3), (14, 2), (15, 3), (16, 3);
 
insert into classes (name) values
('7A'),
('7B'),
('8A'),
('8B');

insert into subject_per_week (class_id, subject_id, count) values
(1,1,3),  (1,2,2), (1,3,3), (1,4,3), (1,5,2),
(1,6,2), (1,7,2), (1,9,2), (1,10,2), (1,11,2),
(1,12,1), (1,13,1), (1,14,3), (1,16,1),

(2,1,3),  (2,2,2), (2,3,3), (2,4,3), (2,5,2),
(2,6,2), (2,7,2), (2,9,2), (2,10,2), (2,11,2),
(2,12,1), (2,13,1), (2,14,3), (2,16,1),  

(3,1,3),  (3,2,2),  (3,3,3),  (3,4,3),  (3,5,2),
(3,6,2),  (3,7,2),  (3,8,2),  (3,9,2),  (3,10,2),
(3,11,2),  (3,12,1),  (3,13,1),  (3,14,3),  (3,15,1), 

(4,1,3),  (4,2,2),  (4,3,3),  (4,4,3),  (4,5,2),
(4,6,2),  (4,7,2),  (4,8,2),  (4,9,2),  (4,10,2),
(4,11,2),  (4,12,1),  (4,13,1),  (4,14,3),  (4,15,1);
 
 insert into classrooms (number, type) values
 ('25', 'MAP'),
 ('37', 'MAP'),
 ('26', 'ALL'),
 ('39', 'ALL'),
 ('35', 'ALL'),
 ('30', 'COMPUTER'),
 ('33', 'LAB'),
 ('32', 'LAB'),
 ('38', 'LAB'),
 ('28', 'ALL'),
 ('13', 'GYM');