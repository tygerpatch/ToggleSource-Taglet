package stuff;

//In Eclipse, click on Project menu and then select "Generate Javadoc..."
//On the third window there is a text box for additional javadoc options.abstract
//Place the following in that box.
//
//-taglet stuff.ToggleSource 
//-tagletpath C:\Users\Todd\Git\Projects\ToggleSource-Taglet\bin
//-bottom "<script type='text/javascript'>function toggleSource(id){var code_element = document.getElementById('code-' + id);if(code_element.style.visibility == 'hidden'){code_element.style.visibility = 'visible';}else{code_element.style.visibility = 'hidden';}var link_element = document.getElementById('link-' + id);if(link_element.innerHTML == 'show'){link_element.innerHTML = 'hide';}else {link_element.innerHTML = 'show';}}</script>"
//
//Note: The tagletpath is going to be different for each user.
public class MyClass {

    private String message = "Hello World";

    public MyClass() {
    }

    /**
     * @toggle-source 3
     */
    public MyClass(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
