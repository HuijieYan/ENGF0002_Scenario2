public class Model {
    private Matcher matcher = new Matcher();

    public String match(String regex,String input){
        try {
            boolean result = matcher.match(regex,input);
            if(result){
                return "The input match this regex";
            }
            return "The input does not match this regex";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
