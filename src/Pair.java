public class Pair {
    private Integer f;
    private Integer s;

    public  Pair( Integer f, Integer s ) {
        this.f = f;
        this.s = s;
    }

    public Integer getFirst() {
        return f;
    }

    public Integer getSecond() {
        return s;
    }

    public String toString() {
        return "["+getFirst()+","+getSecond()+"]";
    }

}