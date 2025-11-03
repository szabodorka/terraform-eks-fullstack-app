DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user"(
                       id SERIAL PRIMARY KEY,
                       username text not null unique,
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
                       question_id integer REFERENCES question(id) ON DELETE CASCADE
);



INSERT INTO "user" (username, password, score) VALUES ('john', 'pw123', 10);
INSERT INTO "user" (username, password, score) VALUES ('joe', 'apple1', 12);
INSERT INTO "user" (username, password, score) VALUES ('jane', 'harrypotter123', 30);


INSERT INTO question (title, description, user_id) VALUES ('What is the Java Stream API?', 'Can someone explain the basics of the Stream API with a simple example?', 1);
INSERT INTO question (title, description, user_id) VALUES ('What is the purpose of the final keyword in Java?', 'I do not fully understand when and why to use final on variables, methods or classes.', 2);
INSERT INTO question (title, description, user_id) VALUES ('How to handle NullPointerException?', 'What are best practices to avoid NPEs in Java?', 3);


INSERT INTO answer (title, message, user_id, question_id) VALUES ('Stream API purpose', 'Stream API helps with functional-style operations on collections like map, filter, reduce.', 2,1);
INSERT INTO answer (title, message, user_id, question_id) VALUES ('Simple Stream example', 'You can use streams to process collections. For example: list.stream().filter(x -> x > 10).collect(Collectors.toList());', 3,1);