DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS "user";


CREATE TABLE "user"(
                       id SERIAL PRIMARY KEY,
                       username text not null,
                       registration_date  TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
                       password varchar(100) not null,
                       score int not null
);


CREATE TABLE question(
                         id SERIAL PRIMARY KEY,
                         title text not null,
                         description text not null,
                         user_id integer not null REFERENCES "user"(id),
                         post_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);



CREATE TABLE answer(
                       id SERIAL PRIMARY KEY,
                       title text not null,
                       message text not null,
                       user_id integer not null REFERENCES "user"(id),
                       post_date  TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
                       question_id integer REFERENCES question(id)
);




INSERT INTO "user" (username, password, score) VALUES ('john', 'pw123', 10);
INSERT INTO "user" (username, password, score) VALUES ('joe', 'apple1', 12);
INSERT INTO "user" (username, password, score) VALUES ('jane', 'harrypotter123', 30);


INSERT INTO question (title, description, user_id) VALUES ('first question', 'this is the first question', 1);


INSERT INTO answer (title, message, user_id, question_id) VALUES ('first answer', 'this is the first anwser', 2,1);
INSERT INTO answer (title, message, user_id, question_id) VALUES ('second answer', 'this is the second answer', 3,1);