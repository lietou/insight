package com.dianping.cat.consumer.state.model.transform;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public interface IMaker<T> {

   public String buildIp(T node);

   public Machine buildMachine(T node);

   public Message buildMessage(T node);

   public ProcessDomain buildProcessDomain(T node);

   public StateReport buildStateReport(T node);
}
