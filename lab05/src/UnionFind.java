public class UnionFind {
    private final int[] parentF;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parentF = new int[N];
        for (int i = 0; i < N; i++) {
            parentF[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -parentF[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return parentF[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parentF.length) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        if (parentF[v] >= 0) {
            parentF[v] = find(parentF[v]);
            return parentF[v];
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int num1 = find(v1);
        int num2 = find(v2);
        if (num1 != num2) {
            if (sizeOf(num1) <= sizeOf(num2)) {
                parentF[num2] += parentF[num1];
                parentF[num1] = num2;
            } else {
                parentF[num1] += parentF[num2];
                parentF[num2] = num1;
            }
        }
    }
}


