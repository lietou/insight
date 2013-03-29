package com.dianping.cat.consumer.state.model.transform;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public interface ILinker {

   public boolean onMachine(StateReport parent, Machine machine);

   public boolean onMessage(Machine parent, Message message);

   public boolean onProcessDomain(Machine parent, ProcessDomain processDomain);
}
