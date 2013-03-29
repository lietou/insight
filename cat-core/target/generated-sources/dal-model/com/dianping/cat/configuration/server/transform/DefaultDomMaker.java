package com.dianping.cat.configuration.server.transform;

import static com.dianping.cat.configuration.server.Constants.ATTR_BASE_DIR;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_SERVICE_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_SQL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_URL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_HDFS_DISABLED;
import static com.dianping.cat.configuration.server.Constants.ATTR_ID;
import static com.dianping.cat.configuration.server.Constants.ATTR_JOB_MACHINE;
import static com.dianping.cat.configuration.server.Constants.ATTR_LOCAL_BASE_DIR;
import static com.dianping.cat.configuration.server.Constants.ATTR_LOCAL_MODE;
import static com.dianping.cat.configuration.server.Constants.ATTR_MAX_SIZE;
import static com.dianping.cat.configuration.server.Constants.ATTR_NAME;
import static com.dianping.cat.configuration.server.Constants.ATTR_SERVER_URI;
import static com.dianping.cat.configuration.server.Constants.ATTR_SERVICE_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_SHOW_CAT_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ATTR_SQL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_URL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_VALUE;

import static com.dianping.cat.configuration.server.Constants.ELEMENT_REMOTE_SERVERS;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public ServerConfig buildConfig(Node node) {
      String localMode = getAttribute(node, ATTR_LOCAL_MODE);
      String jobMachine = getAttribute(node, ATTR_JOB_MACHINE);

      ServerConfig config = new ServerConfig();

      if (localMode != null) {
         config.setLocalMode(convert(Boolean.class, localMode, null));
      }

      if (jobMachine != null) {
         config.setJobMachine(convert(Boolean.class, jobMachine, null));
      }

      return config;
   }

   @Override
   public ConsoleConfig buildConsole(Node node) {
      String defaultDomain = getAttribute(node, ATTR_DEFAULT_DOMAIN);
      String showCatDomain = getAttribute(node, ATTR_SHOW_CAT_DOMAIN);
      String remoteServers = getText(getChildTagNode(node, ELEMENT_REMOTE_SERVERS));

      ConsoleConfig console = new ConsoleConfig();

      if (defaultDomain != null) {
         console.setDefaultDomain(defaultDomain);
      }

      if (showCatDomain != null) {
         console.setShowCatDomain(convert(Boolean.class, showCatDomain, null));
      }

      if (remoteServers != null) {
         console.setRemoteServers(remoteServers);
      }

      return console;
   }

   @Override
   public ConsumerConfig buildConsumer(Node node) {
      ConsumerConfig consumer = new ConsumerConfig();

      return consumer;
   }

   @Override
   public Domain buildDomain(Node node) {
      String name = getAttribute(node, ATTR_NAME);
      String urlThreshold = getAttribute(node, ATTR_URL_THRESHOLD);
      String sqlThreshold = getAttribute(node, ATTR_SQL_THRESHOLD);
      String serviceThreshold = getAttribute(node, ATTR_SERVICE_THRESHOLD);

      Domain domain = new Domain(name);

      if (urlThreshold != null) {
         domain.setUrlThreshold(convert(Integer.class, urlThreshold, null));
      }

      if (sqlThreshold != null) {
         domain.setSqlThreshold(convert(Integer.class, sqlThreshold, null));
      }

      if (serviceThreshold != null) {
         domain.setServiceThreshold(convert(Integer.class, serviceThreshold, null));
      }

      return domain;
   }

   @Override
   public HdfsConfig buildHdfs(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String maxSize = getAttribute(node, ATTR_MAX_SIZE);
      String serverUri = getAttribute(node, ATTR_SERVER_URI);
      String baseDir = getAttribute(node, ATTR_BASE_DIR);

      HdfsConfig hdfs = new HdfsConfig(id);

      if (maxSize != null) {
         hdfs.setMaxSize(maxSize);
      }

      if (serverUri != null) {
         hdfs.setServerUri(serverUri);
      }

      if (baseDir != null) {
         hdfs.setBaseDir(baseDir);
      }

      return hdfs;
   }

   @Override
   public LongConfig buildLongConfig(Node node) {
      String defaultUrlThreshold = getAttribute(node, ATTR_DEFAULT_URL_THRESHOLD);
      String defaultSqlThreshold = getAttribute(node, ATTR_DEFAULT_SQL_THRESHOLD);
      String defaultServiceThreshold = getAttribute(node, ATTR_DEFAULT_SERVICE_THRESHOLD);

      LongConfig longConfig = new LongConfig();

      if (defaultUrlThreshold != null) {
         longConfig.setDefaultUrlThreshold(convert(Integer.class, defaultUrlThreshold, null));
      }

      if (defaultSqlThreshold != null) {
         longConfig.setDefaultSqlThreshold(convert(Integer.class, defaultSqlThreshold, null));
      }

      if (defaultServiceThreshold != null) {
         longConfig.setDefaultServiceThreshold(convert(Integer.class, defaultServiceThreshold, null));
      }

      return longConfig;
   }

   @Override
   public Property buildProperty(Node node) {
      String name = getAttribute(node, ATTR_NAME);
      String value = getAttribute(node, ATTR_VALUE);

      Property property = new Property(name);

      if (value != null) {
         property.setValue(value);
      }

      return property;
   }

   @Override
   public StorageConfig buildStorage(Node node) {
      String localBaseDir = getAttribute(node, ATTR_LOCAL_BASE_DIR);
      String hdfsDisabled = getAttribute(node, ATTR_HDFS_DISABLED);

      StorageConfig storage = new StorageConfig();

      if (localBaseDir != null) {
         storage.setLocalBaseDir(localBaseDir);
      }

      if (hdfsDisabled != null) {
         storage.setHdfsDisabled(convert(Boolean.class, hdfsDisabled, null));
      }

      return storage;
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   protected String getAttribute(Node node, String name) {
      Node attribute = node.getAttributes().getNamedItem(name);

      return attribute == null ? null : attribute.getNodeValue();
   }

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

   protected String getText(Node node) {
      if (node != null) {
         StringBuilder sb = new StringBuilder();
         NodeList children = node.getChildNodes();
         int len = children.getLength();

         for (int i = 0; i < len; i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {
               sb.append(child.getNodeValue());
            }
         }

         if (sb.length() != 0) {
            return sb.toString();
         }
      }

      return null;
   }
}
