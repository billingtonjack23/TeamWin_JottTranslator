package node;
import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.*;
public class FuncDefParams implements JottTree{

    private final IDNode idNode;
    //private final TypeNode typeNode;
    private final Token typeToken;
    private final ArrayList<FuncDefParams_t> funcDefParams_t_list;

    public FuncDefParams(IDNode idNode, Token typeToken, ArrayList<FuncDefParams_t> funcDefParams_t_list) {
        this.idNode = idNode;
        this.typeToken = typeToken;
        this.funcDefParams_t_list = funcDefParams_t_list;
    }

    public static FuncDefParams parseFunctionDefParams(ArrayList<Token> tokens) throws Exception{

        //check if tokens arraylist is empty
        if(tokens.size() == 0)
        {
                //throw excpetion
                //CREATE NEW CLASS AND THROW EXCEPTION
                // SUGGESTED RENAME SYNTAX ERROR
                throw new SyntaxError("Token list is empty. <Func Def Params>", null);
        }
        
        Token t = tokens.get(0);

        if (!t.getTokenType().equals(TokenType.ID_KEYWORD)) {
            FuncDefParams funcDefParams = new FuncDefParams(null, null, null);
            return funcDefParams;
        }
        if ((t.getToken().equals("Double")) || (t.getToken().equals("Integer")) || (t.getToken().equals("String")) || (t.getToken().equals("Boolean"))) {
            throw new SyntaxError("Expected an <id> got a <Type> keyword: --> " + t.getToken(), t);
        }

        IDNode idNode = IDNode.parseIDNode(tokens);

        t = tokens.get(0);
        if (t.getTokenType().equals(TokenType.COLON)) {
            tokens.remove(0);

            t = tokens.get(0);
            if ((!t.getToken().equals("Double")) && (!t.getToken().equals("Integer")) && (!t.getToken().equals("String")) && (!t.getToken().equals("Boolean"))) {
                throw new SyntaxError("Expected an <Type> keyword, but got " + t.getTokenType() + " --> " + t.getToken(), t);
            }

            Token typeToken = t;
            tokens.remove(0);

            ArrayList<FuncDefParams_t> funcDefParams_t_list = new ArrayList<>();

            t = tokens.get(0);
            while (t.getTokenType().equals(TokenType.COMMA)) {
                FuncDefParams_t funcDefParams_t = FuncDefParams_t.parseFuncDefParams_t(tokens);

                funcDefParams_t_list.add(funcDefParams_t);

                t = tokens.get(0);
            }

            FuncDefParams funcDefParams = new FuncDefParams(idNode, typeToken, funcDefParams_t_list);
            return funcDefParams;
        }
        else {
            throw new SyntaxError("Expected a colon ':' between <id> and <type> inside of <func_def_params>, but got a " + t.getTokenType() + " --> " + t.getToken(), t);
        }


    }




    public String convertToJott() {
        if (idNode == null) {
            String empty = "";
            return empty;
        }
        else {
            String value = idNode.convertToJott() + ":" + typeToken.getToken();
            for (int i = 0; i < funcDefParams_t_list.size(); i++) {
                FuncDefParams_t funcDefParams_t = funcDefParams_t_list.get(i);
                value = value + funcDefParams_t.convertToJott();
            }
            return value;
        }
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
