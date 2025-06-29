package graph;

public class UnionFind {

    private final Vertex[] vertices;

    public UnionFind(int n) {
        vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex(i);
        }
    }

    private Vertex root(Vertex p) {
        Vertex cur = p;
        while (cur.parent != cur) {
            cur.parent = cur.parent.parent;
            cur = cur.parent;
        }

        p.parent = cur;
        return cur;
    }

    public boolean find(int pIndex, int qIndex) {
        Vertex p = vertices[pIndex];
        Vertex q = vertices[qIndex];
        return root(p).equals(root(q));
    }

    public void union(int pIndex, int qIndex) {
        Vertex p = vertices[pIndex];
        Vertex q = vertices[qIndex];
        Vertex rootP = root(p);
        Vertex rootQ = root(q);
        if (rootP.size > rootQ.size) {
            rootQ.parent = rootP;
            rootP.size += rootQ.size;
        } else {
            rootP.parent = rootQ;
            rootQ.size += rootP.size;
        }
    }

    class Vertex {
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
}
