package com.code.generator.metadata;

public class StructField {
    private String name;
    private String type;

    private StructField(){}

    @Override
    public String toString() {
        return "\tprivate "+ type +" " + name + ";\n";
    }

    public static class Builder{
        private StructField field;

        public Builder(){
            this.field = new StructField();
        }

        public Builder withName(String name){
            this.field.name = name;
            return this;
        }
        public Builder withType(String type){
            this.field.type = type;
            return this;
        }
        public StructField build(){
            return this.field;
        }
    }
}
