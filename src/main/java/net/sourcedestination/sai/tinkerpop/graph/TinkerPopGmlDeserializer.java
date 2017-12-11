package net.sourcedestination.sai.tinkerpop.graph;

import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.gml.GMLReader;
import net.sourcedestination.sai.graph.GraphDeserializer;

import java.io.IOException;
import java.io.StringBufferInputStream;

/**
 * Created by jmorwick on 12/11/17.
 */
public class TinkerPopGmlDeserializer implements GraphDeserializer<TinkerPopWrapper> {
    @Override
    public TinkerPopWrapper apply(String gml) {
        com.tinkerpop.blueprints.Graph g = new TinkerGraph();
        try { GMLReader.inputGraph(g,new StringBufferInputStream(gml)); }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        TinkerPopWrapper saiG = new TinkerPopWrapper(g);
        return saiG;
    }
}
