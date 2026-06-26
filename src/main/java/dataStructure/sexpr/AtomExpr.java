package dataStructure.sexpr;

public class AtomExpr implements Expr {

    private final String value;

    public static final AtomExpr NIL = new AtomExpr("nil");

    public AtomExpr(String value) {
        this.value = value;
    }

    public static AtomExpr atom(String value) {
        if (value == null) {
            return NIL;
        }
        return new AtomExpr(value);
    }

    @Override
    public boolean isAtom() {
        return true;
    }

    @Override
    public AtomExpr asAtom() {
        return this;
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public ListExpr asList() {
        return null;
    }

    @Override
    public String expr() {
        return null;
    }
}
