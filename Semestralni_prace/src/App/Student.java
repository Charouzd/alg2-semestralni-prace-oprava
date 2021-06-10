package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Student Class</h1>
 * <p>
 * Object class with proprieties describing a student. - Name and Lastname -
 * Sex/Genger <b>M</b>an or <b>W</b>oman - born means birthday in form
 * dd.MM.yyyy - Student number - unique graduation number contains several
 * information first number from left says genger(1/2 = M/W) next two numbers
 * define school next four numbers is queue number
 * </p>
 *
 * @author Filip Charouzd
 */
public class Student implements Comparable<Student> {

    protected int number;
    protected String name;
    protected String lastName;
    protected char sex;
    protected LocalDate born;
    private TestResults BestResult = new TestResults(number, 0);
    private List<TestResults> results = new ArrayList();

    public Student(String name, String lastName, char sex, LocalDate born, int num)  {
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.born = born;
        this.number = num;
        

    }

    public void searchMyResults(String file) throws IOException {
        
        try (BufferedReader br = new BufferedReader(new FileReader(new File(file)))) {
            String line;
            String[] colums;
            TestResults r;
            int i = 1;
            br.readLine();//soubor má záhlavý, takže první řádek si načtu(takže dál program bude brát data od tohoto řádku) a prostě s ním nic neudělám
            while ((line = br.readLine()) != null) {
                colums = line.split(",");
               
                if (Integer.parseInt(colums[0]) == this.number) {
                    r = new TestResults(Integer.parseInt(colums[0]), Double.parseDouble(colums[1]));
                    results.add(r);
                    if (r.getScore() > BestResult.getScore()) {
                        BestResult = r;
                    }
                }
            }
        }
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public char getSex() {
        return sex;
    }

    public LocalDate getBorn() {
        return born;
    }

    public TestResults getResult() {
        return BestResult;
    }

    public List<TestResults> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return String.format("%s", "Student number:" + number + "  Name:" + name + " " + lastName + "(" + sex + ") born in " + born);
    }

    public void addResult(TestResults r) {
        r.grade();
        results.add(r);
        if (r.getScore() > this.BestResult.getScore()) {
            this.BestResult = r;
        }
    }

    public String absolventToString() {
        if (BestResult.isPassed()) {
            return String.format("%s", "Student's number:" + number + "  Name:" + name + " " + lastName + "(" + sex + ") born in " + born + " succesfuly gradueted for " + BestResult.getGrade() + "(" + BestResult.getPercentage() + "%)");
        } else {
            return String.format("%s", "Student's number:" + number + "  Name:" + name + " " + lastName + "(" + sex + ") born in " + born + " was unseccesfull because he aquire " + BestResult.getPercentage() + "%)");
        }
    }
// comparator vrací -1;0;1 

    @Override
    public int compareTo(Student o) {
        return Double.compare(BestResult.getScore(), o.BestResult.getScore());

    }

   /* public static void main(String[] args) throws IOException {

        Student stud = new Student("Filip", "Charouzd", "M".charAt(0), LocalDate.now(), 1122334);
        List<TestResults> rest = stud.getResults();
        System.out.println(stud.absolventToString());
        for (TestResults r : rest) {
            System.out.println(r.toString());
        }
    }*/
}
