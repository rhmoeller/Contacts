PRAGMA table_info(Field);

INSERT INTO Contact VALUES (0);
INSERT INTO FieldType VALUES (2, 'name');

INSERT INTO Field VALUES (0,0,2,'first','Robert');
INSERT INTO Field VALUES (1,0,2,'last','Moeller');


SELECT * FROM Contact;
SELECT * FROM Field;
SELECT * FROM FieldType;

SELECT
  Contact.id,
  Field.value,
  Field.name,
  FieldType.id,
  FieldType.prompt
FROM
  (Contact
INNER JOIN
  Field
ON
  Contact.id = Field.contact)
INNER JOIN
  FieldType
ON
  FieldType.id = Field.type;

