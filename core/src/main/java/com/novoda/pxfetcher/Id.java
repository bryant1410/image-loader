package com.novoda.pxfetcher;

public class Id {

    private final long id;

    public Id(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id id1 = (Id) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public static class Comparator implements java.util.Comparator<Id> {

        private static final int LHS_GREATER_THAN_RHS = 1;
        private static final int LHS_EQUAL_TO_RHS = 0;
        private static final int LHS_LESS_THAN_RHS = -1;

        @Override
        public int compare(Id lhs, Id rhs) {
            if (lhs == rhs) {
                return LHS_EQUAL_TO_RHS;
            }

            if (lhs.id > rhs.id) {
                return LHS_GREATER_THAN_RHS;
            }

            return LHS_LESS_THAN_RHS;
        }

    }

}
