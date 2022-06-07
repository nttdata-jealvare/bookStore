DELETE from BOOK;
DELETE from EDITORIAL;

INSERT into EDITORIAL (ID, NAME) values (21, 'Primera Editorial');
INSERT into EDITORIAL (ID, NAME) values (22, 'Segunda Editorial');

INSERT into BOOK (TITLE, AUTHOR, PUBLISH, PAGES, DESCRIPTION, EDITORIAL_ID) values ('Primer libro', 'John Johnson', '1985-03-03', 84, 'Primer libro escrito en este laboratorio', 21);
INSERT into BOOK (TITLE, AUTHOR, PUBLISH, PAGES, DESCRIPTION, EDITORIAL_ID) values ('Segundo libro', 'William Mosby', '2002-03-12', 814, 'Segundo libro escrito en este laboratorio', 22);