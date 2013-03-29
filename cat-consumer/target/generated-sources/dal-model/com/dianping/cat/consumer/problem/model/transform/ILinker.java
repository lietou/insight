package com.dianping.cat.consumer.problem.model.transform;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public interface ILinker {

   public boolean onDuration(Entry parent, Duration duration);

   public boolean onEntry(Machine parent, Entry entry);

   public boolean onMachine(ProblemReport parent, Machine machine);

   public boolean onSegment(JavaThread parent, Segment segment);

   public boolean onThread(Entry parent, JavaThread thread);
}
