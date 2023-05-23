//This file is to handle events related to the Notes

//importing required classes
import java.util.ArrayList;

public class Note {
    //getting players
    Player John = Player.getJohn();
    Player Maria = Player.getMaria();
    Player Vincent = Player.getVincent();
    Player Gerald = Player.getGerald();
    Player Regan = Player.getRegan();
    Player User = Player.getUser();

    //creating psyNote object
    PsyNote psy = new PsyNote();

    //creating suspect arraylist
    static ArrayList<Sus> suspects = new ArrayList<>();

    //creating vote object
    Vote voteOB = new Vote();

    public void testInd() {
        System.out.println(John.name);
        System.out.println(Maria.name);
        System.out.println(Vincent.name);
        System.out.println(Gerald.name);
        System.out.println(Regan.name);
        System.out.println(User.name);
    }

    //setting attributes;
    Sus john = new Sus(John);
    Sus maria = new Sus(Maria);
    Sus vincent = new Sus(Vincent);
    Sus gerald = new Sus(Gerald);
    Sus regan = new Sus(Regan);
    Sus user = new Sus(User);

    //adding suspects to arraylist
    public void addSuspects() {
        suspects.add(john);
        suspects.add(maria);
        suspects.add(vincent);
        suspects.add(gerald);
        suspects.add(regan);
        suspects.add(user);
    }

    //getting role hint arraylists
    ArrayList<Hint> villagerHint = Hint.getVillH();
    ArrayList<Hint> doctorHint = Hint.getDocH();
    ArrayList<Hint> psychicHint = Hint.getPsycH();
    ArrayList<Hint> werewolfHint = Hint.getWereH();

    //evaluating evidence (hints)
    public void evaluate(Player target,String hint) {
        //setting attribute
        double inc = 0;

        //looping through wereH arraylist
        for (int i = 0; i < werewolfHint.size(); i++) {
            //if hint = current werewolf hint
            if (hint == werewolfHint.get(i).hint) {
                //adding 2 to inc
                inc += 2;
            }
        }

        //looping through docH arraylist
        for (int i = 0; i < doctorHint.size(); i++) {
            //if hint = current doctor hint
            if (hint == doctorHint.get(i).hint) {
                //minusing 1 from inc
                inc -= 1;
            }
        }
        
        //looping through villH arraylist
        for (int i = 0; i < villagerHint.size(); i++) {
            //if hint = current villager hint
            if (hint == villagerHint.get(i).hint) {
                //minusing 0.5 from inc
                inc -= 0.5;
            }
        }

        //looping through psycH arraylist
        for (int i = 0; i < psychicHint.size(); i++) {
            //if hint = current psychic hint
            if (hint == psychicHint.get(i).hint) {
                //minusing 1 from inc
                inc -= 1;
            }
        }

        //  looking for correct suspect
        //looping through suspect arraylist
        for (int i = 0; i < suspects.size(); i++) {
            //if target = current suspect
            if (target == suspects.get(i).target) {
                //updating suspicion level
                suspects.get(i).update(inc);
            }
        }
    }

    //receiving info from Psychic's shout
    public void listen(Player Imposter) {
        //  looking for correct suspect
        //looping through suspect arraylist
        for (int i = 0; i < suspects.size(); i++) {
            //if target = current suspect
            if (Imposter == suspects.get(i).target) {
                //updating suspicion level
                suspects.get(i).update(999);
            }
        }
    }

    //choosing who to vote
    public void choose() {
        //setting attribute
        Sus most = suspects.get(0);

        //looping through suspects arraylist
        for (int i = 1; i < suspects.size(); i++) {
            //if biggest suspect's suspicion level is less than current suspect's
            if (most.lvl < suspects.get(i).lvl) {
                //updating var
                most = suspects.get(i);
            }
        }

        //if the biggest suspicion level is 0
        if (most.lvl == 0) {
            //voting random player
            voteOB.werewolfCast();
        //biggest suspicion level is not 0
        } else {
            //casting vote for biggest suspect
            voteOB.cast(most.target);
        }
    }
}

class Sus {
    //setting attributes
    Player target;
    double lvl;

    //constructor
    public Sus (Player targetPlayer) {
        this.target = targetPlayer;
        this.lvl = 0;
    }

    //increasing suspicion level
    public void update(double increment) {
        //adding increment to total
        lvl += increment;
    }
}

class PsyNote {
    //setting attributes
    boolean Jcheck; //John's status of being checked
    boolean Mcheck;
    boolean Vcheck;
    boolean Gcheck;
    boolean Rcheck;
    boolean Ucheck;
    Player Imposter; //werewolf

    //constructor
    public PsyNote() {
        Jcheck = false;
        Mcheck = false;
        Vcheck = false;
        Gcheck = false;
        Rcheck = false;
        Ucheck = false;
        Imposter = new Player();
    }

    //getting players
    Player John = Player.getJohn();
    Player Maria = Player.getMaria();
    Player Vincent = Player.getVincent();
    Player Gerald = Player.getGerald();
    Player Regan = Player.getRegan();
    Player User = Player.getUser();

    //getting players arraylist
    ArrayList<Player> players = Player.getPlayers();

    public void testInd() {
        System.out.println(John.name);
        System.out.println(Maria.name);
        System.out.println(Vincent.name);
        System.out.println(Gerald.name);
        System.out.println(Regan.name);
        System.out.println(User.name);
    }

    //notifying all players of werewolf identity
    public void shout() {
        System.out.println();
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("The psychic has found the werewolf!");
        System.out.println(Imposter.name+" is guilty!");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println();
        
        //looping through players arraylist
        for (int i = 0; i < players.size(); i++) {
            players.get(i).notes.listen(Imposter);
        }
    }

    //updating player check statuses
    public void sync() {
        Jcheck = John.checked;
        Mcheck = Maria.checked;
        Vcheck = Vincent.checked;
        Gcheck = Gerald.checked;
        Rcheck = Regan.checked;
        Ucheck = Game.User.checked;
    }

    //adding info to psychic's notes
    public void add(Player target,boolean results) {
        //syncing checked status
        sync();
        
        //if results are true
        if (results == true) {
            //running shout function
            shout();
        }
    }

    //printing players' "checked" status for the user
    public void print() {
        System.out.println("Player "+'"'+"Checked"+'"'+" Statuses:");
        System.out.println();
        System.out.println("John: "+John.checked);
        System.out.println("Maria: "+Maria.checked);
        System.out.println("Vincent: "+Vincent.checked);
        System.out.println("Gerald: "+Gerald.checked);
        System.out.println("Regan: "+Regan.checked);
        System.out.println("User: "+User.checked);
    }
}