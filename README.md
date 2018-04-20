# Contacts
## Pre-requisites
* Java version 1.8 level 8
* Add lib/sqlite.jar to classpath

## Setup
To flush database and import sample data run com.hjortsholm.contacts.Setup.
Sample pictures are located in data/sample-pictures.
All pictures used in application will be copied to data/profile-pictures.

## Database structure
The database structure are dynamically build on the classes Contact and Field.

#### Contact
* id - integer, autoincrement, primary key

#### Field
* id - integer, autoincrement, primary key
* contact - integer, not null
* type - integer, not null
* name -  varchar, not null
* value - varchar, not null

## Test 
Tests are located in src/tests
All tests are made with JUnit5 (dependencies should be in lib/)

## Documentation
All documentation is located in doc/.

    doc/
        designs/
            Color Palette.png       - Color palette used in application.
            Screen Design.pdf/.bmpr - Screen designs made while planning.
            Sketch.jpg              - Screen re-design.
            final/
                *                   - Screenshots of final product.
        diagrams/
            Class Diagram.pdf       - Class diagram made while planning.
        documentation/     
            *                       - Generated javadoc documentation.
        Build Settings.png          - Screenshot of Eclipse build settings.
                
                
    
