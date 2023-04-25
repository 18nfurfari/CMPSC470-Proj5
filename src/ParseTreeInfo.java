import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ParseTreeInfo
{
    // Use this classes to store information into parse tree node (subclasses of ParseTree.Node)
    // You should not modify ParseTree.java
    public static class TypeSpecInfo
    {
    }
    public static class ProgramInfo
    {
    }
    public static class FuncDeclInfo
    {
        public String typeOf;
    }
    public static class ParamInfo
    {
    }
    public static class LocalDeclInfo
    {
    }
    public static class StmtStmtInfo
    {
        public String typeOf;
    }
    public static class ArgInfo
    {
    }
    public static class ExprInfo
    {

        public String typeOf="";
        public int lineNo;
        public int column;


        public String typeOf()
        {
            return typeOf;
        }

    }
}
