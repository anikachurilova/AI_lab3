import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return f.equals(pair.f) &&
                s.equals(pair.s);
    }

}