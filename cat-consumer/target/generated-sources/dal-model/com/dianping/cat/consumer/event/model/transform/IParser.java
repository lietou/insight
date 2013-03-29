package com.dianping.cat.consumer.event.model.transform;

import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public interface IParser<T> {
   public EventReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForMachine(IMaker<T> maker, ILinker linker, Machine parent, T node);

   public void parseForEventName(IMaker<T> maker, ILinker linker, EventName parent, T node);

   public void parseForRange(IMaker<T> maker, ILinker linker, Range parent, T node);

   public void parseForEventType(IMaker<T> maker, ILinker linker, EventType parent, T node);
}
