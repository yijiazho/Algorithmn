package dataStructure.sexpr;

public interface Expr {
    boolean isAtom();

    AtomExpr asAtom();

    boolean isList();

    ListExpr asList();

    String expr();
}
