package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


class PathFinder {

    private String path = "";

    // A - B - C -  E
    // - d
    // a : b, d
    // b: a, c
    // c: b, E
    // d: a
    // E: C



    // suppose the graph is valid, and there is guaranteed a solution
    public String findPath(Map<Character, List<Character>> graph, Character from, Character to) {

        if (from == to) {
            return "";
        }
        search(graph, from, null, to, new StringBuilder());
        return this.path;
    }

    // find path between two nodes
    // dfs algo
    // find all neighbours, except the node we are coming from
    private void search(Map<Character, List<Character>> graph, Character cur, Character from, Character target,  StringBuilder path) {

        if (target.equals(cur)) {
            // add to res and return
            path.append(cur);
            this.path = path.toString();
            return;
        }

        for (Character next: graph.get(cur)) {
            if (next == from) {
                continue;
            }
            // add current to path
            path.append(cur);
            search(graph, next, cur, target, path);
            // trim it
            path.setLength(path.length() - 1);
        }
    }

    public static void main(String[] args) {

        PathFinder pathFinder = new PathFinder();
        Map<Character, List<Character>> graph = new HashMap<>();
        graph.put('A', List.of('B', 'D'));
        graph.put('B', List.of('A', 'C'));
        graph.put('C', List.of('B'));
        graph.put('D', List.of('A'));

        String res = pathFinder.findPath(graph, 'A', 'D');
        System.out.println(res);
    }
}