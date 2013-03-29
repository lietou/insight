package com.dianping.cat.consumer.database.model.transform;

import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_METHOD;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_TABLE;
import java.util.Stack;

import com.dianping.cat.consumer.database.model.IVisitor;
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private DatabaseReport m_databaseReport;

   public DefaultMerger(DatabaseReport databaseReport) {
      m_databaseReport = databaseReport;
   }

   public DatabaseReport getDatabaseReport() {
      return m_databaseReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeDatabaseReport(DatabaseReport old, DatabaseReport databaseReport) {
      old.mergeAttributes(databaseReport);
   }

   protected void mergeDomain(Domain old, Domain domain) {
      old.mergeAttributes(domain);
   }

   protected void mergeMethod(Method old, Method method) {
      old.mergeAttributes(method);
   }

   protected void mergeTable(Table old, Table table) {
      old.mergeAttributes(table);
   }

   @Override
   public void visitDatabaseReport(DatabaseReport databaseReport) {
      m_databaseReport.mergeAttributes(databaseReport);
      visitDatabaseReportChildren(m_databaseReport, databaseReport);
   }

   protected void visitDatabaseReportChildren(DatabaseReport old, DatabaseReport databaseReport) {
      if (old != null) {
         m_objs.push(old);

         for (Domain domain : databaseReport.getDomains().values()) {
            m_tags.push(ENTITY_DOMAIN);
            visitDomain(domain);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitDomain(Domain domain) {
      Object parent = m_objs.peek();
      Domain old = null;

      if (parent instanceof DatabaseReport) {
         DatabaseReport databaseReport = (DatabaseReport) parent;

         old = databaseReport.findDomain(domain.getId());

         if (old == null) {
            old = new Domain(domain.getId());
            databaseReport.addDomain(old);
         }

         mergeDomain(old, domain);
      }

      visitDomainChildren(old, domain);
   }

   protected void visitDomainChildren(Domain old, Domain domain) {
      if (old != null) {
         m_objs.push(old);

         for (Table table : domain.getTables().values()) {
            m_tags.push(ENTITY_TABLE);
            visitTable(table);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMethod(Method method) {
      Object parent = m_objs.peek();
      Method old = null;

      if (parent instanceof Table) {
         Table table = (Table) parent;

         old = table.findMethod(method.getId());

         if (old == null) {
            old = new Method(method.getId());
            table.addMethod(old);
         }

         mergeMethod(old, method);
      }

      visitMethodChildren(old, method);
   }

   protected void visitMethodChildren(Method old, Method method) {
   }

   @Override
   public void visitTable(Table table) {
      Object parent = m_objs.peek();
      Table old = null;

      if (parent instanceof Domain) {
         Domain domain = (Domain) parent;

         old = domain.findTable(table.getId());

         if (old == null) {
            old = new Table(table.getId());
            domain.addTable(old);
         }

         mergeTable(old, table);
      }

      visitTableChildren(old, table);
   }

   protected void visitTableChildren(Table old, Table table) {
      if (old != null) {
         m_objs.push(old);

         for (Method method : table.getMethods().values()) {
            m_tags.push(ENTITY_METHOD);
            visitMethod(method);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
