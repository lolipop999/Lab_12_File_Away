import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.System.setOut;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;

public class Main {
    public static void main(String[] args)
    {

        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> lines = new ArrayList<>();

    /*

    Here is the data file we are reading:
    000001, Bilbo, Baggins, Esq., 1060
    000002, Frodo, Baggins, Esq., 1120
    000003, Samwise, Gamgee, Esq., 1125
    000004, Peregrin, Took, Esq., 1126
    000005, Meridoc, Brandybuck, Esq., 1126

    */

        final int FIELDS_LENGTH = 5;

        String id, firstName, lastName, title;
        int yob;

        try
        {

            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // read the file
                int line = 0;  // keep track of lines
                while(reader.ready())
                {
                    rec = reader.readLine();
                    lines.add(rec);  // read all the lines into memory in an array list
                    line++;
                    // echo to screen
                    System.out.printf("\nLine %4d %-60s ", line, rec);
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");

                int numOfWords = 0;
                int numOfChars = 0;
                String[] words;

                for(String l:lines) {
                    words = l.split(" "); // Split the record into the fields
                    numOfWords += words.length;
                    numOfChars += l.length();
                }

                out.println("The file you chose to read was " + file);
                out.println("The number of lines in the file you chose to read was " + line);
                out.println("The number of words in the file you chose to read was " + numOfWords);
                out.println("The number of characters in the file you chose to read was " + numOfChars);


            }
            else  // user closed the file dialog without choosing
            {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }  // end of TRY

        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


}