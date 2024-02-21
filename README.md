# InsuredEvidence

The program records insured individuals in a database that supports basic CRUD operations. The Insured Records
Management System is a simple Java application designed to manage a database of insured individuals. The application
provides a user interface for performing various actions such as adding, listing, searching, modifying, and deleting
insured persons. It also allows users to create a file containing the details of all insured individuals in the
database. The application has the following main features:

    Add Insured Person: Add a new insured person to the database with details such as name, surname, phone number, and age.

    List All Insured Individuals: View a list of all insured individuals in the database.

    Search for Insured Person: Search for an insured person by entering their name or surname.

    Modify Insured Person: Modify the details of a specific insured person, identified by their unique ID.

    Delete Insured Person: Delete an insured person from the database based on their ID.

    Create a File: Generate a text file containing the details of all insured individuals in the database.

The application uses a simple data structure to store insured individuals, represented by the InsuredPerson class. The main class, Main, contains the user interface logic and manages the user session loop. The UserInterface class handles user input and validation, while the DatabaseOfInsured class stores and manages the insured individuals.
