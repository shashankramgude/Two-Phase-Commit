/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transaction implements org.apache.thrift.TBase<Transaction, Transaction._Fields>, java.io.Serializable, Cloneable, Comparable<Transaction> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Transaction");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField F_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("fName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField DO_COMMIT_OR_ABORT_FIELD_DESC = new org.apache.thrift.protocol.TField("doCommitOrAbort", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField OPERATION_FIELD_DESC = new org.apache.thrift.protocol.TField("operation", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CAN_COMMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("canCommit", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TransactionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TransactionTupleSchemeFactory());
  }

  public int id; // optional
  public String fName; // optional
  public String content; // optional
  public String doCommitOrAbort; // optional
  public String operation; // optional
  public String canCommit; // optional
  public String status; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    F_NAME((short)2, "fName"),
    CONTENT((short)3, "content"),
    DO_COMMIT_OR_ABORT((short)4, "doCommitOrAbort"),
    OPERATION((short)5, "operation"),
    CAN_COMMIT((short)6, "canCommit"),
    STATUS((short)7, "status");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // F_NAME
          return F_NAME;
        case 3: // CONTENT
          return CONTENT;
        case 4: // DO_COMMIT_OR_ABORT
          return DO_COMMIT_OR_ABORT;
        case 5: // OPERATION
          return OPERATION;
        case 6: // CAN_COMMIT
          return CAN_COMMIT;
        case 7: // STATUS
          return STATUS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ID,_Fields.F_NAME,_Fields.CONTENT,_Fields.DO_COMMIT_OR_ABORT,_Fields.OPERATION,_Fields.CAN_COMMIT,_Fields.STATUS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.F_NAME, new org.apache.thrift.meta_data.FieldMetaData("fName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DO_COMMIT_OR_ABORT, new org.apache.thrift.meta_data.FieldMetaData("doCommitOrAbort", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPERATION, new org.apache.thrift.meta_data.FieldMetaData("operation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CAN_COMMIT, new org.apache.thrift.meta_data.FieldMetaData("canCommit", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Transaction.class, metaDataMap);
  }

  public Transaction() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Transaction(Transaction other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetFName()) {
      this.fName = other.fName;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetDoCommitOrAbort()) {
      this.doCommitOrAbort = other.doCommitOrAbort;
    }
    if (other.isSetOperation()) {
      this.operation = other.operation;
    }
    if (other.isSetCanCommit()) {
      this.canCommit = other.canCommit;
    }
    if (other.isSetStatus()) {
      this.status = other.status;
    }
  }

  public Transaction deepCopy() {
    return new Transaction(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.fName = null;
    this.content = null;
    this.doCommitOrAbort = null;
    this.operation = null;
    this.canCommit = null;
    this.status = null;
  }

  public int getId() {
    return this.id;
  }

  public Transaction setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public String getFName() {
    return this.fName;
  }

  public Transaction setFName(String fName) {
    this.fName = fName;
    return this;
  }

  public void unsetFName() {
    this.fName = null;
  }

  /** Returns true if field fName is set (has been assigned a value) and false otherwise */
  public boolean isSetFName() {
    return this.fName != null;
  }

  public void setFNameIsSet(boolean value) {
    if (!value) {
      this.fName = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public Transaction setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public String getDoCommitOrAbort() {
    return this.doCommitOrAbort;
  }

  public Transaction setDoCommitOrAbort(String doCommitOrAbort) {
    this.doCommitOrAbort = doCommitOrAbort;
    return this;
  }

  public void unsetDoCommitOrAbort() {
    this.doCommitOrAbort = null;
  }

  /** Returns true if field doCommitOrAbort is set (has been assigned a value) and false otherwise */
  public boolean isSetDoCommitOrAbort() {
    return this.doCommitOrAbort != null;
  }

  public void setDoCommitOrAbortIsSet(boolean value) {
    if (!value) {
      this.doCommitOrAbort = null;
    }
  }

  public String getOperation() {
    return this.operation;
  }

  public Transaction setOperation(String operation) {
    this.operation = operation;
    return this;
  }

  public void unsetOperation() {
    this.operation = null;
  }

  /** Returns true if field operation is set (has been assigned a value) and false otherwise */
  public boolean isSetOperation() {
    return this.operation != null;
  }

  public void setOperationIsSet(boolean value) {
    if (!value) {
      this.operation = null;
    }
  }

  public String getCanCommit() {
    return this.canCommit;
  }

  public Transaction setCanCommit(String canCommit) {
    this.canCommit = canCommit;
    return this;
  }

  public void unsetCanCommit() {
    this.canCommit = null;
  }

  /** Returns true if field canCommit is set (has been assigned a value) and false otherwise */
  public boolean isSetCanCommit() {
    return this.canCommit != null;
  }

  public void setCanCommitIsSet(boolean value) {
    if (!value) {
      this.canCommit = null;
    }
  }

  public String getStatus() {
    return this.status;
  }

  public Transaction setStatus(String status) {
    this.status = status;
    return this;
  }

  public void unsetStatus() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return this.status != null;
  }

  public void setStatusIsSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case F_NAME:
      if (value == null) {
        unsetFName();
      } else {
        setFName((String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case DO_COMMIT_OR_ABORT:
      if (value == null) {
        unsetDoCommitOrAbort();
      } else {
        setDoCommitOrAbort((String)value);
      }
      break;

    case OPERATION:
      if (value == null) {
        unsetOperation();
      } else {
        setOperation((String)value);
      }
      break;

    case CAN_COMMIT:
      if (value == null) {
        unsetCanCommit();
      } else {
        setCanCommit((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case F_NAME:
      return getFName();

    case CONTENT:
      return getContent();

    case DO_COMMIT_OR_ABORT:
      return getDoCommitOrAbort();

    case OPERATION:
      return getOperation();

    case CAN_COMMIT:
      return getCanCommit();

    case STATUS:
      return getStatus();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case F_NAME:
      return isSetFName();
    case CONTENT:
      return isSetContent();
    case DO_COMMIT_OR_ABORT:
      return isSetDoCommitOrAbort();
    case OPERATION:
      return isSetOperation();
    case CAN_COMMIT:
      return isSetCanCommit();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Transaction)
      return this.equals((Transaction)that);
    return false;
  }

  public boolean equals(Transaction that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_fName = true && this.isSetFName();
    boolean that_present_fName = true && that.isSetFName();
    if (this_present_fName || that_present_fName) {
      if (!(this_present_fName && that_present_fName))
        return false;
      if (!this.fName.equals(that.fName))
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_doCommitOrAbort = true && this.isSetDoCommitOrAbort();
    boolean that_present_doCommitOrAbort = true && that.isSetDoCommitOrAbort();
    if (this_present_doCommitOrAbort || that_present_doCommitOrAbort) {
      if (!(this_present_doCommitOrAbort && that_present_doCommitOrAbort))
        return false;
      if (!this.doCommitOrAbort.equals(that.doCommitOrAbort))
        return false;
    }

    boolean this_present_operation = true && this.isSetOperation();
    boolean that_present_operation = true && that.isSetOperation();
    if (this_present_operation || that_present_operation) {
      if (!(this_present_operation && that_present_operation))
        return false;
      if (!this.operation.equals(that.operation))
        return false;
    }

    boolean this_present_canCommit = true && this.isSetCanCommit();
    boolean that_present_canCommit = true && that.isSetCanCommit();
    if (this_present_canCommit || that_present_canCommit) {
      if (!(this_present_canCommit && that_present_canCommit))
        return false;
      if (!this.canCommit.equals(that.canCommit))
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Transaction other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFName()).compareTo(other.isSetFName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fName, other.fName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDoCommitOrAbort()).compareTo(other.isSetDoCommitOrAbort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDoCommitOrAbort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.doCommitOrAbort, other.doCommitOrAbort);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOperation()).compareTo(other.isSetOperation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operation, other.operation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCanCommit()).compareTo(other.isSetCanCommit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCanCommit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.canCommit, other.canCommit);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Transaction(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetFName()) {
      if (!first) sb.append(", ");
      sb.append("fName:");
      if (this.fName == null) {
        sb.append("null");
      } else {
        sb.append(this.fName);
      }
      first = false;
    }
    if (isSetContent()) {
      if (!first) sb.append(", ");
      sb.append("content:");
      if (this.content == null) {
        sb.append("null");
      } else {
        sb.append(this.content);
      }
      first = false;
    }
    if (isSetDoCommitOrAbort()) {
      if (!first) sb.append(", ");
      sb.append("doCommitOrAbort:");
      if (this.doCommitOrAbort == null) {
        sb.append("null");
      } else {
        sb.append(this.doCommitOrAbort);
      }
      first = false;
    }
    if (isSetOperation()) {
      if (!first) sb.append(", ");
      sb.append("operation:");
      if (this.operation == null) {
        sb.append("null");
      } else {
        sb.append(this.operation);
      }
      first = false;
    }
    if (isSetCanCommit()) {
      if (!first) sb.append(", ");
      sb.append("canCommit:");
      if (this.canCommit == null) {
        sb.append("null");
      } else {
        sb.append(this.canCommit);
      }
      first = false;
    }
    if (isSetStatus()) {
      if (!first) sb.append(", ");
      sb.append("status:");
      if (this.status == null) {
        sb.append("null");
      } else {
        sb.append(this.status);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TransactionStandardSchemeFactory implements SchemeFactory {
    public TransactionStandardScheme getScheme() {
      return new TransactionStandardScheme();
    }
  }

  private static class TransactionStandardScheme extends StandardScheme<Transaction> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Transaction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // F_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fName = iprot.readString();
              struct.setFNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DO_COMMIT_OR_ABORT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.doCommitOrAbort = iprot.readString();
              struct.setDoCommitOrAbortIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // OPERATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.operation = iprot.readString();
              struct.setOperationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CAN_COMMIT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.canCommit = iprot.readString();
              struct.setCanCommitIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status = iprot.readString();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Transaction struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.fName != null) {
        if (struct.isSetFName()) {
          oprot.writeFieldBegin(F_NAME_FIELD_DESC);
          oprot.writeString(struct.fName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.content != null) {
        if (struct.isSetContent()) {
          oprot.writeFieldBegin(CONTENT_FIELD_DESC);
          oprot.writeString(struct.content);
          oprot.writeFieldEnd();
        }
      }
      if (struct.doCommitOrAbort != null) {
        if (struct.isSetDoCommitOrAbort()) {
          oprot.writeFieldBegin(DO_COMMIT_OR_ABORT_FIELD_DESC);
          oprot.writeString(struct.doCommitOrAbort);
          oprot.writeFieldEnd();
        }
      }
      if (struct.operation != null) {
        if (struct.isSetOperation()) {
          oprot.writeFieldBegin(OPERATION_FIELD_DESC);
          oprot.writeString(struct.operation);
          oprot.writeFieldEnd();
        }
      }
      if (struct.canCommit != null) {
        if (struct.isSetCanCommit()) {
          oprot.writeFieldBegin(CAN_COMMIT_FIELD_DESC);
          oprot.writeString(struct.canCommit);
          oprot.writeFieldEnd();
        }
      }
      if (struct.status != null) {
        if (struct.isSetStatus()) {
          oprot.writeFieldBegin(STATUS_FIELD_DESC);
          oprot.writeString(struct.status);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TransactionTupleSchemeFactory implements SchemeFactory {
    public TransactionTupleScheme getScheme() {
      return new TransactionTupleScheme();
    }
  }

  private static class TransactionTupleScheme extends TupleScheme<Transaction> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Transaction struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetFName()) {
        optionals.set(1);
      }
      if (struct.isSetContent()) {
        optionals.set(2);
      }
      if (struct.isSetDoCommitOrAbort()) {
        optionals.set(3);
      }
      if (struct.isSetOperation()) {
        optionals.set(4);
      }
      if (struct.isSetCanCommit()) {
        optionals.set(5);
      }
      if (struct.isSetStatus()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetFName()) {
        oprot.writeString(struct.fName);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetDoCommitOrAbort()) {
        oprot.writeString(struct.doCommitOrAbort);
      }
      if (struct.isSetOperation()) {
        oprot.writeString(struct.operation);
      }
      if (struct.isSetCanCommit()) {
        oprot.writeString(struct.canCommit);
      }
      if (struct.isSetStatus()) {
        oprot.writeString(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Transaction struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.fName = iprot.readString();
        struct.setFNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(3)) {
        struct.doCommitOrAbort = iprot.readString();
        struct.setDoCommitOrAbortIsSet(true);
      }
      if (incoming.get(4)) {
        struct.operation = iprot.readString();
        struct.setOperationIsSet(true);
      }
      if (incoming.get(5)) {
        struct.canCommit = iprot.readString();
        struct.setCanCommitIsSet(true);
      }
      if (incoming.get(6)) {
        struct.status = iprot.readString();
        struct.setStatusIsSet(true);
      }
    }
  }

}

