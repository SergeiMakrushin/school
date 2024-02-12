-- liquibase formatted sql

--changeset smakrushin:1
CREATE INDEX student_name_index ON student (name);

--changeset smakrushin:2
CREATE INDEX faculty_name_color ON faculty (name, color);