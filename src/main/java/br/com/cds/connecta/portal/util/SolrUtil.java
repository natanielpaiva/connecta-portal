package br.com.cds.connecta.portal.util;

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

public class SolrUtil {

    private static final Logger logger = LoggerFactory.getLogger(SolrUtil.class);
    
    private static final String SOLR_INDEX_ROOT_PROP = "connecta.search.solrbackend";

    public static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";

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
    
    public static SolrDocumentList searchSingleField(String field, String value, Integer rows, String domain) {
        SolrServer solrServer = getSolrServer();
        SolrQuery query = new SolrQuery(String.format("%s:*%s*", field, value) + " AND domain:" + domain);

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
    
    public static List<Map<String, Object>> searchSingleFieldAsMapList(String field, String value, Integer rows, String domain) {
        SolrDocumentList documents = searchSingleField(field, value, rows, domain);
        List<Map<String, Object>> viewers = new ArrayList<>(documents.size());
        
        for (SolrDocument document : documents) {
            Map<String, Object> viewer = new HashMap<>(document);
            viewers.add(viewer);
        }
        
        return viewers;
    }
    
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
        List<SolrInputDocument> updateList = new ArrayList<>();
        for (SolrDocument doc : results) {
            System.out.println("Processed Doc with ID: " + doc.getFieldValue("id"));
            SolrInputDocument inputDoc = new SolrInputDocument();
            Map<String, Integer> archivedValue = new HashMap<>();
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
        List<SolrInputDocument> updateList = new ArrayList<>();
        for (SolrDocument doc : results) {
            System.out.println("Processed Doc with ID: " + doc.getFieldValue("id"));
            SolrInputDocument inputDoc = new SolrInputDocument();
            Map<String, Integer> archivedValue = new HashMap<>();
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

    public static void removeSolrUser(Integer jobId, Integer userId,
            SolrServer solrServer, Object jobResultId, Object solrId,
            Collection<Object> listaUserId) throws SolrServerException,
            IOException {
        List<Object> listaRemove = new ArrayList<>();
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
        Map<String, Collection<Object>> map = new HashMap<>();
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
        } catch (SolrServerException | IOException e) {
            logger.error("Failed to  Delta Impor..classtupdate index", e);
        }
    }

    private static SolrServer getSolrServer() {
        try {
            Properties props = new Properties();

            props.load(SolrUtil.class.getClassLoader().getResourceAsStream("application.properties"));

            return new HttpSolrServer(props.getProperty(SOLR_INDEX_ROOT_PROP));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
