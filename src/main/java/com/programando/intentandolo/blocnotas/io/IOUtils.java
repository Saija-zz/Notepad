/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programando.intentandolo.blocnotas.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author saija
 */
public class IOUtils {

    public static void saveFile(String pathfileName, String data) {
        try {
            File filePath = new File(pathfileName);
            
            FileUtils.writeStringToFile(filePath, data, Charset.forName("UTF-8"));
            
        } catch (IOException | IllegalArgumentException ex) {
            Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
