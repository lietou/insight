package com.dianping.cat.consumer.cross.model.transform;

import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_LOCAL;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_REMOTE;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_TYPE;
import java.util.Stack;

import com.dianping.cat.consumer.cross.model.IVisitor;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private CrossReport m_crossReport;

   public DefaultMerger(CrossReport crossReport) {
      m_crossReport = crossReport;
   }

   public CrossReport getCrossReport() {
      return m_crossReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeCrossReport(CrossReport old, CrossReport crossReport) {
      old.mergeAttributes(crossReport);
   }

   protected void mergeLocal(Local old, Local local) {
      old.mergeAttributes(local);
   }

   protected void mergeName(Name old, Name name) {
      old.mergeAttributes(name);
   }

   protected void mergeRemote(Remote old, Remote remote) {
      old.mergeAttributes(remote);
   }

   protected void mergeType(Type old, Type type) {
      old.mergeAttributes(type);
   }

   @Override
   public void visitCrossReport(CrossReport crossReport) {
      m_crossReport.mergeAttributes(crossReport);
      visitCrossReportChildren(m_crossReport, crossReport);
   }

   protected void visitCrossReportChildren(CrossReport old, CrossReport crossReport) {
      if (old != null) {
         m_objs.push(old);

         for (Local local : crossReport.getLocals().values()) {
            m_tags.push(ENTITY_LOCAL);
            visitLocal(local);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitLocal(Local local) {
      Object parent = m_objs.peek();
      Local old = null;

      if (parent instanceof CrossReport) {
         CrossReport crossReport = (CrossReport) parent;

         old = crossReport.findLocal(local.getId());

         if (old == null) {
            old = new Local(local.getId());
            crossReport.addLocal(old);
         }

         mergeLocal(old, local);
      }

      visitLocalChildren(old, local);
   }

   protected void visitLocalChildren(Local old, Local local) {
      if (old != null) {
         m_objs.push(old);

         for (Remote remote : local.getRemotes().values()) {
            m_tags.push(ENTITY_REMOTE);
            visitRemote(remote);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitName(Name name) {
      Object parent = m_objs.peek();
      Name old = null;

      if (parent instanceof Type) {
         Type type = (Type) parent;

         old = type.findName(name.getId());

         if (old == null) {
            old = new Name(name.getId());
            type.addName(old);
         }

         mergeName(old, name);
      }

      visitNameChildren(old, name);
   }

   protected void visitNameChildren(Name old, Name name) {
   }

   @Override
   public void visitRemote(Remote remote) {
      Object parent = m_objs.peek();
      Remote old = null;

      if (parent instanceof Local) {
         Local local = (Local) parent;

         old = local.findRemote(remote.getId());

         if (old == null) {
            old = new Remote(remote.getId());
            local.addRemote(old);
         }

         mergeRemote(old, remote);
      }

      visitRemoteChildren(old, remote);
   }

   protected void visitRemoteChildren(Remote old, Remote remote) {
      if (old != null) {
         m_objs.push(old);

         if (remote.getType() != null) {
            m_tags.push(ENTITY_TYPE);
            visitType(remote.getType());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitType(Type type) {
      Object parent = m_objs.peek();
      Type old = null;

      if (parent instanceof Remote) {
         Remote remote = (Remote) parent;

         old = remote.getType();

         if (old == null) {
            old = new Type();
            remote.setType(old);
         }

         mergeType(old, type);
      }

      visitTypeChildren(old, type);
   }

   protected void visitTypeChildren(Type old, Type type) {
      if (old != null) {
         m_objs.push(old);

         for (Name name : type.getNames().values()) {
            m_tags.push(ENTITY_NAME);
            visitName(name);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
