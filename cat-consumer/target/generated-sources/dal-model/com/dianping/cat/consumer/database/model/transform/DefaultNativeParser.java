package com.dianping.cat.consumer.database.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.database.model.IVisitor;
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static DatabaseReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static DatabaseReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      DatabaseReport databaseReport = new DatabaseReport();

      try {
         databaseReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return databaseReport;
   }

   @Override
   public void visitDatabaseReport(DatabaseReport databaseReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitDatabaseReportChildren(databaseReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitDatabaseReportChildren(DatabaseReport databaseReport, int _field, int _type) {
      switch (_field) {
         case 1:
            databaseReport.setDatabase(readString());
            break;
         case 2:
            databaseReport.setConnectUrl(readString());
            break;
         case 3:
            databaseReport.setStartTime(readDate());
            break;
         case 4:
            databaseReport.setEndTime(readDate());
            break;
         case 5:
            if (_type == 1) {
                  databaseReport.addDatabaseName(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  databaseReport.addDatabaseName(readString());
               }
            }
            break;
         case 6:
            if (_type == 1) {
                  databaseReport.addDomainName(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  databaseReport.addDomainName(readString());
               }
            }
            break;
         case 33:
            if (_type == 1) {
              Domain domains = new Domain();

              visitDomain(domains);
              m_linker.onDomain(databaseReport, domains);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Domain domains = new Domain();

                 visitDomain(domains);
                 m_linker.onDomain(databaseReport, domains);
               }
            }
            break;
      }
   }

   @Override
   public void visitDomain(Domain domain) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitDomainChildren(domain, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitDomainChildren(Domain domain, int _field, int _type) {
      switch (_field) {
         case 1:
            domain.setId(readString());
            break;
         case 33:
            if (_type == 1) {
              Table tables = new Table();

              visitTable(tables);
              m_linker.onTable(domain, tables);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Table tables = new Table();

                 visitTable(tables);
                 m_linker.onTable(domain, tables);
               }
            }
            break;
      }
   }

   @Override
   public void visitMethod(Method method) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMethodChildren(method, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMethodChildren(Method method, int _field, int _type) {
      switch (_field) {
         case 1:
            method.setId(readString());
            break;
         case 2:
            method.setTotalCount(readInt());
            break;
         case 3:
            method.setFailCount(readInt());
            break;
         case 4:
            method.setFailPercent(readDouble());
            break;
         case 5:
            method.setAvg(readDouble());
            break;
         case 6:
            method.setSum(readDouble());
            break;
         case 7:
            method.setTps(readDouble());
            break;
         case 8:
            if (_type == 1) {
                  method.addSql(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  method.addSql(readString());
               }
            }
            break;
         case 9:
            method.setTotalPercent(readDouble());
            break;
      }
   }

   @Override
   public void visitTable(Table table) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitTableChildren(table, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitTableChildren(Table table, int _field, int _type) {
      switch (_field) {
         case 1:
            table.setId(readString());
            break;
         case 2:
            table.setTotalCount(readInt());
            break;
         case 3:
            table.setFailCount(readInt());
            break;
         case 4:
            table.setFailPercent(readDouble());
            break;
         case 5:
            table.setAvg(readDouble());
            break;
         case 6:
            table.setSum(readDouble());
            break;
         case 7:
            table.setTps(readDouble());
            break;
         case 8:
            table.setTotalPercent(readDouble());
            break;
         case 33:
            if (_type == 1) {
              Method methods = new Method();

              visitMethod(methods);
              m_linker.onMethod(table, methods);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Method methods = new Method();

                 visitMethod(methods);
                 m_linker.onMethod(table, methods);
               }
            }
            break;
      }
   }

   private java.util.Date readDate() {
      try {
         return new java.util.Date(readVarint(64));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private double readDouble() {
      try {
         return m_in.readDouble();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private int readInt() {
      try {
         return (int) readVarint(32);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private String readString() {
      try {
         return m_in.readUTF();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private byte readTag() {
      try {
         return m_in.readByte();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected long readVarint(final int length) throws IOException {
      int shift = 0;
      long result = 0;

      while (shift < length) {
         final byte b = m_in.readByte();
         result |= (long) (b & 0x7F) << shift;
         if ((b & 0x80) == 0) {
            return result;
         }
         shift += 7;
      }

      throw new RuntimeException("Malformed variable int " + length + "!");
   }
}
