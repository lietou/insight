package com.dianping.cat.consumer.problem.model.transform;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_GROUP_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STATUS;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_VALUE;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public Duration buildDuration(Node node) {
      String value = getAttribute(node, ATTR_VALUE);
      String count = getAttribute(node, ATTR_COUNT);

      Duration duration = new Duration(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         duration.setCount(convert(Integer.class, count, 0));
      }

      return duration;
   }

   @Override
   public Entry buildEntry(Node node) {
      String type = getAttribute(node, ATTR_TYPE);
      String status = getAttribute(node, ATTR_STATUS);

      Entry entry = new Entry();

      if (type != null) {
         entry.setType(type);
      }

      if (status != null) {
         entry.setStatus(status);
      }

      return entry;
   }

   @Override
   public String buildIp(Node node) {
      return getText(node);
   }

   @Override
   public Machine buildMachine(Node node) {
      String ip = getAttribute(node, ATTR_IP);

      Machine machine = new Machine(ip);

      return machine;
   }

   @Override
   public String buildMessage(Node node) {
      return getText(node);
   }

   @Override
   public ProblemReport buildProblemReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

      ProblemReport problemReport = new ProblemReport(domain);

      if (startTime != null) {
         problemReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         problemReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return problemReport;
   }

   @Override
   public Segment buildSegment(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String count = getAttribute(node, ATTR_COUNT);

      Segment segment = new Segment(id == null ? null : convert(Integer.class, id, null));

      if (count != null) {
         segment.setCount(convert(Integer.class, count, 0));
      }

      return segment;
   }

   @Override
   public JavaThread buildThread(Node node) {
      String groupName = getAttribute(node, ATTR_GROUP_NAME);
      String name = getAttribute(node, ATTR_NAME);
      String id = getAttribute(node, ATTR_ID);

      JavaThread thread = new JavaThread(id);

      if (groupName != null) {
         thread.setGroupName(groupName);
      }

      if (name != null) {
         thread.setName(name);
      }

      return thread;
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

   protected java.util.Date toDate(String str, String format, java.util.Date defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
      }
   }
}
