//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 17 "Parser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASSIGN=257;
public final static short OR=258;
public final static short AND=259;
public final static short NOT=260;
public final static short EQ=261;
public final static short NE=262;
public final static short LE=263;
public final static short LT=264;
public final static short GE=265;
public final static short GT=266;
public final static short ADD=267;
public final static short SUB=268;
public final static short MUL=269;
public final static short DIV=270;
public final static short MOD=271;
public final static short IDENT=272;
public final static short INT_LIT=273;
public final static short BOOL_LIT=274;
public final static short BOOL=275;
public final static short INT=276;
public final static short FUNC=277;
public final static short IF=278;
public final static short THEN=279;
public final static short ELSE=280;
public final static short WHILE=281;
public final static short PRINT=282;
public final static short RETURN=283;
public final static short CALL=284;
public final static short BEGIN=285;
public final static short END=286;
public final static short LPAREN=287;
public final static short RPAREN=288;
public final static short VAR=289;
public final static short SEMI=290;
public final static short COMMA=291;
public final static short FUNCRET=292;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    7,    7,    6,   22,    3,    8,
    8,    9,    9,   10,   13,   13,   14,   14,   14,   14,
   14,   14,   15,   16,   17,   18,   19,   20,    4,    4,
    5,   11,   11,   12,   12,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    1,    0,   12,    1,
    0,    3,    1,    3,    2,    0,    1,    1,    1,    1,
    1,    1,    4,    3,    3,    7,    5,    4,    2,    0,
    4,    1,    0,    3,    1,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    1,    1,    1,
    5,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    2,    4,    0,    0,    0,    0,    0,
   13,    6,    5,    0,    7,    0,    0,   14,    0,   12,
    0,   30,    0,    0,   29,   16,    0,    0,    0,    0,
    0,    0,    0,    0,   30,    9,   15,   17,   18,   19,
   20,   21,   22,   31,    0,    0,    0,   48,   49,   50,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   24,   25,    0,   23,    0,    0,    0,   47,    0,
    0,    0,    0,    0,    0,    0,    0,   38,   39,   40,
   28,    0,   27,    0,    0,    0,    0,   51,    0,   26,
    0,
};
final static short yydgoto[] = {                          1,
    2,    4,    5,   23,   25,   14,   15,    9,   10,   11,
   94,   95,   28,   37,   38,   39,   40,   41,   42,   43,
   53,   26,
};
final static short yysindex[] = {                         0,
    0, -276, -232,    0,    0, -277, -246, -260, -243, -244,
    0,    0,    0, -208,    0, -220, -246,    0, -260,    0,
 -210,    0, -213, -260,    0,    0, -194, -114, -209, -173,
 -201, -196, -270, -270,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -270, -270, -270,    0,    0,    0,
 -180, -270, -127, -116, -213,  -86,  -75,  -47, -177,  -36,
 -270, -270, -270, -270, -270, -270, -270, -270, -270, -270,
 -270,    0,    0,  -35,    0,  -73,  -73, -270,    0,   24,
   24, -262, -262, -262, -262, -156, -156,    0,    0,    0,
    0, -163,    0, -168, -170,   15,  -73,    0, -270,    0,
   15,
};
final static short yyrindex[] = {                         0,
    0,  122,    0,    0,    0,    0, -165,    0,    0, -160,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -23,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -11,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -159,    0, -179,
 -172, -203, -195, -164, -158, -242, -211,    0,    0,    0,
    0,    0,    0,    0, -157, -250,    0,    0,    0,    0,
 -249,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   90,    0,  132,  138,    0,    0,  142,
    0,    0,  105,   -3,    0,    0,    0,    0,    0,    0,
  -34,    0,
};
final static int YYTABLESIZE=295;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
    3,   48,   49,   50,   67,   68,   69,   70,   71,    7,
   56,   57,   58,   51,   12,   13,   52,   60,   36,   36,
   36,   36,   36,   36,   36,   36,   80,   81,   82,   83,
   84,   85,   86,   87,   88,   89,   90,   35,   34,    6,
   35,   34,    8,   96,   16,   36,   17,   36,   36,   37,
   37,   37,   37,   37,   37,   37,   37,   43,   43,   43,
   43,   43,   43,   18,  101,   44,   44,   44,   44,   44,
   44,   19,   92,   93,   22,   24,   37,   29,   37,   37,
   44,   41,   41,   45,   43,   46,   43,   43,   42,   42,
   47,   59,   44,  100,   44,   44,   45,   45,   45,   45,
   45,   45,   46,   46,   46,   46,   46,   46,   41,   78,
   41,   41,   69,   70,   71,   42,   97,   42,   42,   98,
   99,    1,   11,   45,   55,   45,   45,   10,   33,   46,
   32,   46,   46,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   61,   62,   63,   64,   65,   66,
   67,   68,   69,   70,   71,   27,   21,   30,   20,   74,
    0,    0,   72,   31,    0,    0,   32,   33,   34,    0,
   35,   36,    0,   73,   61,   62,   63,   64,   65,   66,
   67,   68,   69,   70,   71,   61,   62,   63,   64,   65,
   66,   67,   68,   69,   70,   71,    0,    0,   30,    0,
    0,    0,    0,   75,   31,    0,    0,   32,   33,   34,
    0,   35,   76,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   61,   62,   63,   64,   65,   66,
   67,   68,   69,   70,   71,    0,   30,    0,    0,    0,
   77,    0,   31,    0,    0,   32,   33,   34,    8,   35,
   91,   79,    0,    0,    8,    0,    0,    8,    8,    8,
   16,    8,    8,    0,    0,    0,   16,    0,    0,   16,
   16,   16,    0,   16,   16,   61,   62,   63,   64,   65,
   66,   67,   68,   69,   70,   71,   63,   64,   65,   66,
   67,   68,   69,   70,   71,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         34,
  277,  272,  273,  274,  267,  268,  269,  270,  271,  287,
   45,   46,   47,  284,  275,  276,  287,   52,  261,  262,
  263,  264,  265,  266,  267,  268,   61,   62,   63,   64,
   65,   66,   67,   68,   69,   70,   71,  288,  288,  272,
  291,  291,  289,   78,  288,  288,  291,  290,  291,  261,
  262,  263,  264,  265,  266,  267,  268,  261,  262,  263,
  264,  265,  266,  272,   99,  261,  262,  263,  264,  265,
  266,  292,   76,   77,  285,  289,  288,  272,  290,  291,
  290,  261,  262,  257,  288,  287,  290,  291,  261,  262,
  287,  272,  288,   97,  290,  291,  261,  262,  263,  264,
  265,  266,  261,  262,  263,  264,  265,  266,  288,  287,
  290,  291,  269,  270,  271,  288,  280,  290,  291,  288,
  291,    0,  288,  288,   35,  290,  291,  288,  288,  288,
  288,  290,  291,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   24,   19,  272,   17,   55,
   -1,   -1,  290,  278,   -1,   -1,  281,  282,  283,   -1,
  285,  286,   -1,  290,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,   -1,  272,   -1,
   -1,   -1,   -1,  290,  278,   -1,   -1,  281,  282,  283,
   -1,  285,  288,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   -1,  272,   -1,   -1,   -1,
  288,   -1,  278,   -1,   -1,  281,  282,  283,  272,  285,
  286,  288,   -1,   -1,  278,   -1,   -1,  281,  282,  283,
  272,  285,  286,   -1,   -1,   -1,  278,   -1,   -1,  281,
  282,  283,   -1,  285,  286,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  263,  264,  265,  266,
  267,  268,  269,  270,  271,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=292;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASSIGN","OR","AND","NOT","EQ","NE","LE","LT","GE","GT","ADD",
"SUB","MUL","DIV","MOD","IDENT","INT_LIT","BOOL_LIT","BOOL","INT","FUNC","IF",
"THEN","ELSE","WHILE","PRINT","RETURN","CALL","BEGIN","END","LPAREN","RPAREN",
"VAR","SEMI","COMMA","FUNCRET",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : fun_decl",
"prim_type : INT",
"prim_type : BOOL",
"type_spec : prim_type",
"$$1 :",
"fun_decl : FUNC IDENT LPAREN params RPAREN FUNCRET prim_type BEGIN local_decls $$1 stmt_list END",
"params : param_list",
"params :",
"param_list : param_list COMMA param",
"param_list : param",
"param : VAR type_spec IDENT",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : assign_stmt",
"stmt : print_stmt",
"stmt : return_stmt",
"stmt : if_stmt",
"stmt : while_stmt",
"stmt : compound_stmt",
"assign_stmt : IDENT ASSIGN expr SEMI",
"print_stmt : PRINT expr SEMI",
"return_stmt : RETURN expr SEMI",
"if_stmt : IF LPAREN expr RPAREN stmt ELSE stmt",
"while_stmt : WHILE LPAREN expr RPAREN stmt",
"compound_stmt : BEGIN local_decls stmt_list END",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : VAR type_spec IDENT SEMI",
"args : arg_list",
"args :",
"arg_list : arg_list COMMA expr",
"arg_list : expr",
"expr : expr ADD expr",
"expr : expr SUB expr",
"expr : expr MUL expr",
"expr : expr DIV expr",
"expr : expr MOD expr",
"expr : expr EQ expr",
"expr : expr NE expr",
"expr : expr LE expr",
"expr : expr LT expr",
"expr : expr GE expr",
"expr : expr GT expr",
"expr : LPAREN expr RPAREN",
"expr : IDENT",
"expr : INT_LIT",
"expr : BOOL_LIT",
"expr : CALL IDENT LPAREN args RPAREN",
};

//#line 144 "Parser.y"
    // public ParseTree.Program parsetree_program=null;
    public ParserImpl parserImpl;

    private Lexer lexer;
    private Token last_token;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
            last_token = (Token)yylval.obj;
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }


    public void yyerror (String error) {
        //System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        int last_token_lineno = 0;
        int last_token_column = 0;
        System.out.println ("Error message by Parser.yyerror() at near " + last_token_lineno+":"+last_token_column + ": " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }
//#line 375 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 49 "Parser.y"
{ Debug("program -> decl_list"                  ); yyval.obj = program____decllist(val_peek(0).obj); }
break;
case 2:
//#line 52 "Parser.y"
{ Debug("decl_list -> decl_list decl"           ); yyval.obj = decllist____decllist_decl(val_peek(1).obj,val_peek(0).obj); }
break;
case 3:
//#line 53 "Parser.y"
{ Debug("decl_list -> eps"                      ); yyval.obj = decllist____eps          (     ); }
break;
case 4:
//#line 56 "Parser.y"
{ Debug("decl -> fun_decl"                      ); yyval.obj = decl____fundecl(val_peek(0).obj); }
break;
case 5:
//#line 59 "Parser.y"
{ Debug("prim_type -> INT"                      ); yyval.obj = primtype____INT(); }
break;
case 6:
//#line 60 "Parser.y"
{ Debug("prim_type -> BOOL"                      ); yyval.obj = primtype____BOOL(); }
break;
case 7:
//#line 63 "Parser.y"
{ Debug("type_spec -> prim_type"                ); yyval.obj = typespec____primtype(val_peek(0).obj); }
break;
case 8:
//#line 66 "Parser.y"
{ Debug("fun_decl -> FUNC ID(params)->prim_type BEGIN local_decls"); yyval.obj = fundecl____FUNC_IDENT_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_10X_stmtlist_END(val_peek(7).obj, val_peek(5).obj, val_peek(2).obj, val_peek(0).obj); }
break;
case 9:
//#line 67 "Parser.y"
{ Debug("                                           stmt_list END"); yyval.obj =      fundecl____FUNC_IDENT_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_X10_stmtlist_END(val_peek(10).obj, val_peek(8).obj, val_peek(5).obj, val_peek(3).obj, val_peek(2).obj, val_peek(1).obj); }
break;
case 10:
//#line 70 "Parser.y"
{ Debug("params -> param_list"                         ); yyval.obj = params____paramlist(val_peek(0).obj); }
break;
case 11:
//#line 71 "Parser.y"
{ Debug("param -> eps"                         ); yyval.obj = params____eps(); }
break;
case 12:
//#line 74 "Parser.y"
{ Debug("param_list -> param_list COMMA param"); yyval.obj = paramlist____paramlist_COMMA_param(val_peek(2).obj, val_peek(0).obj); }
break;
case 13:
//#line 75 "Parser.y"
{ Debug("param_list -> param"                         ); yyval.obj = param_list____param(val_peek(0).obj); }
break;
case 14:
//#line 78 "Parser.y"
{ Debug("param -> VAR  type_spec  IDENT"); yyval.obj = param____VAR_typespec_IDENT(val_peek(1).obj, val_peek(0).obj); }
break;
case 15:
//#line 80 "Parser.y"
{ Debug("stmt_list -> stmt_list stmt"           ); yyval.obj = stmtlist____stmtlist_stmt(val_peek(1).obj, val_peek(0).obj); }
break;
case 16:
//#line 81 "Parser.y"
{ Debug("stmt_list -> eps"                      ); yyval.obj = stmtlist____eps          (      ); }
break;
case 17:
//#line 84 "Parser.y"
{ Debug("stmt -> assign_stmt"                   ); yyval.obj = stmt____assignstmt  (val_peek(0).obj); }
break;
case 18:
//#line 85 "Parser.y"
{ Debug("stmt -> print_stmt"                   ); yyval.obj = stmt____printstmt  (val_peek(0).obj); }
break;
case 19:
//#line 86 "Parser.y"
{ Debug("stmt -> return_stmt"                   ); yyval.obj = stmt____returnstmt  (val_peek(0).obj); }
break;
case 20:
//#line 87 "Parser.y"
{ Debug("stmt -> if_stmt"                   ); yyval.obj = stmt____ifstmt  (val_peek(0).obj); }
break;
case 21:
//#line 88 "Parser.y"
{ Debug("stmt -> while_stmt"                   ); yyval.obj = stmt____whilestmt  (val_peek(0).obj); }
break;
case 22:
//#line 89 "Parser.y"
{ Debug("stmt -> compound_stmt"                   ); yyval.obj = stmt____compoundstmt  (val_peek(0).obj); }
break;
case 23:
//#line 92 "Parser.y"
{ Debug("assign_stmt -> IDENT <- expr"        ); yyval.obj = assignstmt____IDENT_ASSIGN_expr_SEMI(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj); }
break;
case 24:
//#line 95 "Parser.y"
{ Debug("print_stmt -> PRINT expr SEMI"        ); yyval.obj = printstmt____PRINT_expr_SEMI(val_peek(1).obj); }
break;
case 25:
//#line 98 "Parser.y"
{ Debug("return_stmt -> RETURN expr"          ); yyval.obj = returnstmt____RETURN_expr_SEMI(val_peek(1).obj); }
break;
case 26:
//#line 101 "Parser.y"
{ Debug("if_stmt -> IF  LPAREN  expr  RPAREN  stmt  ELSE  stmt"          ); yyval.obj = ifstmt____IF_LPAREN_expr_RPAREN_stmt_ELSE_stmt(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj); }
break;
case 27:
//#line 104 "Parser.y"
{ Debug("while_stmt -> WHILE  LPAREN  expr  RPAREN  stmt") ; yyval.obj = whilestmt____WHILE_LPAREN_expr_RPAREN_stmt(val_peek(2).obj, val_peek(0).obj); }
break;
case 28:
//#line 107 "Parser.y"
{ Debug("compound_stmt  ->  BEGIN  local_decls  stmt_list  END") ; yyval.obj = compoundstmt____BEGIN_localdecls_stmtlist_END(val_peek(2).obj, val_peek(1).obj); }
break;
case 29:
//#line 110 "Parser.y"
{ Debug("local_decls -> local_decls local_decl" ); yyval.obj = localdecls____localdecls_localdecl(val_peek(1).obj, val_peek(0).obj); }
break;
case 30:
//#line 111 "Parser.y"
{ Debug("local_decls -> eps"                    ); yyval.obj = localdecls____eps(); }
break;
case 31:
//#line 114 "Parser.y"
{ Debug("local_decl -> VAR type_spec IDENT SEMI"); yyval.obj = localdecl____VAR_typespec_IDENT_SEMI(val_peek(2).obj, val_peek(1).obj); }
break;
case 32:
//#line 117 "Parser.y"
{ Debug("args -> arg_list"                           ); yyval.obj = args____arglist(val_peek(0).obj); }
break;
case 33:
//#line 118 "Parser.y"
{ Debug("args -> eps"                           ); yyval.obj = args____eps(); }
break;
case 34:
//#line 121 "Parser.y"
{ Debug("arg_list -> arg_list COMMA expr"                           ); yyval.obj = arglist____arglist_COMMA_expr(val_peek(2).obj, val_peek(0).obj); }
break;
case 35:
//#line 122 "Parser.y"
{ Debug("expr"); yyval.obj = arglist____expr(val_peek(0).obj);}
break;
case 36:
//#line 125 "Parser.y"
{ Debug("expr -> expr ADD expr"                 ); yyval.obj = expr____expr_ADD_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 37:
//#line 126 "Parser.y"
{ Debug("expr -> expr SUB expr"                 ); yyval.obj = expr____expr_SUB_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 38:
//#line 127 "Parser.y"
{ Debug("expr -> expr MUL expr"                 ); yyval.obj = expr____expr_MUL_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 39:
//#line 128 "Parser.y"
{ Debug("expr -> expr DIV expr"                 ); yyval.obj = expr____expr_DIV_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 40:
//#line 129 "Parser.y"
{ Debug("expr -> expr MOD expr"                 ); yyval.obj = expr____expr_MOD_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 41:
//#line 130 "Parser.y"
{ Debug("expr -> expr EQ  expr"                 ); yyval.obj = expr____expr_EQ_expr                 (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 42:
//#line 131 "Parser.y"
{ Debug("expr -> expr NE expr"                 ); yyval.obj = expr____expr_NE_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 43:
//#line 132 "Parser.y"
{ Debug("expr -> expr LE expr"                 ); yyval.obj = expr____expr_LE_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 44:
//#line 133 "Parser.y"
{ Debug("expr -> expr LT expr"                 ); yyval.obj = expr____expr_LT_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 45:
//#line 134 "Parser.y"
{ Debug("expr -> expr GE expr"                 ); yyval.obj = expr____expr_GE_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 46:
//#line 135 "Parser.y"
{ Debug("expr -> expr GT expr"                 ); yyval.obj = expr____expr_GT_expr                (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 47:
//#line 136 "Parser.y"
{ Debug("expr -> LPAREN expr RPAREN"            ); yyval.obj = expr____LPAREN_expr_RPAREN           (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 48:
//#line 137 "Parser.y"
{ Debug("expr -> IDENT"                         ); yyval.obj = expr____IDENT                        (val_peek(0).obj      ); }
break;
case 49:
//#line 138 "Parser.y"
{ Debug("expr -> INT_LIT"                       ); yyval.obj = expr____INTLIT                       (val_peek(0).obj      ); }
break;
case 50:
//#line 139 "Parser.y"
{ Debug("expr -> BOOL_LIT"                       ); yyval.obj = expr____BOOLLIT                       (val_peek(0).obj      ); }
break;
case 51:
//#line 140 "Parser.y"
{ Debug("expr -> CALL IDENT LPAREN args RPAREN" ); yyval.obj = expr____CALL_IDENT_LPAREN_args_RPAREN(val_peek(3).obj,val_peek(1).obj   ); }
break;
//#line 728 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
