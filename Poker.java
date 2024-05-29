import java.util.*;


public class Poker {

    public static class StartSettings {

        //Here you choose 1. Player and ai pot, 2. Amount of rounds until the game ends(unless capital reaches 0).

        public static int[] startCapitalForPlayerAndAI() {
            Scanner scan = new Scanner(System.in);

            System.out.println("Input the amount of start capital you want the player to have: ");
            int playerCapital = scan.nextInt();
            System.out.println("Input the amount of start capital you want the AI to have: ");
            int aiCapital = scan.nextInt();
            //Choose capital
            while ((aiCapital >= 1000000 || playerCapital >= 1000000) && (playerCapital < 1 || aiCapital < 1)) {
                System.out.println("The values need to exceed $1 and be less than 1 000 000. Please input again.");
                System.out.println("Input Player capital: ");
                playerCapital = scan.nextInt();
                System.out.println("Input AI capital: ");
                aiCapital = scan.nextInt();
            }

            System.out.println("You will have " + playerCapital + "$");
            System.out.println("The AI will have " + aiCapital + "$");

            return new int[]{playerCapital, aiCapital}; //Returns an array of their capital, this is the money that will increment.

        }

        public static int amountOfRounds() {
            Scanner scan = new Scanner(System.in);
            System.out.println("How many rounds do you want? ");
            int rounds = scan.nextInt();
            while (!(rounds > 1)) {
                System.out.println("Input a number greater than 1");
                rounds = scan.nextInt();
            }
            return rounds;
        }

    }


    public static class Deck {
        public static ArrayList<String> deckOfCards() {
            String[] suits = {"HEARTS", "SPADES", "DIAMONDS", "CLUBS"};
            String[] ranks = {"TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING", "ACE"};

            //The reason im using arrayLists is because I do not have to keep making new arrays for every update in the deck.(since they update the size of the array automatically when something is removed).
            ArrayList<String> deck = new ArrayList<>();
            for (String suit : suits) {
                for (String rank : ranks) {
                    deck.add(rank + "-" + suit);
                }
            }
            return deck; //Deck of 52 cards

        }

        public static ArrayList<String> shuffleDeck(ArrayList<String> deck) {
            Collections.shuffle(deck);
            return deck;
        }

        public static ArrayList<String> removeCardsAfterDraw(ArrayList<String> shuffledDeck, String[] drawnCards) {
            for (int i = 0; i < 4; i++) { //Removes the first 4 cards from the deck as they are part of the player and AI's hand.
                shuffledDeck.remove(String.valueOf(drawnCards[i]));
            }
            return shuffledDeck;
        }
    }

    public static class Card {

        //Draws the first 4 cards of the shuffled deck
        public static String[] drawPlayerAndAICards(ArrayList<String> shuffledDeck) {
            String[] cardsInPlayerAndEnemyHand = new String[4];
            for (int i = 0; i < cardsInPlayerAndEnemyHand.length; i++) {
                cardsInPlayerAndEnemyHand[i] = shuffledDeck.get(i);

            }
            return cardsInPlayerAndEnemyHand; //an array with 4 cards, split 2/2 for player and AI
        }

        public static String[] aiHand(String[] cardsInHand) {
            String[] cardsInAIHand = new String[2];
            cardsInAIHand[0] = cardsInHand[2];
            cardsInAIHand[1] = cardsInHand[3];
            return cardsInAIHand;
        }

        public static String[] playerHand(String[] cardsInHand) {
            String[] cardsInPlayerHand = new String[2];
            cardsInPlayerHand[0] = cardsInHand[0];
            cardsInPlayerHand[1] = cardsInHand[1];
            return cardsInPlayerHand;
        }

        //Map Suit and rank to their respective integers.
        //Add onto this by making wincons later

        public static ArrayList<Integer> convertSuitToNum(ArrayList<String> cardsInPlayerAndOnBoard) {
            //Take input array and convert it to number to compare.
            //Will be 5 cards long, 1 for AI and 1 for Player.
            //'1' is hearts, '2' is clubs, '3' is spades, '4' is diamonds.
            //It will return an arraylist like [1,2,1,3,4]. This means they have 2 hearts and no win-conditions met through suits.

            int[] numbersArray = new int[cardsInPlayerAndOnBoard.size()];
            for (int i = 0; i < cardsInPlayerAndOnBoard.size(); i++) {
                String[] splitArray = cardsInPlayerAndOnBoard.get(i).split("-");
                switch (splitArray[1]) {
                    case "HEARTS" -> numbersArray[i] = 1;
                    case "CLUBS" -> numbersArray[i] = 2;
                    case "SPADES" -> numbersArray[i] = 3;
                    default -> numbersArray[i] = 4;
                }
            }
            ArrayList<Integer> numbersArrayAsArrayList = new ArrayList<>();
            for (int j : numbersArray) {
                numbersArrayAsArrayList.add(j);
            }
            return numbersArrayAsArrayList;
        }


        public static ArrayList<Integer> convertRankToNum(ArrayList<String> progressionBoard) {


            int[] numbersArray = new int[progressionBoard.size()];
            for (int i = 0; i < progressionBoard.size(); i++) {
                String[] splitArray = progressionBoard.get(i).split("-");
                //Splits the full card name and extracts the part that states its number. THREE-HEARTS. Splits at the "-" and gets THREE as input.
                switch (splitArray[0]) {
                    case "TWO":
                        numbersArray[i] = 2;
                        break;
                    case "THREE":
                        numbersArray[i] = 3;
                        break;
                    case "FOUR":
                        numbersArray[i] = 4;
                        break;
                    case "FIVE":
                        numbersArray[i] = 5;
                        break;
                    case "SIX":
                        numbersArray[i] = 6;
                        break;
                    case "SEVEN":
                        numbersArray[i] = 7;
                        break;
                    case "EIGHT":
                        numbersArray[i] = 8;
                        break;
                    case "NINE":
                        numbersArray[i] = 9;
                        break;
                    case "TEN":
                        numbersArray[i] = 10;
                        break;
                    case "JACK":
                        numbersArray[i] = 11;
                        break;
                    case "QUEEN":
                        numbersArray[i] = 12;
                        break;
                    case "KING":
                        numbersArray[i] = 13;
                        break;
                    case "ACE":
                        numbersArray[i] = 14;
                        break;
                }
            }
            ArrayList<Integer> numbersArrayAsArrayList = new ArrayList<>();
            for (int item : numbersArray) {
                numbersArrayAsArrayList.add((item));
            }
            //Adds the numbers into the array with ranks.
            return numbersArrayAsArrayList;
        }

        public static ArrayList<String> cardsOnBoardProgression(ArrayList<String> cardsInPlayerAndOnBoard, int turnCounter, ArrayList<String> storeValuesRemoved) {


            if (turnCounter == 3) {
                //Store the last 2 elements in the array.
                storeValuesRemoved.add(cardsInPlayerAndOnBoard.get(5));
                storeValuesRemoved.add(cardsInPlayerAndOnBoard.get(6));
                //Remove those 2 cards
                cardsInPlayerAndOnBoard.remove(6);
                cardsInPlayerAndOnBoard.remove(5);

                return cardsInPlayerAndOnBoard;
            } else if (turnCounter == 4) {
                cardsInPlayerAndOnBoard.add(storeValuesRemoved.get(0));
                return cardsInPlayerAndOnBoard; //Adds the first number stored earlier

            } else if (turnCounter == 5) {
                cardsInPlayerAndOnBoard.add(storeValuesRemoved.get(1));
                return cardsInPlayerAndOnBoard; //Adds the second number stored earlier
            }
            return cardsInPlayerAndOnBoard;
        }
    }

    public static class GameState {

        public static double smallAndBigBlindPot(int[] amountOfCapitalInAIAndPlayer) {
            //take 10 % of big and 5 % of small blind to put into pot.
            int count = 0;
            double[] arrayWithBigAndSmallBlind = new double[2];
            for (int i = 0; i < amountOfCapitalInAIAndPlayer.length; i++) {
                count++;
                if (count == 1) {
                    arrayWithBigAndSmallBlind[i] = amountOfCapitalInAIAndPlayer[i] * 0.05; //Small blind

                } else {
                    arrayWithBigAndSmallBlind[i] = amountOfCapitalInAIAndPlayer[i] * 0.10; //Big blind
                }

            }

            return Arrays.stream(arrayWithBigAndSmallBlind).sum(); //Returns the sum of those numbers
        }

        public static double[] removePot(int[] startCapital) {
            //Index 0 is player
            //Index 1 is AI
            double startCapitalPlayer = startCapital[0];
            double startCapitalAI = startCapital[1];
            double playerMinusPot = startCapitalPlayer - startCapitalPlayer * 0.05;
            double AIMinusPot = startCapitalAI - startCapitalAI * 0.10;

            return new double[]{playerMinusPot, AIMinusPot}; //Updated player and ai capital after removal of pot.


        }

        public static void isTurn(int Turn) {
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("Turn " + Turn + ":");
        }

        public static double[] updateCapital(double returnedPlayerBet, double returnedAIBet, double[] playerAndAiCapital, boolean[] endIfOver, boolean[] fullyEndGame) {
            double[] capital = new double[2];

            capital[0] = playerAndAiCapital[0] - returnedPlayerBet; // Subtract AI's bet first
            capital[1] = playerAndAiCapital[1] - returnedAIBet; // Subtract player's bet second
            if (playerAndAiCapital[1] <= 0 || playerAndAiCapital[0] <= 0) {
                endIfOver[0] = false;
                fullyEndGame[0] = false;
            }
            return capital;
        }

        public static double updatePot(double returnedPlayerBet, double returnedAIBet) {
            //bets are returned then removed from AI and Player's capital
            return returnedAIBet + returnedPlayerBet;
        }

        public static void printCapitalAndPot(double[] playerAndAICapital, double totalPot) {
            System.out.println("The pot is: " + totalPot);
            System.out.println("Player Capital: " + playerAndAICapital[0]);
            System.out.println("AI Capital: " + playerAndAICapital[1]);
        }

        public static ArrayList<String> getCardsOnBoard(ArrayList<String> deck) {
            ArrayList<String> cardsOnBoard = new ArrayList<String>();
            //Loops and gets the first 5 cards on board.
            for (int i = 0; i < 5; i++) {
                String cards = deck.get(i);
                cardsOnBoard.add(cards);
            }
            return cardsOnBoard;
        }

        public static void showCardsOnBoard(ArrayList<String> deck, int turnCounter, int amountOfCards) {
            System.out.println("Card(s) on board: ");
            for (int i = 0; i < amountOfCards; i++) {
                for (int j = 0; j <= turnCounter; j++) {
                    if (i == j) {
                        System.out.print(deck.get(j));

                    }
                }
                System.out.print(" ");
            }
            //prints out the cards on board. Uses amountOfCards to evaluate how many to show.


        }


        public static ArrayList<String> getPlayerHandAndBoard(String[] playerHand, ArrayList<String> cardsOnBoard) {
            //Gets the player hand and board as an array list
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(playerHand));
            arrayList.addAll(cardsOnBoard);
            return arrayList;
            //Cards on board and in player hand.
        }
    }


    public static class WinCons {


        //This class will have a method which takes two arrays - AI and Player cards in hand + cards on board. - after every turn and checks if you have any of the win conditions (such as) pair or three of a kind.
        public static int highCard(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksWithBoardAI, int turnCounter) {
            //1 is win for player
            //0 is win for ai
            //2 is same sum
            //Only needs to run once per turn,
            if (turnCounter == 2) {
                //What the method does is that it converts it into a stream where it then maps each elements value to its integer representation. and then sums it.
                int playerSum = ranks.stream().mapToInt(Integer::intValue).sum();
                int AISum = ranks.stream().mapToInt(Integer::intValue).sum();
                if (playerSum > AISum) {
                    return 1;
                } else if (AISum > playerSum) {
                    return 0;
                } else {
                    return 2;
                }
            } else if (turnCounter == 3 || turnCounter == 4 || turnCounter == 5) {
                int playerSum = ranksWithBoard.stream().mapToInt(Integer::intValue).sum();
                int AISum = ranksWithBoardAI.stream().mapToInt(Integer::intValue).sum();
                if (playerSum > AISum) {
                    return 1;
                } else if (AISum > playerSum) {
                    return 0;
                } else {
                    return 2;
                }
            } else {
                return 0;
            }

        }

        public static int pair(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, int turnCounter) {
            HashMap<Integer, Integer> hash = new HashMap<>();
            switch (turnCounter) {
                case 2:
                    //Loop through ranks and check if the cardrank == rank, add an instance.
                    for (int rank = 2; rank <= 13; rank++) {
                        int occur = 0;
                        for (int cardRank : ranks) {
                            if (cardRank == rank) {
                                occur++;
                            }
                        }
                        if (occur >= 2) { //checks if the instance is >= 2, then add one key-value pair. With occur being amount of x card, and rank being the card's rank.
                            hash.put(rank, occur);
                        }
                    }
                    //0 is no pair,
                    //1 is a pair
                    //2 is two pair
                    //3 is three of a kind
                    //8 is four of a kind
                    //7 is full house
                    //The reason behind the jump is that straight as well as flush are of lower value than full house and four of a kind.
                    if (hash.isEmpty()) {
                        return 0;
                    } else {
                        return 1;
                    }

                case 3, 4, 5:
                    //Same as previous loop, this time we use rank and occur to add their values into a hash map.

                    for (int rank = 2; rank <= 13; rank++) {
                        int occur = 0;
                        for (int cardRank : ranksWithBoard) {
                            if (cardRank == rank) {
                                occur++;
                            }
                        }
                        if (occur >= 2) {
                            hash.put(rank, occur);
                        }
                    }

                    if (hash.isEmpty()) {
                        return 0;
                    } else if (hash.size() == 1) {
                        return 1;
                    } else if (hash.containsValue(3)) {
                        if (hash.size() == 2) { //Two key-value pairs exist in the hash.
                            //fix later
                            return 6;
                        } else {
                            return 3;
                        }
                    } else if (hash.size() == 2) {
                        return 2;
                    } else if (hash.containsValue(4)) {
                        return 7;
                    }
                    break;
            }
            return 6;
        }


        public static boolean straight(ArrayList<Integer> ranksWithBoard, int turnCounter) {
            Collections.sort(ranksWithBoard);

            //Starts at index 0 then becomes index 1 and lastly index 2 for the last turn,
            //straight
            //no straight
            return switch (turnCounter) {
                case 3 ->
                    //5 cards in hand.
                    //So we just need to take the lowest input and check if it corrulates with all other cards in the hand

                    //It checks if the numbers are in a rising order
                        ranksWithBoard.get(0) == ranksWithBoard.get(1) - 1 && ranksWithBoard.get(1) == ranksWithBoard.get(2) - 1 && ranksWithBoard.get(2) == ranksWithBoard.get(3) - 1 && ranksWithBoard.get(3) == ranksWithBoard.get(4) - 1;
                case 4, 5 -> {
                    for (int i = 0; i <= ranksWithBoard.size() - 5; i++) {
                        if (ranksWithBoard.get(i) == ranksWithBoard.get(i + 1) - 1 && ranksWithBoard.get(i + 1) == ranksWithBoard.get(i + 2) - 1 && ranksWithBoard.get(i + 2) == ranksWithBoard.get(i + 3) - 1 && ranksWithBoard.get(i + 3) == ranksWithBoard.get(i + 4) - 1) {
                            yield true;
                        }
                    }
                    yield false;
                }
                default -> false;
            };

        }

        public static boolean flush(ArrayList<Integer> suits, ArrayList<Integer> suitsWithBoard, int turnCounter) {
            //Sort the list from smallest to largest
            Collections.sort(suits);
            switch (turnCounter) {
                case 3:
                    int first = suits.get(0);
                    //If all numbers are the same it returns true, else false
                    //This only works since there is always 5 cards in hand on turn 3.
                    for (int i = 0; i < suitsWithBoard.size(); i++) {
                        if (!Objects.equals(first, i)) {
                            return false;
                        }
                    }
                    return true;
                case 4, 5:
                    //Loop through 5 cards and check if they are of the same suit.
                    for (int i = 0; i < suitsWithBoard.size() - 5; i++) {
                        if (Objects.equals(suitsWithBoard.get(i), suitsWithBoard.get(i + 1)) && Objects.equals(suitsWithBoard.get(i + 1), suitsWithBoard.get(i + 2)) && Objects.equals(suitsWithBoard.get(i + 2), suitsWithBoard.get(i + 3)) && Objects.equals(suitsWithBoard.get(i + 3), suitsWithBoard.get(i + 4)) && Objects.equals(suitsWithBoard.get(i + 4), suitsWithBoard.get(i + 5))) {
                            if (suitsWithBoard.get(i) == 1) {
                                return true;
                            }
                        }
                    }
                    return false;
            }
            return false;
        }

        public static boolean royalFlush(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> suits, ArrayList<Integer> suitsWithBoard, int turnCounter) {
            //Sort from lowest to highest
            Collections.sort(ranksWithBoard);
            Collections.sort(suitsWithBoard);
            if (turnCounter == 3) { //Royal Straight flush
                return ranks.get(0) == 10 && straight(ranksWithBoard, turnCounter) && flush(suits, suitsWithBoard, turnCounter);


            } else if (turnCounter == 4 || turnCounter == 5) {
                for (int i = 0; i < ranksWithBoard.size() - 5; i++) {
                    //Royal flush straight with 6 and 7 cards.
                    //make a sublist made of 5 cards, check if they are in ascending order
                    List<Integer> subList = ranksWithBoard.subList(i, i + 5);
                    List<Integer> subListSuits = suitsWithBoard.subList(i, i + 5);

                    // Convert sublist to ArrayList
                    ArrayList<Integer> subRanksArrayList = new ArrayList<>(subList);
                    ArrayList<Integer> subSuitsArrayList = new ArrayList<>(subListSuits);

                    //Check for royal straight flush
                    if (ranksWithBoard.get(i) == 10 && straight(subRanksArrayList, turnCounter) && flush(subSuitsArrayList, suitsWithBoard, turnCounter)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }

        public static boolean straightFlush(ArrayList<Integer> ranksWithBoard, ArrayList<Integer> suits, ArrayList<Integer> suitsWithBoard, int turnCounter) {
            if (turnCounter == 3) {
                //Simply check if it is a straight and a flush.
                return straight(ranksWithBoard, turnCounter) && flush(suits, suitsWithBoard, turnCounter);
                //Check if it is straightFlush
            } else if (turnCounter == 4 || turnCounter == 5) {
                return straight(ranksWithBoard, turnCounter) && flush(suits, suitsWithBoard, turnCounter);
            }
            return false;
        }


        public static int whoWon(ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgressionPlayer, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, double totalPot) {
            ArrayList<Integer> pointsPlayer = new ArrayList<>();
            ArrayList<Integer> pointsAI = new ArrayList<>();

            ArrayList<Integer> suits = Poker.Card.convertSuitToNum(cardsInPlayerHandArrayList);
            ArrayList<Integer> ranks = Poker.Card.convertRankToNum(cardsInPlayerHandArrayList);
            ArrayList<Integer> suitsAI = Poker.Card.convertSuitToNum(cardsInAIHandArrayList);
            ArrayList<Integer> ranksAI = Poker.Card.convertRankToNum(cardsInAIHandArrayList);

            ArrayList<Integer> suitsWithBoard = Poker.Card.convertSuitToNum(cardsOnBoardProgressionPlayer);
            ArrayList<Integer> ranksWithBoard = Poker.Card.convertRankToNum(cardsOnBoardProgressionPlayer);
            ArrayList<Integer> suitsWithBoardAI = Poker.Card.convertSuitToNum(cardsOnBoardProgressionAI);
            ArrayList<Integer> ranksWithBoardAI = Poker.Card.convertRankToNum(cardsOnBoardProgressionAI);


            //The way this will work is that it will add numbers of increasing value for every condition met
            //For example if player has higher card, it adds integer 1 to array, same with ai.
            //And then this continues, and as the greater power of each hand the larger of an integer is added to the arrayList.
            //Then just check largest number in array to find who wins.
            //If equal refer to high card default.

            if (turnCounter == 2) {

                //Since the player and AI only have 2 cards in hand on turn 2, i only need to check for pair.
                int playerPairs = pair(ranks, ranksWithBoard, turnCounter);
                int aiPairs = pair(ranksAI, ranksWithBoardAI, turnCounter);
                if (playerPairs > aiPairs) {
                    System.out.println("Player won!");
                    return 1;
                } else if (playerPairs < aiPairs) {
                    System.out.println("AI won!");
                    return 0;
                }
            }
            if (turnCounter == 3 || turnCounter == 4 || turnCounter == 5) {
                //Who has higher cards
                int highCardResult = highCard(ranks, ranksWithBoard, ranksWithBoardAI, turnCounter);
                if (highCardResult == 1) {
                    pointsPlayer.add(1);

                } else if (highCardResult == 0) {
                    pointsAI.add(1);
                }//If equal do nothing

                // Who has better pair
                int playerPairs = pair(ranks, ranksWithBoard, turnCounter);
                int aiPairs = pair(ranksAI, ranksWithBoardAI, turnCounter);

                //These add the value received to the array.
                for (int i = 0; i < 9; i++) {
                    if (playerPairs == i) {
                        pointsPlayer.add(playerPairs);
                    }
                }
                for (int i = 0; i < 9; i++) {
                    if (aiPairs == i) {
                        pointsAI.add(aiPairs);
                    }
                }


                //Check if straight
                boolean isStraight = straight(ranksWithBoard, turnCounter);
                boolean isStraightAI = straight(ranksWithBoardAI, turnCounter);

                pointsPlayer.add(isStraight ? 4 : 0);
                pointsAI.add(isStraightAI ? 4 : 0);

                //Check if flush
                boolean isFlush = flush(suits, suitsWithBoard, turnCounter);
                boolean isFlushAI = flush(suitsAI, suitsWithBoardAI, turnCounter);

                pointsPlayer.add(isFlush ? 5 : 0);
                pointsAI.add(isFlushAI ? 5 : 0);

                //Check if straightFlush
                boolean isStraightFlush = straightFlush(ranksAI, suits, suitsWithBoard, turnCounter);
                boolean isStraightFlushAI = straightFlush(ranksAI, suits, suitsWithBoardAI, turnCounter);

                pointsPlayer.add(isStraightFlush ? 8 : 0);
                pointsAI.add(isStraightFlushAI ? 8 : 0);

                //Check if royalFlush
                boolean isRoyalFlush = royalFlush(ranks, ranksWithBoard, suits, suitsWithBoard, turnCounter);
                boolean isRoyalFlushAI = royalFlush(ranks, ranksWithBoardAI, suits, suitsWithBoardAI, turnCounter);

                pointsPlayer.add(isRoyalFlush ? 9 : 0);
                pointsAI.add(isRoyalFlushAI ? 9 : 0);

                //1 is player win
                //0 is AI win
                //2 is draw with perfectly equal cards.
                if (Collections.max(pointsPlayer) > Collections.max(pointsAI)) {
                    System.out.println("The player WON!");
                    return 1;

                } else if (Collections.max(pointsPlayer) < Collections.max(pointsAI)) {
                    System.out.println("The ai WON!");
                    return 0;
                } else {
                    if (highCardResult == 1) {
                        System.out.println("Player Won with Higher cards!");
                        return 1;
                    } else if (highCardResult == 0) {
                        System.out.println("AI won with Higher Cards!");
                        return 0;
                    } else {
                        return 2;
                    }
                }
            }
            return 1;
        }


        public static void givePotAllIn(double playerBet, double AIBetOrRaise, double totalPot, double[] playerAndAICapital, ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgressionPlayer, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, boolean[] endIfOver) {
            //This adds pot, whilst the method update capital removes the bet from their capital.

            if ((playerBet == playerAndAICapital[0]) && (AIBetOrRaise == playerAndAICapital[1])) {
                //Both players go all in
                int whoWon = whoWon(cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, totalPot);
                if (whoWon == 1) {

                    System.out.println("Player won with better cards!");
                    System.out.println(totalPot + "$ has been added to player capital!");

                } else if (whoWon == 0) {

                    System.out.println("AI won with better cards!");
                    System.out.println(totalPot + "$ has been added to AI capital!");


                }
            } else if ((AIBetOrRaise == playerAndAICapital[1])) {
                //Ai went all in, whilst player folded.
                System.out.println("Player gets " + totalPot + "$");
                playerAndAICapital[0] += totalPot;
            } else if ((playerBet == playerAndAICapital[0])) {
                //Player went all in, whilst AI folded
                System.out.println("AI gets " + totalPot + "$");
                playerAndAICapital[1] += totalPot;

            }
        }
    }


    public static class Player {


        public static void showPlayerHand(String[] cardsInPlayerHand) {
            //Only makes it look better
            System.out.println("You drew: " + Arrays.toString(cardsInPlayerHand).replace('[', '(').replace(']', ')'));
        }

        public static int chosenPlayerOperation() {
            //if you act first you can either check or bet, or fold.
            //If you are second to act you can either check, raise or fold or call.
            //Also since the player is always first, there is no need to add a raise or call function for the player.

            Scanner scan = new Scanner(System.in);
            System.out.println("'1' is CHECK, '2' is BET, '3' is FOLD ");
            int action = scan.nextInt();
            while ((action != 1) && (action != 2) && (action != 3)) {
                System.out.println("Invalid input");
                System.out.println("'1' is CHECK, '2' is BET, '3' is FOLD ");
                action = scan.nextInt();
            }

            return action;
        }

        public static double runPlayerOperation(double[] playerAndAICapital, boolean isCurrentlyPlayerTurn, double totalPot, int playerOperation, ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgressionPlayer, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, boolean[] endIfOver, double returnedBet) {
            //initilize double to access it outside the if-statements.
            double betToAddToPot = 0.0;
            if (playerOperation == 1) {
                //Check
                Poker.Player.check();

            } else if (playerOperation == 2) {
                //Bet
                betToAddToPot = Poker.Player.returnTotalBet(playerAndAICapital, isCurrentlyPlayerTurn, totalPot, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver, returnedBet);
                return betToAddToPot;
            } else if (playerOperation == 3) {
                //Fold
                Poker.Player.fold(totalPot, playerAndAICapital, endIfOver);
            }

            return betToAddToPot;
        }


        public static double returnTotalBet(double[] playerAndAICapitalMinusPot, boolean isPlayerTurn, double totalPot, ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgressionPlayer, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, boolean[] endIfOver, double returnedBet) {
            Scanner scan = new Scanner(System.in);
            //1 is 1/4 of pot
            //2 is 1/2 pot
            //3 is all in
            //4 is a personal amount
            if (isPlayerTurn) {
                System.out.println("You have chosen BET");
                System.out.println("Choose what to BET");
                System.out.println("'1' is 1/4-OF-POT, '2' is 1/2-OF-POT, '3' is ALL-IN, '4' is a personal amount: ");
                System.out.println();
                int betAction = scan.nextInt();
                //Keep asking if not 1,2,3 or 4.
                while (betAction != 1 && betAction != 2 && betAction != 3 && betAction != 4) {
                    System.out.println("Invalid input");
                    betAction = scan.nextInt();
                }
                switch (betAction) {
                    case 1:
                        //1/4 of pot
                        if (playerAndAICapitalMinusPot[0] > totalPot * 0.25) {

                            return totalPot * 0.25;

                        }
                    case 2:
                        //1/2 of pot
                        if (playerAndAICapitalMinusPot[0] > totalPot * 0.5) {
                            return totalPot * 0.5;

                        }
                    case 3:
                        //3 all in
                        return playerAndAICapitalMinusPot[0];


                    case 4:
                        //4 is a personal amount
                        System.out.println("How much? ");
                        double personalBet = scan.nextDouble();
                        //Check if the personal amount can be put in.
                        while (personalBet > playerAndAICapitalMinusPot[0]) {
                            System.out.println("The input value exceeds your total capital: ");
                            System.out.println("Your current pot is: " + playerAndAICapitalMinusPot[0] + "$");
                            personalBet = scan.nextDouble();
                        }
                        return personalBet;
                }

            } else {
                Scanner input = new Scanner(System.in);

                //should return 2 values.
                //AI logic
                //AI always goes all in
                System.out.println("The AI chose bet");
                System.out.println("The ai went all in");

                if (returnedBet != playerAndAICapitalMinusPot[0]) {
                    System.out.println("Player choose between matching ALL-IN or Folding: ");

                    double action;
                    do {
                        System.out.println("1 is ALL-IN, 2 is FOLD");
                        action = input.nextDouble();


                    } while (action != 1 && action != 2);
                    if (action == 1) {
                        //Matched going all in.
                        Poker.WinCons.whoWon(cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, totalPot);
                        return playerAndAICapitalMinusPot[1];

                    } else {
                        //Folds, meahas been added to player capital!ning no money is added or removed from pot.
                        System.out.println(totalPot + "$ has been added to AI capital");
                        playerAndAICapitalMinusPot[1] += totalPot;
                        return 0.0;
                    }
                }else{
                    return playerAndAICapitalMinusPot[0];
                }
            }

            return 0;
        }

        public static void check() {
            //Simply passes turn
            System.out.println("CHECK");
        }

        public static void fold(double totalPot, double[] playerAndAICapital, boolean[] endIfOver) {
            //Folds then ends game
            //End game logic needed
            System.out.println("You folded");
            System.out.println(totalPot + "$ Has been given to the AI");
            playerAndAICapital[1] += totalPot;
            endIfOver[0] = false;
            //Add a statement that restarts the game
        }

    }

    public static class AI {
        public static int chosenAIOperation(int playerOperation) {
            Random random = new Random();
            //Returns a number between 0 - 5.
            int randNum;
            //If the player didnt bet, and you still rolled either call or Raise.
            //And if the player didnt check so The ai cant check.
            //Then reassign the random value to a new one
            //Player gets 1: Ai can get everything besides raising
            //Player gets 2: AI can do everything besides checking and betting
            //Player gets 3: AI can do do nothing and wins.

            if (playerOperation == 1) {
                do {
                    randNum = random.nextInt(5) + 1;
                } while (randNum == 4); // Re-roll if the number is 4
            } else if (playerOperation == 2) {
                do {
                    randNum = random.nextInt(5) + 1;
                } while (randNum == 1 || randNum == 2);
            } else {
                return 0;
            }

            return randNum;


        }

        public static double runAIOperation(int playerOperation, double[] playerAndAICapital, boolean isCurrentlyPlayerTurn, double totalPot, double returnedBet, ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgressionPlayer, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, boolean[] endIfOver) {
            //assign the chosen decision of AI
            int aiOperation = Poker.AI.chosenAIOperation(playerOperation);
            //Store the value to return
            double aiBet = 0.0;

            switch (aiOperation) {
                case 1:
                    //Check
                    Poker.AI.checkAI();
                    break;

                case 2:
                    //Bet

                    aiBet = Poker.Player.returnTotalBet(playerAndAICapital, isCurrentlyPlayerTurn, totalPot, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver, returnedBet);
                    return aiBet;


                case 3:
                    //Fold
                    AI.AIFold(playerAndAICapital, totalPot, endIfOver);
                    break;
                case 4:
                    //Raise
                    return Poker.AI.raiseAI(playerAndAICapital, totalPot, returnedBet, isCurrentlyPlayerTurn, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver);


                case 5:
                    //Call
                    return Poker.AI.callAI(playerAndAICapital, returnedBet, totalPot, isCurrentlyPlayerTurn, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgressionPlayer, cardsOnBoardProgressionAI, turnCounter, endIfOver);


            }
            return 0.0;
        }

        public static double raiseAI(double[] playerAndAICapital, double totalPot, double returnedBet, boolean isPlayerTurn, ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgression, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, boolean[] endIfOver) { //It's a raise if someone has already made a bet.
            if (playerAndAICapital[1] > returnedBet) {
                System.out.println("The ai chose: RAISE");
                System.out.println("The ai has raised by " + playerAndAICapital[1] * 0.5);
                return playerAndAICapital[1] * 0.5;
            } else if (playerAndAICapital[1] == returnedBet) { //If they have exactly equal capital as player, the ai calls.
                return callAI(playerAndAICapital, returnedBet, totalPot, isPlayerTurn, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgression, cardsOnBoardProgressionAI, turnCounter, endIfOver);
            } else { //Otherwise it has to go all-in, since it can not call nor raise the capital.
                return Player.returnTotalBet(playerAndAICapital, isPlayerTurn, totalPot, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgression, cardsOnBoardProgressionAI, turnCounter, endIfOver, returnedBet);
            }
        }

        public static double callAI(double[] playerAndAICapital, double returnedBet, double totalPot, boolean isPlayerTurn, ArrayList<String> cardsInPlayerHandArrayList, ArrayList<String> cardsInAIHandArrayList, ArrayList<String> cardsOnBoardProgression, ArrayList<String> cardsOnBoardProgressionAI, int turnCounter, boolean[] endIfOver) { //Can only be done if the prior player has made a bet. (is to match the bet).
            //Match the previous bet. Which is why it checks that you have more or equal to the other players bet
            if (playerAndAICapital[1] >= returnedBet) {
                System.out.println("The AI chose: CALL");
                return returnedBet;
            } else {
                return Player.returnTotalBet(playerAndAICapital, isPlayerTurn, totalPot, cardsInPlayerHandArrayList, cardsInAIHandArrayList, cardsOnBoardProgression, cardsOnBoardProgressionAI, turnCounter, endIfOver, returnedBet);
            }
        }

        public static void checkAI() {
            System.out.println("The AI chose: CHECK");
            System.out.println("Moving onto next turn");//This literally does nothing besides move the turn forward.
        }

        public static void AIFold(double[] playerAndAICapital, double totalPot, boolean[] endIfOver) {
            System.out.println("The AI chose: FOLD");
            System.out.println(totalPot + "$ Has been given to the other player");
            playerAndAICapital[0] += totalPot; //Pot gets added to player
            endIfOver[0] = false;
        }
    }


}






