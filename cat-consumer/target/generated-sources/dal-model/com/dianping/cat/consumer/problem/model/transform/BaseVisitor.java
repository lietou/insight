package com.dianping.cat.consumer.problem.model.transform;

import com.dianping.cat.consumer.problem.model.IVisitor;
import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitDuration(Duration duration) {
   }

   @Override
   public void visitEntry(Entry entry) {
      for (Duration duration : entry.getDurations().values()) {
         visitDuration(duration);
      }

      for (JavaThread thread : entry.getThreads().values()) {
         visitThread(thread);
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      for (Entry entry : machine.getEntries()) {
         visitEntry(entry);
      }
   }

   @Override
   public void visitProblemReport(ProblemReport problemReport) {
      for (Machine machine : problemReport.getMachines().values()) {
         visitMachine(machine);
      }
   }

   @Override
   public void visitSegment(Segment segment) {
   }

   @Override
   public void visitThread(JavaThread thread) {
      for (Segment segment : thread.getSegments().values()) {
         visitSegment(segment);
      }
   }
}
