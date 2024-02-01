package org.example;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DynamicGenerator {

    private StructClass clazz;

    public DynamicGenerator(StructClass clazz){
        this.clazz = clazz;
    }


    public void generate() throws IOException {

        final String contents = clazz.toString();
        final String className = clazz.className();
        final String packageName = clazz.packageName();
        final String fullClassName = packageName.replace('.', '/') + "/" + className;

        Path sourceFile = Paths.get(className+".java");
        Files.write(sourceFile, contents.getBytes());

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.out.println("JDK required (running inside of JRE)");
            System.exit(1);
        } else {
            System.out.println("you got it!");
        }

        int compilationResult = compiler.run(null, null, null, sourceFile.toUri().getPath());
        if (compilationResult == 0) {
            System.out.println("Compilation is successful");
        } else {
            System.out.println("Compilation Failed");
            System.exit(1);
        }

        URL classPath = sourceFile.toAbsolutePath().getParent().toUri().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classPath});
        Class<?> cls = null;
        try {
            cls = Class.forName(className, true, classLoader);
            Object instance = cls.getConstructor().newInstance();
            //Object instance = cls.newInstance();
            for(StructMethod structMethod: clazz.getMethods()){
                Class<?> parameterTypes = null;
                Object parameterValues = null;
                if(!structMethod.getArgs().isEmpty()){
                    //parameterTypes.add
                    //parameterValues.add
                }
                //Method method = cls.getDeclaredMethod(structMethod.getName(), parameterTypes);
                Method method = cls.getDeclaredMethod(structMethod.getName(), null);
                //System.out.println(method.invoke(instance, parameterValues));
                System.out.println(method.invoke(instance, null));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

}

