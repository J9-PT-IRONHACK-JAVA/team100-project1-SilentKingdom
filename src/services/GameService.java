package services;

import model.Army;

import java.util.Scanner;

public class GameService {

    static Scanner scanner = new Scanner(System.in);

    static Army army;

    public static int readInt(String prompt, int userChoices){
        int input;
        do {
            System.out.println(prompt);
            try {
                input = Integer.parseInt(scanner.next());
            }catch (Exception e){
                input = -1;
                System.out.println("Please enter an integer!");
            }
        }while (input < 1 || input > userChoices );
        return input;
    }

    //method to simulate clearing out the console
    public static void cleanConsole(){
        for (int i = 0; i < 100; i++)
            System.out.println();

    }

    //method to print a heading
    public static void printHeading(String title){
        printSeparator(30);
        System.out.println(title);
        printSeparator(30);

    }

    public static void printSeparator(int n){
        for (int i = 0; i < n; i++)
            System.out.print("-");
        System.out.println();
    }

    public static void anythingToContinue(){
        System.out.println("\nEnter anything to continue...");
        scanner.next();
    }

    public void startGame(String prompt, int userChoices){

        // Standard input and out (interactive part)

        boolean nameSet = false;
        String name;
        cleanConsole();
        printSeparator(40);
        printSeparator(30);
        System.out.println
                ();
        printSeparator(30);
        printSeparator(40);
        anythingToContinue();

        do {
            cleanConsole();
            printHeading("What's your name?");
            name = scanner.next();
            cleanConsole();
            printHeading("Your name is " + name + "\nIs that correct?");
            System.out.println("(1) Yes!");
            System.out.println("(2) No, I want to change my name.");
            int input = readInt("-> ", 2);
            if (input == 1){
                nameSet = true;
            }
        }while (!nameSet);

//        army = new Army(name, );   Mirar como aplicar una clase de Player para despues importarle
//        el Army

        // Choose how to instantiate armies: Import or Introduce size, attributes?

        // Import / Instantiate army's (Ask for Army's names, choose side, etc)

        // Choose war modality? Random, PlayerVsPlayer, PlayerVsBot?

        // Start war modality instantiate and start WarService


        char[][] grid = {{'X', 'O', 'O'}, {'X', 'X', 'O'}};

        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},{' ', '|', ' ', '|', ' '}};


        //mirar el largo del csv y mirar si son warriors o wizards;
        //clase Army y aplicar la grid
        //Army length - isAlive -
        //Transormar Status en una interfaz visual
        //mirar el largo del csv y mirar si son warriors o wizards;


        gameBoard[0][0] = (char) grid [0][0];
        gameBoard[0][2] = (char) grid [0][1];
        gameBoard[0][4] = (char) grid [0][2];
        gameBoard[2][0] = (char) grid [1][0];


        for(char[] row : gameBoard){
            for (char c : row){
                System.out.print(c);;
            }
            System.out.println();
        }
    }

    public void exitGame(){}
}
