import java.sql.Array;
import java.util.*;
import java.util.HashMap;
import javax.swing.text.html.parser.Parser;

@SuppressWarnings("unchecked")
public class ParserImpl
{
    public static Boolean _debug = true;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }

    // This is for chained symbol table.
    // This includes the global scope only at this moment.
    Env env = new Env(null);
    // this stores the root of parse tree, which will be used to print parse tree and run the parse tree
    ParseTree.Program parsetree_program = null;

    Object program____decllist(Object s1) throws Exception
    {
        System.out.println("program____decllist " + s1);
        // 1. check if decllist has main function having no parameters and returns int type
        // 2. assign the root, whose type is ParseTree.Program, to parsetree_program
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;

        boolean isTrue=false;
        boolean isTrue2=false;

        for (ParseTree.FuncDecl func : decllist)
        {
            if (func.ident.equals("main") && (func.rettype.typename.equals("int") || func.rettype.typename.equals("bool")) && func.params.size()==0)
            {
                isTrue=true;
                parsetree_program = new ParseTree.Program(decllist);
                break;
            }
        }

        if (!isTrue)
        {
            throw new Exception("The program must have one main function that returns " +
                    "int type and has no parameters.");
        }

        return new ParseTree.Program(decllist);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decllist____decllist_decl(Object s1, Object s2) throws Exception {
        try {
            ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>) s1;
            ParseTree.FuncDecl decl = (ParseTree.FuncDecl) s2;

            if (decl.ident.equals("main")) {
                ArrayList<ParseTree.Stmt> testList = (ArrayList<ParseTree.Stmt>)decl.stmtlist;
                for (ParseTree.Stmt stmt : testList) {
                    if (stmt instanceof ParseTree.ReturnStmt) {
                        if (!(stmt.info.typeOf.equals(decl.info.typeOf))) {
                            throw new Exception("The type of returning value (" + stmt.info.typeOf +
                                    ") should match with the return type (" + decl.info.typeOf + ") of the function main().");

                        }
                    }
                }
            }
            decllist.add(decl);
            return decllist;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ParseTree.FuncDecl>();
        }
    }


    Object decllist____eps() throws Exception
    {
        return new ArrayList<ParseTree.FuncDecl>();
    }

    Object decl____fundecl(Object s1) throws Exception
    {
        ParseTree.FuncDecl funcDecl = (ParseTree.FuncDecl)s1;
        return funcDecl;
    }

    Object primtype____INT() throws Exception
    {
        return new ParseTree.TypeSpec("int");
    }

    Object primtype____BOOL() throws Exception
    {

        return new ParseTree.TypeSpec("bool");
    }

    Object typespec____primtype(Object s1)
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        return primtype;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object fundecl____FUNC_IDENT_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_10X_stmtlist_END(Object s2, Object s4, Object s7, Object s9) throws Exception
    {
        try {
            // 1. add function_type_info object (name, return type, params) into the global scope of env
            // 2. create a new symbol table on top of env
            // 3. add parameters into top-local scope of env
            // 4. etc.
            Token                            id         = (Token                           )s2;
            ArrayList<ParseTree.Param>       params     = (ArrayList<ParseTree.Param>      )s4;
            ParseTree.TypeSpec               rettype    = (ParseTree.TypeSpec              )s7;
            ArrayList<ParseTree.LocalDecl>   localdecls = (ArrayList<ParseTree.LocalDecl>  )s9;

            ParseTree.FuncDecl funcDecl = new ParseTree.FuncDecl(id.lexeme, rettype, params, localdecls, null);
            funcDecl.info.typeOf = rettype.typename;
            ArrayList<ParseTree.FuncDecl> funcDeclList = new ArrayList<>();
            funcDeclList.add(funcDecl);
            return funcDeclList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object fundecl____FUNC_IDENT_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_X10_stmtlist_END(Object s2, Object s4, Object s7, Object s9, Object s11, Object s12) throws Exception
    {
        try {
            // 1. check if this function has at least one return type
            // 2. etc.
            // 3. create and return funcdecl node
            Token                            id         = (Token                           )s2;
            ArrayList<ParseTree.Param>       params     = (ArrayList<ParseTree.Param>      )s4;
            ParseTree.TypeSpec               rettype    = (ParseTree.TypeSpec              )s7;
            ArrayList<ParseTree.LocalDecl>   localdecls = (ArrayList<ParseTree.LocalDecl>) s9;
            ArrayList<ParseTree.Stmt>        stmtlist   = (ArrayList<ParseTree.Stmt>       )s11;

            ParseTree.FuncDecl funcdecl = new ParseTree.FuncDecl(id.lexeme, rettype, params, localdecls, stmtlist);
            return funcdecl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object params____paramlist(Object s1) throws Exception
    {
        ArrayList<ParseTree.Param> paramlist = new ArrayList<>();
        paramlist.add((ParseTree.Param)s1);
        return paramlist;
    }

    Object params____eps() throws Exception
    {
        return new ArrayList<ParseTree.Param>();
    }

    Object param_list____param(Object s1)  throws Exception
    {
        ParseTree.Param param = (ParseTree.Param)s1;
        return param;
    }

    Object paramlist____paramlist_COMMA_param(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.Param> paramlist = (ArrayList<ParseTree.Param>)s1;
        ParseTree.Param            param    = (ParseTree.Param           )s2;
        paramlist.add(param);
        return paramlist;
    }

    Object param____VAR_typespec_IDENT(Object s1, Object s2) throws Exception
    {
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s1;
        Token id = (Token)s2;

        return new ParseTree.Param(id.lexeme, typespec);
    }

    Object stmtlist____stmtlist_stmt(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>)s1;
        ParseTree.Stmt            stmt     = (ParseTree.Stmt           )s2;
        stmtlist.add(stmt);
        return stmtlist;
    }
    Object stmtlist____eps() throws Exception
    {
        return new ArrayList<ParseTree.Stmt>();
    }

    Object stmt____assignstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.AssignStmt);
        return s1;
    }

    Object stmt____printstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.PrintStmt);
        return s1;
    }

    Object stmt____returnstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.ReturnStmt);
        return s1;
    }

    Object stmt____ifstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.IfStmt);
        return s1;
    }

    Object stmt____whilestmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.WhileStmt);
        return s1;
    }

    Object stmt____compoundstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.CompoundStmt);
        return s1;
    }

    Object compoundstmt____BEGIN_localdecls_stmtlist_END(Object obj0, Object obj1)
    {
        ArrayList<ParseTree.LocalDecl> localDecls= (ArrayList<ParseTree.LocalDecl>) obj0;
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>) obj1;
        return new ParseTree.CompoundStmt(localDecls, stmtlist);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object assignstmt____IDENT_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if ident.value_type matches with expr.value_type
        // 2. etc.
        // e. create and return node
        Token          id     = (Token         )s1;
        Token          assign = (Token         )s2;
        ParseTree.Expr expr   = (ParseTree.Expr)s3;
        Object id_type = env.Get(id.lexeme);
        {
            // check if expr.type matches with id_type
            if(id_type.equals("int")
                && (expr instanceof ParseTree.ExprIntLit)
                )
                {} // ok
            else if(id_type.equals("int")
                && (expr instanceof ParseTree.ExprCall)
                && (env.Get(((ParseTree.ExprCall)expr).ident).equals("func()->int"))
                )
            {} // ok
            else if(id_type.equals("bool")
                    && (expr instanceof ParseTree.ExprBoolLit)
            )
            {} // ok?
            else if(id_type.equals("bool")
                    && (expr instanceof ParseTree.ExprCall)
                    && (env.Get(((ParseTree.ExprCall)expr).ident).equals("func()->bool"))
            )
            {} // ok?
            else
            {
                throw new Exception("semantic error");
            }
        }
        ParseTree.AssignStmt stmt = new ParseTree.AssignStmt(id.lexeme, expr);
        //stmt.ident_reladdr = 1;
        return stmt;
    }

    Object printstmt____PRINT_expr_SEMI(Object s1) throws Exception
    {
        // 1. check if ident.value_type matches with expr.value_type
        // 2. etc.
        // e. create and return node
        ParseTree.Expr expr = (ParseTree.Expr)s1;

        return new ParseTree.PrintStmt(expr);
    }

    Object returnstmt____RETURN_expr_SEMI(Object s2) throws Exception
    {
        // 1. check if expr.value_type matches with the current function return type
        // 2. etc.
        // 3. create and return node
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        return new ParseTree.ReturnStmt(expr);
    }

    Object ifstmt____IF_LPAREN_expr_RPAREN_stmt_ELSE_stmt(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr = (ParseTree.Expr)s1;
        ParseTree.Stmt stmt1 = (ParseTree.Stmt)s2;
        ParseTree.Stmt stmt2 = (ParseTree.Stmt)s3;

        return new ParseTree.IfStmt(expr, stmt1, stmt2);
    }

    Object whilestmt____WHILE_LPAREN_expr_RPAREN_stmt(Object s1, Object s2) throws Exception
    {
        ParseTree.Expr expr = (ParseTree.Expr)s1;
        ParseTree.Stmt stmt = (ParseTree.Stmt)s2;

        return new ParseTree.WhileStmt(expr, stmt);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object localdecls____localdecls_localdecl(Object s1, Object s2)
    {
        ArrayList<ParseTree.LocalDecl> localdecls = (ArrayList<ParseTree.LocalDecl>)s1;
        ParseTree.LocalDecl            localdecl  = (ParseTree.LocalDecl           )s2;
        localdecls.add(localdecl);
        return localdecls;
    }
    Object localdecls____eps() throws Exception
    {
        return new ArrayList<ParseTree.LocalDecl>();
    }
    Object localdecl____VAR_typespec_IDENT_SEMI(Object s2, Object s3)
    {
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s2;
        Token              id       = (Token             )s3;
        ParseTree.LocalDecl localdecl = new ParseTree.LocalDecl(id.lexeme, typespec);
        //localdecl.reladdr = 1;
        return localdecl;
    }

    Object args____arglist(Object obj)
    {

        return new ArrayList<ParseTree.Arg>();
    }

    Object args____eps() throws Exception
    {
        return new ArrayList<ParseTree.Expr>();
    }

    Object arglist____arglist_COMMA_expr(Object obj, Object obj1)
    {
        ArrayList<ParseTree.Expr> arg_list = (ArrayList<ParseTree.Expr>) obj;
        ParseTree.Expr expr = (ParseTree.Expr) obj1;
        ((ArrayList<ParseTree.Expr>) obj).add((ParseTree.Expr)obj1);
        return arg_list;
    }

    Object arglist____expr(Object obj)
    {
        ArrayList<ParseTree.Expr> expr = (ArrayList<ParseTree.Expr>) obj;
        return expr;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object expr____expr_ADD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;


        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprAdd(expr1,expr2);
    }

    Object expr____expr_SUB_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;


        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprSub(expr1,expr2);
    }

    Object expr____expr_MUL_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;


        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprMul(expr1,expr2);
    }

    Object expr____expr_DIV_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;


        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprDiv(expr1,expr2);
    }

    Object expr____expr_MOD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;


        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprMod(expr1,expr2);
    }

    Object expr____expr_EQ_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprEq(expr1,expr2);
    }

    Object expr____expr_NE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        if (expr1.info.typeOf.equals(expr2.info.typeOf)) {
            return new ParseTree.ExprNe(expr1,expr2);
        }
        throw new Exception("Error with expr____expr_NE_expr");
    }

    Object expr____expr_LE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprLe(expr1,expr2);
    }

    Object expr____expr_LT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprLt(expr1,expr2);
    }

    Object expr____expr_GE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprGe(expr1,expr2);
    }

    Object expr____expr_GT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprGt(expr1,expr2);
    }

    Object expr____expr_AND_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprAnd(expr1,expr2);
    }

    Object expr____expr_OR_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprOr(expr1,expr2);
    }

    Object expr____NOT_expr(Object s1, Object s2) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s2;
        Token          oper  = (Token         )s1;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprNot(expr1);
    }

    Object expr____LPAREN_expr_RPAREN(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. create and return node whose value_type is the same to the expr.value_type
        Token          lparen = (Token         )s1;
        ParseTree.Expr expr   = (ParseTree.Expr)s2;
        Token          rparen = (Token         )s3;

        return new ParseTree.ExprParen(expr);
    }

    Object expr____CALL_IDENT_LPAREN_args_RPAREN(Object s2, Object s4) throws Exception
    {
        // 1. check if id.lexeme can be found in chained symbol tables
        // 2. check if it is function type
        // 3. check if the number and types of env(id.lexeme).params match with those of args
        // 4. etc.
        // 5. create and return node that has the value_type of env(id.lexeme).return_type
        Token                    id   = (Token                   )s2;
        ArrayList<ParseTree.Arg> args = (ArrayList<ParseTree.Arg>)s4;
        Object func_attr = env.Get(id.lexeme);
        {
            // check if argument types match with function param types
            if(env.Get(id.lexeme).equals("func()->int")
                && (args.size() == 0)
                )
            {} // ok
            else
            {
                throw new Exception("semantic error");
            }
        }
        return new ParseTree.ExprCall(id.lexeme, args);
    }

    Object expr____IDENT(Object s1) throws Exception
    {
        // 1. check if id.lexeme can be found in chained symbol tables
        // 2. check if it is variable type
        // 3. etc.
        // 4. create and return node that has the value_type of the id.lexeme

        // Previous given code
        //        Token id = (Token)s1;
        //        ParseTree.ExprIdent expr = new ParseTree.ExprIdent(id.lexeme);
        //        expr.reladdr = 1;
        //        return expr;
        Token id = (Token)s1;
        ParseTree.ExprIdent exprIdent = new ParseTree.ExprIdent(id.lexeme);
        exprIdent.info.typeOf = (String) env.Get(id.lexeme);
        return exprIdent;
    }

    Object expr____INTLIT(Object s1) throws Exception
    {
        // 1. create and return node that has int type
        Token token = (Token)s1;
        int value = Integer.parseInt(token.lexeme);
        ParseTree.ExprIntLit exprIntLit = new ParseTree.ExprIntLit(value);
        exprIntLit.info.typeOf = "int";
        return exprIntLit;
    }

    Object expr____BOOLLIT(Object s1) throws Exception
    {
        // 1. create and return node that has bool type
        Token token = (Token)s1;
        boolean value = Boolean.parseBoolean(token.lexeme);
        ParseTree.ExprBoolLit exprBool= new ParseTree.ExprBoolLit(value);
        exprBool.info.typeOf="bool";
        return exprBool;
    }
}
