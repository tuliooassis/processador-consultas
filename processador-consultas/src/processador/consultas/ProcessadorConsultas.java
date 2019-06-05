/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processador.consultas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Aluno
 */
public class ProcessadorConsultas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        StringBuilder contentBuilder = new StringBuilder();
        
        try {
            for (File file : listFileTree(new File("../idx/"))) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
                
                String content = contentBuilder.toString();
                contentBuilder = new StringBuilder();
                
                // criar indice
                // fazer consulta
            }
        }
        
        catch (IOException e) {
            System.err.print(e);
        }
    }
    
    public static Collection<File> listFileTree(File dir) {
        Set<File> fileTree = new HashSet<File>();
        if (dir == null || dir.listFiles() == null) {
            return fileTree;
        }
        for (File entry : dir.listFiles()) {
            if (entry.isFile()) {
                fileTree.add(entry);
            } else {
                fileTree.addAll(listFileTree(entry));
            }
        }
        return fileTree;
    }
    

}