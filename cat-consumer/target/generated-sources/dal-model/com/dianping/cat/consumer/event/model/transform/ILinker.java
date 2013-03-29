package com.dianping.cat.consumer.event.model.transform;

import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public interface ILinker {

   public boolean onMachine(EventReport parent, Machine machine);

   public boolean onName(EventType parent, EventName name);

   public boolean onRange(EventName parent, Range range);

   public boolean onType(Machine parent, EventType type);
}
