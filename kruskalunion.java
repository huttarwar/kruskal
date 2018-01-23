import java.io.*;
import java.util.*;

public class kruskalunion {
    static class Edge implements Comparable<Edge> {
        char source, destination;
        int weight;

        Edge(char src, char dest, int wgt) {
            source = src;
            destination = dest;
            weight = wgt;
        }
        @Override
        public int compareTo(Edge o) {
            return Integer.compare(weight, o.weight);
        }
        @Override
        public String toString() {
            return "(" + source + "," + destination + ")"+"="+weight;
        }
    }

    private static Map<Character, Character> par; // parent
    private static Map<Character, Integer> rank; //depth

    public static void initialize(char[] graph) {
        par = new HashMap<>();
        rank = new HashMap<>();
        for (char ver : graph) {
            par.put(ver, ver);
            rank.put(ver, 1);
        }
    }

    public static char Find(char node) {
        char parent = par.get(node);
        if (parent == node)
            return node;
        else
            return Find(parent);
    }

    public static void Union(char set1, char set2) {
        char s1, s2;
        while ((s1 = par.get(set1)) != set1) {
            set1 = s1;
        }
        while ((s2 = par.get(set2)) != set2) {
            set2 = s2;
        }

        int rank1 = rank.get(set1), rank2 = rank.get(set2);
        if (rank1 > rank2) {
            par.put(set2, set1);
            update(set2);
        } else if (rank2 > rank1) {
            par.put(set1, set2);
            update(set1);
        } else {
            par.put(set2, set1);
            update(set2);
        }
    }

    public static void update(char curr) {
        int currDepth = rank.get(curr);
        char currentsParent = par.get(curr);
        int parDepth = rank.get(currentsParent);
        if (!(currDepth < parDepth || currentsParent == curr)) {
            rank.put(currentsParent, currDepth + 1);
            update(currentsParent);
        }
    }

    public static void main(String[] args) throws IOException {

        int i = 0, j = 0, n = 0, m = 0;
        Edge[] edges = new Edge[20];
        char[] ver1 = new char[20];
        String[] mainArray = new String[10];
        Scanner scanner = new Scanner( System.in );
        System.out.println("Enter the Input path :-");
        String input = scanner.nextLine();
        System.out.println("Enter the Output path :-");
        String output = scanner.nextLine();
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
            mainArray[i] = line;
            if (i == 0) {
                n = Integer.parseInt(mainArray[0]);
                i++;
            } else if (i == 1) {
                String vertices = mainArray[1].replace(",", "");  //remove the commas;;
                ver1 = new char[n];
                ver1 = vertices.toCharArray();
                i++;
            }
            else if (i == 2) {
                m = Integer.parseInt(mainArray[2]);
                edges = new Edge[m];
                i++;
            } else {
                edges[j] = new Edge(line.charAt(1), line.charAt(3), Integer.parseInt(String.valueOf(line.charAt(6))));
                j++;
                i++;
            }
        }

        kruskalunion(ver1, edges,output);
    }
    public static ArrayList<Edge> kruskalunion(char[] ver1, Edge[] edges, String output) throws FileNotFoundException {
        //Initialize A = empty set
        ArrayList<Edge> mst = new ArrayList<>();

        //for each vertex v belongs to G.V MAKE-SET(v)
        initialize(ver1);

        //sort the edges of G.E into non decreasing order by weight w
        Arrays.sort(edges);

        //For each edge (u,v) belongs to G.E taken in non decreasing order by weight
        for (Edge edge : edges) {
            //If (find-set(u)!=find-set(v)
            if (Find(edge.source) != Find(edge.destination)) {

                mst.add(edge);//A = A union (u, v)

                Union(edge.source, edge.destination);//UNION
            }

            //Display contents


        }
        output=output.toString()+"kruskal.out.txt";
        System.out.println(""+output);
        PrintWriter outputfile = new PrintWriter(output);
        outputfile.println("" + mst.size());
        for (int i = 0; i < mst.size(); i++) {
            outputfile.println(mst.get(i));
        }
        outputfile.close();
        return mst;
    }
}


