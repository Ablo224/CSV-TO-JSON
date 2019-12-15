package fr.uvsq.abdoumassyasmine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;


public class WriteCsv {
	/*
     * L'enregistreur de classe
     */
    private static final Logger LOGGER = Logger.getLogger(WriteCsv .class);


    /**
     * Écrivez le CSV donné d'un json plat dans le fichier donné.
     * @param fluxJson
     * @param separator
     * @param fileName
     * @param headers
     * @throws  IOException
     */
    public static void writeLargeFile(List<Map<String, String>> fluxJson, String separator, String fileName, Set<String> headers){
    	String csvString;
        csvString = StringUtils.join(headers.toArray(), separator) + "\n";
        File file = new File(fileName);
        
        try {
            
            FileUtils.write(file, csvString, "ISO8859_1");
            
            for (Map<String, String> map : fluxJson) {
            	csvString = "";
            	csvString = getSeperatedColumns(headers, map, separator) + "\n";
            	Files.write(Paths.get(fileName), csvString.getBytes("ISO8859_1"), StandardOpenOption.APPEND);
            }            
        } catch (IOException e) {
           
        }
    }    

    /**
     * Obtenez des comlumns séparés en utilisant un séparateur (virgule, demi-colonne, tabulation).
     * @param headers
     * @param  map
     * @param separator
   
     */
    private static String getSeperatedColumns(Set<String> headers, Map<String, String> map, String separator) {
        List<String> items = new ArrayList<String>();
        for (String header : headers) {
            String value = map.get(header) == null ? "" : map.get(header).replaceAll("[\\,\\;\\r\\n\\t\\s]+", " "); 
            items.add(value);
        }

        return StringUtils.join(items.toArray(), separator);
    }

  

    /**
      * Obtenez l'en-tête ordonné CSV
     * @param fluxJson
     * @param separator
     * @param fileName
     * @param headers
     * @throws  IOException
     */
    public static Set<String> collectOrdered(List<Map<String, String>> fluxJson) {
        Set<String> headers = new TreeSet<String>();
        for (Map<String, String> map : fluxJson) {
        	headers.addAll(map.keySet());
        }
        return headers;
    }       
}
