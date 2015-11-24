package br.com.cds.connecta.portal;

import br.com.cds.connecta.framework.core.exception.SystemException;
import br.com.cds.connecta.framework.core.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.UpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import org.apache.solr.common.SolrDocumentList;

public class UtilSolr {

    private static final Logger logger = LoggerFactory.getLogger(UtilSolr.class);
    
    private static final String SOLR_INDEX_ROOT_PROP = "connecta.search.solrbackend";

    public static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";

//    public static void update(List<ClassificationResults> listClassification) {
//        if (!Util.isEmpty(listClassification)) {
//            logger.info("<UtilSolr Update solr sentiment> Process started->size=" + listClassification.size() + "");
//            for (ClassificationResults classificationResults : listClassification) {
//                if (classificationResults.getFeeling() != null) {
//                    update(classificationResults.getJobResulId(), classificationResults.getFeeling(), classificationResults.getNetwork());
//                }
//
//            }
//            logger.info("<UtilSolr Update solr sentiment> Process fineshed ");
//        }
//    }
//
//    public static void update(Integer jobResultId, SentimentEnum sentimentEnum, SocialNetworkEnum socialNetworkEnum) {
//        try {
//            SolrServer solrServer = getSolrServer();
//            String solrId = findSolrId(jobResultId, socialNetworkEnum, solrServer);
//            if (!Util.isEmpty(solrId)) {
//                ModifiableSolrParams params = new ModifiableSolrParams();
//                params.set(UpdateParams.COMMIT, "true");
//                QueryRequest request = new QueryRequest(params);
//                request.setPath("/update");
//                SolrInputDocument inputDoc = new SolrInputDocument();
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("set", sentimentEnum.getDescription());
//                inputDoc.setField("id", solrId);
//                inputDoc.setField("JOBRESULTID", jobResultId);
//                inputDoc.setField("NETWORK", socialNetworkEnum.getDescription());
//                inputDoc.setField("SENTIMENT", map);
//                solrServer.add(inputDoc);
//                solrServer.request(request);
//                solrServer.commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Failed to solr sentiment update", e);
//        }
//    }
//
//    public static void update(SolrDocBean docBean) {
//        try {
//            SolrServer solrServer = getSolrServer();
//            String solrId = docBean.getPostId();
//            if (!Util.isEmpty(solrId)) {
//                ModifiableSolrParams params = new ModifiableSolrParams();
//                params.set(UpdateParams.COMMIT, "true");
//                QueryRequest request = new QueryRequest(params);
//                request.setPath("/update");
//                SolrInputDocument inputDoc = new SolrInputDocument();
//                inputDoc.setField("id", solrId);
//                inputDoc.setField("JOBID", docBean.getJobId());
//                inputDoc.setField("JOBNAME", docBean.getJobName());
//                inputDoc.setField("JOBTYPE", docBean.getJobType());
//                solrServer.add(inputDoc);
//                solrServer.request(request);
//                solrServer.commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Failed to solr sentiment update", e);
//        }
//    }

    public static SolrDocumentList executeSolrQuery(List<String> filterQueries) throws SolrServerException {
        SolrServer solrServer = getSolrServer();
        SolrQuery query = new SolrQuery("*:*");

        String[] filtersArray = filterQueries.toArray(new String[0]);
        query.addFilterQuery(filtersArray);

        // Seta a quantidade de resultados na resposta        
        query.setRows(Integer.MAX_VALUE);

        List<String> asList = Arrays.asList(query.getFilterQueries());
        System.out.println("Filter Queries: " + asList);

        QueryResponse response = solrServer.query(query);
        return response.getResults();
    }
    
    public static SolrDocumentList searchSingleField(String field, String value, Integer rows) {
        SolrServer solrServer = getSolrServer();
        SolrQuery query = new SolrQuery(String.format("%s:*%s*", field, value));

        // Seta a quantidade de resultados na resposta
        query.setRows(rows);
        
        try {
            QueryResponse response;
            response = solrServer.query(query);
            return response.getResults();
        } catch (SolrServerException ex) {
            throw new SystemException(ex);
        }
    }
    
    public static List<Map<String, Object>> searchSingleFieldAsMapList(String field, String value, Integer rows) {
        SolrDocumentList documents = searchSingleField(field, value, rows);
        List<Map<String, Object>> viewers = new ArrayList<>(documents.size());
        
        for (SolrDocument document : documents) {
            Map<String, Object> viewer = new HashMap<>(document);
            viewers.add(viewer);
        }
        
        return viewers;
    }
    
    

//    public static SolrDocumentList getDueSiconvPrograms(Alerta alerta, AlertaParameterTypeEnum type) throws SolrServerException, ParseException {
//        
//        List<String> filterQueries = new ArrayList<String>();
//        
//        Date lastExecution = alerta.getLastExecution();
//        Calendar now = Calendar.getInstance();
//        Integer diferencaEmDias = UtilDate.diferencaEmDias(lastExecution, now.getTime());
//        
//        if(diferencaEmDias == 0){
//            setCollectedAtFilterQuery(alerta, filterQueries);
//        }
//
//        Map<AlertaParameterTypeEnum, AlertaParameter> paramsMap = alerta.getAlertaParameters();
//
//        AlertaParameter dateParam = paramsMap.get(type);
//        Integer dateParamInt = Integer.parseInt(dateParam.getValue());
//        
//        now.set(Calendar.HOUR_OF_DAY, 23);
//        now.set(Calendar.MINUTE, 59);
//        now.set(Calendar.MILLISECOND, 59);
//        now.add(Calendar.DATE, dateParamInt-1);
//        String dateFrom = UtilDate.formatDate(now.getTime(), SOLR_DATE_FORMAT);
//        now.add(Calendar.DATE, 1);
//        String dateTo = UtilDate.formatDate(now.getTime(), SOLR_DATE_FORMAT);
//        
//        filterQueries.add(type.getFieldName() + ":[\"" + dateFrom +"\" TO \""+dateTo+"\"]");
//
//        createSiconvFilterQuery(filterQueries, paramsMap);
//
//        return executeSolrQuery(filterQueries);
//    }

//    
//    private static void setCollectedAtFilterQuery(Alerta alerta, List<String> filterQueries) {
//        Date lastExecution = alerta.getLastExecution();
//        String dateStr = UtilDate.formatDate(lastExecution, SOLR_DATE_FORMAT);
//        filterQueries.add("COLLECTED_AT:[" + dateStr + " TO *]");
//    }

    
//    public static SolrDocumentList getNewSiconvPrograms(Alerta alerta) throws SolrServerException {
//        List<String> filterQueries = new ArrayList<String>();
//
//        // Seta o filtro de data (COLLECTED_AT)
//        setCollectedAtFilterQuery(alerta, filterQueries);
//
//        Map<AlertaParameterTypeEnum, AlertaParameter> paramsMap = alerta.getAlertaParameters();
//
//        createSiconvFilterQuery(filterQueries, paramsMap);
//
//        return executeSolrQuery(filterQueries);
//    }
//
//    private static void createSiconvFilterQuery(List<String> filterQueries, Map<AlertaParameterTypeEnum, AlertaParameter> paramsMap) {
//
//        // Retira do mapa parametros que não são FQ's
//        paramsMap.remove(AlertaParameterTypeEnum.FIM_PROPOSTAS);
//        paramsMap.remove(AlertaParameterTypeEnum.INICIO_PROPOSTAS);
//        paramsMap.remove(AlertaParameterTypeEnum.NOVO_PROGRAMA);
//
//        // Seta os filtros para as keywords, no campo NOME
//        AlertaParameter keywordParam = paramsMap.remove(AlertaParameterTypeEnum.KEYWORDS);
//        if (!Util.isNull(keywordParam)) {
//            String[] split = keywordParam.getValue().split("\\|");
//            System.out.println("Split keywords: " + Arrays.asList(split));
//            String keywordFilter = keywordParam.getType().getFieldName() + ":(";
//            for (int i = 0; i < split.length - 1; i++) {
//                String value = split[i];
//                value = value.toUpperCase();
//                value = "/.*" + value + ".*/";
//                keywordFilter += value + " OR ";
//            }
//            String value = split[split.length - 1];
//            value = value.toUpperCase();
//            value = "/.*" + value + ".*/";
//            keywordFilter += value + ")";
//            filterQueries.add(keywordFilter);
//        }
//
//        //Seta o FQ do campo UFS_HABILITADAS
//        AlertaParameter ufsParam = paramsMap.remove(AlertaParameterTypeEnum.UFS);
//        if (!Util.isNull(ufsParam)) {
//            String[] split = ufsParam.getValue().split("\\|");
//            System.out.println("Split ufs: " + Arrays.asList(split));
//            String ufsFilter = ufsParam.getType().getFieldName() + ":(";
//            for (int i = 0; i < split.length - 1; i++) {
//                ufsFilter += split[i] + " OR ";
//            }
//            ufsFilter += split[split.length - 1] + ")";
//            filterQueries.add(ufsFilter);
//        }
//
//        // Seta o filtro para os FQ's que não são multiple value
//        for (AlertaParameterTypeEnum keySet : paramsMap.keySet()) {
//            AlertaParameter param = paramsMap.get(keySet);
//            filterQueries.add(param.getType().getFieldName() + ":" + param.getValue());
//        }
//    }
//
//    private static SolrInputDocument createSolrDoc(SolrDocBean bean) {
//        SolrInputDocument doc = new SolrInputDocument();
//        doc.addField("ID", bean.getJobId() + "_" + bean.getPostId());
//        doc.addField("ARCHIVED", 0);
//        doc.addField("POST_ID", bean.getPostId());
//        doc.addField("JOBRESULTID", bean.getJobResultId());
//        doc.addField("JOBID", bean.getJobId());
//        doc.addField("JOBNAME", bean.getJobName());
//        doc.addField("JOBTYPE", bean.getJobType());
//        doc.addField("COLLECTOR_USERID", bean.getCollectorUserId());
//        doc.addField("COLLECTOR_USERNAME", bean.getCollectorUserName());
//        doc.addField("CREATED_AT", bean.getCreatedAt());
//        doc.addField("CREATED_AT_STR", bean.getCreatedAtSrt());
//        doc.addField("COLLECTED_AT", bean.getCollectedAt());
//        doc.addField("USER_NAME", bean.getUserName());
//        doc.addField("USER_ID", bean.getUserId());
//        doc.addField("USER_PICTURE", bean.getUserPicture());
//        doc.addField("TITLE", bean.getTitle());
//        doc.addField("DESCRIPTION", bean.getDescription());
//        doc.addField("KEYWORDS", bean.getKeywords());
//        doc.addField("STORY", bean.getStory());
//        doc.addField("LINK", bean.getLink());
//        doc.addField("TYPE", bean.getType());
//        doc.addField("NETWORK", bean.getNetwork());
//        doc.addField("SOURCE", bean.getSource());
//        doc.addField("VIEWCOUNT", bean.getViewCount());
//        doc.addField("FAVORITECOUNT", bean.getFavoriteCount());
//        doc.addField("SHARED", bean.getShared());
//        doc.addField("LIKES", bean.getLikes());
//        doc.addField("DISLIKES", bean.getDislikes());
//        doc.addField("COMMENTCOUNT", bean.getCommentCount());
//        doc.addField("COMMENTS", bean.getComments());
//        doc.addField("FRIENDS", bean.getFriends());
//        
//        // Algumas redes não retornam hashtags
//        if(bean.getHashtag() != null && !bean.getHashtag().isEmpty()){
//            doc.addField("HASHTAG", bean.getHashtag());
//        }
//        doc.addField("MESSAGE", bean.getMessage());
//        doc.addField("USER_SCREEN_NAME", bean.getUserScreenName());
//        doc.addField("USER_LOCATION", bean.getUserLocation());
//        doc.addField("LOCATION_COORDINATES", bean.getLocationCoordinates());
//        doc.addField("LOCATION_COORDINATES_TYPE", bean.getLocationCoordinatesType());
//        doc.addField("LATITUDE", bean.getLatitude());
//        doc.addField("LONGITUDE", bean.getLongitude());
//        doc.addField("PICTURE", bean.getPicture());
//        doc.addField("GROUP", bean.getGroup());
//        doc.addField("SENTIMENT", bean.getSentiment());
//        doc.addField("SENTIMENT_VALUE", bean.getSentimentValue());
//        doc.addField("SENTIMENT_USER_INTERACTION", bean.getSentimentUserInteraction());
//        doc.addField("RATING", bean.getRating());
//        return doc;
//    }
//
//    private static SolrInputDocument createSolrDoc(SiconvSolrDoc bean) {
//        SolrInputDocument doc = new SolrInputDocument();
//        doc.addField("ID", bean.getSiconvId());
//        doc.addField("ID_SICONV", bean.getSiconvId());
//        doc.addField("LINK", bean.getLink());
//        doc.addField("PROGRAMA_DESCRICAO", bean.getDescricao());
//        doc.addField("ACAO_ORCAMENTARIA", bean.getAcaoOrcamentaria());
//        doc.addField("COD_PROGRAMA_SICONV", bean.getCodProgramaSiconv());
//        doc.addField("NOME", bean.getNome());
//        doc.addField("SITUACAO", bean.getSituacao());
//        doc.addField("OBRIGA_PLANO_TRABALHO", bean.getObrigaPlanoTrabalho());
//        doc.addField("ACEITA_EMENDA_PARLAMENTAR", bean.getAceitaEmendaParlamentar());
//        doc.addField("UFS_HABILITADAS", bean.getUfsHabilitadas());
//
//        //URLS_NATUREZA_JURIDICA
//        doc.addField("URL_ATENDEA", bean.getAtendeA());
//
//        //DATAS
//        doc.addField("DATA_INICIO_RECEBIMENTO_PROPOSTAS", bean.getDataInicioRecebimentoPropostas());
//        doc.addField("DATA_FIM_RECEBIMENTO_PROPOSTAS", bean.getDataFimRecebimentoPropostas());
//        doc.addField("DATA_INICIO_BENEFICIARIO_ESPECIFICO", bean.getDataFimRecebimentoPropostas());
//        doc.addField("DATA_FIM_BENEFICIARIO_ESPECIFICO", bean.getDataFimBeneficiarioEspecifico());
//        doc.addField("DATA_DISPONIBILIZACAO", bean.getDataDisponibilizacao());
//        doc.addField("DATA_INICIO_EMENDA_PARLAMENTAR", bean.getDataInicioEmendaParlamentar());
//        doc.addField("DATA_PUBLICACAO_DOU", bean.getDataPublicacaoDou());
//
//        //Orgaos
//        doc.addField("NOME_ORGAO", bean.getListOrgaoName());
//        doc.addField("ORGAO_ID_SICONV", bean.getListOrgaoIdSiconv());
//        doc.addField("TIPO_ORGAO", bean.getListOrgaoType());
//        doc.addField("URL_ORGAO", bean.getListOrgaoUrl());
//        if (bean.getOrgaoExecutor() != null) {
//            doc.addField("ORGAO_EXECUTOR", bean.getOrgaoExecutor().getOrgaoIdSiconv());
//            doc.addField("ORGAO_EXECUTOR_NOME", bean.getOrgaoExecutor().getNome());
//        }
//        if (bean.getOrgaoSuperior() != null) {
//            doc.addField("ORGAO_SUPERIOR", bean.getOrgaoSuperior().getOrgaoIdSiconv());
//            doc.addField("ORGAO_SUPERIOR_NOME", bean.getOrgaoSuperior().getNome());
//        }
//        if (bean.getOrgaoMandatório() != null) {
//            doc.addField("ORGAO_MANDATARIO", bean.getOrgaoMandatório().getOrgaoIdSiconv());
//            doc.addField("ORGAO_MANDATARIO_NOME", bean.getOrgaoMandatório().getNome());
//        }
//        if (bean.getOrgaoVinculado() != null) {
//            doc.addField("ORGAO_VINCULADO", bean.getOrgaoVinculado().getOrgaoIdSiconv());
//            doc.addField("ORGAO_VINCULADO_NOME", bean.getOrgaoVinculado().getNome());
//        }
//
//        //LINKS
//        doc.addField("LINK_PROPOSTAS", bean.getLinkPropostas());
//        doc.addField("LINK_CONVENIOS", bean.getLinkConvenios());
//        doc.addField("LINK_EMENDAS", bean.getLinkEmendas());
//
//        //Default
////        doc.addField("JOBTYPE", 5);
////        doc.addField("ARCHIVED", bean.getArchived());
////        doc.addField("DELETED", bean.getDeleted());
//        doc.addField("COLLECTED_AT", bean.getCollectedAt());
//        doc.addField("NETWORK", "SICONV");
//
//        return doc;
//    }
//
//    /**
//     * Insere documentos no Solr.
//     *
//     * @param beans - Lista com os beans de documentos a serem inseridos.
//     * @throws SolrServerException
//     * @throws IOException
//     */
//    public static void insert(SolrDocBean bean) throws SolrServerException, IOException {
//        SolrServer solrServer = getSolrServer();
//
//        solrServer.add(createSolrDoc(bean));
//        solrServer.commit();
//    }
//
//    /**
//     * Insere documentos de Programas Sionv no Solr
//     *
//     * @param bean - Resultado da API siconv
//     * @throws SolrServerException
//     * @throws IOException
//     */
//    public static void insertProgramaSiconv(List<SiconvSolrDoc> programas) throws SolrServerException, IOException {
//        SolrServer solrServer = getSolrServer();
//
//        for (SiconvSolrDoc programa : programas) {
//            solrServer.add(createSolrDoc(programa));
//        }
//
//        solrServer.commit();
//    }
//
//    public static void insert(List<SolrDocBean> beans) throws SolrServerException, IOException {
//        SolrServer solrServer = getSolrServer();
//        for (SolrDocBean bean : beans) {
//            solrServer.add(createSolrDoc(bean));
//        }
//        solrServer.commit();
//    }
//
//    private static String findSolrId(Integer jobResultId,
//            SocialNetworkEnum socialNetworkEnum, SolrServer solrServer)
//            throws SolrServerException {
//        SolrQuery query = new SolrQuery("*:*");
//        query.addFilterQuery("JOBRESULTID:" + jobResultId + "", "NETWORK:" + socialNetworkEnum.getDescription() + "");
//        QueryResponse response = solrServer.query(query);
//        SolrDocumentList results = response.getResults();
//        if (!Util.isEmpty(results)) {
//            SolrDocument solrDocument = results.get(0);
//            /*System.out.println(solrDocument.getFieldValue("id"));
//             System.out.println(solrDocument.getFieldValue("JOBRESULTID"));
//             System.out.println(solrDocument.getFieldValue("USER_NAME"));
//             System.out.println(solrDocument.getFieldValue("NETWORK"));
//             System.out.println(solrDocument.getFieldValue("SENTIMENT"));*/
//            return solrDocument.getFieldValue("id").toString();
//        }
//        logger.info("<UtilSolr findSolrId>Return null -> JOBRESULTID:" + jobResultId + "NETWORK:" + socialNetworkEnum.getDescription() + "");
//        return null;
//    }
//    
//    public static void updateCollected(Date date) throws SolrServerException, IOException {
//        SolrServer solrServer = getSolrServer();
//        SolrInputDocument doc = new SolrInputDocument();
//        doc.addField("ID", "104657");
//
//        Map<String, String> archivedValue = new HashMap<String, String>();
//        archivedValue.put("set", UtilDate.formatDate(date, SOLR_DATE_FORMAT));
//        doc.setField("COLLECTED_AT", archivedValue);
//
//        solrServer.add(doc);
//        solrServer.commit();
//
//    }

//    public static SolrDocBean findSolrById(String jobResultId, SocialNetworkEnum socialNetworkEnum) throws SolrServerException {
//
//        SolrQuery query = new SolrQuery("*:*");
//        query.addFilterQuery("id:" + jobResultId + "", "NETWORK:" + socialNetworkEnum.getDescription() + "");
//        SolrServer solrServer = new HttpSolrServer(UtilConnectaConfig.getIndexServer().trim());
//        QueryResponse response = solrServer.query(query);
//        org.apache.solr.common.SolrDocumentList results = response.getResults();
//        SolrDocBean result = new SolrDocBean();
//
//        if (!Util.isEmpty(results)) {
//            SolrDocument solrDocument = results.get(0);
//            result.setPostId(jobResultId);
//
//            Collection<Object> jobId = solrDocument.getFieldValues("JOBID");
//            result.setJobId(getListString(jobId));
//
//            Collection<Object> jobName = solrDocument.getFieldValues("JOBNAME");
//            result.setJobName(getListString(jobName));
//
//            Collection<Object> jobType = solrDocument.getFieldValues("JOBTYPE");
//            result.setJobType(getListString(jobType));
//        }
//        logger.info("<UtilSolr findSolrId>Return null -> JOBRESULTID:" + jobResultId + "NETWORK:" + socialNetworkEnum.getDescription() + "");
//        return result;
//    }
    /**
     * Atribui o valor de Arquivado (ARCHIVED) para os documentos do Solr
     * referentes ao JobId.
     *
     * @param jobId
     * @throws SolrServerException
     * @throws IOException
     */
    public static void archiveDocsByJobId(Integer jobId) throws SolrServerException, IOException {
        SolrServer solrServer = getSolrServer();
        SolrDocumentList results = getDocsByJobId(jobId, solrServer);
        if (Util.isEmpty(results)) {
            System.out.println("Não foram encontrados nenhum doc para o JOBID: " + jobId);
            return;
        }
        List<SolrInputDocument> updateList = setDocsAsArchived(results);
        executeUpdate(solrServer, updateList);
    }

    /**
     * Restaura os documentos com status de arquivado (ARCHIVED) para os
     * documentos do Solr referentes ao jobId.
     *
     * @param jobId
     * @throws SolrServerException
     * @throws IOException
     */
    public static void restoreDocsByJobId(Integer jobId) throws SolrServerException, IOException {
        SolrServer solrServer = getSolrServer();
        SolrDocumentList results = getDocsByJobId(jobId, solrServer);
        if (Util.isEmpty(results)) {
            System.out.println("Não foram encontrados nenhum doc para o JOBID: " + jobId);
            return;
        }
        List<SolrInputDocument> updateList = setDocsAsNotArchived(results);
        executeUpdate(solrServer, updateList);
    }

    /**
     * Recupera documentos do Solr de um determinado Job pelo JobId
     *
     * @param jobId
     * @param solrServer
     * @return SolrDocumentList
     * @throws SolrServerException
     */
    private static SolrDocumentList getDocsByJobId(Integer jobId, SolrServer solrServer) throws SolrServerException {
        SolrQuery query = new SolrQuery("*:*");
        query.addFilterQuery("JOBID:" + jobId);
        query.setRows(Integer.MAX_VALUE);
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        return results;
    }

    /**
     * Atribui o valor de Arquivado (Archived) aos documentos da lista ao mesmo
     * tempo que converte a lista de documentos do Solr para documentos de
     * atualização para serem enviados em requisição de update pelo SolrJ
     *
     * @param results
     * @return List<SolrInputDocument>
     */
    private static List<SolrInputDocument> setDocsAsArchived(SolrDocumentList results) {
        List<SolrInputDocument> updateList = new ArrayList<SolrInputDocument>();
        for (SolrDocument doc : results) {
            System.out.println("Processed Doc with ID: " + doc.getFieldValue("id"));
            SolrInputDocument inputDoc = new SolrInputDocument();
            Map<String, Integer> archivedValue = new HashMap<String, Integer>();
            archivedValue.put("set", 1);
            inputDoc.setField("ARCHIVED", archivedValue);
            inputDoc.setField("id", doc.getFieldValue("id"));
            updateList.add(inputDoc);
        }
        return updateList;
    }

    /**
     * Atribui o valor de não Arquivado (Archived) aos documentos da lista ao
     * mesmo tempo que converte a lista de documentos do Solr para documentos de
     * atualização para serem enviados em requisição de update pelo SolrJ
     *
     * @param results
     * @return List<SolrInputDocument>
     */
    private static List<SolrInputDocument> setDocsAsNotArchived(SolrDocumentList results) {
        List<SolrInputDocument> updateList = new ArrayList<SolrInputDocument>();
        for (SolrDocument doc : results) {
            System.out.println("Processed Doc with ID: " + doc.getFieldValue("id"));
            SolrInputDocument inputDoc = new SolrInputDocument();
            Map<String, Integer> archivedValue = new HashMap<String, Integer>();
            archivedValue.put("set", 0);
            inputDoc.setField("ARCHIVED", archivedValue);
            inputDoc.setField("id", doc.getFieldValue("id"));
            updateList.add(inputDoc);
        }
        return updateList;
    }

    /**
     * Executa o update no servidor a partir da lista de documentos a serem
     * atualizados.
     *
     * @param solrServer
     * @param updateList
     * @throws SolrServerException
     * @throws IOException
     */
    private static void executeUpdate(SolrServer solrServer, List<SolrInputDocument> updateList) throws SolrServerException, IOException {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(UpdateParams.COMMIT, "true");
        QueryRequest request = new QueryRequest(params);
        request.setPath("/update");
        solrServer.add(updateList);
        solrServer.request(request);
        solrServer.commit();
    }

//    public static void deleteSolrJob(List<Job> listJobs)
//            throws SolrServerException {
//        SolrServer solrServer = getSolrServer();
//        if (!listJobs.isEmpty()) {
//            for (Job job : listJobs) {
//                try {
//                    solrServer.deleteByQuery("JOBID:" + job.getId());
//                    solrServer.commit();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }

    /*public static void executeDeleteSolrJob(Integer jobId, Integer userId) {
     try {
     SolrServer solrServer = new HttpSolrServer(UtilConnectaConfig.getIndexServer().trim());
     SolrQuery query = new SolrQuery("*:*");
     query.addFilterQuery("JOBID:" + jobId + "");
     Integer fetchSize = 100;
     query.setRows(Integer.MAX_VALUE);
     QueryResponse response = solrServer.query(query);
     Integer offset = 0;
     Integer totalResults = response.getResults().size();
     while (offset < totalResults) {
     query.setStart(offset);  // requires an int? wtf?
     query.setRows(fetchSize);

     prepareDeleteSolrJob(jobId, userId, solrServer, query);

     offset += fetchSize;
     }

     } catch (SolrServerException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }

     }

     //Aṕos o refactory apenas será 1 solrId para cada usuário 
     private static void prepareDeleteSolrJob(Integer jobId, Integer userId,
     SolrServer solrServer, SolrQuery query) throws SolrServerException,
     IOException {
     org.apache.solr.common.SolrDocumentList results = solrServer.query(query).getResults();
     if (!Util.isEmpty(results)) {
     for (SolrDocument solrDocument : results) {
     Object jobResultId = solrDocument.getFieldValue("JOBRESULTID");
     Object solrId = solrDocument.getFieldValue("id");
     System.out.println(solrId);
     deleteSolrDocument(solrServer, solrId);
     Collection<Object> listaUserId = solrDocument.getFieldValues("COLLECTOR_USERID");
     if (!Util.isEmpty(listaUserId)) {
     if (listaUserId.size() == 1) {
     deleteSolrDocument(solrServer, solrId);
     }else{
     removeSolrUser(jobId, userId, solrServer,jobResultId, solrId, listaUserId);
     }
     }else{
     deleteSolrDocument(solrServer, solrId);
     }

     }
     }
     }
     */
    public static void removeSolrUser(Integer jobId, Integer userId,
            SolrServer solrServer, Object jobResultId, Object solrId,
            Collection<Object> listaUserId) throws SolrServerException,
            IOException {
        List<Object> listaRemove = new ArrayList<Object>();
        for (Object solrUserId : listaUserId) {
            if (solrUserId.toString().equals(userId.toString())) {
                listaRemove.add(solrUserId);
            }
        }
        for (Object userRemove : listaRemove) {
            listaUserId.remove(userRemove);
        }

        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(UpdateParams.COMMIT, "true");
        QueryRequest request = new QueryRequest(params);
        request.setPath("/update");
        SolrInputDocument inputDoc = new SolrInputDocument();
        Map<String, Collection<Object>> map = new HashMap<String, Collection<Object>>();
        map.put("set", listaUserId);
        inputDoc.setField("id", solrId);
        inputDoc.setField("JOBID", jobId);
        inputDoc.setField("JOBRESULTID", jobResultId);
        inputDoc.setField("COLLECTOR_USERID", map);
        solrServer.add(inputDoc);
        solrServer.request(request);
        solrServer.commit();
    }

    public static void deleteSolrDocument(SolrServer solrServer,
            Object idSolrDocument) throws IOException {
        try {
            solrServer.deleteByQuery("id:" + idSolrDocument);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    public static void executeDeltaImport() {
        try {
            logger.info("<UtilSolr Solr Delta Import>Process Started ");
            SolrServer server = getSolrServer();
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("command", "delta-import");
            QueryRequest request = new QueryRequest(params);
            request.setPath("/dataimport");
            server.request(request);
            logger.info("<UtilSolr Solr Delta Import>Process Finished");
        } catch (Exception e) {
            logger.error("Failed to  Delta Impor..classtupdate index", e);
        }
    }

    private static SolrServer getSolrServer() {
        try {
            Properties props = new Properties();

            props.load(UtilSolr.class.getClassLoader().getResourceAsStream("application.properties"));

            return new HttpSolrServer(props.getProperty(SOLR_INDEX_ROOT_PROP));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

//    /**
//     * Recebe uma lista e insere dentro de um map com a chave key
//     *
//     * @param value
//     * @return
//     */
//    public static Map<String, Set<String>> getMapValue(Set<String> value) {
//        Map<String, Set<String>> result = new HashMap<String, Set<String>>();
//        result.put("set", value);
//        return result;
//    }
//    private static Set<String> getListString(Collection<Object> collection) {
//        Set<String> result = new HashSet<String>();
//        for (Object object : collection) {
//            result.add(object.toString());
//        }
//
//        return result;
//    }
}
