package Scripting;

import java.io.IOException;
import java.util.ArrayList;

public class ScriptManager
{
    ArrayList<Script> scriptList = new ArrayList<>();
    ScriptManager() { }
    //public Script loadScript(String filePath) throws IOException { }
    void updateScripts()
    {
        for(Script s : scriptList)
        {
            System.out.println("Updating " + s.name());
            s.update();
        }
    }
}
