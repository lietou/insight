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
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_MESSAGE;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_DURATION;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_ENTRY;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_PROBLEM_REPORT;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_SEGMENT;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_THREAD;

import com.dianping.cat.consumer.problem.model.IEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;
import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public class DefaultXmlBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(4096);

   private boolean m_compact;

   public DefaultXmlBuilder() {
      this(false);
   }

   public DefaultXmlBuilder(boolean compact) {
      m_compact = compact;
   }

   public String buildXml(IEntity<?> entity) {
      m_sb.setLength(0);
      m_sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
      entity.accept(this);
      return m_sb.toString();
   }

   protected void endTag(String name) {
      m_level--;

      indent();
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String escape(Object value) {
      return escape(value, false);
   }
   
   protected String escape(Object value, boolean text) {
      if (value == null) {
         return null;
      }

      String str = value.toString();
      int len = str.length();
      StringBuilder sb = new StringBuilder(len + 16);

      for (int i = 0; i < len; i++) {
         final char ch = str.charAt(i);

         switch (ch) {
         case '<':
            sb.append("&lt;");
            break;
         case '>':
            sb.append("&gt;");
            break;
         case '&':
            sb.append("&amp;");
            break;
         case '"':
            if (!text) {
               sb.append("&quot;");
               break;
            }
         default:
            sb.append(ch);
            break;
         }
      }

      return sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void startTag(String name) {
      startTag(name, false, null);
   }
   
   protected void startTag(String name, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, closed, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, false, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, Object text, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      if (dynamicAttributes != null) {
         for (java.util.Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            m_sb.append(' ').append(e.getKey()).append("=\"").append(escape(e.getValue())).append('"');
         }
      }

      if (text != null && closed) {
         m_sb.append('>');
         m_sb.append(escape(text, true));
         m_sb.append("</").append(name).append(">\r\n");
      } else {
         if (closed) {
            m_sb.append('/');
         } else {
            m_level++;
         }
   
         m_sb.append(">\r\n");
      }
   }

   private void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String toString(java.util.Date date, String format) {
      if (date != null) {
         return new java.text.SimpleDateFormat(format).format(date);
      } else {
         return null;
      }
   }

   @Override
   public void visitDuration(Duration duration) {
      startTag(ENTITY_DURATION, null, ATTR_VALUE, duration.getValue(), ATTR_COUNT, duration.getCount());

      if (!duration.getMessages().isEmpty()) {
         for (String message : duration.getMessages().toArray(new String[0])) {
            tagWithText(ELEMENT_MESSAGE, message);
         }
      }

      endTag(ENTITY_DURATION);
   }

   @Override
   public void visitEntry(Entry entry) {
      startTag(ENTITY_ENTRY, null, ATTR_TYPE, entry.getType(), ATTR_STATUS, entry.getStatus());

      if (!entry.getDurations().isEmpty()) {
         for (Duration duration : entry.getDurations().values().toArray(new Duration[0])) {
            visitDuration(duration);
         }
      }

      if (!entry.getThreads().isEmpty()) {
         for (JavaThread thread : entry.getThreads().values().toArray(new JavaThread[0])) {
            visitThread(thread);
         }
      }

      endTag(ENTITY_ENTRY);
   }

   @Override
   public void visitMachine(Machine machine) {
      startTag(ENTITY_MACHINE, null, ATTR_IP, machine.getIp());

      if (!machine.getEntries().isEmpty()) {
         for (Entry entry : machine.getEntries().toArray(new Entry[0])) {
            visitEntry(entry);
         }
      }

      endTag(ENTITY_MACHINE);
   }

   @Override
   public void visitProblemReport(ProblemReport problemReport) {
      startTag(ENTITY_PROBLEM_REPORT, null, ATTR_DOMAIN, problemReport.getDomain(), ATTR_STARTTIME, toString(problemReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(problemReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!problemReport.getDomainNames().isEmpty()) {
         for (String domain : problemReport.getDomainNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DOMAIN, domain);
         }
      }

      if (!problemReport.getIps().isEmpty()) {
         for (String ip : problemReport.getIps().toArray(new String[0])) {
            tagWithText(ELEMENT_IP, ip);
         }
      }

      if (!problemReport.getMachines().isEmpty()) {
         for (Machine machine : problemReport.getMachines().values().toArray(new Machine[0])) {
            visitMachine(machine);
         }
      }

      endTag(ENTITY_PROBLEM_REPORT);
   }

   @Override
   public void visitSegment(Segment segment) {
      startTag(ENTITY_SEGMENT, null, ATTR_ID, segment.getId(), ATTR_COUNT, segment.getCount());

      if (!segment.getMessages().isEmpty()) {
         for (String message : segment.getMessages().toArray(new String[0])) {
            tagWithText(ELEMENT_MESSAGE, message);
         }
      }

      endTag(ENTITY_SEGMENT);
   }

   @Override
   public void visitThread(JavaThread thread) {
      startTag(ENTITY_THREAD, null, ATTR_GROUP_NAME, thread.getGroupName(), ATTR_NAME, thread.getName(), ATTR_ID, thread.getId());

      if (!thread.getSegments().isEmpty()) {
         for (Segment segment : thread.getSegments().values().toArray(new Segment[0])) {
            visitSegment(segment);
         }
      }

      endTag(ENTITY_THREAD);
   }
}
