package com.dianping.cat.consumer.state.model.entity;

import static com.dianping.cat.consumer.state.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MACHINE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.state.model.BaseEntity;
import com.dianping.cat.consumer.state.model.IVisitor;

public class Machine extends BaseEntity<Machine> {
   private String m_ip;

   private Map<String, ProcessDomain> m_processDomains = new LinkedHashMap<String, ProcessDomain>();

   private Map<Long, Message> m_messages = new LinkedHashMap<Long, Message>();

   private long m_total;

   private long m_totalLoss;

   private double m_maxTps;

   private double m_avgTps;

   private long m_blockTotal;

   private long m_blockLoss;

   private long m_blockTime;

   private long m_pigeonTimeError;

   private long m_networkTimeError;

   private long m_dump;

   private long m_dumpLoss;

   private double m_size;

   private double m_delaySum;

   private double m_delayAvg;

   private int m_delayCount;

   public Machine() {
   }

   public Machine(String ip) {
      m_ip = ip;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMachine(this);
   }

   public Machine addMessage(Message message) {
      m_messages.put(message.getId(), message);
      return this;
   }

   public Machine addProcessDomain(ProcessDomain processDomain) {
      m_processDomains.put(processDomain.getName(), processDomain);
      return this;
   }

   public Message findMessage(Long id) {
      return m_messages.get(id);
   }

   public ProcessDomain findProcessDomain(String name) {
      return m_processDomains.get(name);
   }

   public Message findOrCreateMessage(Long id) {
      Message message = m_messages.get(id);

      if (message == null) {
         synchronized (m_messages) {
            message = m_messages.get(id);

            if (message == null) {
               message = new Message(id);
               m_messages.put(id, message);
            }
         }
      }

      return message;
   }

   public ProcessDomain findOrCreateProcessDomain(String name) {
      ProcessDomain processDomain = m_processDomains.get(name);

      if (processDomain == null) {
         synchronized (m_processDomains) {
            processDomain = m_processDomains.get(name);

            if (processDomain == null) {
               processDomain = new ProcessDomain(name);
               m_processDomains.put(name, processDomain);
            }
         }
      }

      return processDomain;
   }

   public double getAvgTps() {
      return m_avgTps;
   }

   public long getBlockLoss() {
      return m_blockLoss;
   }

   public long getBlockTime() {
      return m_blockTime;
   }

   public long getBlockTotal() {
      return m_blockTotal;
   }

   public double getDelayAvg() {
      return m_delayAvg;
   }

   public int getDelayCount() {
      return m_delayCount;
   }

   public double getDelaySum() {
      return m_delaySum;
   }

   public long getDump() {
      return m_dump;
   }

   public long getDumpLoss() {
      return m_dumpLoss;
   }

   public String getIp() {
      return m_ip;
   }

   public double getMaxTps() {
      return m_maxTps;
   }

   public Map<Long, Message> getMessages() {
      return m_messages;
   }

   public long getNetworkTimeError() {
      return m_networkTimeError;
   }

   public long getPigeonTimeError() {
      return m_pigeonTimeError;
   }

   public Map<String, ProcessDomain> getProcessDomains() {
      return m_processDomains;
   }

   public double getSize() {
      return m_size;
   }

   public long getTotal() {
      return m_total;
   }

   public long getTotalLoss() {
      return m_totalLoss;
   }

   @Override
   public void mergeAttributes(Machine other) {
      assertAttributeEquals(other, ENTITY_MACHINE, ATTR_IP, m_ip, other.getIp());

      if (other.getTotal() != 0) {
         m_total = other.getTotal();
      }

      if (other.getTotalLoss() != 0) {
         m_totalLoss = other.getTotalLoss();
      }

      if (other.getMaxTps() - 1e6 < 0) {
         m_maxTps = other.getMaxTps();
      }

      if (other.getAvgTps() - 1e6 < 0) {
         m_avgTps = other.getAvgTps();
      }

      if (other.getBlockTotal() != 0) {
         m_blockTotal = other.getBlockTotal();
      }

      if (other.getBlockLoss() != 0) {
         m_blockLoss = other.getBlockLoss();
      }

      if (other.getBlockTime() != 0) {
         m_blockTime = other.getBlockTime();
      }

      if (other.getPigeonTimeError() != 0) {
         m_pigeonTimeError = other.getPigeonTimeError();
      }

      if (other.getNetworkTimeError() != 0) {
         m_networkTimeError = other.getNetworkTimeError();
      }

      if (other.getDump() != 0) {
         m_dump = other.getDump();
      }

      if (other.getDumpLoss() != 0) {
         m_dumpLoss = other.getDumpLoss();
      }

      if (other.getSize() - 1e6 < 0) {
         m_size = other.getSize();
      }

      if (other.getDelaySum() - 1e6 < 0) {
         m_delaySum = other.getDelaySum();
      }

      if (other.getDelayAvg() - 1e6 < 0) {
         m_delayAvg = other.getDelayAvg();
      }

      if (other.getDelayCount() != 0) {
         m_delayCount = other.getDelayCount();
      }
   }

   public boolean removeMessage(Long id) {
      if (m_messages.containsKey(id)) {
         m_messages.remove(id);
         return true;
      }

      return false;
   }

   public boolean removeProcessDomain(String name) {
      if (m_processDomains.containsKey(name)) {
         m_processDomains.remove(name);
         return true;
      }

      return false;
   }

   public Machine setAvgTps(double avgTps) {
      m_avgTps = avgTps;
      return this;
   }

   public Machine setBlockLoss(long blockLoss) {
      m_blockLoss = blockLoss;
      return this;
   }

   public Machine setBlockTime(long blockTime) {
      m_blockTime = blockTime;
      return this;
   }

   public Machine setBlockTotal(long blockTotal) {
      m_blockTotal = blockTotal;
      return this;
   }

   public Machine setDelayAvg(double delayAvg) {
      m_delayAvg = delayAvg;
      return this;
   }

   public Machine setDelayCount(int delayCount) {
      m_delayCount = delayCount;
      return this;
   }

   public Machine setDelaySum(double delaySum) {
      m_delaySum = delaySum;
      return this;
   }

   public Machine setDump(long dump) {
      m_dump = dump;
      return this;
   }

   public Machine setDumpLoss(long dumpLoss) {
      m_dumpLoss = dumpLoss;
      return this;
   }

   public Machine setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public Machine setMaxTps(double maxTps) {
      m_maxTps = maxTps;
      return this;
   }

   public Machine setNetworkTimeError(long networkTimeError) {
      m_networkTimeError = networkTimeError;
      return this;
   }

   public Machine setPigeonTimeError(long pigeonTimeError) {
      m_pigeonTimeError = pigeonTimeError;
      return this;
   }

   public Machine setSize(double size) {
      m_size = size;
      return this;
   }

   public Machine setTotal(long total) {
      m_total = total;
      return this;
   }

   public Machine setTotalLoss(long totalLoss) {
      m_totalLoss = totalLoss;
      return this;
   }

}
