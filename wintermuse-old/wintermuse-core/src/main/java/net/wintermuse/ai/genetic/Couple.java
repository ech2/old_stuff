package net.wintermuse.ai.genetic;

import net.wintermuse.util.Pair;

/**
 * NB: equals() and hashCode() implemented in such way,
 * that order of 'first' and 'second' fields will not affect them.
 * E.g. hash(Couple(1,2)) equals hash(Couple(2,1))
 * Created by oscii on 22/04/14.
 */
public class Couple<T> extends Pair<T, T> {
    public Couple(T first, T second) {
        super(first, second);
    }

    public static <C> Couple<C> ofCouple(C first, C second) {
        return new Couple<C>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Couple couple = (Couple) o;

        return eqls(couple.first, couple.second) || eqls(couple.second, couple.first);
    }

    @Override
    public int hashCode() {
        int firstHash = first != null ? first.hashCode() : 0;
        int secondHash = second != null ? second.hashCode() : 0;

        return 31 * (firstHash * secondHash) + (firstHash + secondHash) * 7;
    }


}
