import java.util.*;
import java.text.*;

class Edge {
    private Integer _from;
    private Integer _to;
    public Edge(Integer from, Integer to) {
        _from = from;
        _to = to;
    }

    public Integer getFrom() {
        return _from;
    }

    public Integer getTo() {
        return _to;
    }
}

class Node {

    private double _x;
    private double _y;
    private double _z;
    private int _index;

    public Node(double x, double y, double z, int index) {
        _x = x;
        _y = y;
        _z = z;
        _index = index;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public double getZ() {
        return _z;
    }

    public int getIndex() {
        return _index;
    }
}

class Constraints {
    private int _lowerLimit;
    private int _upperLimit;

    public Constraints(int lowerLimit, int upperLimit) {
        _lowerLimit = lowerLimit;
        _upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return _lowerLimit;
    }

    public int getUpperLimit() {
        return _upperLimit;
    }
}

class NetworkGenerator {

    private final DecimalFormat df = new DecimalFormat("0.00");
    private int _size;
    private int _lowerLimit;
    private int _upperLimit;

    public NetworkGenerator(Constraints size, Constraints dimensions) {
        int lowerLimit = size.getLowerLimit();
        int upperLimit = size.getUpperLimit();

        _size = new Random().
                nextInt( (upperLimit - lowerLimit) + 1)
                + lowerLimit;

        _lowerLimit = dimensions.getLowerLimit();
        _upperLimit = dimensions.getUpperLimit();
    }

    public NetworkGenerator(int lowerLimit, int upperLimit) {
        _lowerLimit = lowerLimit;
        _upperLimit = upperLimit;
        _size = new Random()
                .nextInt( (_upperLimit - _lowerLimit) + 1)
                + _lowerLimit;
    }

    public Network generateNetwork() {
        List<Node> nodeList = new ArrayList<>();

        for (int i=0; i<_size; ++i) {
            Double x = getRandomNumber(_lowerLimit, _upperLimit);
            Double y = getRandomNumber(_lowerLimit, _upperLimit);
            Double z = getRandomNumber(_lowerLimit, _upperLimit);
            nodeList.add(new Node(x, y, z, i));
        }
        Collections.shuffle(nodeList);
        Set<Node> nodes = new LinkedHashSet<>(nodeList);
        Set<Edge> edges = new LinkedHashSet<>(generateEdges(nodeList));
        return new Network(nodes, edges);
    }

    private Set<Edge> generateEdges(List<Node> nodes) {
        Set<Edge> edges = new LinkedHashSet<>();
        if (!(nodes.size() >= 2)) {
            edges.add(
                    new Edge(nodes.get(0).getIndex(),
                            nodes.get(0).getIndex()
                    )
            );
            return edges;
        }
        edges.add(
                new Edge(
                        nodes.get(0).getIndex(),
                        nodes.get(1).getIndex()
                )
        );

        for (int x=2; x<nodes.size(); ++x) {
            int previous = nodes.get(x-1).getIndex();
            edges.add(
                    new Edge( previous, nodes.get(x).getIndex() )
            );
        }
        return edges;
    }

    private Double getRandomNumber(int lowerLimit,  int upperLimit) {
        return Double.valueOf(
                df.format(lowerLimit
                        + (upperLimit - lowerLimit)
                        * new Random().nextDouble()
                ));
    }
}

class Network {
    private Set<Node> _nodes;
    private Set<Edge> _edges;

    public Network(Set nodes, Set edges) {
        _nodes = nodes;
        _edges = edges;
    }

    public void printNetwork() {
        for(Node node : _nodes) {
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println(stringBuilder
                    .append(node.getX()).append(", ")
                    .append(node.getY()).append(", ")
                    .append(node.getZ()).toString());
        }

        for (Edge edge : _edges) {
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println(stringBuilder
                    .append(edge.getFrom())
                    .append(", ")
                    .append(edge.getTo())
                    .toString());
        }
    }
}