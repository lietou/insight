package com.dianping.cat.consumer.problem.model.transform;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public interface IParser<T> {
   public ProblemReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForDuration(IMaker<T> maker, ILinker linker, Duration parent, T node);

   public void parseForEntry(IMaker<T> maker, ILinker linker, Entry parent, T node);

   public void parseForMachine(IMaker<T> maker, ILinker linker, Machine parent, T node);

   public void parseForSegment(IMaker<T> maker, ILinker linker, Segment parent, T node);

   public void parseForJavaThread(IMaker<T> maker, ILinker linker, JavaThread parent, T node);
}
