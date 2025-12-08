import java.util.*;

class Graph {

    class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    };

    class subset {
        int parent, rank;
    };

    int V, E;
    Edge edge[];

    Graph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    int find(subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    void Union(subset subsets[], int x, int y) {
        int xr = find(subsets, x);
        int yr = find(subsets, y);

        if (subsets[xr].rank < subsets[yr].rank)
            subsets[xr].parent = yr;
        else if (subsets[xr].rank > subsets[yr].rank)
            subsets[yr].parent = xr;
        else {
            subsets[yr].parent = xr;
            subsets[xr].rank++;
        }
    }

    // --------------------------
    //  EXTRA: ASCII GRAPH OUTPUT
    // --------------------------
    void printAsciiGraph() {
        System.out.println("\n----------------------------------------");
        System.out.println("ASCII GRAPH (All edges with weights)");
        System.out.println("----------------------------------------");

        for (Edge e : edge) {
            System.out.println(
                "(" + e.src + ") ----" + e.weight + "---- (" + e.dest + ")"
            );
        }
        System.out.println("----------------------------------------\n");
    }

    void printAsciiMST(Edge result[], int eCount) {
        System.out.println("\n----------------------------------------");
        System.out.println("ASCII GRAPH (MST Only)");
        System.out.println("----------------------------------------");

        for (int i = 0; i < eCount; i++) {
            Edge e = result[i];
            System.out.println(
                "(" + e.src + ") ----" + e.weight + "---- (" + e.dest + ")  [SELECTED]"
            );
        }

        System.out.println("----------------------------------------\n");
    }

    // ---------------------------
    //  KRUSKAL MST WITH ASCII
    // ---------------------------
    void KruskalMST() {

        printAsciiGraph();  // Show graph before processing

        Edge result[] = new Edge[V];
        int e = 0;
        int i = 0;

        for (i = 0; i < V; ++i)
            result[i] = new Edge();

        Arrays.sort(edge);

        subset subsets[] = new subset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new subset();

        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0;

        while (e < V - 1) {
            Edge next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }

        System.out.println("MST Edges:");
        int minimumCost = 0;
        for (i = 0; i < e; ++i) {
            System.out.println(
                result[i].src + " -- " + result[i].dest + " == " + result[i].weight
            );
            minimumCost += result[i].weight;
        }

        System.out.println("Minimum Cost Spanning Tree = " + minimumCost);

        // Print MST ASCII
        printAsciiMST(result, e);
    }


    public static void main(String[] args) {

        int V = 4;
        int E = 5;
        Graph graph = new Graph(V, E);

        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 10;

        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 6;

        graph.edge[2].src = 0;
        graph.edge[2].dest = 3;
        graph.edge[2].weight = 5;

        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 15;

        graph.edge[4].src = 2;
        graph.edge[4].dest = 3;
        graph.edge[4].weight = 4;

        graph.KruskalMST();
    }
}
