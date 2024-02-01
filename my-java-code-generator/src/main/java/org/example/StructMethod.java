package org.example;

import java.util.ArrayList;
import java.util.List;

public class StructMethod {
    private String name;
    private List<StructField> args;
    private String type;
    private String implementation;

    private StructMethod(){
        this.args = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public List<StructField> getArgs(){
        return args;
    }
    @Override
    public String toString() {
        String argStr = "()";
        if(!args.isEmpty()){
            //mount arg list
        }
        return "\tpublic "+type+" " +name+ " "+argStr+"{\n" +
                "\t\t" + implementation + "\n" +
                "\t}\n";
    }

    public static class Builder{
        private StructMethod method;

        public Builder(){
            this.method = new StructMethod();
        }

        public Builder withName(String methodName){
            this.method.name =  methodName;
            return this;
        }

        public Builder withType(String methodType){
            this.method.type =  methodType;
            return this;
        }

        public Builder withImplementation(String implementation){
            this.method.implementation =  implementation;
            return this;
        }

        public Builder withParameters(String methodParametersType) {
            if(null != methodParametersType && !"none".equals(methodParametersType)){
               //generate args
            }
            return this;
        }

        public StructMethod build() {
            return this.method;
        }
    }
}
