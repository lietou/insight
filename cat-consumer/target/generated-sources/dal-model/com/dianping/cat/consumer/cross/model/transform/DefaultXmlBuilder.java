package com.dianping.cat.consumer.cross.model.transform;

import static com.dianping.cat.consumer.cross.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ROLE;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_TPS;
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_IP;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_CROSS_REPORT;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_LOCAL;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_REMOTE;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_TYPE;

import com.dianping.cat.consumer.cross.model.IEntity;
import com.dianping.cat.consumer.cross.model.IVisitor;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

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

   protected String toString(Number number, String format) {
      if (number != null) {
         return new java.text.DecimalFormat(format).format(number);
      } else {
         return null;
      }
   }

   @Override
   public void visitCrossReport(CrossReport crossReport) {
      startTag(ENTITY_CROSS_REPORT, null, ATTR_DOMAIN, crossReport.getDomain(), ATTR_STARTTIME, toString(crossReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(crossReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!crossReport.getDomainNames().isEmpty()) {
         for (String domain : crossReport.getDomainNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DOMAIN, domain);
         }
      }

      if (!crossReport.getIps().isEmpty()) {
         for (String ip : crossReport.getIps().toArray(new String[0])) {
            tagWithText(ELEMENT_IP, ip);
         }
      }

      if (!crossReport.getLocals().isEmpty()) {
         for (Local local : crossReport.getLocals().values().toArray(new Local[0])) {
            visitLocal(local);
         }
      }

      endTag(ENTITY_CROSS_REPORT);
   }

   @Override
   public void visitLocal(Local local) {
      startTag(ENTITY_LOCAL, null, ATTR_ID, local.getId());

      if (!local.getRemotes().isEmpty()) {
         for (Remote remote : local.getRemotes().values().toArray(new Remote[0])) {
            visitRemote(remote);
         }
      }

      endTag(ENTITY_LOCAL);
   }

   @Override
   public void visitName(Name name) {
      startTag(ENTITY_NAME, true, null, ATTR_ID, name.getId(), ATTR_TOTALCOUNT, name.getTotalCount(), ATTR_FAILCOUNT, name.getFailCount(), ATTR_FAILPERCENT, toString(name.getFailPercent(), "0.00"), ATTR_AVG, toString(name.getAvg(), "0.00"), ATTR_SUM, toString(name.getSum(), "0.00"), ATTR_TPS, toString(name.getTps(), "0.00"));
   }

   @Override
   public void visitRemote(Remote remote) {
      startTag(ENTITY_REMOTE, null, ATTR_ID, remote.getId(), ATTR_ROLE, remote.getRole());

      if (remote.getType() != null) {
         visitType(remote.getType());
      }

      endTag(ENTITY_REMOTE);
   }

   @Override
   public void visitType(Type type) {
      startTag(ENTITY_TYPE, null, ATTR_ID, type.getId(), ATTR_TOTALCOUNT, type.getTotalCount(), ATTR_FAILCOUNT, type.getFailCount(), ATTR_FAILPERCENT, toString(type.getFailPercent(), "0.00"), ATTR_AVG, toString(type.getAvg(), "0.00"), ATTR_SUM, toString(type.getSum(), "0.00"), ATTR_TPS, toString(type.getTps(), "0.00"));

      if (!type.getNames().isEmpty()) {
         for (Name name : type.getNames().values().toArray(new Name[0])) {
            visitName(name);
         }
      }

      endTag(ENTITY_TYPE);
   }
}
