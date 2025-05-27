package com.code.generator.util;

import com.code.generator.metadata.StructClass;
import com.code.generator.util.Yaml;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class YamlTest {

    private Yaml yaml;
    private StructClass structClass;

    @Before
    public void setup() {
        structClass = mock(StructClass.class);
        yaml = new Yaml();
    }

    @Test
    public void mapToStructClassShouldMapCorrectly() throws URISyntaxException, IOException {
        Map<String, String> bufferMap = new HashMap<>();
        bufferMap.put("class:name", "ClassName");
        bufferMap.put("class:package", "PackageName");
        bufferMap.put("attributes:attribute1", "AttributeType");
        bufferMap.put("methods:method1:return", "ReturnType");
        bufferMap.put("methods:method1:implementation", "Implementation");
        bufferMap.put("methods:method1:parameters", "ParametersType");

        yaml.mapToStructClass("descriptor.yml");

        verify(structClass, times(1)).addField(any());
        verify(structClass, times(1)).addMethod(any());
    }

    @Test(expected = RuntimeException.class)
    public void mapToStructClassShouldThrowExceptionWhenURISyntaxExceptionOccurs() throws URISyntaxException, IOException {
        doThrow(URISyntaxException.class).when(yaml).mapToStructClass(anyString());

        yaml.mapToStructClass("fileName");
    }

    @Test(expected = RuntimeException.class)
    public void mapToStructClassShouldThrowExceptionWhenIOExceptionOccurs() throws URISyntaxException, IOException {
        doThrow(IOException.class).when(yaml).mapToStructClass(anyString());

        yaml.mapToStructClass("fileName");
    }
}