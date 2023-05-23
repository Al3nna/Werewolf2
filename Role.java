//This file is to handle events related to the roles

//importing required classes
import java.util.ArrayList;
import java.util.Collections;

public class Role {
    //setting attributes
    String title;
    String perk;
    boolean usedPerk;

    //constructor
    public Role(String Role,String Perk) {
        this.title = Role;
        this.perk = Perk;
        this.usedPerk = false;
    }

    //constructor (no input)
    public Role() {
        this.title = "empty";
        this.perk = "empty";
        this.usedPerk = false;
    }

    //getting players arraylist
    ArrayList<Player> players = Player.getPlayers();

    public void test() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).name);
        }
    }

    //creating & setting roles
    static Role villagerRole = new Role("Villager", "None");
    static Role doctorRole = new Role("Doctor", "Save");
    static Role psychicRole = new Role("Psychic", "Sight");
    static Role werewolfRole = new Role("Werewolf", "Hunt");

    //creating methods to get roles
    public static Role getVillager() {return villagerRole;}
    public static Role getDoctor() {return doctorRole;}
    public static Role getPsychic() {return psychicRole;}
    public static Role getWerewolf() {return werewolfRole;}

    //assigning player roles
    public void assign() {
        //creating pls arraylist
        ArrayList<Integer> pls = new ArrayList<>();
        //looping through players
        for (int i = 0; i < players.size(); i++) {
            //adding values to pls
            pls.add(i);
        }

        //shuffling pls
        Collections.shuffle(pls);

        //looping 3 times
        for (int i = 0; i < 3; i++) {
            //setting attributes
            int l = pls.get(0);

            Player pl = players.get(l);

            //setting up switch case
            switch (i) {
                case 0:
                    //setting current player's role
                    pl.role = psychicRole;
                    break;
                case 1:
                    pl.role = werewolfRole;
                    break;
                case 2:
                    pl.role = doctorRole;
                    break;
            }

            //removing chosen values from arraylists
            pls.remove(0);
        }

        //looping three times
        for (int i = 0; i < 3; i++) {
            //setting attributes
            int l = pls.get(0);
            Player pl = players.get(l);

            //setting remain players' role to Villager
            pl.role = villagerRole;

            //removing chosen value from arraylist
            pls.remove(0);
        }
    }
}
