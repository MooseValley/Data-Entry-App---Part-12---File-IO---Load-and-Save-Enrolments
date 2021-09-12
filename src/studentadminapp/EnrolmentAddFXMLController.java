/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentadminapp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.swing.JOptionPane;
import static studentadminapp.CourseAddFXMLController.FILE_NAME;
import static studentadminapp.CourseAddFXMLController.coursesArrayList;

/**
 * FXML Controller class
 *
 * @author MichaelO
 */
public class EnrolmentAddFXMLController implements Initializable 
{
    public static final String FILE_NAME = "enrolments.txt";

    @FXML
    private Button addButton;
    @FXML
    private Button clearInputsButton;
    @FXML
    private Button returnToMainMenuButton;
    @FXML
    private ComboBox<Student> studentComboBox;
    @FXML
    private ComboBox<Course> courseComboBox;

    
    static ArrayList<Enrolment> enrolmentsArrayList = new ArrayList<Enrolment>();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        
        //studentComboBox.getItems().addAll ("Moose", "Frankie", "Bella");
        
        for (Student s : StudentAddFXMLController.studentsArrayList)
        {
            studentComboBox.getItems().addAll (s);
        }
        
        for (Course c : CourseAddFXMLController.coursesArrayList)
        {
            courseComboBox.getItems().addAll (c);
        }
        
          
        //studentComboBox.setOnAction (e -> printSelectedStudent() );
    }    

    @FXML
    private void addButtonHandler(ActionEvent event) 
    {
        //printSelectedStudent();
        
        try
        {
            Enrolment e = new Enrolment (studentComboBox.getValue(),
                                         courseComboBox.getValue() );
                
            enrolmentsArrayList.add (e);

            JOptionPane.showMessageDialog (null, "Success: Student Course enrolment created: " + "\n" +
                    "* " + e.toString() );

        }
        catch (Exception err)
        {
            JOptionPane.showMessageDialog (null, "Error: please select a Student and a Course.");
        }
    }
    @FXML
    private void clearInputsButtonHandler(ActionEvent event) {
    }

    @FXML
    private void returnToMainMenuButtonHandler(ActionEvent event) throws Exception 
    {
        Utility.changeToScene (getClass(), event, "FXMLDocument.fxml");
    }

    @FXML
    private void courseComboBox(ActionEvent event) {
    }
    
    
    private void printSelectedStudent()
    {
        System.out.println ("Studnet:  " + studentComboBox.getValue()  +
                            ", Course: " + courseComboBox.getValue() );
        
    }


    public static void saveEnrolmentsToFile ()
    {
        try (Formatter outFile = new Formatter (FILE_NAME) )
        {
            for (Enrolment e : enrolmentsArrayList)
            {
                outFile.format (e.toStringWithLineBreak() );             
            }
        }
        catch (Exception err)
        {
            System.out.println ("ERROR: file could not be saved: '" + FILE_NAME + "'.");
            JOptionPane.showMessageDialog (null, "ERROR: file could not be saved: '" + FILE_NAME + "'.");
        }
    }

    public static void loadEnrolmentsFromFile ()
    {
        try (Scanner inFile = new Scanner (new FileReader (FILE_NAME) ) )
        {
            while (inFile.hasNext() == true)
            {
                int studId = Integer.parseInt (inFile.nextLine() );
                
                Student stud = null;
                
                for (int k = 0; k < StudentAddFXMLController.studentsArrayList.size(); k++)
                {
                    if (studId == StudentAddFXMLController.studentsArrayList.get(k).getStudId() )
                    {
                        stud = StudentAddFXMLController.studentsArrayList.get(k);
                        k = StudentAddFXMLController.studentsArrayList.size(); // Exit Loop !
                    }
                }
                
                String courseCode = inFile.nextLine();
                
                Course course = null;
                
                for (int k = 0; k < CourseAddFXMLController.coursesArrayList.size(); k++)
                {
                    if (courseCode.equalsIgnoreCase (CourseAddFXMLController.coursesArrayList.get(k).getCode() ) == true )
                    {
                        course = CourseAddFXMLController.coursesArrayList.get(k);
                        k = CourseAddFXMLController.coursesArrayList.size(); // Exit Loop !
                    }
                }
                
                
                Enrolment e = new Enrolment (stud, course);
                enrolmentsArrayList.add (e);
         
                //JOptionPane.showMessageDialog (null, "Success: course created.");
            }
        }
        catch (NumberFormatException | CourseException err)
        {
            //JOptionPane.showMessageDialog (null, err.getMessage() );
            JOptionPane.showMessageDialog (null, "ERROR: invalid data, file could not be loaded: '" + 
                                          FILE_NAME + "'." + "\n\n" + err.getMessage() );
        }
        catch (FileNotFoundException err)
        {
            // Do nothing.  File does not yest exist, so this is fine.
        }
        catch (Exception err)
        {
            System.out.println ("ERROR: file could not be loaded: '" + FILE_NAME + "'.");
            JOptionPane.showMessageDialog (null, "ERROR: file could not be loaded: '" + FILE_NAME + "'.");
        }
    }
    
}
