package net.sourcedestination.sai.tinkerpop.graph;

import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.gml.GMLReader;
import net.sourcedestination.sai.graph.Graph;
import net.sourcedestination.sai.graph.GraphDeserializer;
import net.sourcedestination.sai.task.DBPopulator;

import java.io.File;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by jmorwick on 12/11/17.
 */
public class TinkerPopGmlDeserializer implements GraphDeserializer<TinkerPopWrapper> {
    @Override
    public TinkerPopWrapper apply(String gml) {
        return readGML(gml);
    }

    public static TinkerPopWrapper readGML(String gml) {
        com.tinkerpop.blueprints.Graph g = new TinkerGraph();
        try { GMLReader.inputGraph(g,new StringBufferInputStream(gml)); }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        TinkerPopWrapper saiG = new TinkerPopWrapper(g);
        return saiG;
    }

    public static DBPopulator getGmlPopulator(File gmlFile) {
        try {
            final Scanner input = new Scanner(gmlFile);
            input.useDelimiter("\\s*graph\\s*\\[");

            return new DBPopulator() {

                @Override
                public Stream<Graph> getGraphStream() {
                    return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(
                                    new Iterator<TinkerPopWrapper>() {
                                        @Override
                                        public boolean hasNext() {
                                            return input.hasNext();
                                        }

                                        @Override
                                        public TinkerPopWrapper next() {
                                            return  readGML("graph [ " + input.next());
                                        }
                            }, Spliterator.ORDERED),
                            false);
                }

                @Override
                public int getNumGraphs() {
                    return -1;
                }
            };
        } catch(Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
