package com.dianping.cat.consumer.cross.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.cross.model.IVisitor;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static CrossReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static CrossReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      CrossReport crossReport = new CrossReport();

      try {
         crossReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return crossReport;
   }

   @Override
   public void visitCrossReport(CrossReport crossReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitCrossReportChildren(crossReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitCrossReportChildren(CrossReport crossReport, int _field, int _type) {
      switch (_field) {
         case 1:
            crossReport.setDomain(readString());
            break;
         case 2:
            crossReport.setStartTime(readDate());
            break;
         case 3:
            crossReport.setEndTime(readDate());
            break;
         case 4:
            if (_type == 1) {
                  crossReport.addDomain(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  crossReport.addDomain(readString());
               }
            }
            break;
         case 5:
            if (_type == 1) {
                  crossReport.addIp(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  crossReport.addIp(readString());
               }
            }
            break;
         case 33:
            if (_type == 1) {
              Local locals = new Local();

              visitLocal(locals);
              m_linker.onLocal(crossReport, locals);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Local locals = new Local();

                 visitLocal(locals);
                 m_linker.onLocal(crossReport, locals);
               }
            }
            break;
      }
   }

   @Override
   public void visitLocal(Local local) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitLocalChildren(local, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitLocalChildren(Local local, int _field, int _type) {
      switch (_field) {
         case 1:
            local.setId(readString());
            break;
         case 33:
            if (_type == 1) {
              Remote remotes = new Remote();

              visitRemote(remotes);
              m_linker.onRemote(local, remotes);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Remote remotes = new Remote();

                 visitRemote(remotes);
                 m_linker.onRemote(local, remotes);
               }
            }
            break;
      }
   }

   @Override
   public void visitName(Name name) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitNameChildren(name, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitNameChildren(Name name, int _field, int _type) {
      switch (_field) {
         case 1:
            name.setId(readString());
            break;
         case 2:
            name.setTotalCount(readLong());
            break;
         case 3:
            name.setFailCount(readInt());
            break;
         case 4:
            name.setFailPercent(readDouble());
            break;
         case 5:
            name.setAvg(readDouble());
            break;
         case 6:
            name.setSum(readDouble());
            break;
         case 7:
            name.setTps(readDouble());
            break;
      }
   }

   @Override
   public void visitRemote(Remote remote) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitRemoteChildren(remote, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitRemoteChildren(Remote remote, int _field, int _type) {
      switch (_field) {
         case 1:
            remote.setId(readString());
            break;
         case 2:
            remote.setRole(readString());
            break;
         case 33:
            Type type = new Type();

            visitType(type);
            m_linker.onType(remote, type);
            break;
      }
   }

   @Override
   public void visitType(Type type) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitTypeChildren(type, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitTypeChildren(Type type, int _field, int _type) {
      switch (_field) {
         case 1:
            type.setId(readString());
            break;
         case 2:
            type.setTotalCount(readLong());
            break;
         case 3:
            type.setFailCount(readInt());
            break;
         case 4:
            type.setFailPercent(readDouble());
            break;
         case 5:
            type.setAvg(readDouble());
            break;
         case 6:
            type.setSum(readDouble());
            break;
         case 7:
            type.setTps(readDouble());
            break;
         case 33:
            if (_type == 1) {
              Name names = new Name();

              visitName(names);
              m_linker.onName(type, names);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Name names = new Name();

                 visitName(names);
                 m_linker.onName(type, names);
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

   private long readLong() {
      try {
         return readVarint(64);
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
