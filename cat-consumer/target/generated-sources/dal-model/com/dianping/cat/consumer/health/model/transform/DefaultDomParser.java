package com.dianping.cat.consumer.health.model.transform;

import static com.dianping.cat.consumer.health.model.Constants.ELEMENT_DOMAIN;

import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_CACHE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CALL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CLIENTSERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_HEALTH_REPORT;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_KVDB_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MACHINE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MEM_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_PROBLEM_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SQL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_URL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_WEB_CACHE;

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
import com.dianping.cat.consumer.health.model.entity.BaseCacheInfo;
import com.dianping.cat.consumer.health.model.entity.BaseInfo;
import com.dianping.cat.consumer.health.model.entity.Call;
import com.dianping.cat.consumer.health.model.entity.ClientService;
import com.dianping.cat.consumer.health.model.entity.HealthReport;
import com.dianping.cat.consumer.health.model.entity.KvdbCache;
import com.dianping.cat.consumer.health.model.entity.MachineInfo;
import com.dianping.cat.consumer.health.model.entity.MemCache;
import com.dianping.cat.consumer.health.model.entity.ProblemInfo;
import com.dianping.cat.consumer.health.model.entity.Service;
import com.dianping.cat.consumer.health.model.entity.Sql;
import com.dianping.cat.consumer.health.model.entity.Url;
import com.dianping.cat.consumer.health.model.entity.WebCache;

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

   public HealthReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public HealthReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_HEALTH_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("health-report element(%s) is expected!", ENTITY_HEALTH_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public HealthReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      HealthReport healthReport = maker.buildHealthReport(node);

      if (node != null) {
         HealthReport parent = healthReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAIN)) {
            String domain = maker.buildDomain(child);

            parent.addDomain(domain);
         }
         Node problemInfoNode = getChildTagNode(node, ENTITY_PROBLEM_INFO);

         if (problemInfoNode != null) {
            ProblemInfo problemInfo = maker.buildProblemInfo(problemInfoNode);

            if (linker.onProblemInfo(parent, problemInfo)) {
               parseForProblemInfo(maker, linker, problemInfo, problemInfoNode);
            }
         }

         Node urlNode = getChildTagNode(node, ENTITY_URL);

         if (urlNode != null) {
            Url url = maker.buildUrl(urlNode);

            if (linker.onUrl(parent, url)) {
               parseForUrl(maker, linker, url, urlNode);
            }
         }

         Node serviceNode = getChildTagNode(node, ENTITY_SERVICE);

         if (serviceNode != null) {
            Service service = maker.buildService(serviceNode);

            if (linker.onService(parent, service)) {
               parseForService(maker, linker, service, serviceNode);
            }
         }

         Node callNode = getChildTagNode(node, ENTITY_CALL);

         if (callNode != null) {
            Call call = maker.buildCall(callNode);

            if (linker.onCall(parent, call)) {
               parseForCall(maker, linker, call, callNode);
            }
         }

         Node sqlNode = getChildTagNode(node, ENTITY_SQL);

         if (sqlNode != null) {
            Sql sql = maker.buildSql(sqlNode);

            if (linker.onSql(parent, sql)) {
               parseForSql(maker, linker, sql, sqlNode);
            }
         }

         Node webCacheNode = getChildTagNode(node, ENTITY_WEB_CACHE);

         if (webCacheNode != null) {
            WebCache webCache = maker.buildWebCache(webCacheNode);

            if (linker.onWebCache(parent, webCache)) {
               parseForWebCache(maker, linker, webCache, webCacheNode);
            }
         }

         Node kvdbCacheNode = getChildTagNode(node, ENTITY_KVDB_CACHE);

         if (kvdbCacheNode != null) {
            KvdbCache kvdbCache = maker.buildKvdbCache(kvdbCacheNode);

            if (linker.onKvdbCache(parent, kvdbCache)) {
               parseForKvdbCache(maker, linker, kvdbCache, kvdbCacheNode);
            }
         }

         Node memCacheNode = getChildTagNode(node, ENTITY_MEM_CACHE);

         if (memCacheNode != null) {
            MemCache memCache = maker.buildMemCache(memCacheNode);

            if (linker.onMemCache(parent, memCache)) {
               parseForMemCache(maker, linker, memCache, memCacheNode);
            }
         }

         Node clientServiceNode = getChildTagNode(node, ENTITY_CLIENTSERVICE);

         if (clientServiceNode != null) {
            ClientService clientService = maker.buildClientService(clientServiceNode);

            if (linker.onClientService(parent, clientService)) {
               parseForClientService(maker, linker, clientService, clientServiceNode);
            }
         }

         Node machineInfoNode = getChildTagNode(node, ENTITY_MACHINE_INFO);

         if (machineInfoNode != null) {
            MachineInfo machineInfo = maker.buildMachineInfo(machineInfoNode);

            if (linker.onMachineInfo(parent, machineInfo)) {
               parseForMachineInfo(maker, linker, machineInfo, machineInfoNode);
            }
         }
      }

      return healthReport;
   }

   public void parseForBaseCacheInfo(IMaker<Node> maker, ILinker linker, BaseCacheInfo parent, Node node) {
   }

   public void parseForBaseInfo(IMaker<Node> maker, ILinker linker, BaseInfo parent, Node node) {
   }

   public void parseForCall(IMaker<Node> maker, ILinker linker, Call parent, Node node) {
      Node baseInfoNode = getChildTagNode(node, ENTITY_BASE_INFO);

      if (baseInfoNode != null) {
         BaseInfo baseInfo = maker.buildBaseInfo(baseInfoNode);

         if (linker.onBaseInfo(parent, baseInfo)) {
            parseForBaseInfo(maker, linker, baseInfo, baseInfoNode);
         }
      }
   }

   public void parseForClientService(IMaker<Node> maker, ILinker linker, ClientService parent, Node node) {
      Node baseInfoNode = getChildTagNode(node, ENTITY_BASE_INFO);

      if (baseInfoNode != null) {
         BaseInfo baseInfo = maker.buildBaseInfo(baseInfoNode);

         if (linker.onBaseInfo(parent, baseInfo)) {
            parseForBaseInfo(maker, linker, baseInfo, baseInfoNode);
         }
      }
   }

   public void parseForKvdbCache(IMaker<Node> maker, ILinker linker, KvdbCache parent, Node node) {
      Node baseCacheInfoNode = getChildTagNode(node, ENTITY_BASE_CACHE_INFO);

      if (baseCacheInfoNode != null) {
         BaseCacheInfo baseCacheInfo = maker.buildBaseCacheInfo(baseCacheInfoNode);

         if (linker.onBaseCacheInfo(parent, baseCacheInfo)) {
            parseForBaseCacheInfo(maker, linker, baseCacheInfo, baseCacheInfoNode);
         }
      }
   }

   public void parseForMachineInfo(IMaker<Node> maker, ILinker linker, MachineInfo parent, Node node) {
   }

   public void parseForMemCache(IMaker<Node> maker, ILinker linker, MemCache parent, Node node) {
      Node baseCacheInfoNode = getChildTagNode(node, ENTITY_BASE_CACHE_INFO);

      if (baseCacheInfoNode != null) {
         BaseCacheInfo baseCacheInfo = maker.buildBaseCacheInfo(baseCacheInfoNode);

         if (linker.onBaseCacheInfo(parent, baseCacheInfo)) {
            parseForBaseCacheInfo(maker, linker, baseCacheInfo, baseCacheInfoNode);
         }
      }
   }

   public void parseForProblemInfo(IMaker<Node> maker, ILinker linker, ProblemInfo parent, Node node) {
   }

   public void parseForService(IMaker<Node> maker, ILinker linker, Service parent, Node node) {
      Node baseInfoNode = getChildTagNode(node, ENTITY_BASE_INFO);

      if (baseInfoNode != null) {
         BaseInfo baseInfo = maker.buildBaseInfo(baseInfoNode);

         if (linker.onBaseInfo(parent, baseInfo)) {
            parseForBaseInfo(maker, linker, baseInfo, baseInfoNode);
         }
      }
   }

   public void parseForSql(IMaker<Node> maker, ILinker linker, Sql parent, Node node) {
      Node baseInfoNode = getChildTagNode(node, ENTITY_BASE_INFO);

      if (baseInfoNode != null) {
         BaseInfo baseInfo = maker.buildBaseInfo(baseInfoNode);

         if (linker.onBaseInfo(parent, baseInfo)) {
            parseForBaseInfo(maker, linker, baseInfo, baseInfoNode);
         }
      }
   }

   public void parseForUrl(IMaker<Node> maker, ILinker linker, Url parent, Node node) {
      Node baseInfoNode = getChildTagNode(node, ENTITY_BASE_INFO);

      if (baseInfoNode != null) {
         BaseInfo baseInfo = maker.buildBaseInfo(baseInfoNode);

         if (linker.onBaseInfo(parent, baseInfo)) {
            parseForBaseInfo(maker, linker, baseInfo, baseInfoNode);
         }
      }
   }

   public void parseForWebCache(IMaker<Node> maker, ILinker linker, WebCache parent, Node node) {
      Node baseCacheInfoNode = getChildTagNode(node, ENTITY_BASE_CACHE_INFO);

      if (baseCacheInfoNode != null) {
         BaseCacheInfo baseCacheInfo = maker.buildBaseCacheInfo(baseCacheInfoNode);

         if (linker.onBaseCacheInfo(parent, baseCacheInfo)) {
            parseForBaseCacheInfo(maker, linker, baseCacheInfo, baseCacheInfoNode);
         }
      }
   }
}
