public class Token
{
    public String lexeme;
    public int row;
    public int col;
    public Token(String lexeme, int row, int col)
    {
        this.lexeme = lexeme;
        this.row = row;
        this.col = col;
    }
}
