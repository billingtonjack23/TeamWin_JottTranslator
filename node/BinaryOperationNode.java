package node;

import provided.Token;

public class BinaryOperationNode implements Expr{
    private Operand leftOperand;
    private Operand rightOperand;
    private Token operator;

    public BinaryOperationNode(Operand leftOperand, Token operator, Operand rightOperand){
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public Operand getLeftOperand(){
        return leftOperand;
    }

    public Operand getRightOperand(){
        return rightOperand;
    }

    public Token getOperator(){
        return operator;
    }

    public String convertToJott() {
        String value = leftOperand.convertToJott() + operator.getToken() + rightOperand.convertToJott();
        return value;
    }

    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    public String convertToC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
}
