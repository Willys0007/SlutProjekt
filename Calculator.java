
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {


    public static char operator() {
        System.out.println("Choose a operator, either '+' '-' '/' '*':  ");
        char[] operators = {'+', '-', '/', '*'};


        char selectedOperator = 0;
        Scanner scan = new Scanner(System.in);
        char operator = scan.next().charAt(0);
        //Return the operator you chose.
        for (int i = 0; i < operators.length; i++) {
            if (operator == operators[i]) {
                return selectedOperator += operators[i];
            }
        }
        return selectedOperator;
    }


    public static double addNumber() {
        Scanner scannn = new Scanner(System.in);
        //Adds a number
        System.out.println("Input a number: ");
        return scannn.nextDouble();
    }


    public static int operatorOrNumberOrGetResultFirst() {
        Scanner scan = new Scanner(System.in);
        System.out.println("'1' is to add a number, '2' is to add a operator, '3' is to get the result");
        int whichOperatorIsFirst = scan.nextInt();
        //Checks that the OPERATION (not operator) is valid)
        while ((whichOperatorIsFirst != 1) && (whichOperatorIsFirst != 2) && (whichOperatorIsFirst != 3)) {
            System.out.println("Input a viable value: ");
            whichOperatorIsFirst = scan.nextInt();

        }
        if (whichOperatorIsFirst == 1) {
            return 1;
        } else if (whichOperatorIsFirst == 2) {
            return 2;
        } else {
            return 3;
        }

    }


    public static String keepAskingUserForInput() {
        //make a stringbuilder object to concatenate, then convert back to a string and exit out of loop by using return statement
        StringBuilder stringToCalculate = new StringBuilder();

        while (true) {
            int operatorOrNumberOrResult = Calculator.operatorOrNumberOrGetResultFirst();

            switch (operatorOrNumberOrResult) {
                case 1:
                    //Select number
                    double initialNumber = Calculator.addNumber();
                    stringToCalculate.append(initialNumber).append(" ");
                    break;

                case 2:
                    //Select operator
                    char initialOperator = Calculator.operator();
                    stringToCalculate.append(initialOperator).append(" ");
                    break;

                case 3:
                    //returns the string that eval calculates.
                    return stringToCalculate.toString();

                default:
                    //In case 1 or 2 is not put in, it asks again.
                    System.out.println("Input a valid number, either '1' or '2': ");

            }
        }
    }
}





