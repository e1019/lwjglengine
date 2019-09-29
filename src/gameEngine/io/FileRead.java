package gameEngine.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {
    public static String readFile(String dir){
        StringBuilder fileData = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(dir));
            String line;
            while((line = reader.readLine()) != null){
                fileData.append(line).append("\n");
            }
            reader.close();
        } catch(IOException e) {
            Debug.fatalError("Failed to read file " + dir);
        }

        return fileData.toString();
    }
}
