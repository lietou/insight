package com.dianping.cat.consumer.sql.model.transform;

import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_DATABASE;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_METHOD;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_TABLE;
import java.util.Stack;

import com.dianping.cat.consumer.sql.model.IVisitor;
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private SqlReport m_sqlReport;

   public DefaultMerger(SqlReport sqlReport) {
      m_sqlReport = sqlReport;
   }

   public SqlReport getSqlReport() {
      return m_sqlReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeDatabase(Database old, Database database) {
      old.mergeAttributes(database);
   }

   protected void mergeMethod(Method old, Method method) {
      old.mergeAttributes(method);
   }

   protected void mergeSqlReport(SqlReport old, SqlReport sqlReport) {
      old.mergeAttributes(sqlReport);
   }

   protected void mergeTable(Table old, Table table) {
      old.mergeAttributes(table);
   }

   @Override
   public void visitDatabase(Database database) {
      Object parent = m_objs.peek();
      Database old = null;

      if (parent instanceof SqlReport) {
         SqlReport sqlReport = (SqlReport) parent;

         old = sqlReport.findDatabase(database.getId());

         if (old == null) {
            old = new Database(database.getId());
            sqlReport.addDatabase(old);
         }

         mergeDatabase(old, database);
      }

      visitDatabaseChildren(old, database);
   }

   protected void visitDatabaseChildren(Database old, Database database) {
      if (old != null) {
         m_objs.push(old);

         for (Table table : database.getTables().values()) {
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
   public void visitSqlReport(SqlReport sqlReport) {
      m_sqlReport.mergeAttributes(sqlReport);
      visitSqlReportChildren(m_sqlReport, sqlReport);
   }

   protected void visitSqlReportChildren(SqlReport old, SqlReport sqlReport) {
      if (old != null) {
         m_objs.push(old);

         for (Database database : sqlReport.getDatabases().values()) {
            m_tags.push(ENTITY_DATABASE);
            visitDatabase(database);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitTable(Table table) {
      Object parent = m_objs.peek();
      Table old = null;

      if (parent instanceof Database) {
         Database database = (Database) parent;

         old = database.findTable(table.getId());

         if (old == null) {
            old = new Table(table.getId());
            database.addTable(old);
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
