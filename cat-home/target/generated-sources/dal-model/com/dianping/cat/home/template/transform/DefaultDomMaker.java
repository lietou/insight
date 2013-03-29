package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ATTR_ALARM;
import static com.dianping.cat.home.template.Constants.ATTR_ALARM_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_BASEURL;
import static com.dianping.cat.home.template.Constants.ATTR_ID;
import static com.dianping.cat.home.template.Constants.ATTR_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_MAX;
import static com.dianping.cat.home.template.Constants.ATTR_MIN;
import static com.dianping.cat.home.template.Constants.ATTR_TYPE;
import static com.dianping.cat.home.template.Constants.ATTR_VALUE;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public Connection buildConnection(Node node) {
      String baseUrl = getAttribute(node, ATTR_BASEURL);

      Connection connection = new Connection();

      if (baseUrl != null) {
         connection.setBaseUrl(baseUrl);
      }

      return connection;
   }

   @Override
   public Duration buildDuration(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String min = getAttribute(node, ATTR_MIN);
      String max = getAttribute(node, ATTR_MAX);
      String interval = getAttribute(node, ATTR_INTERVAL);
      String alarm = getAttribute(node, ATTR_ALARM);
      String alarmInterval = getAttribute(node, ATTR_ALARM_INTERVAL);

      Duration duration = new Duration(id);

      if (min != null) {
         duration.setMin(convert(Integer.class, min, null));
      }

      if (max != null) {
         duration.setMax(convert(Integer.class, max, null));
      }

      if (interval != null) {
         duration.setInterval(convert(Integer.class, interval, null));
      }

      if (alarm != null) {
         duration.setAlarm(alarm);
      }

      if (alarmInterval != null) {
         duration.setAlarmInterval(convert(Integer.class, alarmInterval, null));
      }

      return duration;
   }

   @Override
   public Param buildParam(Node node) {
      String type = getAttribute(node, ATTR_TYPE);
      String value = getAttribute(node, ATTR_VALUE);

      Param param = new Param(type);

      if (value != null) {
         param.setValue(value);
      }

      return param;
   }

   @Override
   public ThresholdTemplate buildThresholdTemplate(Node node) {
      ThresholdTemplate thresholdTemplate = new ThresholdTemplate();

      return thresholdTemplate;
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
}
