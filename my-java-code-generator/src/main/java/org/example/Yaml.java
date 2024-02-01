package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Yaml {

    private StructClass structClass;

    public void mapToStructClass(String fileName) {
        try {
            Path path = Paths.get(getClass().getClassLoader()
                    .getResource(fileName).toURI());

            Map<String, String>bufferMap = new HashMap<>();
            List<String> lines = Files.readAllLines(path);
            int index = 0;
            String key = "";
            while(index<lines.size()){
            //for(String line: lines){
                String line = lines.get(index);
                if (!"".equals(line.trim())){
                    String[] parts = line.split(":");
                    if (parts.length == 1){
                        if("methods:".equals(key)){
                       // if(key.startsWith("methods:")){
                            key += parts[0].replace("-", "").trim()+":";
                        }else
                            key = parts[0].replace("-", "").trim()+":";
                    }else{
                        bufferMap.put(key + parts[0].replace("-", "").trim(), parts[1].substring(1));
                    }
                     index++;
                }
            }
            System.out.println(bufferMap);

            this.structClass = new StructClass.Builder()
                    .withClassName(bufferMap.get("class:name"))
                    .withPackageName(bufferMap.get("class:package"))
                    .build();

            Set<String> attributes = bufferMap.keySet().stream().filter(k -> k.startsWith("attributes:")).collect(Collectors.toSet());
             for(String attKey: attributes){
                 String attValue = bufferMap.get(attKey);
                 structClass.addField(new StructField.Builder()
                         .withName(attKey.replace("attributes:", ""))
                         .withType(attValue)
                         .build());
             }

            Set<String> methods = bufferMap.keySet().stream().filter(k -> k.startsWith("methods:")).collect(Collectors.toSet());
            String methodImplementation = null;
            String methodReturnType = null;
            String methodParametersType = null;
            String methodName = null;
            for(String methKey: methods){
                if(methKey.contains("return")){
                    methodReturnType = bufferMap.get(methKey);
                }else if(methKey.contains("implementation")){
                    methodImplementation = bufferMap.get(methKey);
                }else if(methKey.contains("parameters")){
                    methodParametersType = bufferMap.get(methKey);
                }
                methodName = methKey.split(":")[1];
            }
            structClass.addMethod(new StructMethod.Builder()
                    .withName(methodName)
                    .withType(methodReturnType)
                    .withImplementation(methodImplementation)
                    .withParameters(methodParametersType)
                    .build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StructClass toStructClass() {
        return this.structClass;
    }
}
