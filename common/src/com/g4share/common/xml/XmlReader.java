/**
 * User: gm
 */

package com.g4share.common.xml;
import com.g4share.common.Constants;
import com.g4share.common.except.SaxInterruptedException;
import com.g4share.common.log.LogLevel;
import com.sun.istack.internal.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlReader<T extends Enum<T> & XmlTag, U>  {

    private XmlTagStore<T> tagStore;
    private XmlModelManager<T, U> modelManager;

    public XmlReader(@NotNull XmlTagStore<T> tagStore,
                     @NotNull XmlModelManager<T, U> modelManager) {
        this.tagStore = tagStore;
        this.modelManager = modelManager;
    }

    public XmlModelManager<T, U> getModelManager() {
        return modelManager;
    }

    public Constants.ResultCode read(InputStream rssStream) {
        //create sax parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException ex) {
            modelManager.eventOccurred(LogLevel.FATAL, "config cannot be opened.");

            return Constants.ResultCode.FATAL_ERROR_CODE;
        }


        //sax handler definition
        DefaultHandler handler = new DefaultHandler() {
            Map<String, String> keyValues = new HashMap<>();
            StringBuffer text = new StringBuffer();
            boolean hasNode;

            //store current xml node
            private List<T> currentPath = new ArrayList<>();

            @Override
            public void startElement(String uri, String localName, String qName,
                                     Attributes attributes) throws SAXException {

                //add node to store
                if (hasNode){
                    //text are added when tag closes
                    boolean readMore = modelManager.addNode(currentPath, null, keyValues.size() == 0 ? null : keyValues);
                    if (!readMore){
                        throw new SaxInterruptedException();
                    }

                    keyValues.clear();
                    hasNode = false;
                }

                text.setLength(0);

                //parse qname as XmlNode
                T node = tagStore.fromString(qName);
                if (node == null) return;

                //if node is defined
                if (tagStore.isPathKey(node, currentPath)){
                    //if the node is the last in its defined path
                    keyValues = new HashMap<>();

                    for(int i = 0; i < attributes.getLength(); i++){
                        //store each attribute
                        keyValues.put(attributes.getQName(i), attributes.getValue(i));
                    }

                    //change current xml node
                    currentPath.add(node);
                    hasNode = true;
                }
            }

            @Override
            public void endElement(String uri, String localName,
                                   String qName) throws SAXException {
                T node = tagStore.fromString(qName);
                if (node == null) return;

                //return if no current node closes
                //ex <node>value</node>
                if (currentPath.get(currentPath.size() - 1) != node) return;

                //add node to store
                if (hasNode){
                    boolean readMore = modelManager.addNode(currentPath,
                            text.length() == 0 ? null : text.toString(),
                            keyValues.size() == 0 ? null : keyValues);
                    if (!readMore){
                        throw new SaxInterruptedException();
                    }

                    keyValues.clear();
                    hasNode = false;
                }


                currentPath.remove(node);
            }

            @Override
            public void characters(char ch[], int start, int length) throws SAXException {
                text.append(ch, start, length);
            }

            @Override
            public void warning(SAXParseException exception) {
                modelManager.eventOccurred(LogLevel.TRACE, "xml error; line " + exception.getLineNumber());
            }

            @Override
            public void error(SAXParseException exception) {
                modelManager.eventOccurred(LogLevel.TRACE, "xml error; line " + exception.getLineNumber());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                modelManager.eventOccurred(LogLevel.ERROR, "xml error; line " + exception.getLineNumber());
                throw (exception);
            }
        };

        try {
            saxParser.parse(rssStream, handler);
        } catch (SaxInterruptedException ex) {
            modelManager.eventOccurred(LogLevel.TRACE, "reading interrupted.");

            return Constants.ResultCode.INTERRUPTED_CODE;
        } catch (SAXException | IOException ex) {
            modelManager.eventOccurred(LogLevel.FATAL, "error read xml: possible wrong format.");

            return Constants.ResultCode.FATAL_ERROR_CODE;
        }

        return Constants.ResultCode.SUCCESS_CODE;
    }

    private void log(LogLevel level, String message){
        if (modelManager != null)  modelManager.eventOccurred(level, message);
    }
}
