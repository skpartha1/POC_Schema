package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ZipFileBuilderTest {

    @Test
    public void testZipCreation() throws FileNotFoundException {

        ZipFileBuilder underTest = new ZipFileBuilder();
        File file = new File("/Users/ashish/Documents/Ashish/Test.zip");
        if(file.exists()) file.delete();

        try(FileOutputStream fos = new FileOutputStream(file)){
            underTest.buildZipFile(fos, "TestBsiness", "XXXX");
        }catch(Exception ex){

        }
        assert underTest != null;
    }

}