ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age < 16);

ALTER TABLE student
ADD PRIMARY KEY (name);

ALTER TABLE faculty
ADD PRIMARY KEY (name, color);

ALTER TABLE student
ALTER COLUMN age
SET DEFAULT 20;