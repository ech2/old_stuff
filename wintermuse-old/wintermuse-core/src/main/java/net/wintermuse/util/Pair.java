package net.wintermuse.util;

/**
 * from http://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java/3646398#3646398
 * Created by oscii on 22/04/14.
 */
public class Pair<A, B> implements Comparable<Pair<A, B>> {
    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    protected static int compare(Object o1, Object o2) {
        return (o1 == null) ?
                ((o2 == null) ? 0 : -1) :
                ((o2 == null) ? +1 : ((Comparable) o1).compareTo(o2));
    }

    @Override
    public int compareTo(Pair<A, B> o) {
        int cmp = compare(first, o.first);
        return cmp == 0 ? compare(second, o.second) : cmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return eqls(pair.first, pair.second);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    protected boolean eqls(Object p1, Object p2) {
        if (first != null ? !first.equals(p1) : p1 != null)
            return false;
        if (second != null ? !second.equals(p2) : p2 != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
