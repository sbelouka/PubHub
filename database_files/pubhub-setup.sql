DROP TABLE IF EXISTS books;

CREATE TABLE books(
  isbn_13 varchar (13) PRIMARY KEY,
  title varchar (100),
  author varchar (80),
  publish_date date,
  price decimal (6,2),
  CONTENT bytea
);

INSERT INTO books VALUES(
  '1111111111111',          	-- id
  'The Adventures of Steve',    -- title
  'Russell Barron', 			-- author
  current_date,    				-- publishDate
  123.50,   					-- price
  null							-- blob
);

DROP TABLE IF EXISTS tags;

CREATE TABLE tags(
tagID INT UNIQUE,
tagName VARCHAR(100),
PRIMARY KEY (tagID)
);

DROP TABLE IF EXISTS booksTags;

CREATE TABLE booksTags(
isbn_13 VARCHAR(13),
tagID  INT,
FOREIGN KEY (isbn_13) REFERENCES books(isbn_13),
FOREIGN KEY (tagID) REFERENCES tags(tagID)
);

