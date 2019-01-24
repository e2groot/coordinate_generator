public class Main {

    public static void main(String[] args) {
        NetworkGenerator networkGenerator = new NetworkGenerator(new Constraints(1, 500),
                new Constraints(0, 50));
        Network network = networkGenerator.generateNetwork();
        network.printNetwork();
    }
}
