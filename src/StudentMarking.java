import java.util.Scanner;
// DO NOT import anything else

/**
 * This class forms Java Assignment 1, 2021
 */
public class StudentMarking {

    /**
     * The message that the main menu must display --- DO NOT CHANGE THIS
     */
    public final String MENU_TEMPLATE =
            "%nWelcome to the Student System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a student ID%n"
                    + "2. Capture marks for students%n"
                    + "3. List student IDs and average mark%n";
    /**
     * DO NOT CHANGE THIS
     */
    public final String NOT_FOUND_TEMPLATE =
            "No student id of %s exists";


   /* Do NOT change the two templates ABOVE this comment.
      DO CHANGE the templates BELOW.
   */

    // TODO (All questions) - Complete these templates which will be used throughout the program
    public final String ENTER_MARK_TEMPLATE = "Please enter mark %d for student %s%n";
    public final String STUDENT_ID_TEMPLATE = "Your studID is %s";
    public final String NAME_RESPONSE_TEMPLATE = "You entered a given name of %s and a surname of %s";
    public final String LOW_HIGH_TEMPLATE = "Student %s has a lowest mark of %d%nA highest mark of %d%n";
    public final String AVG_MARKS_TEMPLATE = "Student ***%s*** has an average of %5.2f%n";
    public final String COLUMN_1_TEMPLATE = "   %c";
    public final String COLUMN_2_TEMPLATE = "           %c%n";
    public final String CHART_KEY_TEMPLATE = "Highest     Lowest%n   %d           %d%n";
    public final String REPORT_PER_STUD_TEMPLATE = "| Student ID   %d is %s | Average is %5.2f |%n";

    /**
     * Creates a student ID based on user input
     *
     * @param sc Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @return a studentID according to the pattern specified in {@link StudentMarking#STUDENT_ID_TEMPLATE}
     */
    public String generateStudId(Scanner sc) {
        // TODO (3.4) - Complete the generateStudId method which will allow a user to generate a student id

        System.out.printf(
                "Please enter your given name and surname (Enter 0 to return to main menu)%n");
        String name = sc.nextLine();
        if (name.equals("0"))
            return null;
        else {
            String[] splitName = name.split(" ");
            System.out.printf(NAME_RESPONSE_TEMPLATE + "%n", splitName[0], splitName[1]);

            char firstNameInitial = Character.toUpperCase(splitName[0].charAt(0));
            char surnameInitial = Character.toUpperCase(splitName[1].charAt(0));
            int surnameLength = splitName[1].length();
            char middleFirstName = splitName[0].charAt(splitName[0].length() / 2);
            char middleSurname = splitName[1].charAt(surnameLength / 2);
            String studId = "" + firstNameInitial +
                    surnameInitial +
                    (String.format("%02d", surnameLength)) +
                    middleFirstName +
                    middleSurname;
            System.out.printf(STUDENT_ID_TEMPLATE, studId);
            return studId;
        }
    }

    /**
     * Reads three marks (restricted to a floor and ceiling) for a student and returns their mean
     *
     * @param sc     Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @return the mean of the three marks entered for the student
     */
    public double captureMarks(Scanner sc, String studId) {
        // TODO (3.5) - Complete the captureMarks method which will allow a user to input three mark for a chosen student
        // DO NOT change MAX_MARK and MIN_MARK
        final int MAX_MARK = 100;
        final int MIN_MARK = 0;

        int[] mark = new int[3];
        for (int x = 0; x < 3; ++x) {
            System.out.printf(ENTER_MARK_TEMPLATE, x + 1, studId);
            mark[x] = sc.nextInt();
            sc.nextLine();
            while (mark[x] < MIN_MARK || mark[x] > MAX_MARK) {
                System.out.printf(ENTER_MARK_TEMPLATE, x + 1, studId);
                mark[x] = sc.nextInt();
                sc.nextLine();
            }
        }

        int low;
        int high;
        if ((mark[0] < mark[1]) && (mark[0] < mark[2]))
            low = mark[0];
        else if ((mark[1] < mark[0]) && (mark[1] < mark[2]))
            low = mark[1];
        else
            low = mark[2];

        if ((mark[0] > mark[1]) && (mark[0] > mark[2]))
            high = mark[0];
        else if ((mark[1] > mark[0]) && (mark[1] > mark[2]))
            high = mark[1];
        else
            high = mark[2];
        System.out.printf(LOW_HIGH_TEMPLATE, studId, low, high);

        double avg = Double.valueOf(mark[0] + mark[1] + mark[2]) / 3;
        System.out.printf(AVG_MARKS_TEMPLATE, studId, avg);

        System.out.printf("Would you like to print a bar chart? [y/n]%n");
        String answer = sc.nextLine();
        if (answer.equals("y") || answer.equals("Y"))
            printBarChart(studId, high, low);
        return avg;
    }

    /**
     * outputs a simple character-based vertical bar chart with 2 columns
     *
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @param high   a student's highest mark
     * @param low    a student's lowest mark
     */
    public void printBarChart(String studId, int high, int low) {
        // TODO (3.6) - Complete the printBarChart method which will print a bar chart of the highest and lowest results of a student
        System.out.printf("Student id statistics: %s%n", studId);
        final char bar = '*';

        int diff = high - low;
        for (int i = 1; i <= diff; ++i)
            System.out.printf(COLUMN_1_TEMPLATE + "%n", bar);
        for (int x = 1; x <= low; ++x)
            System.out.printf(COLUMN_1_TEMPLATE + COLUMN_2_TEMPLATE, bar, bar);
        System.out.printf(CHART_KEY_TEMPLATE, high, low);
    }

    /**
     * Prints a specially formatted report, one line per student
     *
     * @param studList student IDs originally generated by {@link StudentMarking#generateStudId(Scanner)}
     * @param count    the total number of students in the system
     * @param avgArray mean (average) marks
     */
    public void reportPerStud(String[] studList,
                              int count,
                              double[] avgArray) {
        // TODO (3.7) - Complete the reportPerStud method which will print all student IDs and average marks
        for (int i = 0; i < count; ++i) {
            System.out.printf(REPORT_PER_STUD_TEMPLATE, i + 1, studList[i], avgArray[i]);
        }
    }

    /**
     * The main menu
     */
    public void displayMenu() {
        // TODO (3.2) - Complete the displayMenu method to use the appropriate template with printf
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a {@link Scanner} that reads from {@link System#in}.
     *
     * @param args Command-line parameters (ignored)
     */
    public static void main(String[] args) {
        // TODO (3.3) - Complete the main method to give the program its core

        // DO NOT change sc, sm, EXIT_CODE, and MAX_STUDENTS
        Scanner sc = new Scanner(System.in);
        StudentMarking sm = new StudentMarking();
        final int EXIT_CODE = 0;
        final int MAX_STUDENTS = 5;

        // TODO Initialise these
        String[] keepStudId = new String[5];
        double[] avgArray = new double[5];

        // TODO Build a loop around displaying the menu and reading then processing input
        int noOfStudentId = 0;
        boolean loopMenu = true;

        while (loopMenu) {
            sm.displayMenu();
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case EXIT_CODE:
                    loopMenu = false;
                    break;
                case 1:
                    if (noOfStudentId == MAX_STUDENTS)
                        System.out.print("You cannot add any more students to the array");
                    else
                        keepStudId[noOfStudentId] = sm.generateStudId(sc);
                    ++noOfStudentId;
                    break;
                case 2:
                    System.out.printf(
                            "Please enter the studId to capture their marks (Enter 0 to return to main menu)%n");
                    String studId = sc.nextLine();

                    boolean found = false;
                    for (int x = 0; x < keepStudId.length; ++x) {
                        if (studId.equals(keepStudId[x])) {
                            avgArray[x] = sm.captureMarks(sc, studId);
                            found = true;
                        }
                    }
                    if (!found)
                        System.out.printf(sm.NOT_FOUND_TEMPLATE, studId);
                    break;
                case 3:
                    sm.reportPerStud(keepStudId, noOfStudentId, avgArray);
                    break;
                default:
                    System.out.printf(
                            "You have entered an invalid option. Enter 0, 1, 2 or 3%n");// Skeleton: keep, unchanged
            }
        }
        System.out.printf("Goodbye%n");
    }
}

/*
    TODO Before you submit:
         1. ensure your code compiles
         2. ensure your code does not print anything it is not supposed to
         3. ensure your code has not changed any of the class or method signatures from the skeleton code
         4. check the Problems tab for the specific types of problems listed in the assignment document
         5. reformat your code: Code > Reformat Code
         6. ensure your code still compiles (yes, again)
 */