package stuff;

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
