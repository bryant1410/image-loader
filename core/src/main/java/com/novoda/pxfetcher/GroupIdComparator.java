package com.novoda.pxfetcher;

public class GroupIdComparator implements java.util.Comparator<Id> {

    private static final int LHS_GREATER_THAN_RHS = 1;
    private static final int LHS_EQUAL_TO_RHS = 0;
    private static final int LHS_LESS_THAN_RHS = -1;

    @Override
    public int compare(Id lhs, Id rhs) {
        if (lhs == rhs) {
            return LHS_EQUAL_TO_RHS;
        }

        if (lhs.getValue() > rhs.getValue()) {
            return LHS_GREATER_THAN_RHS;
        }

        return LHS_LESS_THAN_RHS;
    }

}
