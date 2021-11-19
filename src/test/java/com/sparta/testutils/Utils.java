package com.sparta.testutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils{

    /**
     * Converts the contents of the testdata.txt file into a byte[].
     *
     * @return The byte[] representation of the text file content
     * @throws IOException An IOException is thrown if something goes wrong
     */
    public static byte[] convertTestFileToByteArray() throws IOException{

        Path fileName = Path.of("src/test/resources/testdata.txt");
        String content = Files.readString(fileName);
        String[] stringArray = content.substring(1,content.length() - 1).split(",");
        byte[] byteArray = new byte[stringArray.length];

        for(int i = 0; i < stringArray.length; i++){
            byteArray[i] = Byte.parseByte(stringArray[i].trim());
        }
        return byteArray;
    }
}
