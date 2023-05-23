//This file is to handle events related to the players

//importing required classes
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Player {
    //setting attributes
    String name;
    Role role;
    Rule rule;
    Note notes;
    String trait;
    boolean hunted;
    boolean saved;
    boolean checked;
    boolean eliminated;
    boolean banished;
    boolean voted;
    int timesVoted;
    double accumulatedWeight;

    //creating hints arraylist
    ArrayList<Hint> hints = new ArrayList<>();

    //creating scanner that reads from System.in
    static Scanner scan = new Scanner(System.in);

    //constructor
    public Player(String playerName) {
        this.name = playerName;
        this.trait = "empty";
        this.hunted = false;
        this.saved = false;
        this.checked = false;
        this.eliminated = false;
        this.banished = false;
        this.voted = false;
        this.timesVoted = 0;
        this.accumulatedWeight = 0;
        notes = new Note();
        rule = new Rule();
    }

    //constructor (no input)
    public Player() {
        this.name = "empty";
        this.trait = "empty";
        this.hunted = false;
        this.saved = false;
        this.checked = false;
        this.eliminated = false;
        this.banished = false;
        this.voted = false;
        this.timesVoted = 0;
        this.accumulatedWeight = 0;
    }

    //creating players arraylist
    static ArrayList<Player> players = new ArrayList<>();
    //creating method ot get players arraylist
    public static ArrayList<Player> getPlayers() {return players;}

    //creating players
    static Player John = new Player("John");
    static Player Maria = new Player("Maria");
    static Player Vincent = new Player("Vincent");
    static Player Gerald = new Player("Gerald");
    static Player Regan = new Player("Regan");
    static Player User = new Player("User");

    //creating methods to get players
    public static Player getJohn() {return John;}
    public static Player getMaria() {return Maria;}
    public static Player getVincent() {return Vincent;}
    public static Player getGerald() {return Gerald;}
    public static Player getRegan() {return Regan;}
    public static Player getUser() {return User;}
    
    //adding players to arraylist
    public void addPlayers() {
        players.add(John);
        players.add(Maria);
        players.add(Vincent);
        players.add(Gerald);
        players.add(Regan);
        players.add(User);
    }

    //creating Random object
    static Random rand = new Random();

    //getting roles
    static Role villagerRole = Role.getVillager();
    static Role doctorRole = Role.getDoctor();
    static Role psychicRole = Role.getPsychic();
    static Role werewolfRole = Role.getWerewolf();

    //playing a computer player's perk
    public void compPerk(Player player) {
        //creating attribute
        boolean chosen = false;
        //getting random int
        int ind = rand.nextInt(players.size());

        
        //creating switch case based on player's role
            switch (player.role.title) {
                case "Werewolf":
                    //looping while chosed is false
                    while (!chosen) {
                        //getting random int
                        ind = rand.nextInt(players.size());
                        //if chosen player index is NOT current player
                        if (players.get(ind) != player) {
                            //updating var
                            chosen = true;
                            //updating chosen players value
                            players.get(ind).hunted = true;
                        }
                    }
                    break;
                case "Doctor":
                    //updating chosen players value
                    players.get(ind).saved = true;
                    break;
                case "Psych":
                    //if chosen player index is not current player
                    if (players.get(ind) != player) {
                        //results = whether or not chosen player is werewolf (true/false)
                        boolean results = (players.get(ind).role == werewolfRole);

                        //identifying imposter
                        player.notes.psy.Imposter = players.get(ind);

                        //updating chosen players value
                        players.get(ind).checked = true;

                        //giving info to psychic
                        player.notes.psy.add(players.get(ind), results);
                    }
                    break;
            }

    
    }

    //playing user's perk
    public void userPerk() {
        //if user is NOT a villager
        if (User.role != villagerRole) {
            System.out.println();
            //setting attribute
            String action = "empty";
            boolean valid = false;

            //creating switch case base on user's role
            switch ((User.role.title)) {
                case "Werewolf":
                    action = "ELIMINATE";
                    break;
                case "Doctor":
                    action = "SAVE";
                    break;
                case "Psychic":
                    action = "CHECK (if they are the werewolf)";
                    break;
            }

            //looping 'til valid is true
            while (valid == false) {
                System.out.println("Which player would you like to "+action+"? Please enter their player number.");
                System.out.println();
                //if user is the psychic
                if (User.role == psychicRole) {
                    //printing player "checked" statuses
                    User.notes.psy.print();
                    System.out.println();
                }

                //if user is the doctor
                if (User.role == doctorRole) {
                    //looping through players (including user)
                    for (int i = 0; i < players.size(); i++) {
                        //if player was not eliminated or banished
                        if (players.get(i).eliminated == false && players.get(i).banished == false) {
                            System.out.println("Player "+(i+1)+": "+players.get(i).name);
                        }
                    }
                //user is not the doctor
                } else if (User.role != doctorRole) {
                    //looping through players (excluding user)
                    for (int i = 0; i < players.size()-1; i++) {
                        //if player was not eliminated or banished
                        if (players.get(i).eliminated == false && players.get(i).banished == false) {
                            System.out.println("Player "+(i+1)+": "+players.get(i).name);
                        }
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

                    //if player is not the doctor
                    if (User.role != doctorRole) {
                        //looping through players (excluding user)
                        for (int i = 0; i < players.size()-1; i++) {
                            //if player is not eliminated or banished
                            if (players.get(i).eliminated == false && players.get(i).banished == false) {
                                //if i matches ind
                                if (i == ind) {
                                    //updating var
                                    valid = true;
                                    //creating switch case base on user's role
                                    switch ((User.role.title)) {
                                        case "Werewolf":
                                            //updating chosen player's value
                                            players.get(ind).hunted = true;
                                            break;
                                        case "Psychic":
                                            //setting attribute
                                            boolean results;

                                            //  printing whether or not chosen player is werewolf
                                            //if player IS the werewolf
                                            if (players.get(ind).role.title == "Werewolf") {
                                                System.out.println(players.get(ind).name+" IS the werewolf!!");
                                                //identifying imposter
                                                User.notes.psy.Imposter = players.get(ind);
                                            //player is not the werewolf
                                            } else {
                                                System.out.println(players.get(ind).name+" is NOT the werewolf.");
                                            }

                                            //updating var
                                            results = (players.get(ind).role == werewolfRole);

                                            //updatinv var
                                            players.get(ind).checked = true;

                                            //giving info to psychic
                                            User.notes.psy.add(players.get(ind), results);
                                            break;
                                    }
                                }
                            }
                        }
                    //player is the psychic
                    } else if (User.role == doctorRole) {
                        //looping through players (including user)
                        for (int i = 0; i < players.size(); i++) {
                            //if player is not eliminated or banished
                            if (players.get(i).eliminated == false && players.get(i).banished == false) {
                                //if i matches ind
                                if (i == ind) {
                                    //updating var
                                    valid = true;
                                    players.get(ind).saved = true;
                                }
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
    }
}

class Hint {
    //creating attributes
    String hint;
    double weight;

    //creating hint arraylists
    static ArrayList<Hint> petH = new ArrayList<>();
    static ArrayList<Hint> designerH = new ArrayList<>();
    static ArrayList<Hint> stylistH = new ArrayList<>();
    static ArrayList<Hint> moonH = new ArrayList<>();
    static ArrayList<Hint> villagerHint = new ArrayList<>();
    static ArrayList<Hint> doctorHint = new ArrayList<>();
    static ArrayList<Hint> psychicHint = new ArrayList<>();
    static ArrayList<Hint> werewolfHint = new ArrayList<>();
    //creating methods to get role hint arraylists
    public static ArrayList<Hint> getVillH() {return villagerHint;}
    public static ArrayList<Hint> getDocH() {return doctorHint;}
    public static ArrayList<Hint> getPsycH() {return psychicHint;}
    public static ArrayList<Hint> getWereH() {return werewolfHint;}

    //creating location arraylist
    static ArrayList<String> locations = new ArrayList<>();

    //getting players arraylist
    ArrayList<Player> players = Player.getPlayers();

    public void test() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).name);
        }
    }

    //getting gg log object
    Log gg = Game.getGG();

    //constructor
    public Hint(String Hint,int Priority) {
        this.hint = Hint;
        this.weight = Priority;
    }

    //constructor
    public Hint() {
        this.hint = "empty";
        this.weight = 0;
    }

    //putting attribute into string
    public String toString() {
        return hint;
    }

    //setting hints
    public void setHints() {
        //pet owner hints
        petH.add(new Hint("fur", 5));
        petH.add(new Hint("a bone", 4));
        petH.add(new Hint("pawprints", 5));
        petH.add(new Hint("a dog brush", 4));
        petH.add(new Hint("flea repellant", 5));
        petH.add(new Hint("an id tag", 2));
        petH.add(new Hint("a nail trimmer", 3));
        petH.add(new Hint("a pet bed",1));

        //clothing designer hints
        designerH.add(new Hint("cloth scraps", 5));
        designerH.add(new Hint("a sewing needle", 1));
        designerH.add(new Hint("a measuring tape", 1));
        designerH.add(new Hint("torn clothes", 5));
        designerH.add(new Hint("a big shirt", 2));
        designerH.add(new Hint("big pants", 3));

        //hair stylist hints
        stylistH.add(new Hint("hair", 4));
        stylistH.add(new Hint("a big brush", 5));
        stylistH.add(new Hint("hair dye", 1));
        stylistH.add(new Hint("a small pile of hair", 5));
        stylistH.add(new Hint("hair clippers", 1));
        stylistH.add(new Hint("specialized hair care", 3));
        
        //moon enthusiest hints
        moonH.add(new Hint("a moon photo", 4));
        moonH.add(new Hint("a moon phase poster", 3));
        moonH.add(new Hint("a moon plushy", 5));
        moonH.add(new Hint("moon books", 2));
        moonH.add(new Hint("moon club poster", 1));

        //villager hints
        villagerHint.add(new Hint("a shirt", 5));
        villagerHint.add(new Hint("a brush", 5));
        villagerHint.add(new Hint("a hair tie", 5));
        villagerHint.add(new Hint("clothes", 5));
        villagerHint.add(new Hint("headphones", 5));
        villagerHint.add(new Hint("a measuring tape", 5));
        villagerHint.add(new Hint("books", 5));
        villagerHint.add(new Hint("a necklace", 5));
        villagerHint.add(new Hint("a bracelet", 5));
        villagerHint.add(new Hint("a hat", 5));

        //doctor hints
        doctorHint.add(new Hint("a white shirt", 5));
        doctorHint.add(new Hint("a notebook", 5));
        doctorHint.add(new Hint("a comb", 5));
        doctorHint.add(new Hint("clothes", 5));
        doctorHint.add(new Hint("files", 5));
        doctorHint.add(new Hint("an id tag", 5));
        doctorHint.add(new Hint("books", 5));
        doctorHint.add(new Hint("a watch", 5));
        doctorHint.add(new Hint("a hat", 5));
        doctorHint.add(new Hint("a bracelet", 5));

        //psychic hints
        psychicHint.add(new Hint("a crystal", 5));
        psychicHint.add(new Hint("clothes", 5));
        psychicHint.add(new Hint("specialized hair care", 5));
        psychicHint.add(new Hint("moon books", 5));
        psychicHint.add(new Hint("sweaters", 5));
        psychicHint.add(new Hint("flowers", 5));
        psychicHint.add(new Hint("blankets", 5));
        psychicHint.add(new Hint("a weighted plushy", 5));
        psychicHint.add(new Hint("a bracelet", 5));
        psychicHint.add(new Hint("a necklace", 5));

        //werewolf hints
        werewolfHint.add(new Hint("clothes", 5));
        werewolfHint.add(new Hint("fur", 5));
        werewolfHint.add(new Hint("cloth scraps", 5));
        werewolfHint.add(new Hint("a small pile of hair", 5));
        werewolfHint.add(new Hint("a moon plushy", 5));
        werewolfHint.add(new Hint("flea repellant", 5));
        werewolfHint.add(new Hint("a moon phase poster", 5));
        werewolfHint.add(new Hint("blankets", 5));
        werewolfHint.add(new Hint("ear buds", 5));
        werewolfHint.add(new Hint("a bracelet", 5));
    }

    //setting locations
    public void setLocations() {
        locations.add("bed");
        locations.add("bathroom floor");
        locations.add("dining table");
        locations.add("desk");
        locations.add("bedside table");
        locations.add("kitchen counter");
        locations.add("bedroom floor");
        locations.add("couch");
        locations.add("carpet");
        locations.add("garage floor");
        locations.add("stairs");
        locations.add("bookshelf");
        locations.add("kitchen chair");
    }
    
    //adding player role & trait hints
    public void assignHints(Player player) {
        //if player has a trait
        if (player.trait != null) {
            //creating switch case based on player trait
            switch (player.trait) {
                case "Pet Owner":
                    player.hints.addAll(petH);
                    break;
                case "Clothes Designer":
                    player.hints.addAll(designerH);
                    break;
                case "Hair Stylist":
                    player.hints.addAll(stylistH);
                    break;
                case "Moon Enthusiest":
                    player.hints.addAll(moonH);
                    break;
            }
        }

        //creating switch case
        switch (player.role.title) {
            case "Villager":
                player.hints.addAll(villagerHint);
                break;
            case "Doctor":
                player.hints.addAll(doctorHint);
                break;
            case "Psychic":
                player.hints.addAll(psychicHint);
                break;
            case "Werewolf":
                player.hints.addAll(werewolfHint);
                break;   
        }
    }

    //removing all player hints based on player role
    public void clearHints(Player player) {
        player.hints.clear();
    }

    //creating trait arraylist
    static ArrayList<Rule> traits = new ArrayList<>();
    //creating method to get traits
    public static ArrayList<Rule> getTraits() {return traits;}

    //setting character traits
    public void setT() {
        //adding traits to arraylist
        traits.add(new Rule("Pet Owner", "You are also a pet owner..."));
        traits.add(new Rule("Hair Stylist", "You are also a hair stylist..."));
        traits.add(new Rule("Clothing Designer", "You are also a clothing designer..."));
        traits.add(new Rule("Moon Enthusiest", "You are also a moon enthusiest..."));
    }

    //assigning character traits
    public void assignT() {
        //creating pls arraylist
        ArrayList<Integer> pls = new ArrayList<>();
        //creating num arraylist
        ArrayList<Integer> num = new ArrayList<>();
        
        //looping through players
        for (int i = 0; i < players.size(); i++) {
            //adding values to pls
            pls.add(i);
        }
        
        //looping through traits
        for (int i = 0; i < traits.size(); i++) {
            //adding values to num
            num.add(i);
        }

        //shuffling pls & num
        Collections.shuffle(pls);
        Collections.shuffle(num);

        //looping 4 times
        for (int i = 0; i < 4; i++) {
            //setting attributes
            int l = pls.get(0);
            int n = num.get(0);
            Player pl = players.get(l);

            pl.trait = traits.get(n).label;

            //removing chosen values from arraylists
            pls.remove(0);
            num.remove(0);
        }
    }

    //printing hint
    public void print(Player player) {
        //setting attribute
        boolean complete = false;
        //setting player accumulated weight
        setAccumulatedWeight(player);

        //while hint selection is incomplete
        while (!complete) {
            //if random hint was successsfully chosen
            if (weightedRand(player)) {
                //updating var
                complete = true;
            }
        }
    }

    //adding all player hint weights
    public void setAccumulatedWeight(Player player) {
        //resetting accumulated weight
        player.accumulatedWeight = 0;

        //looping through player hints
        for (int i = 0; i < player.hints.size(); i++) {
            //adding current hint weight to total
            player.accumulatedWeight += player.hints.get(i).weight;
        }
    }

    //selecting random hint
    public boolean weightedRand(Player player) {
        //shuffling hints
        Collections.shuffle(player.hints);

        //getting random weight
        double num = ((double)Math.random()*player.accumulatedWeight);

        //if num is less or equal to hint weight
        if (num <= player.hints.get(0).weight) {
            //shuffling locations arraylist
            Collections.shuffle(locations);
            
            System.out.println();
            System.out.println("Investigating...");
            System.out.println();
            try {
                //delaying program by 2 seconds
                Thread.sleep(2000);
            //catching exception
            } catch (InterruptedException e) {
                System.out.println("-=-Error-=-");
                System.out.println("Program Interrupted");
            }
            
            System.out.println("The detective found "+player.hints.get(0)+" on the "+locations.get(0)+" in "+player.name+"'s house.");
            gg.hintAdd(player, player.hints.get(0).hint);

            //  passing info to players
            //looping through players arraylist
            for (int i = 0; i < players.size(); i++) {
                //running evaluation
                players.get(i).notes.evaluate(player, player.hints.get(0).hint);
            }
            return true;
        } else {
            return false;
        }
    }
}

class Search {
    //creating HintOB
    Hint HintOB = new Hint();
    //getting players arraylist
    ArrayList<Player> players = Player.getPlayers();

    public void srcRround() {
        System.out.println("-------------------");
        System.out.println("Investigation Round");
        System.out.println("-------------------");
        System.out.println();

        //looping through players arraylist
        for (int i = 0; i < players.size(); i++) {
            //printing hint
            HintOB.print(players.get(i));
            System.out.println();
        }
    }
}