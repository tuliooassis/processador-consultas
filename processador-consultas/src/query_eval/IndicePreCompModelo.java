package query_eval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indice.estrutura.Indice;
import indice.estrutura.Ocorrencia;

public class IndicePreCompModelo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int numDocumentos = 0;
    private double avgLenPerDocument = 0;
    private Map<Integer, Integer> tamPorDocumento = new HashMap<Integer, Integer>();
    private Map<Integer, Double> normaPorDocumento = new HashMap<Integer, Double>();

    private Indice idx;

    public IndicePreCompModelo(Indice idx) {
        this.idx = idx;

        precomputeValues(idx);

    }

    /**
     * Acumula o (tfxidf)^2 de mais uma ocorrencia (oc) no somatorio para
     * calcular a norma por documento Usar a propria norma para acumular o
     * somatorio
     *
     * @param lstOcc
     * @param oc
     */
    public void updateSumSquaredForNorm(int numDocsTerm, Ocorrencia oc) {
        double tf = VectorRankingModel.tf(numDocsTerm);
        double idf = VectorRankingModel.idf(numDocsTerm, oc.getFreq());
        //salvando pelo id
        normaPorDocumento.put(oc.getDocId(), Math.pow(idf*tf,2));
    }

    /**
     * Atualiza o tamPorDocumento com mais uma cocorrencia
     *
     * @param oc
     */
    public void updateDocTam(Ocorrencia oc) {
        if (this.tamPorDocumento.containsKey(oc.getDocId())) {
            this.tamPorDocumento.put(oc.getDocId(), this.tamPorDocumento.get(oc.getDocId()) + oc.getFreq());
        } else {
            this.tamPorDocumento.put(oc.getDocId(), oc.getFreq());
            this.numDocumentos++;
        }
    }

    /**
     * Inicializa os atributos por meio do indice (idx): numDocumentos: o numero
     * de documentos que o indice possui avgLenPerDocument: média do tamanho (em
     * palavras) dos documentos tamPorDocumento: para cada doc id, seu tamanho
     * (em palavras) - use o metodo updateDocTam para auxiliar
     * normaPorDocumento: A norma por documento (cada termo é presentado pelo
     * seu peso (tfxidf) - use o metodo updateSumSquaredForNorm para auxiliar
     *
     * @param idx
     */
    private void precomputeValues(Indice idx) {
        numDocumentos = idx.getNumDocumentos();
        
        for(String term : idx.getListTermos()){
            for (Ocorrencia o : idx.getListOccur(term)){
                updateDocTam(o);
            }
        }
        
        for (Integer doc : tamPorDocumento.keySet()){
            avgLenPerDocument += tamPorDocumento.get(doc);
            
        }
        avgLenPerDocument /= tamPorDocumento.size();
        
        
        for(String term : idx.getListTermos()){
            for (Ocorrencia o : idx.getListOccur(term)){
            updateSumSquaredForNorm(tamPorDocumento.get(o.getDocId()), o);
            }
        }
        
    }
    

    public int getDocumentLength(int docId) {
        return this.tamPorDocumento.get(docId);
    }

    public int getNumDocumentos() {
        return numDocumentos;
    }

    public void setNumDocumentos(int numDocumentos) {
        this.numDocumentos = numDocumentos;
    }

    public double getAvgLenPerDocument() {
        return avgLenPerDocument;
    }

    public void setAvgLenPerDocument(double avgLenPerDocument) {
        this.avgLenPerDocument = avgLenPerDocument;
    }

    public Map<Integer, Double> getNormaPorDocumento() {
        return normaPorDocumento;
    }

    public void setNormaPorDocumento(Map<Integer, Double> normaPorDocumento) {
        this.normaPorDocumento = normaPorDocumento;
    }

    public double getNormaDocumento(int docId) {
        return this.normaPorDocumento.get(docId);
    }

}
