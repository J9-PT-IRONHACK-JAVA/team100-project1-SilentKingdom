package services;

import repository.Repository;
import repository.RepositoryCsv;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class GameService {
    private static final String RANDOM_MODE = "random";
    private static final String PVP_MODE = "PVP";
    private static final String PVB_MODE = "PVB";




    //TODO mirar de cambiar el fondo de color
    public static final String colorDeRosa
            = "\u001B[41m";
    public static final String resetColor = "\u001B[0m";

    char[][] gameBoard = new char[10][10];

    int start = 1;

    static Scanner scanner = new Scanner(System.in);
    Point location = new Point(0,0);

    char warrior = 'X';
    char mage = 'O';

    public static final String ANSI_BG_GREEN  = "\u001B[42m";

    public static final String ANSI_GREEN = "\u001B[32m";
    public static void printGrid(RepositoryCsv repo) throws Exception {

        var armiesNames = repo.getDistinctArmies();
//
        var lightArmy = repo.getArmyCombatants(armiesNames[0]);
        var darkArmy = repo.getArmyCombatants(armiesNames[1]);

        System.out.println(colorDeRosa + "\t" + "\t"+ "Army 1" + "\t"+ "\t" + "\t" + resetColor);
        var gridPoss = "\t" + Arrays.toString(new char[]{' '});
        for (int i = 0; i < lightArmy.size(); i++) {
                System.out.println(gridPoss);
        }

//    public static void isAliveColor(){
//            for (int i = 0; i < lightArmy.size(); i++) {
//                if (RepositoryCsv.IS_ALIVE == false){
//
//                }
//            }
//       }
//    }

//    public void startGame(String prompt, int userChoices){

        // Standard input and out (interactive part)

//        army = new Army(name, );   Mirar como aplicar una clase de Player para despues importarle
//        el Army

        // Choose how to instantiate armies: Import or Introduce size, attributes?

        // Import / Instantiate army's (Ask for Army's names, choose side, etc)

        // Choose war modality? Random, PlayerVsPlayer, PlayerVsBot?

        // Start war modality instantiate and start WarService


        char[][] grid = {{'X', 'O', 'O'}, {'X', 'X', 'O'}};



        //mirar el largo del csv y mirar si son warriors o wizards;
        //clase Army y aplicar la grid
        //Army length - isAlive -
        //Transormar Status en una interfaz visual
        //mirar el largo del csv y mirar si son warriors o wizards;


//        gameBoard[0][0] = (char) grid [0][0];
//        gameBoard[0][2] = (char) grid [0][1];
//        gameBoard[0][4] = (char) grid [0][2];
//        gameBoard[2][0] = (char) grid [1][0];
//
//
//        for(char[] row : gameBoard){
//            for (char c : row){
//                System.out.print(c);;
//            }
//            System.out.println();
//        }


    }

    public void exitGame(){}
}
