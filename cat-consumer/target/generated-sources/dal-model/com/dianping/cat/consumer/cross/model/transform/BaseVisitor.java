package com.dianping.cat.consumer.cross.model.transform;

import com.dianping.cat.consumer.cross.model.IVisitor;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitCrossReport(CrossReport crossReport) {
      for (Local local : crossReport.getLocals().values()) {
         visitLocal(local);
      }
   }

   @Override
   public void visitLocal(Local local) {
      for (Remote remote : local.getRemotes().values()) {
         visitRemote(remote);
      }
   }

   @Override
   public void visitName(Name name) {
   }

   @Override
   public void visitRemote(Remote remote) {
      if (remote.getType() != null) {
         visitType(remote.getType());
      }
   }

   @Override
   public void visitType(Type type) {
      for (Name name : type.getNames().values()) {
         visitName(name);
      }
   }
}
