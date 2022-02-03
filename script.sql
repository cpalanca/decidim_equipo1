CREATE TABLE authors (
id int(9) PRIMARY KEY,
nickname NVARCHAR(60) NOT NULL,
password NVARCHAR(30) NOT NULL
);

CREATE TABLE comments (
id int(9) PRIMARY KEY,
id_authors int(9) NOT NULL,
body NVARCHAR(255) NOT NULL,
id_comments NVARCHAR (255),
created_at DATETIME NOT NULL,
down_votes int(9) NOT NULL,
up_votes int(9) NOT NULL,
reply_to int(9) NULL,
reply_level int(2),
FOREIGN KEY(id_authors) REFERENCES authors(id)
);


CREATE TABLE proposal(
id int(9) PRIMARY KEY,
title NVARCHAR(255) NOT NULL,
id_authors int(9) NOT NULL,
id_comments NVARCHAR (255),
n_endorsement int(9) NOT NULL,
created_at DATETIME NOT NULL,
published_at DATETIME NOT NULL,
FOREIGN KEY(id_authors) REFERENCES authors(id),
FOREIGN KEY(id_comments) REFERENCES comments(id)
);

