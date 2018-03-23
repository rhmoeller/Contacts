-- SELECT
--   id

PRAGMA table_info(Field);

INSERT INTO Contact VALUES (0);
INSERT INTO FieldType VALUES (0,'name','name');
INSERT INTO Field VALUES (0,0,0,'first','Robert');

SELECT * FROM Contact;
SELECT * FROM Field;
SELECT * FROM FieldType;

SELECT
  Contact.id,
  Field.value,
  Field.name,
  FieldType.name type,
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




--
--   Field
-- WHERE
--
-- AND
--   Field.type = FieldType.id;
--

