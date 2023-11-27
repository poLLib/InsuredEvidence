# InsuredEvidence
The program records insured individuals in a database that supports basic CRUD operations. This application is a basic example of using Java and does not include features like data persistence (e.g., saving to a file) or more advanced database operations.

This is a Java application for managing a database of insured individuals. It provides a simple text-based user interface that allows users to perform various operations on the database. The application has the following main features:

    Add Insured Person: Users can add a new insured person to the database by providing their name, surname, phone number, and age. The application validates the input to ensure that the name contains only letters, the phone number is a 9-digit number, and the age is between 0 and 100.

    List All Insured Individuals: Users can view a list of all the insured individuals in the database. The application displays their names, surnames, phone numbers, and ages.

    Search for Insured Person: Users can search for a specific insured person by entering their name, surname, or parts of their name or surname. The application will display matching results based on the input.

    Modify Insured Person: Users can update the details of an insured person by searching for them using their name and surname. They can change the name, surname, and phone number of the insured person.

    Delete Insured Person: Users can delete a specific insured person from the database by entering their exact name and surname.

    End the Program: Users can choose to end the program when they are finished with their tasks.

The application uses a simple data structure to store insured individuals, represented by the InsuredPerson class. The main class, Main, contains the user interface logic and manages the user session loop. The UserInterface class handles user input and validation, while the DatabaseOfInsured class stores and manages the insured individuals.
