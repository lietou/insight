package com.dianping.cat.consumer.heartbeat.model.transform;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_DISK;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_PERIOD;
import java.util.Stack;

import com.dianping.cat.consumer.heartbeat.model.IVisitor;
import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private HeartbeatReport m_heartbeatReport;

   public DefaultMerger(HeartbeatReport heartbeatReport) {
      m_heartbeatReport = heartbeatReport;
   }

   public HeartbeatReport getHeartbeatReport() {
      return m_heartbeatReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeDisk(Disk old, Disk disk) {
      old.mergeAttributes(disk);
   }

   protected void mergeHeartbeatReport(HeartbeatReport old, HeartbeatReport heartbeatReport) {
      old.mergeAttributes(heartbeatReport);
   }

   protected void mergeMachine(Machine old, Machine machine) {
      old.mergeAttributes(machine);
   }

   protected void mergePeriod(Period old, Period period) {
      old.mergeAttributes(period);
   }

   @Override
   public void visitDisk(Disk disk) {
      Object parent = m_objs.peek();
      Disk old = null;

      if (parent instanceof Period) {
         Period period = (Period) parent;

         old = period.findDisk(disk.getPath());

         if (old == null) {
            old = new Disk(disk.getPath());
            period.addDisk(old);
         }

         mergeDisk(old, disk);
      }

      visitDiskChildren(old, disk);
   }

   protected void visitDiskChildren(Disk old, Disk disk) {
   }

   @Override
   public void visitHeartbeatReport(HeartbeatReport heartbeatReport) {
      m_heartbeatReport.mergeAttributes(heartbeatReport);
      visitHeartbeatReportChildren(m_heartbeatReport, heartbeatReport);
   }

   protected void visitHeartbeatReportChildren(HeartbeatReport old, HeartbeatReport heartbeatReport) {
      if (old != null) {
         m_objs.push(old);

         for (Machine machine : heartbeatReport.getMachines().values()) {
            m_tags.push(ENTITY_MACHINE);
            visitMachine(machine);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      Object parent = m_objs.peek();
      Machine old = null;

      if (parent instanceof HeartbeatReport) {
         HeartbeatReport heartbeatReport = (HeartbeatReport) parent;

         old = heartbeatReport.findMachine(machine.getIp());

         if (old == null) {
            old = new Machine(machine.getIp());
            heartbeatReport.addMachine(old);
         }

         mergeMachine(old, machine);
      }

      visitMachineChildren(old, machine);
   }

   protected void visitMachineChildren(Machine old, Machine machine) {
      if (old != null) {
         m_objs.push(old);

         for (Period period : machine.getPeriods()) {
            m_tags.push(ENTITY_PERIOD);
            visitPeriod(period);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitPeriod(Period period) {
      Object parent = m_objs.peek();
      Period old = null;

      if (parent instanceof Machine) {
         Machine machine = (Machine) parent;

         old = machine.findPeriod(period.getMinute());

         if (old == null) {
            old = new Period(period.getMinute());
            machine.addPeriod(old);
         }

         mergePeriod(old, period);
      }

      visitPeriodChildren(old, period);
   }

   protected void visitPeriodChildren(Period old, Period period) {
      if (old != null) {
         m_objs.push(old);

         for (Disk disk : period.getDisks()) {
            m_tags.push(ENTITY_DISK);
            visitDisk(disk);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
