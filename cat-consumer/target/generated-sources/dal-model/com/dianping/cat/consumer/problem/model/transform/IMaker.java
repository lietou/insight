package com.dianping.cat.consumer.problem.model.transform;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public interface IMaker<T> {

   public String buildDomain(T node);

   public Duration buildDuration(T node);

   public Entry buildEntry(T node);

   public String buildIp(T node);

   public Machine buildMachine(T node);

   public String buildMessage(T node);

   public ProblemReport buildProblemReport(T node);

   public Segment buildSegment(T node);

   public JavaThread buildThread(T node);
}
