import net.sourcedestination.sai.tinkerpop.graph.TinkerPopGmlDeserializer;
import net.sourcedestination.sai.tinkerpop.graph.TinkerPopWrapper;

/**
 * Created by jmorwick on 12/8/17.
 */
public class Test {
    public static void main(String[] args) {
        TinkerPopWrapper g = TinkerPopGmlDeserializer.readGML("\n" +
                "graph\n" +
                "[\n\n" +
                "   label \"Edge C to A\"" +
                "\n" +
                "  node\n" +
                "  [\n" +
                "   id B\n" +
                "   label \"Node B\"\n" +
                "  ]\n" +
                "  node\n" +
                "  [\n" +
                "   id C\n" +
                "   label \"Node C\"\n" +
                "  ]" +
                "]" );
        g.getFeatures().forEach(System.out::println);
    }
}
