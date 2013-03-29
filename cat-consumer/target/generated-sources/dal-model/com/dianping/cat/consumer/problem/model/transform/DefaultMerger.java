package com.dianping.cat.consumer.problem.model.transform;

import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_DURATION;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_ENTRY;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_SEGMENT;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_THREAD;
import java.util.Stack;

import com.dianping.cat.consumer.problem.model.IVisitor;
import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private ProblemReport m_problemReport;

   public DefaultMerger(ProblemReport problemReport) {
      m_problemReport = problemReport;
   }

   public ProblemReport getProblemReport() {
      return m_problemReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeDuration(Duration old, Duration duration) {
      old.mergeAttributes(duration);
   }

   protected void mergeEntry(Entry old, Entry entry) {
      old.mergeAttributes(entry);
   }

   protected void mergeMachine(Machine old, Machine machine) {
      old.mergeAttributes(machine);
   }

   protected void mergeProblemReport(ProblemReport old, ProblemReport problemReport) {
      old.mergeAttributes(problemReport);
   }

   protected void mergeSegment(Segment old, Segment segment) {
      old.mergeAttributes(segment);
   }

   protected void mergeThread(JavaThread old, JavaThread thread) {
      old.mergeAttributes(thread);
   }

   @Override
   public void visitDuration(Duration duration) {
      Object parent = m_objs.peek();
      Duration old = null;

      if (parent instanceof Entry) {
         Entry entry = (Entry) parent;

         old = entry.findDuration(duration.getValue());

         if (old == null) {
            old = new Duration(duration.getValue());
            entry.addDuration(old);
         }

         mergeDuration(old, duration);
      }

      visitDurationChildren(old, duration);
   }

   protected void visitDurationChildren(Duration old, Duration duration) {
   }

   @Override
   public void visitEntry(Entry entry) {
      Object parent = m_objs.peek();
      Entry old = null;

      if (parent instanceof Machine) {
         Machine machine = (Machine) parent;

         machine.addEntry(entry);
      }

      visitEntryChildren(old, entry);
   }

   protected void visitEntryChildren(Entry old, Entry entry) {
      if (old != null) {
         m_objs.push(old);

         for (Duration duration : entry.getDurations().values()) {
            m_tags.push(ENTITY_DURATION);
            visitDuration(duration);
            m_tags.pop();
         }

         for (JavaThread thread : entry.getThreads().values()) {
            m_tags.push(ENTITY_THREAD);
            visitThread(thread);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      Object parent = m_objs.peek();
      Machine old = null;

      if (parent instanceof ProblemReport) {
         ProblemReport problemReport = (ProblemReport) parent;

         old = problemReport.findMachine(machine.getIp());

         if (old == null) {
            old = new Machine(machine.getIp());
            problemReport.addMachine(old);
         }

         mergeMachine(old, machine);
      }

      visitMachineChildren(old, machine);
   }

   protected void visitMachineChildren(Machine old, Machine machine) {
      if (old != null) {
         m_objs.push(old);

         for (Entry entry : machine.getEntries()) {
            m_tags.push(ENTITY_ENTRY);
            visitEntry(entry);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitProblemReport(ProblemReport problemReport) {
      m_problemReport.mergeAttributes(problemReport);
      visitProblemReportChildren(m_problemReport, problemReport);
   }

   protected void visitProblemReportChildren(ProblemReport old, ProblemReport problemReport) {
      if (old != null) {
         m_objs.push(old);

         for (Machine machine : problemReport.getMachines().values()) {
            m_tags.push(ENTITY_MACHINE);
            visitMachine(machine);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitSegment(Segment segment) {
      Object parent = m_objs.peek();
      Segment old = null;

      if (parent instanceof JavaThread) {
         JavaThread thread = (JavaThread) parent;

         old = thread.findSegment(segment.getId());

         if (old == null) {
            old = new Segment(segment.getId());
            thread.addSegment(old);
         }

         mergeSegment(old, segment);
      }

      visitSegmentChildren(old, segment);
   }

   protected void visitSegmentChildren(Segment old, Segment segment) {
   }

   @Override
   public void visitThread(JavaThread thread) {
      Object parent = m_objs.peek();
      JavaThread old = null;

      if (parent instanceof Entry) {
         Entry entry = (Entry) parent;

         old = entry.findThread(thread.getId());

         if (old == null) {
            old = new JavaThread(thread.getId());
            entry.addThread(old);
         }

         mergeThread(old, thread);
      }

      visitThreadChildren(old, thread);
   }

   protected void visitThreadChildren(JavaThread old, JavaThread thread) {
      if (old != null) {
         m_objs.push(old);

         for (Segment segment : thread.getSegments().values()) {
            m_tags.push(ENTITY_SEGMENT);
            visitSegment(segment);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
