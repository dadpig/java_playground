package com.code.generator.transform;

import com.code.generator.metadata.StructClass;
import com.code.generator.metadata.StructMethod;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DynamicGenerator {

    private StructClass clazz;

    public DynamicGenerator(StructClass clazz){
        this.clazz = clazz;
    }


  public String generate() {
    String contents = clazz.toString();
    String className = clazz.className();
    Path sourceFile = Paths.get(className + ".java");

    try {
        Files.write(sourceFile, contents.getBytes());
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        checkCompiler(compiler);
        compileSourceFile(compiler, sourceFile);
        URLClassLoader classLoader = getClassLoader(sourceFile);
        Class<?> cls = Class.forName(className, true, classLoader);
        Object instance = cls.getConstructor().newInstance();
        return invokeMethods(cls, instance);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

private void checkCompiler(JavaCompiler compiler) {
    if (compiler == null) {
        System.out.println("JDK required (running inside of JRE)");
        System.exit(1);
    }
}

private void compileSourceFile(JavaCompiler compiler, Path sourceFile) {
    int compilationResult = compiler.run(null, null, null, sourceFile.toUri().getPath());
    if (compilationResult != 0) {
        System.out.println("Compilation Failed");
        System.exit(1);
    }
}

private URLClassLoader getClassLoader(Path sourceFile) throws MalformedURLException {
    URL classPath = sourceFile.toAbsolutePath().getParent().toUri().toURL();
    return URLClassLoader.newInstance(new URL[]{classPath});
}

private String invokeMethods(Class<?> cls, Object instance) throws Exception {
    String toString = "";
    for (StructMethod structMethod : clazz.getMethods()) {
        Method method = cls.getDeclaredMethod(structMethod.getName());
        toString = (String) method.invoke(instance);
    }
    return toString;
}

}

