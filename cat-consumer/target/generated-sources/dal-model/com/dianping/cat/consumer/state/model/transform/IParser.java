package com.dianping.cat.consumer.state.model.transform;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public interface IParser<T> {
   public StateReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForMachine(IMaker<T> maker, ILinker linker, Machine parent, T node);

   public void parseForMessage(IMaker<T> maker, ILinker linker, Message parent, T node);

   public void parseForProcessDomain(IMaker<T> maker, ILinker linker, ProcessDomain parent, T node);
}
