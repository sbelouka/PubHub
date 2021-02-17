Alter Table bookstags Drop tagid;

Drop Table tags;

Create Table tags(
tagName varchar(100) UNIQUE
);

Alter table bookstags add tagName varchar(100);

Alter table bookstags add
FOREIGN KEY (tagName) REFERENCES 
tags(tagName);