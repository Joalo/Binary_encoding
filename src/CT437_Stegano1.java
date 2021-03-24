import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CT437_Stegano1
{
    /**
     * Constructor for objects of class Stegano1
     */
    public CT437_Stegano1()
    {

    }

    public static void main(String[] args) {
        String arg1, arg2, arg3, arg4;
        Boolean err = false;

        if (args != null && args.length > 1)
        { // Check for minimum number of arguments
            arg1 = args[0];
            arg2 = args[1];

            if (arg2 == "") {
                err = true;
            }
            else if ((arg1.equals( "A")) && (args.length > 3)){
                // Get other arguments
                arg3 = args[2];
                arg4 = args[3];
                if (arg3 == "" || arg4 == "") {
                    err = true;
                }
                else {
                    // Hide bitstring
                    hide(arg2, arg3, arg4);
                }
            }
            else if (arg1.equals( "E")){
                // Extract bitstring from text
                retrieve(arg2);
            }
            else {
                err = true;
            }
        }
        else {
            err = true;
        }

        if (err == true) {
            System.out.println();
            System.out.println("    Use: CT437_Stegano1 <A:E><Input File><OutputFile><Bitstring>");
            System.out.println("Example: CT437_Stegano1 A wby2.txt wby3.txt 0010101");
            System.out.println("Example: CT437_Stegano1 E wby2.txt");

        }
    }

    static void hide(String inpFile, String outFile, String binString) {
        //
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(inpFile));
            writer = new BufferedWriter(new FileWriter(outFile));
            String line = reader.readLine();

            int curr = 0;
            int bincode = 0;

            while (line != null)
            {
                //If there are no more bits to encode skip to end
                if (curr >= binString.length() )
                {
                    writer.write(line);
                    writer.newLine();
                    // read next line
                    line = reader.readLine();
                    continue;
                }
                else

                    //get value from bit string
                    bincode = Character.getNumericValue(binString.charAt(curr));

                if(bincode == 0)
                {
                    line = line + " ";

                }
                else if(bincode == 1)
                {
                    line = line + "  ";
                }
                else
                    {
                    throw new IOException();
                    }

                curr++;

                // Store amended line in output file
                writer.write(line);
                writer.newLine();
                // read next line
                line = reader.readLine();
            }
            reader.close();
            writer.close();
            System.out.println("code has been hidden");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    static void retrieve(String inpFile)
    {
        BufferedReader reader;
        String answer = "";

        int i=0;
        try {
            reader = new BufferedReader(new FileReader(inpFile));
            String line = reader.readLine();
            while (line != null) {
                // Your code starts here
                if(line.equals(""))
                {
                    line = reader.readLine();
                }
                else
                    {

                    String lastChar = line.substring(line.length()-1);
                    //take into account empty line that may have hidden space
                    if(lastChar.equals(" "))
                    {
                        if(line.length()==1)
                        {
                            answer = answer+"1";
                            line = reader.readLine();
                            continue;
                        }
                        // check if 0 or 1
                        String secondLastChar = line.substring(line.length()-2);
                        if(secondLastChar.equals("  "))
                        {

                            answer = answer + "1";
                        }
                        else
                            {
                            answer = answer + "0";
                            }
                     }
                // read next line
                line = reader.readLine();
                    }
            }
            System.out.println(answer);
            System.out.println("retrieved hidden code");
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}