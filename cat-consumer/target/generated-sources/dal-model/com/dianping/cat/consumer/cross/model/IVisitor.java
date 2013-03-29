package com.dianping.cat.consumer.cross.model;

import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public interface IVisitor {

   public void visitCrossReport(CrossReport crossReport);

   public void visitLocal(Local local);

   public void visitName(Name name);

   public void visitRemote(Remote remote);

   public void visitType(Type type);
}
