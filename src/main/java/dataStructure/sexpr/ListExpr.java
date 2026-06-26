package dataStructure.sexpr;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListExpr implements Expr {

    List<Expr> list;

    public ListExpr(List<Expr> list) {
        this.list = list;
    }

    public static ListExpr list(Expr expr) {
        return new ListExpr(Collections.singletonList(expr));
    }

    public static ListExpr list(Expr... exprs) {
        return new ListExpr(Arrays.asList(exprs));
    }

    @Override
    public boolean isAtom() {
        return false;
    }

    @Override
    public AtomExpr asAtom() {
        return null;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public ListExpr asList() {
        return this;
    }

    @Override
    public String expr() {
        return null;
    }
}
