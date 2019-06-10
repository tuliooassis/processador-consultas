package indice.estrutura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndiceSimples extends Indice {

    /**
     * Versao - para gravação do arquivo binário
     */
    private static final long serialVersionUID = 1L;

    private Map<String, List<Ocorrencia>> mapIndice = new HashMap<String, List<Ocorrencia>>();

    public IndiceSimples() {

    }

    @Override
    public void index(String termo, int docId, int freqTermo) {
        Ocorrencia o = new Ocorrencia(docId, freqTermo);
        if (mapIndice.containsKey(termo)) {
            mapIndice.get(termo).add(o);
        } else {
            ArrayList<Ocorrencia> al = new ArrayList<Ocorrencia>();
            al.add(o);
            mapIndice.put(termo, al);
        }
    }

    @Override
    public Map<String, Integer> getNumDocPerTerm() {
        HashMap<String, Integer> countOcorrencias = new HashMap<>();

        mapIndice.keySet().forEach((key) -> {
            ArrayList<Ocorrencia> ocorrencias = (ArrayList<Ocorrencia>) mapIndice.get(key);

            countOcorrencias.put(key, ocorrencias.size());
        });

        return countOcorrencias;
    }

    @Override
    public int getNumDocumentos() {
        ArrayList<Integer> docs = new ArrayList();
        
        mapIndice.keySet().forEach((key) -> {
            ArrayList<Ocorrencia> ocorrencias = (ArrayList<Ocorrencia>) mapIndice.get(key);
            
            for(Ocorrencia ocorrencia : ocorrencias){
                if(!docs.contains(ocorrencia.getDocId())){
                    docs.add(ocorrencia.getDocId());
                }
            }
        });

        return docs.size();
    }

    @Override
    public Set<String> getListTermos() {
        return mapIndice.keySet();
    }

    @Override
    public List<Ocorrencia> getListOccur(String termo) {
        return mapIndice.get(termo);
    }

}
