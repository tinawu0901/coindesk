-- SELECT * FROM EXAMPLE.bitcoin;
CREATE TABLE bitcoin (
code	VARCHAR(10)  NOT NULL,
symbol	VARCHAR(10),
rate	VARCHAR(50),
description	VARCHAR(50),
rate_float	int,
chineseName	VARCHAR(20),
updatedtime	VARCHAR(50),
CONSTRAINT bitcoin PRIMARY KEY (code)
);