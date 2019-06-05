package query_eval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indice.estrutura.Ocorrencia;

public class BM25RankingModel implements RankingModel {

    private IndicePreCompModelo idxPrecompVals;
    private double b;
    private int k1;

    public BM25RankingModel(IndicePreCompModelo idxPrecomp, double b, int k1) {
        this.idxPrecompVals = idxPrecomp;
        this.b = b;
        this.k1 = k1;
    }

    /**
     * Calcula o idf (adaptado) do bm25
     *
     * @param numDocs
     * @param numDocsArticle
     * @return
     */
    public double idf(int numDocs, int numDocsArticle) {
        return Math.log((numDocs - numDocsArticle + 0.5)/(numDocsArticle + 0.5));
    }

    /**
     * Calcula o beta_{i,j}
     *
     * @param freqTerm
     * @return
     */
    public double beta_ij(int freqTermDoc, int docId) {
        return ((this.k1 + 1) * freqTermDoc)/(this.k1 * (1 - this.b) + b * idxPrecompVals.getDocumentLength(docId)/idxPrecompVals.getAvgLenPerDocument() + freqTermDoc);
    }

    /**
     * Retorna a lista ordenada de documentos usando o modelo do BM25. para
     * isso, em dj_weight calcule o peso do documento j para a consulta. Para
     * cada termo, calcule o Beta_{i,j} e o idf e acumule o pesso desse termo
     * para o documento. Logo após, utilize UtilQuery.getOrderedList para
     * ordenar e retornar os docs ordenado mapQueryOcour: Lista de ocorrencia de
     * termos na consulta lstOcorrPorTermoDocs: Lista de ocorrencia dos termos
     * nos documentos (apenas termos que ocorrem na consulta)
     */
    @Override
    public List<Integer> getOrderedDocs(Map<String, Ocorrencia> mapQueryOcur,
            Map<String, List<Ocorrencia>> lstOcorrPorTermoDocs) {

        Map<Integer, Double> dj_weight = new HashMap<Integer, Double>();

        for (String termo : mapQueryOcur.keySet()) {
            double idf = VectorRankingModel.idf(idxPrecompVals.getNumDocumentos(), lstOcorrPorTermoDocs.get(termo).size());
            List<Ocorrencia> ocorrencias = lstOcorrPorTermoDocs.get(termo);

            for (Ocorrencia ocorrencia : ocorrencias) {
                double betaij = beta_ij(ocorrencia.getFreq(), ocorrencia.getDocId());

                if (dj_weight.containsKey(ocorrencia.getDocId())) {
                    dj_weight.put(ocorrencia.getDocId(), dj_weight.get(ocorrencia.getDocId()) + (betaij * idf));
                } else {
                    dj_weight.put(ocorrencia.getDocId(), betaij * idf);
                }
            }
        }

        return UtilQuery.getOrderedList(dj_weight);
    }

}
