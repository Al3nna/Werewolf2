//This file is to handle events related to the voting

//importing required classes
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Vote {
    //setting attributes
    static Player mostVotes;
    static boolean werewolfBanished;

    //comstructor
    public Vote() {
        mostVotes = new Player();
        werewolfBanished = false;
    }

    //creating method to get werewolfBanished boolean
    public static boolean getWerewolfBanished() {return werewolfBanished;}

    //creating scanner object that reads from System.in
    Scanner scan = new Scanner(System.in);
    //creating random object
    Random rand = new Random();

    Game obGame = new Game();
    

    //getting players arraylist
    ArrayList<Player> players = Player.getPlayers();

    //getting players
    Player John = Player.getJohn();
    Player Maria = Player.getMaria();
    Player Vincent = Player.getVincent();
    Player Gerald = Player.getGerald();
    Player Regan = Player.getRegan();
    Player User = Player.getUser();

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

    //casting vote
    public void cast(Player target) {
        //updating var
        target.timesVoted++;
    }

    //casting werewolf's vote (if user is not the werewolf)
    public void werewolfCast() {
        //getting random int
        int ind = rand.nextInt(players.size());

        //casting vote
        cast(players.get(ind));
    }

    //tallying votes
    public void tally() {
        //setting attributes
        mostVotes = players.get(0);
        int ind = 0;

        System.out.println("Tallying Votes...");
        System.out.println();
            try {
                //delaying program by 2 seconds
                Thread.sleep(2000);
            //catching exception
            } catch (InterruptedException e) {
                System.out.println("-=-Error-=-");
                System.out.println("Program Interrupted");
            }

        //looping through players arraylist
        for (int i = 1; i < players.size(); i++) {
            //if current player has more votes than previous most voted player
            if (players.get(i).timesVoted > mostVotes.timesVoted) {
                //updating var
                mostVotes = players.get(i);
                ind = i;
            }
        }

        //updating player banished status
        mostVotes.banished = true;
        //if player with most votes is the werewolf
        if (mostVotes.role.title == "Werewolf") {
            //updating var
            werewolfBanished = true;
        }

        System.out.println(mostVotes.name+" had the most votes ("+mostVotes.timesVoted+") and was banished!");

        //if player is NOT the werewolf
        if (mostVotes.role.title != "Werewolf") {
            //confirming player is NOT the werewolf
            mostVotes.checked = true;
        }

        //removing player from arraylist
        players.remove(ind);
    }

    //resetting votes
    public void reset(Player player) {
        //resetting vars
        John.timesVoted = 0;
        Maria.timesVoted = 0;
        Vincent.timesVoted = 0;
        Gerald.timesVoted = 0;
        Regan.timesVoted = 0;
        User.timesVoted = 0;
    }

    //casting user's vote
    public void userCast() {
        //creating attribute
        boolean valid = false;
        boolean validAns = false;

        //looping 'til valid is true
        while (valid == false) {
            System.out.println("Which player would you like to BANISH? Please enter their player number.");
            System.out.println();
            //looping while validAns is false
            while (!validAns) {
                System.out.println("Press 1 to see the evidence recovered by the detective. Press 0 to skip.");
                try {
                    //getting input from user
                    int input = scan.nextInt();
                    //consuming leftover line
                    scan.nextLine();
                    
                    //if input is 1 or 0
                if (input == 1 || input == 0) {
                    validAns = true;
                    //creating switch case based on input
                    switch (input) {
                        case 1:
                            System.out.println();
                            //printing hints
                            Game.gg.hintPrint();
                            System.out.println();
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

            //looping through players
            for (int i = 0; i < players.size(); i++) {
                //if player was not eliminated
                if (players.get(i).eliminated == false && players.get(i).banished == false) {
                    System.out.println("Player "+(i+1)+": "+players.get(i).name);
                }
            }
            System.out.println();
            try {
                //getting player number from user
                int playerNum = scan.nextInt();
                //consuming leftover line
                scan.nextLine();
                //identifying player index
                int ind = playerNum - 1;

                //looping through players (excluding user)
                for (int i = 0; i < players.size()-1; i++) {
                    //if player is not eliminated
                    if (players.get(i).eliminated == false && players.get(i).banished == false) {
                        //if i matches ind
                        if (i == ind) {
                            //updating var
                            valid = true;
                            //casting vote
                            cast(players.get(i));
                        }
                    }
                }

                //if input is invalid
                if (valid == false) {
                    System.out.println();
                    System.out.println("-=-Error-=-");
                    System.out.println("Please enter a whole number from the displayed list.");
                    System.out.println();
                //input is valid
                } else {
                    System.out.println();
                }
            //catching exception
            } catch (InputMismatchException e) {
                //consuming leftover line
                scan.nextLine();
                System.out.println();
                System.out.println("-=-Error-=-");
                System.out.println("Please enter a whole number from the displayed list.");
                System.out.println();
            }
        }
        //updating var
        User.voted = true;
        //finishing vote round
        voteRound2();
    }

    //running vote round (part 1)
    public void voteRound1() {
        System.out.println("-------------------");
        System.out.println("Voting Round");
        System.out.println("-------------------");
        System.out.println();

        //looping through players arraylist
        for (int i = 0; i < players.size(); i++) {
            //if player has not voted
            if (players.get(i).voted == false) {
                //if player is not user
                if (players.get(i) != User) {
                    //if player is not werewolf
                    if (players.get(i).role.title != "Werewolf") {
                        //choosing who to vote
                        players.get(i).notes.choose();
                        //updating var
                        players.get(i).voted = true;
                    //player is werewolf
                    } else {
                        //voting random player
                        werewolfCast();
                        //updating var
                        players.get(i).voted = true;
                    }
                //player is user
                } else {
                    //giving player option to exit
                    obGame.exitOption("vote");
                }
            }
        }
    }

    //running vote round (part 2)
    public void voteRound2() {
        System.out.println();
        //tallying votes
        tally();
        //is user was banished
        if (User.banished == true) {
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
                            System.exit(0);
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
        //resetting variables
        John.voted = false;
        Maria.voted = false;
        Vincent.voted = false;
        Gerald.voted = false;
        Regan.voted = false;
        User.voted = false;
    }
}