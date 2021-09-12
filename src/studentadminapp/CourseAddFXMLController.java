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
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author MichaelO
 */
public class CourseAddFXMLController implements Initializable 
{
    public static final String FILE_NAME = "courses.txt";

    @FXML
    private Button addCourseButton;
    @FXML
    private Button clearAllButton;
    @FXML
    private Button returnToMainMenu;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField nameTextField;

    
    static ArrayList<Course> coursesArrayList = new ArrayList<Course>();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCourseButtonHandler(ActionEvent event)
    {
        boolean dataValid = true;
        
        String codeStr = codeTextField.getText().trim();
        String nameStr = nameTextField.getText().trim();
       
        try
        {
            Course c = new Course (codeStr, nameStr);

            coursesArrayList.add (c);
         
            JOptionPane.showMessageDialog (null, "Success: course created.");
        }
        catch (CourseException err)
        {
            JOptionPane.showMessageDialog (null, err.getMessage() );
        }
    }

    @FXML
    private void clearAllButtonHandler(ActionEvent event) 
    {
        codeTextField.setText ("");
        nameTextField.setText ("");
    }
    
    @FXML
    private void returnToMainMenuHandler(ActionEvent event) throws Exception 
    {
       Utility.changeToScene (getClass(), event, "FXMLDocument.fxml");
    }
    
    
    public static void saveCoursesToFile ()
    {
        try (Formatter outFile = new Formatter (FILE_NAME) )
        {
            for (Course c : coursesArrayList)
            {
                outFile.format (c.toStringWithLineBreak() );             
            }
        }
        catch (Exception err)
        {
            System.out.println ("ERROR: file could not be saved: '" + FILE_NAME + "'.");
            JOptionPane.showMessageDialog (null, "ERROR: file could not be saved: '" + FILE_NAME + "'.");
        }
    }

    public static void loadCoursesFromFile ()
    {
        try (Scanner inFile = new Scanner (new FileReader (FILE_NAME) ) )
        {
            while (inFile.hasNext() == true)
            {
                Course c = new Course (inFile.nextLine(), inFile.nextLine() );
                coursesArrayList.add (c);
         
                //JOptionPane.showMessageDialog (null, "Success: course created.");
            }
        }
        catch (CourseException err)
        {
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
