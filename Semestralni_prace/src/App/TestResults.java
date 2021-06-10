package App;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * <h1>Test result </h1>
 * <p>
 * object class creates test object with two proprieties. 1) Student's number -
 * to keep anonymity of student(GDPR) 2) Score - point acquired in test
 * </p>
 *
 * @author Filip Charouzd
 */
public class TestResults {

    private static final double maxScore = 50;
    private final int studNumber;
    private final double score;
    private double grade = Double.NaN;
    private static final double[] grades = {88, 75, 58, 45};
    public boolean passed;

    public TestResults(int studNumber, double score) {
        this.studNumber = studNumber;
        this.score = score;
        grade();
    }

    public double getScore() {
        return score;
    }

    public int getNumber() {
        return studNumber;
    }

    public double getPercentage() {
        return (score / maxScore) * 100;

    }

    public void grade() {

        double percentageSucces = getPercentage();
        if (percentageSucces > grades[0]) {
            grade = 1;
            passed = true;
        } else if (percentageSucces > grades[1]) {
            grade = 2;
            passed = true;
        } else if (percentageSucces > grades[2]) {
            grade = 3;
            passed = true;
        } else if (percentageSucces > grades[3]) {
            grade = 4;
            passed = true;
        } else {
            passed = false;
        }

    }

    @Override
    public String toString() {
        return "TestResults{" + "studNumber=" + studNumber + ", score=" + score + ", grade=" + grade + ", passed=" + passed + '}';
    }

    public double getGrade() {
        return grade;
    }

    public boolean isPassed() {
        return passed;
    }
}
