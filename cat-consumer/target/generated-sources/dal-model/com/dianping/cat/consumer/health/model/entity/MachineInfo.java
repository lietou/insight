package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class MachineInfo extends BaseEntity<MachineInfo> {
   private int m_numbers;

   private double m_avgLoad;

   private double m_avgMaxLoad;

   private int m_avgLoadCount;

   private double m_avgLoadSum;

   private String m_avgMaxLoadMachine;

   private double m_avgOldgc;

   private double m_avgMaxOldgc;

   private int m_avgOldgcCount;

   private double m_avgOldgcSum;

   private String m_avgMaxOldgcMachine;

   private double m_avgHttp;

   private double m_avgMaxHttp;

   private int m_avgHttpCount;

   private double m_avgHttpSum;

   private String m_avgMaxHttpMachine;

   private double m_avgPigeon;

   private double m_avgMaxPigeon;

   private int m_avgPigeonCount;

   private double m_avgPigeonSum;

   private String m_avgMaxPigeonMachine;

   private double m_avgMemoryUsed;

   private double m_avgMaxMemoryUsed;

   private int m_avgMemoryUsedCount;

   private double m_avgMemoryUsedSum;

   private String m_avgMaxMemoryUsedMachine;

   public MachineInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMachineInfo(this);
   }

   public double getAvgHttp() {
      return m_avgHttp;
   }

   public int getAvgHttpCount() {
      return m_avgHttpCount;
   }

   public double getAvgHttpSum() {
      return m_avgHttpSum;
   }

   public double getAvgLoad() {
      return m_avgLoad;
   }

   public int getAvgLoadCount() {
      return m_avgLoadCount;
   }

   public double getAvgLoadSum() {
      return m_avgLoadSum;
   }

   public double getAvgMaxHttp() {
      return m_avgMaxHttp;
   }

   public String getAvgMaxHttpMachine() {
      return m_avgMaxHttpMachine;
   }

   public double getAvgMaxLoad() {
      return m_avgMaxLoad;
   }

   public String getAvgMaxLoadMachine() {
      return m_avgMaxLoadMachine;
   }

   public double getAvgMaxMemoryUsed() {
      return m_avgMaxMemoryUsed;
   }

   public String getAvgMaxMemoryUsedMachine() {
      return m_avgMaxMemoryUsedMachine;
   }

   public double getAvgMaxOldgc() {
      return m_avgMaxOldgc;
   }

   public String getAvgMaxOldgcMachine() {
      return m_avgMaxOldgcMachine;
   }

   public double getAvgMaxPigeon() {
      return m_avgMaxPigeon;
   }

   public String getAvgMaxPigeonMachine() {
      return m_avgMaxPigeonMachine;
   }

   public double getAvgMemoryUsed() {
      return m_avgMemoryUsed;
   }

   public int getAvgMemoryUsedCount() {
      return m_avgMemoryUsedCount;
   }

   public double getAvgMemoryUsedSum() {
      return m_avgMemoryUsedSum;
   }

   public double getAvgOldgc() {
      return m_avgOldgc;
   }

   public int getAvgOldgcCount() {
      return m_avgOldgcCount;
   }

   public double getAvgOldgcSum() {
      return m_avgOldgcSum;
   }

   public double getAvgPigeon() {
      return m_avgPigeon;
   }

   public int getAvgPigeonCount() {
      return m_avgPigeonCount;
   }

   public double getAvgPigeonSum() {
      return m_avgPigeonSum;
   }

   public int getNumbers() {
      return m_numbers;
   }

   @Override
   public void mergeAttributes(MachineInfo other) {
      if (other.getNumbers() != 0) {
         m_numbers = other.getNumbers();
      }

      if (other.getAvgLoad() - 1e6 < 0) {
         m_avgLoad = other.getAvgLoad();
      }

      if (other.getAvgMaxLoad() - 1e6 < 0) {
         m_avgMaxLoad = other.getAvgMaxLoad();
      }

      if (other.getAvgLoadCount() != 0) {
         m_avgLoadCount = other.getAvgLoadCount();
      }

      if (other.getAvgLoadSum() - 1e6 < 0) {
         m_avgLoadSum = other.getAvgLoadSum();
      }

      if (other.getAvgMaxLoadMachine() != null) {
         m_avgMaxLoadMachine = other.getAvgMaxLoadMachine();
      }

      if (other.getAvgOldgc() - 1e6 < 0) {
         m_avgOldgc = other.getAvgOldgc();
      }

      if (other.getAvgMaxOldgc() - 1e6 < 0) {
         m_avgMaxOldgc = other.getAvgMaxOldgc();
      }

      if (other.getAvgOldgcCount() != 0) {
         m_avgOldgcCount = other.getAvgOldgcCount();
      }

      if (other.getAvgOldgcSum() - 1e6 < 0) {
         m_avgOldgcSum = other.getAvgOldgcSum();
      }

      if (other.getAvgMaxOldgcMachine() != null) {
         m_avgMaxOldgcMachine = other.getAvgMaxOldgcMachine();
      }

      if (other.getAvgHttp() - 1e6 < 0) {
         m_avgHttp = other.getAvgHttp();
      }

      if (other.getAvgMaxHttp() - 1e6 < 0) {
         m_avgMaxHttp = other.getAvgMaxHttp();
      }

      if (other.getAvgHttpCount() != 0) {
         m_avgHttpCount = other.getAvgHttpCount();
      }

      if (other.getAvgHttpSum() - 1e6 < 0) {
         m_avgHttpSum = other.getAvgHttpSum();
      }

      if (other.getAvgMaxHttpMachine() != null) {
         m_avgMaxHttpMachine = other.getAvgMaxHttpMachine();
      }

      if (other.getAvgPigeon() - 1e6 < 0) {
         m_avgPigeon = other.getAvgPigeon();
      }

      if (other.getAvgMaxPigeon() - 1e6 < 0) {
         m_avgMaxPigeon = other.getAvgMaxPigeon();
      }

      if (other.getAvgPigeonCount() != 0) {
         m_avgPigeonCount = other.getAvgPigeonCount();
      }

      if (other.getAvgPigeonSum() - 1e6 < 0) {
         m_avgPigeonSum = other.getAvgPigeonSum();
      }

      if (other.getAvgMaxPigeonMachine() != null) {
         m_avgMaxPigeonMachine = other.getAvgMaxPigeonMachine();
      }

      if (other.getAvgMemoryUsed() - 1e6 < 0) {
         m_avgMemoryUsed = other.getAvgMemoryUsed();
      }

      if (other.getAvgMaxMemoryUsed() - 1e6 < 0) {
         m_avgMaxMemoryUsed = other.getAvgMaxMemoryUsed();
      }

      if (other.getAvgMemoryUsedCount() != 0) {
         m_avgMemoryUsedCount = other.getAvgMemoryUsedCount();
      }

      if (other.getAvgMemoryUsedSum() - 1e6 < 0) {
         m_avgMemoryUsedSum = other.getAvgMemoryUsedSum();
      }

      if (other.getAvgMaxMemoryUsedMachine() != null) {
         m_avgMaxMemoryUsedMachine = other.getAvgMaxMemoryUsedMachine();
      }
   }

   public MachineInfo setAvgHttp(double avgHttp) {
      m_avgHttp = avgHttp;
      return this;
   }

   public MachineInfo setAvgHttpCount(int avgHttpCount) {
      m_avgHttpCount = avgHttpCount;
      return this;
   }

   public MachineInfo setAvgHttpSum(double avgHttpSum) {
      m_avgHttpSum = avgHttpSum;
      return this;
   }

   public MachineInfo setAvgLoad(double avgLoad) {
      m_avgLoad = avgLoad;
      return this;
   }

   public MachineInfo setAvgLoadCount(int avgLoadCount) {
      m_avgLoadCount = avgLoadCount;
      return this;
   }

   public MachineInfo setAvgLoadSum(double avgLoadSum) {
      m_avgLoadSum = avgLoadSum;
      return this;
   }

   public MachineInfo setAvgMaxHttp(double avgMaxHttp) {
      m_avgMaxHttp = avgMaxHttp;
      return this;
   }

   public MachineInfo setAvgMaxHttpMachine(String avgMaxHttpMachine) {
      m_avgMaxHttpMachine = avgMaxHttpMachine;
      return this;
   }

   public MachineInfo setAvgMaxLoad(double avgMaxLoad) {
      m_avgMaxLoad = avgMaxLoad;
      return this;
   }

   public MachineInfo setAvgMaxLoadMachine(String avgMaxLoadMachine) {
      m_avgMaxLoadMachine = avgMaxLoadMachine;
      return this;
   }

   public MachineInfo setAvgMaxMemoryUsed(double avgMaxMemoryUsed) {
      m_avgMaxMemoryUsed = avgMaxMemoryUsed;
      return this;
   }

   public MachineInfo setAvgMaxMemoryUsedMachine(String avgMaxMemoryUsedMachine) {
      m_avgMaxMemoryUsedMachine = avgMaxMemoryUsedMachine;
      return this;
   }

   public MachineInfo setAvgMaxOldgc(double avgMaxOldgc) {
      m_avgMaxOldgc = avgMaxOldgc;
      return this;
   }

   public MachineInfo setAvgMaxOldgcMachine(String avgMaxOldgcMachine) {
      m_avgMaxOldgcMachine = avgMaxOldgcMachine;
      return this;
   }

   public MachineInfo setAvgMaxPigeon(double avgMaxPigeon) {
      m_avgMaxPigeon = avgMaxPigeon;
      return this;
   }

   public MachineInfo setAvgMaxPigeonMachine(String avgMaxPigeonMachine) {
      m_avgMaxPigeonMachine = avgMaxPigeonMachine;
      return this;
   }

   public MachineInfo setAvgMemoryUsed(double avgMemoryUsed) {
      m_avgMemoryUsed = avgMemoryUsed;
      return this;
   }

   public MachineInfo setAvgMemoryUsedCount(int avgMemoryUsedCount) {
      m_avgMemoryUsedCount = avgMemoryUsedCount;
      return this;
   }

   public MachineInfo setAvgMemoryUsedSum(double avgMemoryUsedSum) {
      m_avgMemoryUsedSum = avgMemoryUsedSum;
      return this;
   }

   public MachineInfo setAvgOldgc(double avgOldgc) {
      m_avgOldgc = avgOldgc;
      return this;
   }

   public MachineInfo setAvgOldgcCount(int avgOldgcCount) {
      m_avgOldgcCount = avgOldgcCount;
      return this;
   }

   public MachineInfo setAvgOldgcSum(double avgOldgcSum) {
      m_avgOldgcSum = avgOldgcSum;
      return this;
   }

   public MachineInfo setAvgPigeon(double avgPigeon) {
      m_avgPigeon = avgPigeon;
      return this;
   }

   public MachineInfo setAvgPigeonCount(int avgPigeonCount) {
      m_avgPigeonCount = avgPigeonCount;
      return this;
   }

   public MachineInfo setAvgPigeonSum(double avgPigeonSum) {
      m_avgPigeonSum = avgPigeonSum;
      return this;
   }

   public MachineInfo setNumbers(int numbers) {
      m_numbers = numbers;
      return this;
   }

}
