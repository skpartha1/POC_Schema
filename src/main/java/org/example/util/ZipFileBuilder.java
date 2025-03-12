package org.example.util;

import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
public class ZipFileBuilder {

    Map folderFileMap = new HashMap<String, List<String>>(){{
        put("error", Arrays.asList("Error.yaml", "GatewayTimeoutError.yaml","UnAuthorizedError.yaml"));
        put("parameters", Arrays.asList("Authorization.yaml", "Content-Type.yaml","One-Data-Correlation-id.yaml"));
    }};

    public void buildZipFile(OutputStream outputStream, String functionName, String actualYaml){

        try(ZipOutputStream  zos = new ZipOutputStream(outputStream)){
            ZipEntry functionEntry = new ZipEntry(String.format("paths/%sFunction.v1.yaml", functionName));
            zos.putNextEntry(functionEntry);
            zos.write(actualYaml.getBytes());

            folderFileMap.forEach((k,v) -> {
                ((List<String>)v).forEach( (String f) -> {
                    log.info("Writing file folder={} file={}", k,f);
                    ZipEntry entry = new ZipEntry(String.format("%s/%s", k, f));
                    try {
                        zos.putNextEntry(entry);
                        zos.write(IOUtils.toString(this.getClass().getResourceAsStream(String.format("/%s/%s", k, f))).getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            });

        }catch(Exception ex){
            log.info("Error writing the file", ex);
        }
    }
}
