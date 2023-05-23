//This program was made by Alenna for the CTE course "Software & Programming Development 2" instructed by Mr. Gross
//Email - alenna.castaneda@oneidaihla.org
//This is the Driver file

//importing required classes
import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    //setting attributes
    static int day = 0;
    static int night = 1;
    static boolean gameOver = false;
    static String leftOff = "";

    //getting players arraylist
    ArrayList<Player> players = Player.getPlayers();

    // creating/getting base classes
    static Rule ruleOB = new Rule();
    static Role roleOB = new Role();
    static Hint hintOB = new Hint();
    static Player playerOB = new Player();
    static Note noteOB = new Note();
    static Vote voteOB = new Vote();
    static Game gameOB = new Game();

    //creating methods to get gameOB & voteOB
    public static Game getGameOB() {return gameOB;}
    public static Vote getVoteOB() {return voteOB;}

    //getting players
    static Player John = Player.getJohn();
    static Player Maria = Player.getMaria();
    static Player Vincent = Player.getVincent();
    static Player Gerald = Player.getGerald();
    static Player Regan = Player.getRegan();
    static Player User = Player.getUser();

    public void test() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).name);
        }
    }

    public void testInd() {
        System.out.println(John.name);
        System.out.println(Maria.name);
        System.out.println(Vincent.name);
        System.out.println(Gerald.name);
        System.out.println(Regan.name);
        System.out.println(User.name);
    }

    //getting rules
    static Rule basicRule = Rule.getRB();
    static Rule villagerRule = Rule.getRV();
    static Rule doctorRule = Rule.getRD();
    static Rule psychicRule = Rule.getRP();
    static Rule werewolfRule = Rule.getRW();

    //getting roles
    static Role villagerRole = Role.getVillager();
    static Role doctorRole = Role.getDoctor();
    static Role psychicRole = Role.getPsychic();
    static Role werewolfRole = Role.getWerewolf();

    //getting traits arraylist
    ArrayList<Rule> traits = Hint.getTraits();

    //getting werewolfBanished boolean
    Boolean werewolfBanished = Vote.getWerewolfBanished();

    //creating log objects
    static Log john = new Log(John);
    static Log maria = new Log(Maria);
    static Log vincent = new Log(Vincent);
    static Log gerald = new Log(Gerald);
    static Log regan = new Log(Regan);
    static Log user = new Log(User);
    static Log gg = new Log(gameOB, voteOB);

    //creating method to get gg
    public static Log getGG() {return gg;}
    
    //creating scanner that reads from System.in
    static Scanner scan = new Scanner(System.in);

    //creating game & vote log file
    File gvg = new File("Logs/gameLog.txt");

    //creating search object
    static Search search = new Search();

    //setting up game (part 1)
    public void setUp1() {
        //adding players to arraylist
        players.add(John);
        players.add(Maria);
        players.add(Vincent);
        players.add(Gerald);
        players.add(Regan);
        players.add(User);

        //printing rules
        Rule.print(basicRule);
        System.out.println();
        //setting up player traits
        hintOB.setT();
        //setting up hints & Locations
        hintOB.setHints();
        hintOB.setLocations();

        System.out.println("Welcome Player!");
        //creating attributes
        boolean valid = false;
        int input;

        //looping 'til input is valid
        while (!valid) {
            try {
                System.out.println("Press 0 to start a new game. Press 1 to load the last game.");
                //getting input from user
                input = scan.nextInt();
                //consuming leftover line
                scan.nextLine();

                //if input is 1 or 0
                if (input == 1 || input == 0) {
                    valid = true;
                    //creating switch case based on input
                    switch (input) {
                        case 0:
                            newGame();
                            break;
                        case 1:
                            loadGame();
                            break;
                    }
                //input is invalid
                } else {
                    System.out.println();
                    System.out.println("-=-Error-=-");
                    System.out.println("Please enter a whole number from the displayed list.");
                    System.out.println();
                }
            //catching exception
            } catch (InputMismatchException i) {
                //consuming leftover line
                scan.nextLine();
                System.out.println();
                System.out.println("-=-Error-=-");
                System.out.println("Please enter a whole number from the displayed list.");
                System.out.println();
            }
        }
    }

    //setting up game (part 2)
    public void setUp2(String choice) {
        //  setting user rules
        //creating switch case based on user role
        switch (User.role.title) {
            case "Werewolf":
                User.rule = werewolfRule;
                break;
            case "Doctor":
                User.rule = doctorRule;
                break;
            case "Psychic":
                User.rule = psychicRule;
                break;
            default:
                User.rule = villagerRule;
        }

        //printing user role
        Rule.print(User.rule);

        //looping through traits arraylist
        for (int i = 0; i < traits.size(); i++) {
            //if current trait = user trait
            if (traits.get(i).label == User.trait) {
                //printing user trait
                System.out.println(traits.get(i).msg);
            }
        }

        //if player chose to load last game
        if (choice == "load") {
            catchUp();
        //player chose to create new game
        } else {
            play();
        }
    }

    //continuing set up for new game
    public void newGame() {
        //clearing logs
        gg.gameClear();
        john.playerClear();
        maria.playerClear();
        vincent.playerClear();
        gerald.playerClear();
        regan.playerClear();
        user.playerClear();
        gg.hintClear();

        //assigning player roles
        roleOB.assign();

        //assigning player traits
        hintOB.assignT();

        //  setting hints for each player
        //looping through players
        for (int i = 0; i < players.size(); i++) {
            //assigning hints
            hintOB.assignHints(players.get(i));
        }

        for (int j = 0; j < players.size(); j++) {
            //adding suspects
            players.get(j).notes.addSuspects();
        }

        //finishing set up
        setUp2("new");
    }

    //continuing set up for last saved game
    public void loadGame() {
        //if game log file is NOT empty
        if (gvg.length() != 0) {
                //getting saved game's progress
            gg.gameRead();
            john.playerRead();
            maria.playerRead();
            vincent.playerRead();
            gerald.playerRead();
            regan.playerRead();
            user.playerRead();

            //looping through players arraylist
            for (int i = 0; i < players.size();) {
                //add suspects
                players.get(i).notes.addSuspects();
                //assigning player hints
                hintOB.assignHints(players.get(i));
                //if current player was banished or eliminated
                if (players.get(i).eliminated || players.get(i).banished) {
                    //resetting i (to start at beginning of arraylist)
                    i = 0;
                    //updating var (to keep psychic from guessing an inactive player)
                    players.get(i).checked = true;
                    //removing player from arraylist
                    players.remove(i);
                //player is still active
                } else {
                    //moving on to next player
                    i++;
                }
            }

            //finishing set up
            setUp2("load");
        //automatically creating new game
        } else {
            System.out.println();
            System.out.println("---Error---");
            System.out.println("Game log is empty. Starting new game.");
            newGame();
        }
    }

    //giving user option to leave game
    public void exitOption(String place) {
        //creating attributes
        boolean valid = false;
        //int input = 0;

        //looping 'til input is valid
        while (!valid) {
            try {
                System.out.println("Would you like to leave the game?");
                System.out.println("Press 1 to save your progress and leave the game. Press 0 to continue.");
                
                //getting input from user
                int input = scan.nextInt();
                //consuming leftover line
                scan.nextLine();

                //if input is 1 or 0
                if (input == 1 || input == 0) {
                    valid = true;
                    //creating switch case based on input
                    switch (input) {
                        case 0:
                            System.out.println();
                            System.out.println("Returning to game...");
                            //creating switch case based on place
                            switch (place) {
                                case "perk":
                                    playerOB.userPerk();
                                    break;
                                case "vote":
                                    //letting user choose who to vote
                                    voteOB.userCast();
                            }
                            
                            break;
                        case 1:
                            //updating attribute
                            Game.leftOff = place;
                            saveAndQuit();
                            break;
                    }
                //input is invalid
                } else {
                    System.out.println();
                    System.out.println("-=-Error-=-");
                    System.out.println("Please enter a whole number from the displayed list.");
                    System.out.println();
                }
            //catching exception
            } catch (InputMismatchException i) {
                //consuming leftover line
                scan.nextLine();
                System.out.println();
                System.out.println("-=-Error-=-");
                System.out.println("Please enter a whole number from the displayed list.");
                System.out.println();
            }
        }
    }

    //saving and ending game
    public void saveAndQuit() {
        //writing logs
        gg.gameWrite();
        john.playerWrite();
        maria.playerWrite();
        vincent.playerWrite();
        gerald.playerWrite();
        regan.playerWrite();
        user.playerWrite();

        System.out.println();
        System.out.println("Progress has been saved.");
        System.out.println("Thanks for playing!");

        //exiting program
        System.exit(0);
    }

    //playing turn
    public void turn(Role role) {
        //looping through players
        for (int i = 0; i < players.size(); i++) {
            //if current player is werewolf
            if (players.get(i).role == role) {
                //creating switch case based on player
                switch (players.get(i).name) {
                    case "User":
                            //giving player option to exit
                            exitOption("perk");
                            break;
                    default:
                            playerOB.compPerk(players.get(i));
                            break;
                }
            }
        }
    }

    //checking which player was eliminated and resetting player values
    public void eliminationCheck() {
        //looping through players
        for (int i = 0; i < players.size(); i++) {
            //if player was hunted & not healed
            if (players.get(i).hunted == true && players.get(i).saved == false) {
                //updating var
                players.get(i).eliminated = true;
                System.out.println(players.get(i).name+" was eliminated by the werewolf.");
            } else if (players.get(i).hunted == true && players.get(i).saved == true) {
                System.out.println(players.get(i).name+" was saved from the werewolf.");
            }

            //resetting player values
            players.get(i).hunted = false;
            players.get(i).saved = false;

            //removing player if eliminated == true
            if (players.get(i).eliminated == true) {
                //confirming player is NOT the werewolf
                players.get(i).checked = true;
                        //is user was eliminated
                if (players.get(i) == User && User.eliminated == true) {
                    //setting attribute
                    boolean valid = false;

                    System.out.println();
                    System.out.println("You have been eliminated!");
                    System.out.println("Press 1 to watch the rest of the game, Press 2 to exit the game.");
                    System.out.println();

                    while (!valid) {
                        try {
                            //attempting to get input from user
                            int input = scan.nextInt();
                            //consuming leftover line
                            scan.nextLine();

                            //if input is invalid
                            if (input != 1 && input != 2) {
                                System.out.println();
                                System.out.println("-=-Error-=-");
                                System.out.println("Please enter a whole number from the displayed list.");
                                System.out.println();
                            } else {
                                //updating var
                                valid = true;
                                //if input is 2
                                if (input == 2) {
                                    saveAndQuit();
                                }
                            }
                        } catch (Exception e) {
                            //consuming leftover line
                            scan.nextLine();
                            System.out.println();
                            System.out.println("-=-Error-=-");
                            System.out.println("Please enter a whole number from the displayed list.");
                            System.out.println();
                        }
                    }
                }
                //removing player fromm arraylist
                players.remove(i);
            }
        }

        
    }

    //checking if player was banished
    public void banishmentCheck() {
        //looping through players
        for (int i = 0; i < players.size(); i++) {
            //if player was banished
            if (players.get(i).banished == true) {
                System.out.println(players.get(i).name+" was banished from the village.");
            }

            //removing player if banished == true
            if (players.get(i).banished == true) {
                //keeping psychic from guessing inactive player
                players.get(i).checked = true;
                //if werewolf was banished
                if (players.get(i).role.title == "Werewolf") {
                    //updating var
                    voteOB.werewolfBanished = true;
                }
                //is user was banished
                if (players.get(i) == User && User.banished == true) {
                    //setting attribute
                    boolean valid = false;

                    System.out.println();
                    System.out.println("You have been banished!");
                    System.out.println("Press 1 to watch the rest of the game, Press 2 to exit the game.");
                    System.out.println();

                    while (!valid) {
                        try {
                            //attempting to get input from user
                            int input = scan.nextInt();
                            //consuming leftover line
                            scan.nextLine();

                            //if input is invalid
                            if (input != 1 && input != 2) {
                                System.out.println();
                                System.out.println("-=-Error-=-");
                                System.out.println("Please enter a whole number from the displayed list.");
                                System.out.println();
                            } else {
                                //updating var
                                valid = true;
                                //if input is 2
                                if (input == 2) {
                                    saveAndQuit();
                                }
                            }
                        } catch (Exception e) {
                            //consuming leftover line
                            scan.nextLine();
                            System.out.println();
                            System.out.println("-=-Error-=-");
                            System.out.println("Please enter a whole number from the displayed list.");
                            System.out.println();
                        }
                    }
                }
                //removing player fromm arraylist
                players.remove(i);
            }
        }

        
    }

    //catching up to play function (loaded game only)
    public void catchUp() {
        System.out.println("=================");
        System.out.println("Night "+night);
        System.out.println("=================");
        System.out.println();
        //if leftOff = perk
        if (Game.leftOff.contains("vote") == false && Game.leftOff.contains("perk")) {
                //creating switch case based on user's role
                switch (User.role.title) {
                    case "Werewolf":
                        //running werewolf's turn
                        turn(werewolfRole);
                        System.out.println("The werewolf has come out.");
                        //running doctor's turn
                        turn(doctorRole);
                        System.out.println("The doctor has healed a patient.");
                        break;
                    case "Doctor":
                        System.out.println("The werewolf has come out.");
                        //running doctor's turn
                        turn(doctorRole);
                        System.out.println("The doctor has healed a patient.");
                        break;
                    case "Psychic":
                        System.out.println("The werewolf has come out.");
                        System.out.println("The doctor has healed a patient.");
                        break;
                }
                //running psychic's turn
                turn(psychicRole);
                System.out.println("The psychic has read the truth.");
                System.out.println();

                //updating night var
                night++;
                System.out.println("=================");
                System.out.println("Day "+day);
                System.out.println("=================");
                System.out.println();
                //checking which player was elimintated and resetting player values
                eliminationCheck();
                System.out.println();

                //running search round
                search.srcRround();
        }
            
            //continuing with code that both leftOff values will run
        //arunning/finishing vote round
        voteOB.voteRound1();
        //checking which player was banished and resetting player values
        banishmentCheck();

        //  setting gameOver conditions
        //if werewolf was banished
        if (voteOB.werewolfBanished == true) {
            //updating var
            gameOver = true;
            System.out.println();
            System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println("Congradulations Villagers!!");
            System.out.println("The werewolf has been banished and the village is now safe!");
            System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println();
        }
        //if there are only 2 players left and werewolf was not banished
        if (players.size() == 2 && voteOB.werewolfBanished == false) {
            //updating var
            gameOver = true;
            System.out.println();
            System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println("Congradulations Werewolf!!");
            System.out.println("The villagers have been eliminated and you have conquered the village!");
            System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println();
        }
        //game has caught up and can now continue with the regular play function
        play();
    }

    //playing game
    public void play() {
        //looping 'til game is over
        while (gameOver == false) {
            //updating day var
            day++;
            System.out.println("=================");
            System.out.println("Night "+night);
            System.out.println("=================");
            System.out.println();

            //running werewolf's turn
            turn(werewolfRole);
            System.out.println("The werewolf has come out.");
            //running doctor's turn
            turn(doctorRole);
            System.out.println("The doctor has healed a patient.");
            //running psychic's turn
            turn(psychicRole);
            System.out.println("The psychic has read the truth.");
            System.out.println();
            //updating night var
            night++;
            System.out.println("=================");
            System.out.println("Day "+day);
            System.out.println("=================");
            System.out.println();
            //checking which player was elimintated and resetting player values
            eliminationCheck();
            System.out.println();

            //running search round
            search.srcRround();
            //running vote round
            voteOB.voteRound1();
            //checking which player was banished and resetting player values
            banishmentCheck();

            //  setting gameOver conditions
            //if werewolf was banished
            if (voteOB.werewolfBanished == true) {
                
                System.out.println();
                System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                System.out.println("Congradulations Villagers!!");
                System.out.println("The werewolf has been banished and the village is now safe!");
                System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                System.out.println();
                //clearing logs so that user can't enter a finished game
                gg.gameClear();
                john.playerClear();
                maria.playerClear();
                vincent.playerClear();
                gerald.playerClear();
                regan.playerClear();
                user.playerClear();
                gg.hintClear();
                //updating var
                gameOver = true;
            }
            //if there are only 2 players left and werewolf was not banished
            if (players.size() == 2 && voteOB.werewolfBanished == false) {
                //updating var
                gameOver = true;
                System.out.println();
                System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                System.out.println("Congradulations Werewolf!!");
                System.out.println("The villagers have been eliminated and you have conquered the village!");
                System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                System.out.println();
                //clearing logs so that user can't enter a finished game
                gg.gameClear();
                john.playerClear();
                maria.playerClear();
                vincent.playerClear();
                gerald.playerClear();
                regan.playerClear();
                user.playerClear();
                gg.hintClear();
            }
        }
    }

    public static void main(String[] args) {
        gameOB.setUp1();
        //closing scanner
        scan.close();
    }
}
