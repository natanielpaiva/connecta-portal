package br.com.cds.connecta.portal.util;

import br.com.cds.connecta.portal.dto.FrontendFileDTO;
import org.apache.solr.common.util.Base64;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class FileUtil {

    public static byte[] toByteArray(FrontendFileDTO fileDTO) {
        System.out.println("CONVERTING TO BYTE ARRAY");
        System.out.println(fileDTO.getBase64());
        System.out.println(fileDTO.getBase64().split("base64,")[1]);
        
        byte[] bs = Base64.base64ToByteArray(fileDTO.getBase64().split("base64,")[1]);
        
        return bs;
    }
    
}
