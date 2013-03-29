package com.dianping.cat.consumer.event.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.event.model.IVisitor;
import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static EventReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static EventReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      EventReport eventReport = new EventReport();

      try {
         eventReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return eventReport;
   }

   @Override
   public void visitEventReport(EventReport eventReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitEventReportChildren(eventReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitEventReportChildren(EventReport eventReport, int _field, int _type) {
      switch (_field) {
         case 1:
            eventReport.setDomain(readString());
            break;
         case 2:
            eventReport.setStartTime(readDate());
            break;
         case 3:
            eventReport.setEndTime(readDate());
            break;
         case 4:
            if (_type == 1) {
                  eventReport.addDomain(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  eventReport.addDomain(readString());
               }
            }
            break;
         case 5:
            if (_type == 1) {
                  eventReport.addIp(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  eventReport.addIp(readString());
               }
            }
            break;
         case 33:
            if (_type == 1) {
              Machine machines = new Machine();

              visitMachine(machines);
              m_linker.onMachine(eventReport, machines);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Machine machines = new Machine();

                 visitMachine(machines);
                 m_linker.onMachine(eventReport, machines);
               }
            }
            break;
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMachineChildren(machine, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMachineChildren(Machine machine, int _field, int _type) {
      switch (_field) {
         case 1:
            machine.setIp(readString());
            break;
         case 33:
            if (_type == 1) {
              EventType types = new EventType();

              visitType(types);
              m_linker.onType(machine, types);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 EventType types = new EventType();

                 visitType(types);
                 m_linker.onType(machine, types);
               }
            }
            break;
      }
   }

   @Override
   public void visitName(EventName name) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitNameChildren(name, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitNameChildren(EventName name, int _field, int _type) {
      switch (_field) {
         case 1:
            name.setId(readString());
            break;
         case 2:
            name.setTotalCount(readLong());
            break;
         case 3:
            name.setFailCount(readLong());
            break;
         case 4:
            name.setFailPercent(readDouble());
            break;
         case 5:
            name.setSuccessMessageUrl(readString());
            break;
         case 6:
            name.setFailMessageUrl(readString());
            break;
         case 33:
            if (_type == 1) {
              Range ranges = new Range();

              visitRange(ranges);
              m_linker.onRange(name, ranges);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Range ranges = new Range();

                 visitRange(ranges);
                 m_linker.onRange(name, ranges);
               }
            }
            break;
      }
   }

   @Override
   public void visitRange(Range range) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitRangeChildren(range, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitRangeChildren(Range range, int _field, int _type) {
      switch (_field) {
         case 1:
            range.setValue(readInt());
            break;
         case 2:
            range.setCount(readInt());
            break;
         case 3:
            range.setFails(readInt());
            break;
      }
   }

   @Override
   public void visitType(EventType type) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitTypeChildren(type, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitTypeChildren(EventType type, int _field, int _type) {
      switch (_field) {
         case 1:
            type.setId(readString());
            break;
         case 2:
            type.setTotalCount(readLong());
            break;
         case 3:
            type.setFailCount(readLong());
            break;
         case 4:
            type.setFailPercent(readDouble());
            break;
         case 5:
            type.setSuccessMessageUrl(readString());
            break;
         case 6:
            type.setFailMessageUrl(readString());
            break;
         case 33:
            if (_type == 1) {
              EventName names = new EventName();

              visitName(names);
              m_linker.onName(type, names);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 EventName names = new EventName();

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
