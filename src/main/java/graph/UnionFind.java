package graph;

public class UnionFind {

    private V[] vertices;

    public UnionFind(int n) {
        vertices = new V[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new V(i);
        }
    }

    public V get(int id) {
        return vertices[id];
    }

    public V root(V p) {
        V cur = p;
        while (cur.parent != cur) {
            cur.parent = cur.parent.parent;
            cur = cur.parent;
        }

        p.parent = cur;
        return cur;
    }

    public boolean find(V p, V q) {
        return root(p).equals(root(q));
    }

    public void union(V p, V q) {
        V rootP = root(p);
        V rootQ = root(q);
        if (rootP.size > rootQ.size) {
            rootQ.parent = rootP;
            rootP.size += rootQ.size;
        } else {
            rootP.parent = rootQ;
            rootQ.size += rootP.size;
        }
    }

    class V {
        int id;
        V parent;
        int size;

        public V(int id) {
            this.id = id;
            size = 1;
            parent = this;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof V)) {
                return false;
            }
            V that = (V) o;
            return this.id == that.id;
        }

        @Override
        public int hashCode() {
            return id * 31 + 29;
        }
    }
}
