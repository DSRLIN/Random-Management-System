package tools;

public class comparator {
    public comparator(){};
    public boolean compare(long A,long B,long C,long D){
        if(A <= C&&B <= C){
            return false;
        }else if(A >= D&&B >= D) {
            return false;
        }else{
            return true;
        }
    };
}
