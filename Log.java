//This file is to handle events related to the Logs

//importing required classes
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Log {
    //setting attributes
    Player player;
    Game game;
    Vote vote;

    //constructor (player log)
    public Log(Player Player) {
        this.player = Player;
    }

    //constructor (game log)
    public Log(Game Game,Vote Vote) {
        this.game = Game;
        this.vote = Vote;
    }

    //creating player log files
    File jf = new File("Logs/johnLog.txt");
    File mf = new File("Logs/mariaLog.txt");
    File vf = new File("Logs/vincentLog.txt");
    File gf = new File("Logs/geraldLog.txt");
    File rf = new File("Logs/reganLog.txt");
    File uf = new File("Logs/userLog.txt");
    //creating game & vote log file
    File gvg = new File("Logs/gameLog.txt");
    //creating hint log files (for user reference)
    File Hjf = new File("Logs/Hints/hintJohnLog.txt");
    File Hmf = new File("Logs/Hints/hintMariaLog.txt");
    File Hvf = new File("Logs/Hints/hintVincentLog.txt");
    File Hgf = new File("Logs/Hints/hintGeraldLog.txt");
    File Hrf = new File("Logs/Hints/hintReganLog.txt");
    File Huf = new File("Logs/Hints/hintUserLog.txt");

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

    //getting players
    static Player John = Player.getJohn();
    static Player Maria = Player.getMaria();
    static Player Vincent = Player.getVincent();
    static Player Gerald = Player.getGerald();
    static Player Regan = Player.getRegan();
    static Player User = Player.getUser();

    public void testInd() {
        System.out.println(John.name);
        System.out.println(Maria.name);
        System.out.println(Vincent.name);
        System.out.println(Gerald.name);
        System.out.println(Regan.name);
        System.out.println(User.name);
    }

    //creating vote object
    Vote voteOb = new Vote();

    //creating printWriter object
    PrintWriter pw;

    //writing player log
    public void playerWrite() {
        try {
            //creating file (jf by default)
            File file = jf;

            //creating switch case based on player name
            switch (player.name) {
                case "John":
                    //updating var
                    file = jf;
                    break;
                case "Maria":
                    file = mf;
                    break;
                case "Vincent":
                    file = vf;
                    break;
                case "Gerald":
                    file = gf;
                    break;
                case "Regan":
                    file = rf;
                    break;
                case "User":
                    file = uf;
                    break;
            }
            
            //creating printWriter ()
            pw = new PrintWriter(new FileWriter(file));

            //writing message
            pw.println(player.name+"*"+player.role.title+"*"+player.role.usedPerk+"*"+player.voted);
            pw.println(player.rule.label+"*"+player.trait);
            pw.println(player.notes.john.lvl+"*"+player.notes.maria.lvl+"*"+player.notes.vincent.lvl+"*"+player.notes.gerald.lvl+"*"+player.notes.regan.lvl+"*"+player.notes.user.lvl);
            pw.println(player.hunted+"*"+player.saved+"*"+player.checked+"*"+player.eliminated+"*"+player.banished);
            pw.println(player.timesVoted+"*"+player.accumulatedWeight);
            pw.println(player.notes.psy.Jcheck+"*"+player.notes.psy.Mcheck+"*"+player.notes.psy.Vcheck+"*"+player.notes.psy.Gcheck+"*"+player.notes.psy.Rcheck+"*"+player.notes.psy.Ucheck+"*"+player.notes.psy.Imposter.name);
            
            //pushing buffer contents
            pw.flush();
            //closing printWriter
            pw.close();

        //catching exception
        } catch (IOException o) {
            System.out.println();
            System.out.println("Error");
            System.out.println("I/O operation has failed or been interrupted.");
            System.out.println();
        }
    }

    //reading player log
    public void playerRead() {
        //creating attribute
        String token = "";
        String line = "";
        int column = 1;
        int row = 1;
        //creating file (jf by default)
        File file = jf;

        //creating switch case based on player name
        switch (player.name) {
            case "John":
                //updating var
                file = jf;
                break;
            case "Maria":
                file = mf;
                break;
            case "Vincent":
                file = vf;
                break;
            case "Gerald":
                file = gf;
                break;
            case "Regan":
                file = rf;
                break;
            case "User":
                file = uf;
                break;
        }
        
        try {
            //creating printWriter ()
            Scanner scanF = new Scanner(file);
            //creating string tokenizer
            StringTokenizer tokenizer;

            //looping while file has another line
            while (scanF.hasNextLine()) {
                //getting line
                line = scanF.nextLine();
                tokenizer = new StringTokenizer(line);

                //looping while there are more tokens
                while (tokenizer.hasMoreTokens()) {
                    //getting token
                    token = tokenizer.nextToken("*");
                    //creating switch case based on row
                    switch (row) {
                        //Basics (Player.java)
                        case 1:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    //acknowledging attribute
                                    player.name = token;
                                    //creating switch case based on name
                                    switch (player.name) {
                                        case "John":
                                            player = John;
                                            break;
                                        case "Maria":
                                            player = Maria;
                                            break;
                                        case "Vincent":
                                            player = Vincent;
                                            break;
                                        case "Gerald":
                                            player = Gerald;
                                            break;
                                        case "Regan":
                                            player = Regan;
                                            break;
                                        case "User":
                                            player = User;
                                            break;
                                    }
                                    //updating var
                                    column++;
                                    break;
                                case 2:
                                    //default role to avoid NullPointerException
                                    player.role = villagerRole;
                                    //setting role title
                                    player.role.title = token;
                                    //creating switch case based on title
                                    switch (player.role.title) {
                                        case "Villager":
                                            //acknowledging role
                                            player.role = villagerRole;
                                            break;
                                        case "Doctor":
                                            player.role = doctorRole;
                                            break;
                                        case "Psychic":
                                            player.role = psychicRole;
                                            break;
                                        case "Werewolf":
                                            player.role = werewolfRole;
                                            break;
                                    }
                                    column++;
                                    break;
                                case 3:
                                    //getting boolean value of token
                                    player.role.usedPerk = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 4:
                                    //getting boolean value of token
                                    player.voted = Boolean.valueOf(token);
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                        //Rules & Character Traits (Rule.java & Player.java)
                        case 2:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    player.rule.label = token;
                                    //creating switch case based on label
                                    switch (player.rule.label) {
                                        case "Villager":
                                            //acknowledging rule
                                            player.rule = villagerRule;
                                            break;
                                        case "Doctor":
                                            player.rule = doctorRule;
                                            break;
                                        case "Psychic":
                                            player.rule = psychicRule;
                                            break;
                                        case "Werewolf":
                                            player.rule = werewolfRule;
                                            break;
                                    }
                                    column++;
                                    break;
                                case 2:
                                    player.trait = token;
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                        //Suspicion Levels (Note.java)
                        case 3:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    //getting double value of token
                                    player.notes.john.lvl = Double.valueOf(token);
                                    column++;
                                    break;
                                case 2:
                                    player.notes.maria.lvl = Double.valueOf(token);
                                    column++;
                                    break;
                                case 3:
                                    player.notes.vincent.lvl = Double.valueOf(token);
                                    column++;
                                    break;
                                case 4:
                                    player.notes.gerald.lvl = Double.valueOf(token);
                                    column++;
                                    break;
                                case 5:
                                    player.notes.regan.lvl = Double.valueOf(token);
                                    column++;
                                    break;
                                case 6:
                                    player.notes.user.lvl = Double.valueOf(token);
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                        //status (Player.java)
                        case 4:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    //getting boolean value of token
                                    player.hunted = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 2:
                                    player.saved = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 3:
                                    player.checked = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 4:
                                    player.eliminated = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 5:
                                    player.banished = Boolean.valueOf(token);
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                        //Misc (Player.java)
                        case 5:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    //getting integar value of token
                                    player.timesVoted = Integer.valueOf(token);
                                    column++;
                                    break;
                                case 2:
                                    //getting double value of token
                                    player.accumulatedWeight = Double.valueOf(token);
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                        //PsyNote (Note.java)
                        case 6:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    //getting boolean value of token
                                    player.notes.psy.Jcheck = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 2:
                                    player.notes.psy.Mcheck = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 3:
                                    player.notes.psy.Vcheck = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 4:
                                    player.notes.psy.Gcheck = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 5:
                                    player.notes.psy.Rcheck = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 6:
                                    player.notes.psy.Ucheck = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 7:
                                    player.notes.psy.Imposter.name = (token);
                                    //creating switch case based on imposter name
                                    switch (player.notes.psy.Imposter.name) {
                                        case "John":
                                            player.notes.psy.Imposter = Game.John;
                                            break;
                                        case "Maria":
                                            player.notes.psy.Imposter = Game.Maria;
                                            break;
                                        case "Vincent":
                                            player.notes.psy.Imposter = Game.Vincent;
                                            break;
                                        case "Gerald":
                                            player.notes.psy.Imposter = Game.Gerald;
                                            break;
                                        case "Regan":
                                            player.notes.psy.Imposter = Game.Regan;
                                            break;
                                        case "User":
                                            player.notes.psy.Imposter = Game.User;
                                            break;
                                    }
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                    }
                }
            }
            //closing scanner
            scanF.close();
        //catching exception
        } catch (FileNotFoundException f) {
            System.out.println();
            System.out.println("Error");
            System.out.println("File was not found.");
            System.out.println();
        }

        
        
    }

    //clearing player log
    public void playerClear() {
        try {
            //creating file (jf by default)
            File file = jf;

            //creating switch case based on player name
            switch (player.name) {
                case "John":
                    //updating var
                    file = jf;
                    break;
                case "Maria":
                    file = mf;
                    break;
                case "Vincent":
                    file = vf;
                    break;
                case "Gerald":
                    file = gf;
                    break;
                case "Regan":
                    file = rf;
                    break;
                case "User":
                    file = uf;
                    break;
            }
            
            //setting printWriter
            pw = new PrintWriter(new FileWriter(file));

            //overwriting file
            pw.write("");
            //closing printWriter
            pw.close();
        } catch (IOException o) {
            System.out.println();
            System.out.println("Error");
            System.out.println("I/O operation has failed or been interrupted.");
            System.out.println();
        }
    }

    //writing game log
    public void gameWrite() {
        try {
            //creating printWriter
            pw = new PrintWriter(new FileWriter(gvg));

            //writing message
            pw.println(Game.day+"*"+Game.night+"*"+Game.gameOver+"*"+Game.leftOff);
            pw.println(Vote.mostVotes.name+"*"+Vote.werewolfBanished);
        
            //pushing buffer contents
            pw.flush();
            //closing printWriter
            pw.close();
        } catch (IOException o) {
            System.out.println();
            System.out.println("Error");
            System.out.println("I/O operation has failed or been interrupted.");
            System.out.println();
        }
    }

    //reading game log
    public void gameRead() {
        //creating attribute
        String token = "";
        String line = "";
        int column = 1;
        int row = 1;

        try {
            //creating printWriter ()
            Scanner scanF = new Scanner(gvg);
            //creating string tokenizer
            StringTokenizer tokenizer;

            //looping while file has another line
            while (scanF.hasNextLine()) {
                //getting line
                line = scanF.nextLine();
                tokenizer = new StringTokenizer(line);

                //looping while there are more tokens
                while (tokenizer.hasMoreTokens()) {
                    //getting token
                    token = tokenizer.nextToken("*");
                    //creating switch case based on row
                    switch (row) {
                        //game (Game.java)
                        case 1:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    //  acknowledging attribute
                                    //getting integer value of token
                                    Game.day = Integer.valueOf(token);
                                    //updating var
                                    column++;
                                    break;
                                case 2:
                                    Game.night = Integer.valueOf(token);
                                    column++;
                                    break;
                                case 3:
                                    //getting boolean value of token
                                    Game.gameOver = Boolean.valueOf(token);
                                    column++;
                                    break;
                                case 4:
                                    Game.leftOff = token;
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                            break;
                        //vote (Vote.java)
                        case 2:
                            //creating switch case based on column
                            switch (column) {
                                case 1:
                                    Vote.mostVotes.name = token;
                                    //creating switch case based on name
                                    switch (Vote.mostVotes.name) {
                                        case "John":
                                            //acknowledging player
                                            Vote.mostVotes = John;
                                            column++;
                                            break;
                                        case "Maria":
                                            Vote.mostVotes = Maria;
                                            column++;
                                            break;
                                        case "Vincent":
                                            Vote.mostVotes = Vincent;
                                            column++;
                                            break;
                                        case "Gerald":
                                            Vote.mostVotes = Gerald;
                                            column++;
                                            break;
                                        case "Regan":
                                            Vote.mostVotes = Regan;
                                            column++;
                                            break;
                                        case "User":
                                            Vote.mostVotes = User;
                                            column++;
                                            break;
                                    }
                                    column++;
                                    break;
                                case 2:
                                    //getting boolean value of token
                                    Vote.werewolfBanished = Boolean.valueOf(token);
                                    //acknowledging end of row
                                    row++;
                                    //resetting column
                                    column = 1;
                                    break;
                            }
                    }
                }
            }

            //closing scanner
            scanF.close();
        //catching exception
        } catch (FileNotFoundException f) {
            System.out.println();
            System.out.println("Error");
            System.out.println("File was not found.");
            System.out.println();
        }
    }

    //clearing game log
    public void gameClear() {
        try {
            //setting printWriter
            pw = new PrintWriter(new FileWriter(gvg));

            //overwriting file
            pw.write("");
            //closing printWriter
            pw.close();
        } catch (IOException o) {
            System.out.println();
            System.out.println("Error");
            System.out.println("I/O operation has failed or been interrupted.");
            System.out.println();
        }
    }

    //adding hint to hint log
    public void hintAdd(Player target,String hint) {
        try {
            //creating file (Hjf by default)
            File file = Hjf;

            //creating switch case based on player name
            switch (target.name) {
                case "John":
                    //updating var
                    file = Hjf;
                    break;
                case "Maria":
                    file = Hmf;
                    break;
                case "Vincent":
                    file = Hvf;
                    break;
                case "Gerald":
                    file = Hgf;
                    break;
                case "Regan":
                    file = Hrf;
                    break;
                case "User":
                    file = Huf;
                    break;
            }
            
            //creating printWriter ()
            pw = new PrintWriter(new FileWriter(file, true));

            //if file is empty
            if (file.length() == 0) {
                //adding to file end
                pw.append(hint);
            //file is not empty
            } else {
                //adding to file end
                pw.append("\n"+hint);
            }

            //pushing buffer contents
            pw.flush();
            //closing printWriter
            pw.close();
        //catching exception
        } catch (IOException o) {
            System.out.println();
            System.out.println("Error");
            System.out.println("I/O operation has failed or been interrupted.");
            System.out.println();
        }
    }

    //printing hints to terminal so user can see them
    public void hintPrint() {
        //creating attribute
        String token = "";
        String line = "";
        String name = "empty";
        String allHints = "";
        //creating file (Hjf by default)
        File file = Hjf;

        //looping 6 times
        for (int i = 0; i < 6; i++) {
            //resetting var
            allHints = "";
            //creating switch case based on i
            switch (i) {
                case 0:
                    file = Hjf;
                    name = "John";
                    break;
                case 1:
                    file = Hmf;
                    name = "Maria";
                    break;
                case 2:
                    file = Hvf;
                    name = "Vincent";
                    break;
                case 3:
                    file = Hgf;
                    name = "Gerald";
                    break;
                case 4:
                    file = Hrf;
                    name = "Regan";
                    break;
                case 5:
                    file = Huf;
                    name = "User";
                    break;
            }
            try {
                //creating printWriter ()
                Scanner scanF = new Scanner(file);
                //creating string tokenizer
                StringTokenizer tokenizer;

                System.out.println("-----------------------------");
                System.out.println(name+"'s Evidence:");

                //looping while file has another line
                while (scanF.hasNextLine()) {
                    //getting line
                    line = scanF.nextLine();
                    tokenizer = new StringTokenizer(line);

                    //looping while there are more tokens
                    while (tokenizer.hasMoreTokens()) {
                        //getting token
                        token = tokenizer.nextToken("*");
                        
                        allHints += (token+", ");
                    }
                }

                System.out.println(allHints);
                System.out.println("-----------------------------");

                //closing scanner
                scanF.close();
            //catching exception
            } catch (FileNotFoundException f) {
                System.out.println();
                System.out.println("Error");
                System.out.println("File was not found.");
                System.out.println();
            }
        }
    }

    //clearing print logs
    public void hintClear() {
        try {
            //creating file (Hjf by default)
            File file = Hjf;

            //looping 6 times
            for (int i = 0; i < 6; i++) {
                //creating switch case based on i
                switch (i) {
                    case 0:
                        file = Hjf;
                        break;
                    case 1:
                        file = Hmf;
                        break;
                    case 2:
                        file = Hvf;
                        break;
                    case 3:
                        file = Hgf;
                        break;
                    case 4:
                        file = Hrf;
                        break;
                    case 5:
                        file = Huf;
                        break;
                }

                //setting printWriter
                pw = new PrintWriter(new FileWriter(file));

                //overwriting file
                pw.write("");
                //closing printWriter
                pw.close();
            }
        //catching exception
        } catch (IOException o) {
            System.out.println();
            System.out.println("Error");
            System.out.println("I/O operation has failed or been interrupted.");
            System.out.println();
        }
    }
}