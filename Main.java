import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;


public class Main {


    public static void main(String[] args) throws ScriptException {

        //Make random object for AI
        Random random = new Random();


        System.out.println("Do you want to play a game of your choice [Y/N] ");
        Scanner scan = new Scanner(System.in);


        while (true) { //Loop which runs the whole game
            String startGameOrNot = scan.next();
            if (startGameOrNot.equalsIgnoreCase("y")) { //Ask if they want to play
                int correctGame = gameSelection();

                if (correctGame == 1) { //Calculator game
                    Main.runCalculator(scan);
                    break;

                } else if (correctGame == 2) {//Poker game
                    while (true) { //Poker loop that initilizes the values needed through the whole game.
                        Scanner input = new Scanner(System.in);
                        int roundCounter = 0;
                        boolean[] fullyEndGame = {true}; // Used to fully end the poker method.

                        int[] startCapital = Poker.StartSettings.startCapitalForPlayerAndAI(); //important to store the start capital, and update later.
                        int TOTAL_ROUNDS = Poker.StartSettings.amountOfRounds(); //Store rounds for checking for end condition.
                        Main.runPoker(fullyEndGame, startCapital, TOTAL_ROUNDS, roundCounter);
                        System.out.println("Game had ended.");
                        System.out.println("Do you wish to play again? [Y/N]");
                        String playAgain;
                        do { // ask if they want to play again
                            playAgain = input.next();
                        } while (!((playAgain.equalsIgnoreCase("y")) || (playAgain.equalsIgnoreCase("n"))));
                        if (playAgain.equalsIgnoreCase("n")) { //Otherwise break out of loop and end game
                            break;
                        }
                    }
                    System.out.println("Bye bye!");
                    break;
                }
            } else { //Code ends.
                System.out.println("Cya I guess");
                break;
            }
        }
    }

    public static int gameSelection() {
        Scanner scan = new Scanner(System.in);

        int whichgame; //Ask them which game to play
        do {
            System.out.println("Input a value for a game, '1' is a calculator, '2' is poker: ");
            whichgame = scan.nextInt();

        } while (whichgame != 1 && whichgame != 2); //Do this while the numbers are not 1 or 2.
        return whichgame;
    }

    public static void runPoker(boolean[] fullyEndGame, int[] startCapital, int TOTAL_ROUNDS, int roundCounter) {
        double[] playerAndAICapital = Poker.GameState.removePot(startCapital);
        while (fullyEndGame[0] && roundCounter <= TOTAL_ROUNDS) {
            boolean[] endIfOver = {true}; //Use an array as to check if the method should loop.
            boolean isPlayerTurn = true; //Is used to check conditions with doing actions. For example runPlayerOperation.
            int turnCounter = 1; //Increments in every switch statement as a way to manage turns.
            int amountOfCards = 3; //Increments the cards on board at that turn. Increments until it becomes 5 which is the maximum cards on board.


            // Makes a deck and shuffles it.
            ArrayList<String> deckOfCard = Poker.Deck.deckOfCards();
            ArrayList<String> shuffledDeck = Poker.Deck.shuffleDeck(deckOfCard);

            // Draw cards and get cards on board and in hand. Both AI and Player hand are known from the start of the game.
            String[] cardsInHand = Poker.Card.drawPlayerAndAICards(shuffledDeck);
            ArrayList<String> deck = Poker.Deck.removeCardsAfterDraw(shuffledDeck, cardsInHand);
            String[] cardsInPlayerHand = Poker.Card.playerHand(cardsInHand);
            String[] cardsInAIHand = Poker.Card.aiHand(cardsInHand);
            ArrayList<String> cardsOnBoard = Poker.GameState.getCardsOnBoard(deckOfCard);

            //This makes an arrayList with cards in the player hand, which is then used to convert into numbers for suits and ranks.
            ArrayList<String> cardsInPlayerHandArrayList = new ArrayList<>(Arrays.asList(cardsInPlayerHand));
            ArrayList<String> cardsInAIHandArrayList = new ArrayList<>(Arrays.asList(cardsInAIHand));

            //Both cards on board and in the player hand.
            ArrayList<String> cardsInPlayerAndOnBoard = Poker.GameState.getPlayerHandAndBoard(cardsInPlayerHand, cardsOnBoard);
            ArrayList<String> cardsInAIAndOnBoard = Poker.GameState.getPlayerHandAndBoard(cardsInAIHand, cardsOnBoard);
            double totalPot = 0;

            //Parameter placeholders, for example if a parameter is out of reach although is never used, and as such i can avoid an error message.
            ArrayList<String> storeValuesRemoved = new ArrayList<>();
            ArrayList<String> parameterPlaceHolderForString = new ArrayList<>();

            System.out.println("Round " + (roundCounter + 1) + " is starting!");

            while (turnCounter <= 5 && endIfOver[0]) { //This loop exists so that it can loop the poker turns effectivly, and so that i can break out of it without fully ending the poker method.
                switch (turnCounter) {
                    case 1: // Turn 1
                        roundCounter++;
                        //Prints current turn.
                        Poker.GameState.isTurn(turnCounter);
                        //Adds small and big blind from player and AI
                        totalPot = Poker.GameState.smallAndBigBlindPot(startCapital);
                        System.out.println("Small and Big blind has been removed from capital");
                        System.out.println("POT: " + totalPot + "$");
                        System.out.println("Player Capital: "+playerAndAICapital[0]+"$");
                        System.out.println("AI Capital: "+playerAndAICapital[1]+"$");
                        turnCounter++;
                        break;

                    case 2: // Turn 2
                        Poker.GameState.isTurn(turnCounter);
                        System.out.println("Shuffling cards... ");

                        //Prints out current cards in hand.
                        Poker.Player.showPlayerHand(cardsInPlayerHand);
                        //returns the player's chosen operation
                        int playerOperation = Poker.Player.chosenPlayerOperation();


                        // Execute chosen operation:
                        double playerBet = Poker.Player.runPlayerOperation(playerAndAICapital, isPlayerTurn, totalPot, playerOperation, cardsInPlayerHandArrayList, cardsInAIHandArrayList, parameterPlaceHolderForString, parameterPlaceHolderForString, turnCounter, endIfOver, 0);


                        isPlayerTurn = false; //Make it AI turn for alterations to betting. Since betting runs differently depending on this variable.

                        //Returns the AI's action. If they check or fold, the returned value is 0.
                        double AIBetOrRaise = Poker.AI.runAIOperation(playerOperation, playerAndAICapital, isPlayerTurn, totalPot, playerBet, cardsInPlayerHandArrayList, cardsInAIHandArrayList, parameterPlaceHolderForString, parameterPlaceHolderForString, turnCounter, endIfOver);


                        // Update the capital of pot and AI and player:
                        totalPot += Poker.GameState.updatePot(playerBet, AIBetOrRaise);
                        isPlayerTurn = true;//Make it true again incase the AI chooses to go all-in, meaning that the player has to react by either matching or folding, which can only be done on the player turn.

                        //If the player or AI chooses to go all in, this gives out the money to the player and AI.
                        Poker.WinCons.givePotAllIn(playerBet, AIBetOrRaise, totalPot, playerAndAICapital, cardsInPlayerHandArrayList, cardsInAIHandArrayList, parameterPlaceHolderForString, parameterPlaceHolderForString, turnCounter, endIfOver);

                        //Only removes the capital they choose to invest, does not add money as that is done through each method.
                        playerAndAICapital = Poker.GameState.updateCapital(playerBet, AIBetOrRaise, playerAndAICapital, endIfOver, fullyEndGame);
                        turnCounter++;
                        break;


                    case 3, 4, 5: //Turn 3, 4 and 5
                        Poker.GameState.isTurn(turnCounter);

                        //is the amount of cards currently on the board.
                        ArrayList<String> cardsOnBoardProgressionPlayer = Poker.Card.cardsOnBoardProgression(cardsInPlayerAndOnBoard, turnCounter, storeValuesRemoved);
                        ArrayList<String> cardsOnBoardProgressionAI = Poker.Card.cardsOnBoardProgression(cardsInAIAndOnBoard, turnCounter, storeValuesRemoved);

                        //Prints hand and cards on board.
                        Poker.GameState.showCardsOnBoard(deck, turnCounter, amountOfCards);
                        Poker.Player.showPlayerHand(cardsInPlayerHand);

                        Poker.GameState.printCapitalAndPot(playerAndAICapital, totalPot);

                        playerOperation = Poker.Player.chosenPlayerOperation();

                        playerBet = Poker.Player.runPlayerOperation(playerAndAICapital, isPlayerTurn, totalPot, playerOperation, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver, 0);
                        isPlayerTurn = false;

                        AIBetOrRaise = Poker.AI.runAIOperation(playerOperation, playerAndAICapital, isPlayerTurn, totalPot, playerBet, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver);

                        totalPot += Poker.GameState.updatePot(playerBet, AIBetOrRaise);
                        isPlayerTurn = true;

                        Poker.WinCons.givePotAllIn(playerBet, AIBetOrRaise, totalPot, playerAndAICapital, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver);

                        playerAndAICapital = Poker.GameState.updateCapital(playerBet, AIBetOrRaise, playerAndAICapital, endIfOver, fullyEndGame);


                        amountOfCards++;
                        if(turnCounter == 5){ //If it reaches end of turn 5, check who has better cards and restart loop.
                            System.out.println("Checking who wins pot");
                            if(Poker.WinCons.whoWon(cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer,cardsOnBoardProgressionAI, turnCounter, totalPot) == 1){
                                playerAndAICapital[0] += totalPot;
                            }else{
                                playerAndAICapital[1] += totalPot;
                            }
                            endIfOver[0] = false;
                        }
                        turnCounter++;
                        break;
                }
            }
        }
    }

    public static void runCalculator(Scanner scan) throws ScriptException {
        System.out.println("Starting Calculator! ");

        while (true) {
            //Ask for first input from user.
            String stringToCalculate = Calculator.keepAskingUserForInput();

            //Use scriptEngineManager to access the javascript number calculation engine.
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");

            //Calculate the string via this engine.
            Object result = engine.eval(stringToCalculate);
            System.out.println("This is your calculation: ");
            System.out.println(stringToCalculate + "= " + result);
            System.out.println();

            System.out.println("Do you want to input another calculation? [Y/N]");
            String response = scan.next();
            if (response.equalsIgnoreCase("n")) {
                break;
            }
        }

    }
}

