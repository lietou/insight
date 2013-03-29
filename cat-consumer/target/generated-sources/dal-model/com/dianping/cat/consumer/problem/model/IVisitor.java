package com.dianping.cat.consumer.problem.model;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public interface IVisitor {

   public void visitDuration(Duration duration);

   public void visitEntry(Entry entry);

   public void visitMachine(Machine machine);

   public void visitProblemReport(ProblemReport problemReport);

   public void visitSegment(Segment segment);

   public void visitThread(JavaThread thread);
}
