package stuff;

import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ToggleSource implements Taglet {

    @Override
    public boolean inField() {
        return false;
    }

    /**
     * Allow constructor's source code to be viewed.
     */
    @Override
    public boolean inConstructor() {
        return true;
    }

    /**
     * Allow method's source code to be viewed.
     */
    @Override
    public boolean inMethod() {
        return true;
    }

    @Override
    public boolean inOverview() {
        return false;
    }

    @Override
    public boolean inPackage() {
        return false;
    }

    @Override
    public boolean inType() {
        return false;
    }

    /**
     * Taglet will appear on own line. That is, disallow something like: see
     * also {&#64;link package.class#member label}
     */
    @Override
    public boolean isInlineTag() {
        return false;
    }
    private static final String NAME = "toggle-source";

    /**
     * What the Taglet will look like, ie. &#64;toggle-source.
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * This version of toString is used for inline tags like {&#64;link
     * package.class#member label}
     */
    @Override
    public String toString(Tag tag) {
        throw new UnsupportedOperationException("Not supported yet. toString(Tag tag)");
    }
    
    private int count = 0;

    @Override
    public String toString(Tag[] tags) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < tags.length; i++) {
            str.append("<a href='javascript:toggleSource(\"" + count + "\");' id='link-" + count + "'>show</a>");
            str.append("<br />");
            str.append("<div style='border-style: dashed; visibility: hidden;' id='code-" + count + "'>");
            str.append("<code style='white-space:pre-wrap;'>");
            str.append(readMethod(tags[i]));
            str.append("</code>");
            str.append("</div>");
            count++;
        }

        return str.toString();
    }

    /**
     * Modified version of register method taken ToDoTaglet.
     * @see <a href="http://docs.oracle.com/javase/1.4.2/docs/tooldocs/javadoc/taglet/ToDoTaglet.java">ToDOTaglet.java</a>
     */
    public static void register(Map map) {
        // If taglet has already been mapped to a given name, then remove it.
        if (map.get(NAME) != null) {
            map.remove(NAME);
        }

        // Map taglet (ie ToggleSource) to name (ie. toggle-source).
        map.put(NAME, new ToggleSource());
    }
    
    private String readMethod(Tag tag){
        // Obtain the location where the tag was located.
        SourcePosition sourcePosition = tag.position();

        // Get the Java file the tag was found in.
        File file = sourcePosition.file();
        
        // Get the line number the tag is on.
        // Line numbering begins at 1, not 0.
        int line = sourcePosition.line();

        try {
            int numLines = Integer.parseInt(tag.text());
            StringBuilder builder = new StringBuilder();
            
            try {
                BufferedReader buffReader = new BufferedReader(new FileReader(file));

                // Read up to where tag was found.
                for (int i = 1; i < line; i++) {
                    buffReader.readLine();
                }
            
                // Read each line of the method, as specified by the user.
                for (int i = 0; i < numLines; i++) {
                    builder.append(buffReader.readLine());
                    builder.append("<br />");
                }

                buffReader.close();
            
                return builder.toString();                
            }
            catch (FileNotFoundException notFound) {
                notFound.printStackTrace();
            } 
            catch (IOException io) {
                io.printStackTrace();
            }
        } 
        catch (NumberFormatException formatException) {
            // User didn't specify how long the method is.
            formatException.printStackTrace();
        }

        return "";        
    }    
}
