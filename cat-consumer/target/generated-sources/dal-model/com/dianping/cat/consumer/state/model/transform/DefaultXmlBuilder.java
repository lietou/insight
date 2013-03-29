package com.dianping.cat.consumer.state.model.transform;

import static com.dianping.cat.consumer.state.model.Constants.ATTR_AVGTPS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKTOTAL;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYAVG;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYCOUNT;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYSUM;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DUMP;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DUMPLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_MAXTPS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_NETWORKTIMEERROR;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_PIGEONTIMEERROR;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_SIZE;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TOTALLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ELEMENT_IP;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MESSAGE;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAIN;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAINS;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_STATE_REPORT;

import com.dianping.cat.consumer.state.model.IEntity;
import com.dianping.cat.consumer.state.model.IVisitor;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

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
   public void visitMachine(Machine machine) {
      startTag(ENTITY_MACHINE, null, ATTR_IP, machine.getIp(), ATTR_TOTAL, machine.getTotal(), ATTR_TOTALLOSS, machine.getTotalLoss(), ATTR_MAXTPS, toString(machine.getMaxTps(), "0.0"), ATTR_AVGTPS, toString(machine.getAvgTps(), "0.0"), ATTR_BLOCKTOTAL, machine.getBlockTotal(), ATTR_BLOCKLOSS, machine.getBlockLoss(), ATTR_BLOCKTIME, machine.getBlockTime(), ATTR_PIGEONTIMEERROR, machine.getPigeonTimeError(), ATTR_NETWORKTIMEERROR, machine.getNetworkTimeError(), ATTR_DUMP, machine.getDump(), ATTR_DUMPLOSS, machine.getDumpLoss(), ATTR_SIZE, toString(machine.getSize(), "0.0"), ATTR_DELAYSUM, toString(machine.getDelaySum(), "0.0"), ATTR_DELAYAVG, toString(machine.getDelayAvg(), "0.0"), ATTR_DELAYCOUNT, machine.getDelayCount());

      if (!machine.getProcessDomains().isEmpty()) {
         startTag(ENTITY_PROCESSDOMAINS);

         for (ProcessDomain processDomain : machine.getProcessDomains().values().toArray(new ProcessDomain[0])) {
            visitProcessDomain(processDomain);
         }

         endTag(ENTITY_PROCESSDOMAINS);
      }

      if (!machine.getMessages().isEmpty()) {
         for (Message message : machine.getMessages().values().toArray(new Message[0])) {
            visitMessage(message);
         }
      }

      endTag(ENTITY_MACHINE);
   }

   @Override
   public void visitMessage(Message message) {
      startTag(ENTITY_MESSAGE, true, null, ATTR_ID, message.getId(), ATTR_TIME, toString(message.getTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_TOTAL, message.getTotal(), ATTR_TOTALLOSS, message.getTotalLoss(), ATTR_DUMP, message.getDump(), ATTR_DUMPLOSS, message.getDumpLoss(), ATTR_SIZE, toString(message.getSize(), "0.0"), ATTR_DELAYSUM, toString(message.getDelaySum(), "0.0"), ATTR_DELAYCOUNT, message.getDelayCount(), ATTR_PIGEONTIMEERROR, message.getPigeonTimeError(), ATTR_NETWORKTIMEERROR, message.getNetworkTimeError(), ATTR_BLOCKTOTAL, message.getBlockTotal(), ATTR_BLOCKLOSS, message.getBlockLoss(), ATTR_BLOCKTIME, message.getBlockTime());
   }

   @Override
   public void visitProcessDomain(ProcessDomain processDomain) {
      startTag(ENTITY_PROCESSDOMAIN, null, ATTR_NAME, processDomain.getName());

      if (!processDomain.getIps().isEmpty()) {
         for (String ip : processDomain.getIps().toArray(new String[0])) {
            tagWithText(ELEMENT_IP, ip);
         }
      }

      endTag(ENTITY_PROCESSDOMAIN);
   }

   @Override
   public void visitStateReport(StateReport stateReport) {
      startTag(ENTITY_STATE_REPORT, null, ATTR_DOMAIN, stateReport.getDomain(), ATTR_STARTTIME, toString(stateReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(stateReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!stateReport.getMachines().isEmpty()) {
         for (Machine machine : stateReport.getMachines().values().toArray(new Machine[0])) {
            visitMachine(machine);
         }
      }

      endTag(ENTITY_STATE_REPORT);
   }
}
