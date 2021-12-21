drop database if exists school;

create database if not exists school;

use school;

create table if not exists days(
id serial,
name varchar(10) not null,
primary key(id)
);

create table if not exists lesson_numbers (
id serial,
time_name varchar(45) not null,
primary key(id)
);

create table if not exists complexity_per_day(
id serial,
day_id  bigint unsigned not null,
complexity int unsigned not null,
primary key(id),
constraint fk_complexity_days_day_id foreign key (day_id) references days(id) 
on update no action 
on delete cascade
);

create table if not exists teachers (
id serial,
first_name varchar(20) not null,
last_name varchar(20) not null,
primary key(id)
);

create table if not exists teacher_wishes(
id serial,
teacher_id  bigint unsigned not null,
lesson_number_id bigint unsigned not null,
primary key(id),
constraint fk_teacher_wishes_teacher_id foreign key (teacher_id) references teachers(id) 
on update no action 
on delete cascade,
constraint fk_teacher_wishes_lesson_number_id foreign key (lesson_number_id) references lesson_numbers(id) 
on update no action 
on delete cascade
);

create table if not exists subjects (
id serial,
name varchar(50) not null,
room_type_needed varchar(20) not null,
primary key(id)
);

create table if not exists teacher_subjects (
id serial,
subject_id  bigint unsigned not null,
teacher_id  bigint unsigned not null,
primary key(id),
constraint fk_teacher_subjects_subject_id foreign key (subject_id) references subjects(id) 
on update no action 
on delete cascade,
constraint fk_teacher_subjects_teacher_id foreign key (teacher_id) references teachers(id) 
on update no action 
on delete cascade
);

create table if not exists areas (
id serial,
name varchar(50) not null,
primary key(id)
);

create table if not exists subject_area (
id serial,
area_id  bigint unsigned not null,
subject_id  bigint unsigned not null,
primary key(id),
constraint fk_subject_areas_subject_id foreign key (subject_id) references subjects(id) 
on update no action 
on delete cascade,
constraint fk_subject_areas_area_id foreign key (area_id) references areas(id) 
on update no action 
on delete cascade
);

create table if not exists subject_positions (
id serial,
subject_id  bigint unsigned not null,
lesson_number_id bigint unsigned not null,
primary key(id),
constraint fk_subject_positions_subject_id foreign key (subject_id) references subjects(id) 
on update no action 
on delete cascade,
constraint fk_subject_positions_lesson_number_id foreign key (lesson_number_id) references lesson_numbers(id) 
on update no action 
on delete cascade
);

create table if not exists subject_complexities (
id serial,
subject_id  bigint unsigned not null,
complexity int unsigned not null,
primary key(id),
constraint fk_subject_complexities_subject_id foreign key (subject_id) references subjects(id) 
on update no action 
on delete cascade
);

create table if not exists classes (
id serial,
name varchar(3) not null,
primary key(id)
);

create table if not exists subject_per_week (
id serial,
class_id  bigint unsigned not null,
subject_id  bigint unsigned not null,
count  int unsigned not null,
primary key(id),
constraint fk_subject_per_week_class_id foreign key (class_id) references classes(id) 
on update no action 
on delete cascade,
constraint fk_subject_per_week_subject_id foreign key (subject_id) references subjects(id) 
on update no action 
on delete cascade
);

create table if not exists classrooms (
id serial,
number varchar(3) not null,
type varchar(20) not null,
primary key(id)
);

create table if not exists timetable (
id serial,
day_id  bigint unsigned not null,
lesson_number_id  bigint unsigned not null,
class_id  bigint unsigned not null,
subject_id  bigint unsigned not null,
classroom_id  bigint unsigned not null,
teacher_id  bigint unsigned not null,
primary key(id),
constraint fk_timetable_day_id foreign key (day_id) references days(id) 
on update no action 
on delete cascade,
constraint fk_timetable_lesson_number_id foreign key (lesson_number_id) references lesson_numbers(id) 
on update no action 
on delete cascade,
constraint fk_timetable_class_id foreign key (class_id) references classes(id) 
on update no action 
on delete cascade,
constraint fk_timetable_lesson_subject_id foreign key (subject_id) references subjects(id) 
on update no action 
on delete cascade,
constraint fk_timetable_classroom_id foreign key (classroom_id) references classrooms(id) 
on update no action 
on delete cascade,
constraint fk_timetable_lesson_teacher_id foreign key (teacher_id) references teachers(id) 
on update no action 
on delete cascade
);