package query_eval;
import indice.estrutura.IndiceSimples;
import indexador.Indexador;
import indice.estrutura.IndiceLight;
import query_eval.IndicePreCompModelo;
import indice.estrutura.Ocorrencia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import query_eval.BooleanRankingModel.OPERATOR;


public class  QueryRunner {

	public enum OPERACAO{
		AND,OR;
	}
	private RankingModel queryRankingModel;
	private Indexador idx;
	
	public QueryRunner(Indexador idx,RankingModel m)
	{
		this.queryRankingModel = m;
		this.idx = idx;
	}

	/**
	* Adiciona a lista de documentos relevantes para um determinada query (os documentos relevantes foram
	* fornecidos no ".dat" correspondente. Por ex, Belo Horizonte.dat possui os documentos relevantes da consulta "Belo Horizonte"
	* Exemplo de saida:
	* "Sao paulo"=>[1,3,5,6,233],
        *  "Belo Horizonte"=>[2,4,5,3]
	**/
	public static HashMap<String,Set<Integer>> getRelevancePerQuery(){
            HashMap<String,Set<Integer>> relevant = new HashMap<>();
            relevant.put("Belo Horizonte", new HashSet<>(Arrays.asList(484,1083,18111,19588,29241,35985,38636,40226,43658,47967,49186,49772,69168,80198,91593,91853,97000,101043,104137,106575,106575,107302,108604,110030,125549,130173,130173,136473,136473)));
            relevant.put("Sao Paulo", new HashSet<>(Arrays.asList(359,727,820,1084,1084,1291,1324,1719,1752,3546,7687,7847,8170,9258,9277,9744,9928,10558,10756,10792,11478,11664,12218,12381,12383,13726,15814,15837,15837,16722,16722,16722,16865,17422,17422,17661,17661,17703,17703,17768,17768,18143,18285,18299,18299,18305,18307,18312,18312,18364,18411,18482,18489,18489,18515,18769,19963,21467,21504,22159,22159,22647,23402,23412,23413,23420,23439,23441,23505,23983,23988,23990,24007,24088,24089,24546,25107,26173,26805,27860,27897,29427,29459,29460,29476,29477,30016,30119,30402,30640,30641,30649,30652,30653,30654,30658,30667,30669,31202,31879,31880,31882,31886,31888,31889,31890,32353,32573,32738,33267,33530,33643,33643,33739,33850,34606,34613,34627,34653,34699,34770,34773,34776,34777,34778,34779,34784,34785,34786,34787,34791,34794,34798,34800,34803,34806,34807,34808,34809,34812,34813,34814,34815,34819,34820,34821,34822,34824,34825,34829,34830,34831,34832,34836,34837,34840,34841,34843,34844,34845,34847,34849,34850,34851,34852,34855,34857,34858,34861,34862,34863,34865,34868,34869,34871,34872,34873,34874,34876,34878,34879,34880,34882,34884,34885,34886,34888,34890,34891,34892,34893,34894,34895,34896,34897,34898,34900,34901,34902,34903,34906,34907,34908,34911,34912,34914,34915,34918,34919,34921,34923,34926,34928,34930,34931,34932,34933,34934,34935,34936,34937,34939,34940,34941,34943,34945,34950,34952,34953,34955,34958,34959,34962,34963,34965,34967,34968,34969,34970,34971,34972,34974,34975,34978,34979,34981,34982,34983,34984,34985,34986,34987,34990,34991,34992,34995,34998,34999,35000,35001,35002,35004,35006,35007,35011,35012,35020,35021,35022,35023,35024,35026,35027,35028,35029,35030,35031,35032,35034,35035,35036,35038,35039,35040,35041,35043,35046,35047,35048,35049,35052,35054,35055,35056,35058,35059,35061,35063,35064,35066,35067,35069,35070,35073,35074,35076,35077,35079,35083,35086,35087,35089,35092,35093,35095,35098,35099,35100,35101,35102,35108,35109,35110,35111,35112,35113,35114,35115,35117,35119,35122,35123,35124,35125,35126,35130,35131,35132,35134,35135,35138,35139,35140,35141,35143,35144,35145,35148,35149,35150,35151,35153,35155,35156,35157,35158,35160,35161,35162,35163,35164,35165,35166,35167,35168,35170,35171,35173,35174,35175,35177,35181,35182,35183,35184,35186,35188,35189,35193,35194,35195,35196,35197,35198,35201,35202,35204,35205,35206,35207,35210,35212,35213,35214,35215,35216,35217,35218,35221,35222,35223,35224,35225,35226,35227,35229,35230,35231,35233,35235,35238,35239,35240,35241,35875,36238,36238,36238,36511,36655,37053,37054,37056,37085,37087,37087,37240,37241,37242,37243,37244,37247,37249,37252,37255,37256,37258,37261,37265,37274,37282,37283,37297,37600,38535,38535,40127,41761,41761,41906,41942,41944,41945,41948,41955,41958,41961,41964,41967,41971,42255,42582,42765,43939,44001,44027,44033,44043,44596,44836,45081,45239,46533,47221,47240,47510,49745,49752,50674,50702,52221,52388,52391,53155,53269,53833,54087,54205,54428,55965,56310,56690,57810,58105,58426,58490,59093,60377,60430,60431,61190,61403,61403,62759,62764,62828,63123,63584,63586,63593,63613,63617,63637,63702,63715,63775,63776,63827,64326,64678,64679,64908,65568,66592,67061,67301,67685,67965,67967,68791,70209,70457,71254,71322,71562,71920,72332,72497,74190,76506,77762,78269,78432,83535,84257,91915,94770,95286,96931,96944,96945,96952,98274,99800,100337,101163,103003,103262,103495,103736,104490,104847,105368,105369,105372,106867,107278,107604,107841,108256,108256,108677,110028,110131,110315,110916,111222,111495,112599,113733,113999,114091,114625,114888,115341,115646,116539,116543,117843,117875,117951,118365,120283,120284,120719,120839,120924,121025,121580,121946,123161,123706,123768,124032,124267,124692,124892,125486,125623,125876,126579,127303,127538,128145,128635,128906,129465,130272,130359,130537,131865,132004,132679,132760,135137)));
            relevant.put("Irlanda", new HashSet<>(Arrays.asList(1034,1196,1393,3765,7765,9918,9955,11953,13256,15393,26276,33833,37632,37935,39049,39300,43872,44259,45577,46441,47185,51714,51716,51778,51779,51786,52995,52998,53004,54836,58189,83228,93223,95773,102888,105145,105348,111966,124818)));
            return relevant;
	}
	/**
	* Calcula a quantidade de documentos relevantes na top n posições da lista lstResposta que é a resposta a uma consulta
 	* Considere que lstResposta já é a lista de respostas ordenadas por um método de processamento de consulta (BM25, Modelo vetorial).
	* Os documentos relevantes estão no parametro docRelevantes
	*/
	public int countTopNRelevants(int n, List<Integer> lstRespostas, Set<Integer> docRelevantes){
            Integer count = 0;
            for (Integer i = 0; i < n; i ++){
                if(docRelevantes.contains(lstRespostas.get(i))){
                    count++;
                }
            }
            return count;
	}
	/**
	Preprocesse a consulta da mesma forma que foi preprocessado o texto do documento.
	E transforme a consulta em um Map<String,Ocorrencia> onde a String é o termo que ocorreu
	e Ocorencia define quantas vezes esse termo ocorreu na consulta. Coloque o docId como -1.
	DICA: tente reaproveitar metodos do indexador para isso.
        Além disso, se você considerar a consulta como um documento, é possivel fazer
	algo parecido com o que foi feito no metodo index do Indexador.
	*/
	public static Map<String,Ocorrencia> getOcorrenciaTermoConsulta(String consulta){
            IndiceLight idx = new IndiceLight(100);
            Map<String,Ocorrencia> ocorrenciaTermo = new HashMap<>();
            
            HashMap<String, Integer> mapWords = new HashMap<String, Integer>();
            for (String word : consulta.split(" ")) {

                if (word.length() > 0) {
                    if (mapWords.containsKey(word)) {
                        mapWords.put(word, mapWords.get(word) + 1);
                    } else {
                        mapWords.put(word, 1);
                    }
                }
            }
            
            for (String word : mapWords.keySet()) {
                idx.index(word, -1, mapWords.get(word));
            }

            for (String term : idx.getListTermos()) {
                for (Ocorrencia ocorrencia : idx.getListOccur(term)){
                    ocorrenciaTermo.put(term, ocorrencia);
                }
            }

            return ocorrenciaTermo;
	}
	/**
	Retorna um mapa para cada termo existente em setTermo, sua lista ocorrencia no indice (atributo idx do QueryRunner).
	*/
	public Map<String,List<Ocorrencia>> getOcorrenciaTermoColecao(Set<String> setTermo){
            Map<String,List<Ocorrencia>> ocorrenciaTermoColecao = new HashMap<>();
            
            for (String termo : setTermo) {
                ocorrenciaTermoColecao.put(termo, this.idx.idx.getListOccur(termo));
            }
            
            return ocorrenciaTermoColecao;
	}

	/**
	* A partir do indice (atributo idx), retorna a lista de ids de documentos desta consulta 
	* usando o modelo especificado pelo atributo queryRankingModel
	*/
	public Map<String,List<Ocorrencia>> getDocsTermo(String consulta)
	{
		Map<String,List<Ocorrencia>> docsTermo = new HashMap<>();
                
		//Obtenha, para cada termo da consulta, sua ocorrencia por meio do método getOcorrenciaTermoConsulta
		Map<String,Ocorrencia> mapOcorrencia = getOcorrenciaTermoConsulta(consulta);

		//obtenha a lista de ocorrencia dos termos na colecao por meio do método  getOcorrenciaTermoColecao
		Map<String,List<Ocorrencia>> lstOcorrPorTermoDocs = getOcorrenciaTermoColecao(mapOcorrencia.keySet());
	 	

		//utilize o queryRankingModel para retornar o documentos ordenados de acordo com a ocorrencia de termos na consulta e na colecao
                List<Integer> orderedDocs = queryRankingModel.getOrderedDocs(mapOcorrencia, lstOcorrPorTermoDocs);
                
                for (String termo : lstOcorrPorTermoDocs.keySet()){
                    Collections.sort(lstOcorrPorTermoDocs.get(termo), new Comparator<Ocorrencia>(){
                        public int compare(Ocorrencia l, Ocorrencia r){
                            return Integer.compare(orderedDocs.indexOf(l.getDocId()), orderedDocs.indexOf(r.getDocId()));
                        }
                    });
                }
                
		return lstOcorrPorTermoDocs;
	}

	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
                Scanner keyboard = new Scanner(System.in);
		//leia o indice (base da dados fornecida)
  		Indexador indexador = new Indexador();
                
		//Instancie o IndicePreCompModelo para pr ecomputar os valores necessarios para a query
		System.out.println("Precomputando valores atraves do indice...");
		long time = System.currentTimeMillis();
                
                IndicePreCompModelo idxPreCom = new IndicePreCompModelo(indexador.idx);

		System.out.println("Total (precompta o valor da : "+(System.currentTimeMillis()-time)/1000.0+" segs");
		
		//encontra os docs relevantes
		HashMap<String,Set<Integer>> mapRelevances = getRelevancePerQuery();

		System.out.println("Fazendo query...");
		String query;
                
                
                System.out.println("Pesquise algo: ");
                query = keyboard.next();
                
		runQuery(query, indexador, idxPreCom, mapRelevances);
	}
	
	public static void runQuery(String query,Indexador idx, IndicePreCompModelo idxPreCom ,HashMap<String,Set<Integer>> mapRelevantes) {
		long time;
		time = System.currentTimeMillis();
		QueryRunner qr = null;
		//PEça para usuario selecionar entre BM25, Booleano ou modelo vetorial para intanciar o QueryRunner 
		//apropriadamente. NO caso do booleano, vc deve pedir ao usuario se será um "and" ou "or" entre os termos. 
		//abaixo, existem exemplos fixos.
		//QueryRunner qr = new QueryRunner(idx,new BooleanRankingModel(OPERATOR.AND));
		//QueryRunner qr = new QueryRunner(idx,new VectorRankingModel(idxPreCom));
                
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Escolha entre os valores abaixo: ");
                System.out.println("(1) BM25");
                System.out.println("(2) Booleano");
                System.out.println("(3) Modelo Vetorial");
                int resultado = keyboard.nextInt();
                switch(resultado){
                    case 1:
                        qr = new QueryRunner(idx,new BM25RankingModel(idxPreCom, 0.75, 1));
                        break;
                    case 2:
                        System.out.println("Selecione o operador: ");
                        System.out.println("(1) AND");
                        System.out.println("(2) OR");
                        resultado = keyboard.nextInt();
                        if(resultado == 1){
                            qr = new QueryRunner(idx,new BooleanRankingModel(OPERATOR.AND));
                        }
                        else
                            qr = new QueryRunner(idx,new BooleanRankingModel(OPERATOR.OR));
                        break;
                    case 3:
                        qr = new QueryRunner(idx,new VectorRankingModel(idxPreCom));
                        break;
                }
                
		
		System.out.println("Total: "+(System.currentTimeMillis()-time)/1000.0+" segs");
		
		List<Integer> lstResposta = (List) qr.getDocsTermo(query).keySet(); /**utilize o metodo getDocsTerm para pegar a lista de termos da resposta**/
		System.out.println("Tamanho: "+lstResposta.size());
		
		//nesse if, vc irá verificar se a consulta possui documentos relevantes
		//se possuir, vc deverá calcular a Precisao e revocação nos top 5, 10, 20, 50. O for que fiz abaixo é só uma sugestao e o metododo countTopNRelevants podera auxiliar no calculo da revocacao e precisao 
		if(lstResposta.size() > 0)
		{
			int[] arrPrec = {5,10,20,50};
			double revocacao = 0;
			double precisao = 0;
			for(int n : arrPrec)
			{
				revocacao = 0;//substitua aqui pelo calculo da revocacao topN
				precisao = 0;//substitua aqui pelo calculo da revocacao topN
				System.out.println("PRecisao @"+n+": "+precisao);
				System.out.println("Recall @"+n+": "+revocacao);
			}
		}

		//imprima aas top 10 respostas

	}
	
	
}