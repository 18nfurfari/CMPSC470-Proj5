import java.util.ArrayList;
import java.util.HashMap;

//Nicolas Furfari and David Atkins worked in a group on this assignment.

public class Env
{
    HashMap<String, Object> sym_table;
    public Env prev;
    public Env(Env prev)
    {
        this.prev=prev;
        sym_table=new HashMap<String, Object>();
        if (prev!=null)
        {
            PutAll(prev);
        }
    }
    public void Put(String name, Object value)
    {
        sym_table.put(name, value);
    }
    public void PutAll(Env p)
    {
        sym_table.putAll(p.sym_table);
    }
    public Object Get(String name)
    {
        // this is a fake implementation
        // For the real implementation, I recommend to return a class object
        //   since the identifier's type can be variable or function
        //   whose detailed attributes will be different
//        if(name.equals("a") == true) return "int";
//        if(name.equals("b") == true) return "bool";
//        if(name.equals("testfunc") == true) return "func()->int";
        if (sym_table.containsKey(name))
        {
            return sym_table.get(name);
        }
        return null;
    }
}
