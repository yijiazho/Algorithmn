package graph;

public class Vertex {
    int id;
    Vertex parent;
    int size;

    public Vertex(int id) {
        this.id = id;
        size = 1;
        parent = this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vertex)) {
            return false;
        }
        Vertex that = (Vertex) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return id * 31 + 29;
    }
}
