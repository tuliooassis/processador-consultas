/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexador;

import indice.estrutura.Indice;
import indice.estrutura.IndiceLight;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringEscapeUtils;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

/**
 *
 * @author aluno
 */
public class Indexador {
    public Indice idx;
    
    public Indexador(){
        //this.idx = new IndiceSimples();
        this.idx = new IndiceLight(10000);
        this.getIdx();
    }
    
    private boolean getIdx() {
        String stopWordsString = "de,a,o,que,e,do,da,em,um,para,é,com,não,uma,os,no,se,na,por,mais,as,dos,como,mas,foi,ao,ele,das,tem,à,seu,sua,ou,ser,quando,muito,há,nos,já,está,eu,também,só,pelo,pela,até,isso,ela,entre,era,depois,sem,mesmo,aos,ter,seus,quem,nas,me,esse,eles,estão,você,tinha,foram,essa,num,nem,suas,meu,às,minha,têm,numa,pelos,elas,havia,seja,qual,será,nós,tenho,lhe,deles,essas,esses,pelas,este,fosse,dele,tu,te,vocês,vos,lhes,meus,minhas,teu,tua,teus,tuas,nosso,nossa,nossos,nossas,dela,delas,esta,estes,estas,aquele,aquela,aqueles,aquelas,isto,aquilo,estou,está,estamos,estão,estive,esteve,estivemos,estiveram,estava,estávamos,estavam,estivera,estivéramos,esteja,estejamos,estejam,estivesse,estivéssemos,estivessem,estiver,estivermos,estiverem,hei,há,havemos,hão,houve,houvemos,houveram,houvera,houvéramos,haja,hajamos,hajam,houvesse,houvéssemos,houvessem,houver,houvermos,houverem,houverei,houverá,houveremos,houverão,houveria,houveríamos,houveriam,sou,somos,são,era,éramos,eram,fui,foi,fomos,foram,fora,fôramos,seja,sejamos,sejam,fosse,fôssemos,fossem,for,formos,forem,serei,será,seremos,serão,seria,seríamos,seriam,tenho,tem,temos,tém,tinha,tínhamos,tinham,tive,teve,tivemos,tiveram,tivera,tivéramos,tenha,tenhamos,tenham,tivesse,tivéssemos,tivessem,tiver,tivermos,tiverem,terei,terá,teremos,terão,teria,teríamos,teriam";
        List<String> stopWords = new ArrayList<String>(Arrays.asList(stopWordsString.split(",")));

        SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.PORTUGUESE);

        //IndiceSimples is = new IndiceSimples();
        IndiceLight is = new IndiceLight(10000);
        int countDocs = 0;
        try {
            for (File file : listFileTree(new File("../wikiSample/"))) {
                StringBuilder contentBuilder = new StringBuilder();
                HashMap<String, Integer> mapWords = new HashMap<String, Integer>();

                BufferedReader in = new BufferedReader(new FileReader(file));
                if (!file.getName().endsWith(".html")) {
                    continue;
                }
                countDocs++;
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
                String content = StringEscapeUtils.unescapeHtml4(
                        contentBuilder
                                .toString()
                                .replaceAll("<[^>]*>", " ")
                );

                System.out.println("Indexando arquivo " + file.getName());

                // contando palavras
                for (String word : content.split(" ")) {
                    word = removerAcentos(word);
                    word = word.trim().replaceAll("[^a-zA-Z]+", "").toLowerCase();
                    if (stopWords.contains(word)) {
                        continue;
                    }
                    CharSequence cs = word;
                    word = stemmer.stem(cs).toString();
                    if (word.length() > 0) {
                        if (mapWords.containsKey(word)) {
                            mapWords.put(word, mapWords.get(word) + 1);
                        } else {
                            mapWords.put(word, 1);
                        }
                    }
                }

                for (String word : mapWords.keySet()) {
                    is.index(word, Integer.parseInt(file.getName().replace(".html", "")), mapWords.get(word));
                }

                if (countDocs % 1000 == 0) {
                    is.concluiIndexacao();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("../outputs/output" + (int) countDocs / 100 + ".txt"));
                    writer.write(is.toString());
                    writer.close();

                    is = new IndiceLight(10000);
                    System.gc();

                }

            }

        } catch (IOException e) {
            System.err.print(e);
        }

        return true;
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
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
