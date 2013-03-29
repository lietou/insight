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
import static com.dianping.cat.consumer.state.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MESSAGES;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAINS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.state.model.IEntity;
import com.dianping.cat.consumer.state.model.IVisitor;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultJsonBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(2048);

   private boolean m_compact;

   public DefaultJsonBuilder() {
      this(false);
   }

   public DefaultJsonBuilder(boolean compact) {
      m_compact = compact;
   }

   protected void arrayBegin(String name) {
      indent();
      m_sb.append('"').append(name).append(m_compact ? "\":[" : "\": [\r\n");
      m_level++;
   }

   protected void arrayEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append("],").append(m_compact ? "" : "\r\n");
   }

   protected void attributes(Map<String, String> dynamicAttributes, Object... nameValues) {
      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            if (attrValue instanceof List) {
               @SuppressWarnings("unchecked")
               List<Object> list = (List<Object>) attrValue;

               if (!list.isEmpty()) {
                  indent();
                  m_sb.append('"').append(attrName).append(m_compact ? "\":[" : "\": [");

                  for (Object item : list) {
                     m_sb.append(' ');
                     toString(m_sb, item);
                     m_sb.append(',');
                  }

                  m_sb.setLength(m_sb.length() - 1);
                  m_sb.append(m_compact ? "]," : " ],\r\n");
               }
            } else {
               if (m_compact) {
                  m_sb.append('"').append(attrName).append("\":");
                  toString(m_sb, attrValue);
                  m_sb.append(",");
               } else {
                  indent();
                  m_sb.append('"').append(attrName).append("\": ");
                  toString(m_sb, attrValue);
                  m_sb.append(",\r\n");
               }
            }
         }
      }

      if (dynamicAttributes != null) {
         for (Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            if (m_compact) {
               m_sb.append('"').append(e.getKey()).append("\":");
               toString(m_sb, e.getValue());
               m_sb.append(",");
            } else {
               indent();
               m_sb.append('"').append(e.getKey()).append("\": ");
               toString(m_sb, e.getValue());
               m_sb.append(",\r\n");
            }
         }
      }
   }

   public String buildJson(IEntity<?> entity) {
      m_sb.setLength(0);
      entity.accept(this);
      return m_sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void objectBegin(String name) {
      indent();

      if (name == null) {
         m_sb.append("{").append(m_compact ? "" : "\r\n");
      } else {
         m_sb.append('"').append(name).append(m_compact ? "\":{" : "\": {\r\n");
      }

      m_level++;
   }

   protected void objectEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append(m_compact ? "}," : "},\r\n");
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

   protected void toString(StringBuilder sb, Object value) {
      if (value instanceof String) {
         sb.append('"').append(value).append('"');
      } else if (value instanceof Boolean || value instanceof Number) {
         sb.append(value);
      } else {
         sb.append('"').append(value).append('"');
      }
   }

   protected void trimComma() {
      int len = m_sb.length();

      if (m_compact) {
         if (len > 1 && m_sb.charAt(len - 1) == ',') {
            m_sb.replace(len - 1, len, "");
         }
      } else {
         if (len > 3 && m_sb.charAt(len - 3) == ',') {
            m_sb.replace(len - 3, len - 2, "");
         }
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      attributes(null, ATTR_IP, machine.getIp(), ATTR_TOTAL, machine.getTotal(), ATTR_TOTALLOSS, machine.getTotalLoss(), ATTR_MAXTPS, toString(machine.getMaxTps(), "0.0"), ATTR_AVGTPS, toString(machine.getAvgTps(), "0.0"), ATTR_BLOCKTOTAL, machine.getBlockTotal(), ATTR_BLOCKLOSS, machine.getBlockLoss(), ATTR_BLOCKTIME, machine.getBlockTime(), ATTR_PIGEONTIMEERROR, machine.getPigeonTimeError(), ATTR_NETWORKTIMEERROR, machine.getNetworkTimeError(), ATTR_DUMP, machine.getDump(), ATTR_DUMPLOSS, machine.getDumpLoss(), ATTR_SIZE, toString(machine.getSize(), "0.0"), ATTR_DELAYSUM, toString(machine.getDelaySum(), "0.0"), ATTR_DELAYAVG, toString(machine.getDelayAvg(), "0.0"), ATTR_DELAYCOUNT, machine.getDelayCount());

      if (!machine.getProcessDomains().isEmpty()) {
         objectBegin(ENTITY_PROCESSDOMAINS);

         for (Map.Entry<String, ProcessDomain> e : machine.getProcessDomains().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitProcessDomain(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_PROCESSDOMAINS);
      }

      if (!machine.getMessages().isEmpty()) {
         objectBegin(ENTITY_MESSAGES);

         for (Map.Entry<Long, Message> e : machine.getMessages().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitMessage(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_MESSAGES);
      }
   }

   @Override
   public void visitMessage(Message message) {
      attributes(null, ATTR_ID, message.getId(), ATTR_TIME, toString(message.getTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_TOTAL, message.getTotal(), ATTR_TOTALLOSS, message.getTotalLoss(), ATTR_DUMP, message.getDump(), ATTR_DUMPLOSS, message.getDumpLoss(), ATTR_SIZE, toString(message.getSize(), "0.0"), ATTR_DELAYSUM, toString(message.getDelaySum(), "0.0"), ATTR_DELAYCOUNT, message.getDelayCount(), ATTR_PIGEONTIMEERROR, message.getPigeonTimeError(), ATTR_NETWORKTIMEERROR, message.getNetworkTimeError(), ATTR_BLOCKTOTAL, message.getBlockTotal(), ATTR_BLOCKLOSS, message.getBlockLoss(), ATTR_BLOCKTIME, message.getBlockTime());
   }

   @Override
   public void visitProcessDomain(ProcessDomain processDomain) {
      attributes(null, ATTR_NAME, processDomain.getName());

      if (!processDomain.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : processDomain.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }
   }

   @Override
   public void visitStateReport(StateReport stateReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, stateReport.getDomain(), ATTR_STARTTIME, toString(stateReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(stateReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!stateReport.getMachines().isEmpty()) {
         objectBegin(ENTITY_MACHINES);

         for (Map.Entry<String, Machine> e : stateReport.getMachines().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitMachine(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_MACHINES);
      }

      objectEnd(null);
      trimComma();
   }
}
