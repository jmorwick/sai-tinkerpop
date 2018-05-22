package net.sourcedestination.sai.tinkerpop.graph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import net.sourcedestination.sai.db.graph.Feature;
import net.sourcedestination.sai.db.graph.Graph;

import java.util.Arrays;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by jmorwick on 12/5/17.
 */
public class TinkerPopWrapper implements Graph {

    private com.tinkerpop.blueprints.Graph graph;
    private final Feature[] features;

    public TinkerPopWrapper(com.tinkerpop.blueprints.Graph g, Feature ... features) {
        this.graph = g;
        this.features = features;
    }

    @Override
    public Stream<Integer> getEdgeIDs() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(graph.getEdges().iterator(), Spliterator.ORDERED),
                false).map(e -> Integer.parseInt((String)e.getId()));
    }

    @Override
    public Stream<Integer> getNodeIDs() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(graph.getVertices().iterator(), Spliterator.ORDERED),
                false).map(v -> Integer.parseInt((String)v.getId()));
    }

    @Override
    public Stream<Feature> getFeatures() {
        return Arrays.stream(features);
    }

    @Override
    public Stream<Feature> getNodeFeatures(int nid) {
        Vertex v = graph.getVertex(""+nid);
        if(v == null) return Stream.empty();
        return v.getPropertyKeys().stream().map(k -> new Feature(k, ""+v.getProperty(k)));
    }

    @Override
    public Stream<Feature> getEdgeFeatures(int eid) {
        Edge e = graph.getEdge(""+eid);
        if(e == null) return Stream.empty();
        Stream<Feature> propertyStream = e.getPropertyKeys().stream()
                .map(k -> new Feature(k, ""+e.getProperty(k)));
        if(e.getLabel() == null || e.getLabel().equals(""))
            return propertyStream;
        else
            return Stream.concat(propertyStream, Stream.of(new Feature("label", e.getLabel())));
    }


    @Override
    public int getEdgeSourceNodeID(int edgeID) {
        return  Integer.parseInt((String)graph.getEdge(""+edgeID).getVertex(Direction.IN).getId());
    }


    @Override
    public int getEdgeTargetNodeID(int edgeID) {
        return  Integer.parseInt((String)graph.getEdge(""+edgeID).getVertex(Direction.OUT).getId());
    }

}
