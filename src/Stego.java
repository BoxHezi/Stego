import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Stego {
    private static final String SOURCE_FILE = "stego_ecg.txt";
    private static final String DEST_FILE = "output.txt";

    public static void main(String[] args) {
        Scanner source = null;
        StringBuffer binaryResult = new StringBuffer();
        try {
            source = new Scanner(new File(SOURCE_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != source) {
            while (source.hasNextLine()) {
                String line = source.nextLine();
                double value = Double.parseDouble(line);

                String binary = Long.toBinaryString(Double.doubleToRawLongBits(value));
                //get the LSB
                binary = binary.substring(binary.length() - 1);
                //append LSB
                binaryResult.append(binary);
            }
            source.close();
        }
        System.out.println(binaryResult);
        printToFile(binaryResult);
    }

    private static void printToFile(StringBuffer string) {
        PrintWriter printWriter = null;
        StringBuffer result = new StringBuffer();
        try {
            printWriter = new PrintWriter(DEST_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (null != printWriter) {
            for (int i = 0; i < string.length(); i += 8) {
                String tempString = string.substring(i, i + 8);
                //convert binary to ascii and them to char
                String c = Character.toString((char) Integer.parseInt(tempString, 2));
                result.append(c);
            }
            printWriter.println(result);
            printWriter.close();
        }
    }
}
