package ui;

import utils.EvaluateInterface;
import utils.Menu;
import App.Student;
import App.TestResults;
import App.Evalueting;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  *<H1>User interface</H1>
 * <p>
 * Executetale file, comunicates with user through menu options choosen by
 * giving number.
 * </p>
 * <p>
 * Program reads two files.studetXXXX.csv, TESTXXXX.csv. Grades are defined
 * inside class TestResults. File studetXXXX.csv contains name,lastname, sex,
 * birthdate(dd.mm.yyyy), graduation number(GN). File provingXXXX.csv contain
 * GN, points from test. In menu is option for evaluation. It will evalueate
 * every student on list and gives grades. Results can be sorted by points or
 * stored into file(.txt, .pdf,.dat).
 *
 * </p>
 *
 * @author Filip Charouzd
 */
public class UI {

    private static final Pattern p = Pattern.compile("(^[0-9]{7}$)");
    public static EvaluateInterface evaluateInter = new Evalueting();
    ;
    private static final Scanner sc = new Scanner(System.in);
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static boolean again;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean progEnd = false;

        int menu;
        // try {
        do {

            menu = Menu.usersChoiseMainMenu();
            switch (menu) {
                case 0: // konec programu
                    progEnd = true;
                    break;
                case 1: //přidat studenta
                    manualAdding();
                    break;
                case 2://nahrávání  
                    fileLoading();
                    break;
                case 3: // ukázat studenty
                    showStudents();
                    break;
                case 4://dostat absolventy
                    evaluateResults();
                    break;
                case 5://Sorting
                    sortAbsolvents();
                    break;
                case 6:// ukladani
                    fileSaving();
                    break;
                case 7:
                    absolventsCheck();
                    break;

                default:
                    System.out.println("Spatna volba!");
            }

        } while (progEnd == false);

        /* } catch (Exception ex) {
                System.out.println("Vyskytla se neocekavana chyba. Program se ukonci");
                progEnd = true;
            }*/
        System.out.println("Program ukoncen");
    }
// Methody Pro main

    static void manualAdding() {
        again = true;
        do {
            int choise = Menu.usersChoiseManualMenu();
            switch (choise) {
                case 1://přidání studenta
                    addStudent();
                    break;

                case 2: // přidat testy
                    addResult();
                    break;
                case 0:
                    again = false;
                    break;
                default:
                    System.out.println("Spatna volba!");
            }
        } while (again == true);

    }

    private static void fileLoading() {
        int loadChoise = Menu.usersChoiseLoadMenu();
        String fileName;
        switch (loadChoise) {
            case 1:
                loadStudents();
                break;
            case 2:
                loadCurrentResults();
                break;
            case 3:
                loadGivenResult();

                break;
            case 4:
                System.out.println(evaluateInter.showSize());
                break;

            case 0:
                again = false;
                break;
            default:
                System.out.println("Spatna volba!");
        }
    }

    private static void showStudents() {
        System.out.println("Nasledujici studenti jsou prihlaseni k maturitni zkousce:");
        System.out.println(evaluateInter.showStudents());
    }

    private static void evaluateResults() {
        System.out.println("vyhodnoceny výsledky");
        System.out.println(evaluateInter.showAbslovents());
    }

    private static void sortAbsolvents() {
        again = true;
        int choise;
        while (again) {
            choise = Menu.usersChoiseSortMenu();
            switch (choise) {              
                case 1://od nejlepšího
                    System.out.println(evaluateInter.sortByScoreFromBest());
                    break;
                case 2://od nejhoršího
                    System.out.println(evaluateInter.sortByScoreFromWorst());
                    break;
                case 0:
                    again = false;
                    break;
                default:
                    System.out.println("Spatne zadana volba");
            }
        }
    }

    private static void fileSaving() {
        int sortChoise = Menu.usersChoiseSaveMenu();
        System.out.println("Zadej jmeno souboru: ");
        String fileName = sc.next();
        switch (sortChoise) {
            case 1:
                saveTxt(fileName);
                break;
            case 2:
                saveBin(fileName);
                break;
            case 3:
                savePdf(fileName);
                break;
            case 0:
                break;
            default:
                System.out.println("Spatne zadana volba");
        }
    }

    private static void absolventsCheck() {
        System.out.println("Seznam vsech absolventu: ");
        System.out.println("");
        System.out.println(evaluateInter.showAbslovents());
    }

    private static void addStudent() {
        //declarace
        String line, name, lastName, born;
        LocalDate bDate;
        int number;
        char sex;
        // přiřazení a vytvoření
        try {
            System.out.println("Jmeno a prijmeni");
            name = sc.next();
            lastName = sc.next();
            System.out.println("Pohlavi M(muz) / W(zena)");
            sex = sc.next().charAt(0);
            System.out.println("Datum narozeni ve formatu dd.MM.yyyy");
            sc.nextLine();//ukončení poslední řádky
            born = sc.nextLine();
            bDate = LocalDate.parse(born, dtf);
            System.out.println("Studentske cislo: ");
            int num = sc.nextInt();
            String numer = Integer.toString(num);
            Matcher match = p.matcher(numer);
            while (!match.find()) {
                System.out.println("Studentske cislo nesplnuje pozadeavek sedmimistneho cisla");
                num = sc.nextInt();
                numer = Integer.toString(num);
                match = p.matcher(numer);
            }
            num = Integer.parseInt(numer);
            Student stud = new Student(name, lastName, sex, bDate, num);
            evaluateInter.addStudent(stud);
            System.out.println("Student byl uspesne pridan");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Spatne zadane pohlavi. Zkus to znovu");
        } catch (DateTimeParseException e) {
            System.out.println("Spatne zadane datum narozeni. Zkus to znovu");
        } catch (InputMismatchException e) {
            System.out.println("Spatne zadane studentovo cislo. Zkus to znovu");
            sc.nextLine();
        }

    }

    private static void addResult() {
        //declarace
        double points;

        try {
            System.out.println("Zadej sedmimistne studentske cislo:");
            int num = sc.nextInt();
            String numer = Integer.toString(num);
            Matcher match = p.matcher(numer);
            while (!match.find()) {
                System.out.println("Studentske cislo nesplnuje pozadeavek sedmimistneho cisla");
                num = sc.nextInt();
                numer = Integer.toString(num);
                match = p.matcher(numer);
            }
            num = Integer.parseInt(numer);
            System.out.println("Zadej pocet bodu ziskanych v testu:");

            if ((points = sc.nextDouble()) >= 0 && points <= 50) {
                TestResults test = new TestResults(num, points);
                evaluateInter.addResult(test);
                System.out.println("Vysledek byl uspesne pridan pridan.");
            } else {
                System.out.println("Test nebyl pridan pocet bodu neni z mozneho rozsahu(0-50).");
            }
        } catch (InputMismatchException e) {
            System.out.println("Spatne zadane studentovo cislo nebo body ziskane v testu. Zkus to znovu");
            sc.nextLine();
        }
    }

    private static void loadStudents() {
        System.out.println("Zadej cestu k souboru");
        String fileName = sc.next();
        try {
            evaluateInter.loadStudents(fileName);
            System.out.println("Studenti načteni");
        } catch (IOException ex) {
            System.out.println("soubor nebyl nalezen nebo nelze precist");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void loadCurrentResults() {
        String file = "data" + File.separator + "test" + File.separator + "TEST2020.csv";
        {
            try {
                evaluateInter.getAbsolvets(file);
                System.out.println("Výsledky načteny");
            } catch (IOException ex) {
                System.out.println("Soubor se nepodarilo najit, nebo je poskozeny.");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void loadGivenResult() {
        System.out.println("Zadej cestu k souboru s vysledky");
        String file = sc.next();/* "test\\TEST2020.csv";*/
        {
            try {
                evaluateInter.getAbsolvets(file);
                System.out.println("Výsledky načteny");
            } catch (IOException ex) {
                System.out.println("Soubor se nepodarilo najit, nebo je poskozeny.");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void saveTxt(String fileName) {
        try {
            evaluateInter.saveToTxt(fileName);
            System.out.println("Soubor byl vytvořen v podadresari programu(data//results//).");
        } catch (IOException ex) {
            System.out.println("Soubor se nepodarilo vytvořit.");
        }
    }

    private static void saveBin(String fileName) {
        try {
            evaluateInter.saveToBin(fileName);
            System.out.println("Soubor byl vytvořen v podadresari programu(data//results//).");
        } catch (IOException ex) {
            System.out.println("Soubor se nepodarilo vytvořit.");
        }
    }

    private static void savePdf(String fileName) {
        try {
            evaluateInter.saveToPdf(fileName);
            System.out.println("Soubor byl vytvoren v podadresari programu(data//results//).");
        } catch (DocumentException ex) {
            System.out.println("Soubor nebylo mozne vytvorit");
        } catch (FileNotFoundException ex) {
            System.out.println("Soubor nebyl nalezen");
        }
    }

}
