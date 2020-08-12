/**
 * William Marks
 * April 2, 2020
 * The goal of this program is to simulate a university database with the goal of being able to perform tons
 * of different operations and functions as well as this program requires a text file known as **"student.txt"**
 * to operate since this text file will contain all the info the program needs in order to process alot of the functions
 * besides Exiting and Adding.
 */

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Client {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String choice = null;
		Scanner input = new Scanner(System.in);
		University uni = new University();

		System.out.println("The choices are: ");
		System.out.println("P - prints the entire database");
		System.out.println("S – Search for a student’s ID");
		System.out.println("RP – Create a report for all students of the same program");
		System.out.println("RY – Create a report for all students of the same year");
		System.out.println("U – Update information of a student");
		System.out.println("A – Add a student");
		System.out.println("D – Delete a student");
		System.out.println();
		System.out.println("E – Exit");
		System.out.println();

		do {
			System.out.print("Which choice would you like to do: ");
			choice = input.next();

			switch (choice) { // once the user has chosen which function it wants to run it performs the
								// appropriate task.
			case "P":
				System.out.println();
				uni.print();
				System.out.println();
				break;
			case "S":
				System.out.println();
				uni.search();
				System.out.println();
				break;
			case "RY":
				System.out.println();
				uni.printYear();
				break;
			case "RP":
				System.out.println();
				uni.printProgram();
				break;
			case "U":
				System.out.println();
				uni.updateInfo();
				break;
			case "A":
				System.out.println();
				uni.add();
				break;
			case "D":
				System.out.println();
				uni.delete();
			default:
			}
			System.out.println();
		} while (!(choice.equalsIgnoreCase("E")));

		uni.updateTXT();

	}

}

class University {

	/**
	 * Constructor
	 */
	University() {
		Student.populate();
	}

	/**
	 * Updates the text file with all the new data
	 */
	public void updateTXT() {
		String file = "student.txt";
		System.out.println("Updating Student.txt file in progress...");
		boolean success = false;

		// attempts to write the data inside of the "student.txt" file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < Student.Lname.size(); i++) {
				writer.write(Student.Lname.get(i) + "," + Student.Fname.get(i) + "," + Student.ID.get(i) + ","
						+ Student.Program.get(i) + "," + Student.Year.get(i) + "\n");
			}

			writer.flush();
			writer.close();
			success = true;

		} catch (IOException e) { // catches if the file is not found
			System.out.println("Error: Invalid File name");
		}

		if (success == true) { // if the file was successfully updated, it displays this to the user
			System.out.println("File was successfully updated.");
		}

	}

	/**
	 * This method prompts the user which student they'd like to delete from the
	 * database if that student exists.
	 */
	public void delete() {
		Scanner input = new Scanner(System.in);
		String lastName = null;
		boolean found = false;
		int Index = -1;

		System.out.print("Please enter the last name of the student you'd like to delete: ");
		lastName = input.next();

		for (int i = 0; i < Student.Lname.size(); i++) {
			if (Student.Lname.get(i).equals(lastName)) { // checks to see if the student actually exists and logs their
															// index value
				found = true;
				Index = i;
			}
		}

		if (found == true) { // removes all instances of that student if they do exist
			Student.Lname.remove(Index);
			Student.Fname.remove(Index);
			Student.ID.remove(Index);
			Student.Program.remove(Index);
			Student.Year.remove(Index);
			System.out.println("Student succesfully removed.");

		} else { // if the student is not found the appropriate message is displayed
			System.out.println("Student not found.");
		}

	}

	/**
	 * Adds a new student to the database by first checking if they already exist
	 * and if they do not they then get added to the database at hand
	 */
	public void add() {
		Scanner input = new Scanner(System.in);
		String lastName = null;
		String firstName = null;
		String ID = null;
		String program = null;
		String year = null;
		int found = 0;
		int index = -1132;
		boolean HIfound = false;
		String correct = null;

		System.out.print("Please enter the last name of the student you'd like to add: ");
		lastName = input.next();

		for (int i = 0; i < Student.Lname.size(); i++) {
			if (Student.Lname.get(i).equals(lastName)) { // checks if the student exists already
				found = 1;
			}
		}

		for (int i = 0; i < Student.Lname.size(); i++) {
			if (Character.compare(Student.Lname.get(i).charAt(0), lastName.charAt(0)) > 0) { // determines the placement
																								// of where that student
																								// should be in
																								// alphabetical order
																								// and logs the index
																								// value
				if (HIfound == false) {
					HIfound = true;
					index = i;
				}
			}

		}

		if (found != 1) { // if the student was not found it performs the steps to add them
			System.out.print("What is his/her first name?: ");
			firstName = input.next();

			System.out.print("What is his/her 6-Digit ID number: ");
			ID = input.next();

			System.out.print("What program is he/she in?: ");
			input.nextLine();
			program = input.nextLine();

			System.out.print("What year did they start in?(as a numerical value): ");
			year = input.next();

			System.out.println();

			System.out.println("Is this correct: ");

			System.out.println("Student name: " + firstName + " " + lastName);
			System.out.println("Student ID: " + ID);
			System.out.println("Student Program :" + program);
			System.out.println("Student Year: " + year);

			System.out.print("Yes or no?: ");
			correct = input.next();

			if (correct.equals("no")) {
				int choice = -1;
				do {
					System.out.println("What would you like to update?: ");
					System.out.println("1 - Last Name");
					System.out.println("2 - First Name");
					System.out.println("3 - ID");
					System.out.println("4 - Program");
					System.out.println("5 - Year");
					System.out.println("0 - Quit");

					System.out.print("Choice: ");
					if (input.hasNextInt()) {
						choice = input.nextInt();
					}

					switch (choice) {
					case 0:
						break;
					case 1:
						System.out.print("Please enter the new last name: ");
						String temp = input.next();
						lastName = temp;
						break;
					case 2:
						System.out.print("Please enter the new first name: ");
						String temp1 = input.next();
						firstName = temp1;
						break;
					case 3:
						System.out.print("Please enter the new ID number: ");
						String temp2 = input.next();
						ID = temp2;
						break;
					case 4:
						System.out.print("Please enter the new program: ");
						String temp3 = input.next();
						program = temp3;
						break;
					case 5:
						System.out.print("Please enter the new year: ");
						String temp4 = input.next();
						year = temp4;
						break;
					default:
						System.out.println("Error: invalid input for choice.");
					}
				} while (choice != 0);
			}

		}
		if (found != 1) { // adds the user to the database
			Student.Lname.add(index, lastName);
			Student.Fname.add(index, " " + firstName);
			Student.ID.add(index, " " + ID);
			Student.Program.add(index, " " + program);
			Student.Year.add(index, " " + year);
		}

		if (found == 1) { // displays an appropriate message if the student already exists
			System.out.println();
			System.out.println("A student with the same last name already exists in the database.");
		}

	}

	/**
	 * Updates any of the info of a current student in the database if they
	 * currently do exist
	 */
	public void updateInfo() {
		Scanner input = new Scanner(System.in);
		String lastName = null;
		boolean found = false;
		int foundIndex = 0;

		System.out.print("Please enter the last name of which you want to be updated: ");
		lastName = input.next();
		int choice = -1;

		for (int i = 0; i < Student.Lname.size(); i++) { // prints out the students current info if they do exist
			if (Student.Lname.get(i).equalsIgnoreCase(lastName)) {
				found = true;
				foundIndex = i;
				System.out.println("Student name: " + Student.Fname.get(i) + " " + Student.Lname.get(i));
				System.out.println("Student ID: " + Student.ID.get(i));
				System.out.println("Student Program :" + Student.Program.get(i));
				System.out.println("Student Year: " + Student.Year.get(i));

			}
		}

		if (found == true) { // continues looping until the user decides all the info is how they want it
			do {
				System.out.println("What would you like to update?: ");
				System.out.println("1 - Last Name");
				System.out.println("2 - First Name");
				System.out.println("3 - ID");
				System.out.println("4 - Program");
				System.out.println("5 - Year");
				System.out.println("0 - Quit");

				System.out.print("Choice: ");
				if (input.hasNextInt()) {
					choice = input.nextInt();
				}

				switch (choice) {
				case 0:
					break;
				case 1:
					System.out.print("Please enter the new last name: ");
					String temp = input.next();
					Student.Lname.set(foundIndex, temp);
					break;
				case 2:
					System.out.print("Please enter the new first name: ");
					String temp1 = input.next();
					Student.Fname.set(foundIndex, temp1);
					break;
				case 3:
					System.out.print("Please enter the new ID number: ");
					String temp2 = input.next();
					Student.ID.set(foundIndex, temp2);
					break;
				case 4:
					System.out.print("Please enter the new program: ");
					String temp3 = input.next();
					Student.Program.set(foundIndex, temp3);
					break;
				case 5:
					System.out.print("Please enter the new year: ");
					String temp4 = input.next();
					Student.Year.set(foundIndex, temp4);
					break;
				default:
					System.out.println("Error: invalid input for choice.");
				}
			} while (choice != 0);

			System.out.println();
			System.out.println("The new updated information is: ");
			System.out.println("Student name: " + Student.Fname.get(foundIndex) + " " + Student.Lname.get(foundIndex));
			System.out.println("Student ID: " + Student.ID.get(foundIndex));
			System.out.println("Student Program :" + Student.Program.get(foundIndex));
			System.out.println("Student Year: " + Student.Year.get(foundIndex));

		} else { // if the student does not currently exist an appropriate message is given back
			System.out.println("Student not found.");
		}

	}

	/**
	 * Prints a message to all the students in a certain program either inside of
	 * the console or in another file.
	 */
	public void printProgram() {
		Scanner input = new Scanner(System.in);
		String program = null;
		int choice = 2;
		boolean contains = false;
		String Message = null;

		System.out.print("What program of students would you like to send the message to?: ");
		if (input.hasNextLine()) {
			program = input.nextLine();
		} else {
			System.out.println("Error: Invalid Input for program");
		}

		System.out.print("Where would you like to print?(1 for screen or 0 for file): ");
		if (input.hasNextInt()) {
			choice = input.nextInt();
			if (choice == 0 || choice == 1) {

			} else {
				System.out.println("Error: Invalid input for choice");
			}
		} else {
			System.out.println("Error: Invalid input for choice");
		}

		System.out.print("What message would you like to convey to the students: ");
		input.nextLine();
		Message = input.nextLine();
		System.out.println();

		for (int i = 0; i < Student.Lname.size(); i++) { // checks to make sure there are currently students inside of
															// that program
			if (Student.Program.get(i).equals(" " + program)) {
				contains = true;
			}
		}

		if (choice == 1 && contains == true) { // if they decide to print to the screen and students are in that
												// program, then it displays the message to all those specific students
												// inside of the console
			System.out.println("To all " + program + " students:");
			System.out.println();

			for (int i = 0; i < Student.Lname.size(); i++) {
				if (Student.Program.get(i).equals(" " + program)) {
					String fullName = Student.Fname.get(i) + " " + Student.Lname.get(i);
					System.out.println(fullName + " with ID: " + Student.ID.get(i));
				}
			}
			System.out.println();

			System.out.println("Please find the following information below: ");

			System.out.println(Message);

		} else if (choice == 0 && contains == true) { // if they decide to print it into a separate file, the program
														// will ask for that file name and try to print it into a new
														// file of the name given

			String file;
			System.out.println("Enter the file name with extension");
			file = input.next();

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write("To all " + program + " students:\n");
				for (int i = 0; i < Student.Lname.size(); i++) {
					if (Student.Program.get(i).equals(" " + program)) {
						String fullName = Student.Fname.get(i) + " " + Student.Lname.get(i);
						writer.write(fullName + " with ID: " + Student.ID.get(i) + "\n");
					}
				}

				writer.write("Please find the following information below: \n");

				writer.write(Message);

				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Error: Invalid File name");
			}
		} else {
			System.out.println("Program not found.");
		}

	}

	/**
	 * Prints a message to the students in a given year by the user, either in the console
	 * or in a seperate file determined by the user.
	 */
	public void printYear() {
		Scanner input = new Scanner(System.in);
		int year = 0;
		String yearS = null;
		int choice = 2;
		String Message = null;
		boolean exists = false;

		System.out.print("What year of students would you like to send the message to?: ");
		if (input.hasNextInt()) {
			year = input.nextInt();
			yearS = Integer.toString(year);
			if (yearS.length() != 4) {
				System.out.println("Error: Invalid input for year");
			}
		} else {
			System.out.println("Error: Invalid Input for year");
		}

		System.out.print("Where would you like to print?(1 for screen or 0 for file): ");
		if (input.hasNextInt()) {
			choice = input.nextInt();
			if (choice == 0 || choice == 1) {

			} else {
				System.out.println("Error: Invalid input for choice");
			}
		} else {
			System.out.println("Error: Invalid input for choice");
		}

		System.out.print("What message would you like to convey to the students: ");
		input.nextLine();
		Message = input.nextLine();
		System.out.println();

		for (int i = 0; i < Student.Lname.size(); i++) { // checks to see if any students exist in that year
			if (Student.Year.get(i).equals(yearS)) {
				exists = true;
			}
		}

		if (choice == 1 && exists == true) { // if they decide to print to the screen and students are in that program,
												// then it displays the message to all those specific students inside of
												// the console
			System.out.println("To all " + year + " students:");
			System.out.println();

			for (int i = 0; i < Student.Lname.size(); i++) {
				if (Student.Year.get(i).equals(" " + yearS)) {
					String fullName = Student.Fname.get(i) + " " + Student.Lname.get(i);
					System.out.println(fullName + " with ID: " + Student.ID.get(i));
				}
			}
			System.out.println();

			System.out.println("Please find the following information below: ");

			System.out.println(Message);

		} else if (choice == 0 && exists == true) {// if they decide to print it into a separate file, the program will
													// ask for that file name and try to print it into a new file of the
													// name given

			String file;
			System.out.println("Enter the file name with extension");
			file = input.next();

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write("To all " + year + " students:\n");
				for (int i = 0; i < Student.Lname.size(); i++) {
					if (Student.Year.get(i).equals(" " + yearS)) {
						String fullName = Student.Fname.get(i) + " " + Student.Lname.get(i);
						writer.write(fullName + " with ID: " + Student.ID.get(i) + "\n");
					}
				}

				writer.write("Please find the following information below: \n");

				writer.write(Message);

				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Error: Invalid File name");
			}
		}

		if (exists == false) {
			System.out.println("No students exist in the indicated year.");
		}
	}

	/**
	 * Searches the database for a specific student and displays their ID number
	 */
	public void search() {
		Scanner input = new Scanner(System.in);
		String len = null;
		boolean found = false;
		int foundIndex = 0;
		
		System.out.println("What last name would you like to search for?");
		if (input.hasNext()) {
			len = input.next();
		} else {
			System.out.println("Error: Invalid Input for last name");
		}

		for (int i = 0; i < Student.ID.size(); i++) {
			if (Student.Lname.get(i).equals(len)) { //checks to see if the student exists by comparing their last name to the rest inside of the database
				foundIndex = i;
				found = true;
			}
		}

		if (found == true) {
			System.out
					.println("Student with the last name of: " + len + " has the id of: " + Student.ID.get(foundIndex));
		} else {
			System.out.println("Student could not be found.");
		}

	}

	/**
	 * Prints the current database to the user
	 */
	public static void print() {
		System.out.printf("%-10s%10s%20s%20s", "Name", "ID", "Program", "Year");
		for (int i = 0; i < Student.Fname.size(); i++) {
			System.out.println();
			String FullName = Student.Fname.get(i) + " " + Student.Lname.get(i);
			System.out.printf("%-10s%10s%20s%14s", FullName, Student.ID.get(i), Student.Program.get(i),
					Student.Year.get(i));
		}
	}
}

class Student {
	static ArrayList<String> Fname = new ArrayList<String>();
	static ArrayList<String> Lname = new ArrayList<String>();
	static ArrayList<String> ID = new ArrayList<String>();
	static ArrayList<String> Program = new ArrayList<String>();
	static ArrayList<String> Year = new ArrayList<String>();

	Student() {
	}

	/**
	 * populates the arrays to be used throughout the program with 
	 * the data given from the text file.
	 */
	static void populate() {
		String FileName = "student.txt";
		String[] lineArray;
		try {
			int j = 0;
			FileInputStream F = new FileInputStream(FileName);
			Scanner in = new Scanner(F);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				lineArray = line.split(",");
				for (int i = 0; i < lineArray.length; i++) {
					j++;
					if (j == 1) {
						Lname.add(lineArray[i]);
					} else if (j == 2) {
						Fname.add(lineArray[i]);
					} else if (j == 3) {
						ID.add(lineArray[i]);
					} else if (j == 4) {
						Program.add(lineArray[i]);
					} else if (j == 5) {
						Year.add(lineArray[i]);
					}
				}
				lineArray = null;
				j = 0;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: File has not been found");
		}

	}
}
