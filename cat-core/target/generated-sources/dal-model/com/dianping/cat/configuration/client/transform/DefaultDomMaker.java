package com.dianping.cat.configuration.client.transform;

import static com.dianping.cat.configuration.client.Constants.ATTR_DUMP_LOCKED;
import static com.dianping.cat.configuration.client.Constants.ATTR_ENABLED;
import static com.dianping.cat.configuration.client.Constants.ATTR_ID;
import static com.dianping.cat.configuration.client.Constants.ATTR_IP;
import static com.dianping.cat.configuration.client.Constants.ATTR_MAX_THREADS;
import static com.dianping.cat.configuration.client.Constants.ATTR_MODE;
import static com.dianping.cat.configuration.client.Constants.ATTR_NAME;
import static com.dianping.cat.configuration.client.Constants.ATTR_PORT;

import static com.dianping.cat.configuration.client.Constants.ELEMENT_BASE_LOG_DIR;

import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.configuration.client.entity.Bind;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Domain;
import com.dianping.cat.configuration.client.entity.Property;
import com.dianping.cat.configuration.client.entity.Server;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public Bind buildBind(Node node) {
      String ip = getAttribute(node, ATTR_IP);
      String port = getAttribute(node, ATTR_PORT);

      Bind bind = new Bind();

      if (ip != null) {
         bind.setIp(ip);
      }

      if (port != null) {
         bind.setPort(convert(Integer.class, port, 0));
      }

      return bind;
   }

   @Override
   public ClientConfig buildConfig(Node node) {
      String mode = getAttribute(node, ATTR_MODE);
      String enabled = getAttribute(node, ATTR_ENABLED);
      String dumpLocked = getAttribute(node, ATTR_DUMP_LOCKED);
      String baseLogDir = getText(getChildTagNode(node, ELEMENT_BASE_LOG_DIR));

      ClientConfig config = new ClientConfig();

      if (mode != null) {
         config.setMode(mode);
      }

      if (enabled != null) {
         config.setEnabled(convert(Boolean.class, enabled, null));
      }

      if (dumpLocked != null) {
         config.setDumpLocked(convert(Boolean.class, dumpLocked, null));
      }

      if (baseLogDir != null) {
         config.setBaseLogDir(baseLogDir);
      }

      Map<String, String> dynamicAttributes = config.getDynamicAttributes();
      NamedNodeMap attributes = node.getAttributes();
      int length = attributes == null ? 0 : attributes.getLength();

      for (int i = 0; i < length; i++) {
         Node item = attributes.item(i);

         dynamicAttributes.put(item.getNodeName(), item.getNodeValue());
      }

      dynamicAttributes.remove(ATTR_MODE);
      dynamicAttributes.remove(ATTR_ENABLED);
      dynamicAttributes.remove(ATTR_DUMP_LOCKED);

      return config;
   }

   @Override
   public Domain buildDomain(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String ip = getAttribute(node, ATTR_IP);
      String maxThreads = getAttribute(node, ATTR_MAX_THREADS);
      String enabled = getAttribute(node, ATTR_ENABLED);

      Domain domain = new Domain(id);

      if (ip != null) {
         domain.setIp(ip);
      }

      if (maxThreads != null) {
         domain.setMaxThreads(convert(Integer.class, maxThreads, 0));
      }

      if (enabled != null) {
         domain.setEnabled(convert(Boolean.class, enabled, null));
      }

      return domain;
   }

   @Override
   public Property buildProperty(Node node) {
      String name = getAttribute(node, ATTR_NAME);

      Property property = new Property(name);

      property.setText(getText(node));

      return property;
   }

   @Override
   public Server buildServer(Node node) {
      String ip = getAttribute(node, ATTR_IP);
      String port = getAttribute(node, ATTR_PORT);
      String enabled = getAttribute(node, ATTR_ENABLED);

      Server server = new Server(ip);

      if (port != null) {
         server.setPort(convert(Integer.class, port, null));
      }

      if (enabled != null) {
         server.setEnabled(convert(Boolean.class, enabled, null));
      }

      return server;
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
