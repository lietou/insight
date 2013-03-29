package com.dianping.cat.status.model.transform;

import static com.dianping.cat.status.model.Constants.ENTITY_DISK;
import static com.dianping.cat.status.model.Constants.ENTITY_DISK_VOLUME;
import static com.dianping.cat.status.model.Constants.ENTITY_GC;
import static com.dianping.cat.status.model.Constants.ENTITY_MEMORY;
import static com.dianping.cat.status.model.Constants.ENTITY_MESSAGE;
import static com.dianping.cat.status.model.Constants.ENTITY_OS;
import static com.dianping.cat.status.model.Constants.ENTITY_RUNTIME;
import static com.dianping.cat.status.model.Constants.ENTITY_STATUS;
import static com.dianping.cat.status.model.Constants.ENTITY_THREAD;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.dianping.cat.status.model.entity.DiskInfo;
import com.dianping.cat.status.model.entity.DiskVolumeInfo;
import com.dianping.cat.status.model.entity.GcInfo;
import com.dianping.cat.status.model.entity.MemoryInfo;
import com.dianping.cat.status.model.entity.MessageInfo;
import com.dianping.cat.status.model.entity.OsInfo;
import com.dianping.cat.status.model.entity.RuntimeInfo;
import com.dianping.cat.status.model.entity.StatusInfo;
import com.dianping.cat.status.model.entity.ThreadsInfo;

public class DefaultDomParser implements IParser<Node> {

   protected Node getChildTagNode(Node parent, String name) {
      NodeList children = parent.getChildNodes();
      int len = children.getLength();

      for (int i = 0; i < len; i++) {
         Node child = children.item(i);

         if (child.getNodeType() == Node.ELEMENT_NODE) {
            if (child.getNodeName().equals(name)) {
               return child;
            }
         }
      }

      return null;
   }

   protected List<Node> getChildTagNodes(Node parent, String name) {
      NodeList children = parent.getChildNodes();
      int len = children.getLength();
      List<Node> nodes = new ArrayList<Node>(len);

      for (int i = 0; i < len; i++) {
         Node child = children.item(i);

         if (child.getNodeType() == Node.ELEMENT_NODE) {
            if (name == null || child.getNodeName().equals(name)) {
               nodes.add(child);
            }
         }
      }

      return nodes;
   }

   protected Node getDocument(String xml) {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      dbf.setIgnoringElementContentWhitespace(true);
      dbf.setIgnoringComments(true);

      try {
         DocumentBuilder db = dbf.newDocumentBuilder();

         return db.parse(new InputSource(new StringReader(xml)));
      } catch (Exception x) {
         throw new RuntimeException(x);
      }
   }

   protected List<Node> getGrandChildTagNodes(Node parent, String name) {
      Node child = getChildTagNode(parent, name);
      NodeList children = child == null ? null : child.getChildNodes();
      int len = children == null ? 0 : children.getLength();
      List<Node> nodes = new ArrayList<Node>(len);

      for (int i = 0; i < len; i++) {
         Node grandChild = children.item(i);

         if (grandChild.getNodeType() == Node.ELEMENT_NODE) {
            nodes.add(grandChild);
         }
      }

      return nodes;
   }

   public StatusInfo parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public StatusInfo parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_STATUS);

      if (rootNode == null) {
         throw new RuntimeException(String.format("status element(%s) is expected!", ENTITY_STATUS));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public StatusInfo parse(IMaker<Node> maker, ILinker linker, Node node) {
      StatusInfo status = maker.buildStatus(node);

      if (node != null) {
         StatusInfo parent = status;

         Node runtimeNode = getChildTagNode(node, ENTITY_RUNTIME);

         if (runtimeNode != null) {
            RuntimeInfo runtime = maker.buildRuntime(runtimeNode);

            if (linker.onRuntime(parent, runtime)) {
               parseForRuntimeInfo(maker, linker, runtime, runtimeNode);
            }
         }

         Node osNode = getChildTagNode(node, ENTITY_OS);

         if (osNode != null) {
            OsInfo os = maker.buildOs(osNode);

            if (linker.onOs(parent, os)) {
               parseForOsInfo(maker, linker, os, osNode);
            }
         }

         Node diskNode = getChildTagNode(node, ENTITY_DISK);

         if (diskNode != null) {
            DiskInfo disk = maker.buildDisk(diskNode);

            if (linker.onDisk(parent, disk)) {
               parseForDiskInfo(maker, linker, disk, diskNode);
            }
         }

         Node memoryNode = getChildTagNode(node, ENTITY_MEMORY);

         if (memoryNode != null) {
            MemoryInfo memory = maker.buildMemory(memoryNode);

            if (linker.onMemory(parent, memory)) {
               parseForMemoryInfo(maker, linker, memory, memoryNode);
            }
         }

         Node threadNode = getChildTagNode(node, ENTITY_THREAD);

         if (threadNode != null) {
            ThreadsInfo thread = maker.buildThread(threadNode);

            if (linker.onThread(parent, thread)) {
               parseForThreadsInfo(maker, linker, thread, threadNode);
            }
         }

         Node messageNode = getChildTagNode(node, ENTITY_MESSAGE);

         if (messageNode != null) {
            MessageInfo message = maker.buildMessage(messageNode);

            if (linker.onMessage(parent, message)) {
               parseForMessageInfo(maker, linker, message, messageNode);
            }
         }
      }

      return status;
   }

   public void parseForDiskInfo(IMaker<Node> maker, ILinker linker, DiskInfo parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_DISK_VOLUME)) {
         DiskVolumeInfo diskVolume = maker.buildDiskVolume(child);

         if (linker.onDiskVolume(parent, diskVolume)) {
            parseForDiskVolumeInfo(maker, linker, diskVolume, child);
         }
      }
   }

   public void parseForDiskVolumeInfo(IMaker<Node> maker, ILinker linker, DiskVolumeInfo parent, Node node) {
   }

   public void parseForGcInfo(IMaker<Node> maker, ILinker linker, GcInfo parent, Node node) {
   }

   public void parseForMemoryInfo(IMaker<Node> maker, ILinker linker, MemoryInfo parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_GC)) {
         GcInfo gc = maker.buildGc(child);

         if (linker.onGc(parent, gc)) {
            parseForGcInfo(maker, linker, gc, child);
         }
      }
   }

   public void parseForMessageInfo(IMaker<Node> maker, ILinker linker, MessageInfo parent, Node node) {
   }

   public void parseForOsInfo(IMaker<Node> maker, ILinker linker, OsInfo parent, Node node) {
   }

   public void parseForRuntimeInfo(IMaker<Node> maker, ILinker linker, RuntimeInfo parent, Node node) {
   }

   public void parseForThreadsInfo(IMaker<Node> maker, ILinker linker, ThreadsInfo parent, Node node) {
   }
}
