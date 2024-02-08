-- liquibase formatted sql

-- changeset s_makrushin:1
CREATE INDEX student_name_index ON student (name);

-- changeset s_makrushin:2
CREATE INDEX faculty_name_color ON faculty (name, color);