/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import App.Student;
import App.TestResults;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface to evaluation class.
 * Is good for later update to remember all methods or for other version of evaluating. 
 * @author Filip Charouzd
 */
public interface EvaluateInterface{

    public void saveToTxt(String fileName)throws IOException;
    public String sortByScoreFromWorst();
    public void getAbsolvets(String fileName)throws IOException;
    public void loadStudents(String fileName)throws FileNotFoundException, IOException, IllegalArgumentException;
    public void addStudent(Student stud);
    public void addResult(TestResults test); 
    public String showSize() ;
    public String showStudents();
    public String showAbslovents();
    public String sortByScoreFromBest();
    public void saveToBin(String fileName)throws IOException;
     public  void saveToPdf(String fileName) throws DocumentException, FileNotFoundException;
    }
