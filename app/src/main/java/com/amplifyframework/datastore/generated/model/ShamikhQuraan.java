package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the ShamikhQuraan type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "ShamikhQuraans", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class ShamikhQuraan implements Model {
  public static final QueryField ID = field("ShamikhQuraan", "id");
  public static final QueryField NAME = field("ShamikhQuraan", "name");
  public static final QueryField DESCRIPTION = field("ShamikhQuraan", "description");
  public static final QueryField DIMA = field("ShamikhQuraan", "dima");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String dima;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getDima() {
      return dima;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private ShamikhQuraan(String id, String name, String description, String dima) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.dima = dima;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      ShamikhQuraan shamikhQuraan = (ShamikhQuraan) obj;
      return ObjectsCompat.equals(getId(), shamikhQuraan.getId()) &&
              ObjectsCompat.equals(getName(), shamikhQuraan.getName()) &&
              ObjectsCompat.equals(getDescription(), shamikhQuraan.getDescription()) &&
              ObjectsCompat.equals(getDima(), shamikhQuraan.getDima()) &&
              ObjectsCompat.equals(getCreatedAt(), shamikhQuraan.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), shamikhQuraan.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getDima())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ShamikhQuraan {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("dima=" + String.valueOf(getDima()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static ShamikhQuraan justId(String id) {
    return new ShamikhQuraan(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      dima);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    ShamikhQuraan build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep dima(String dima);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private String dima;
    @Override
     public ShamikhQuraan build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ShamikhQuraan(
          id,
          name,
          description,
          dima);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep dima(String dima) {
        this.dima = dima;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String dima) {
      super.id(id);
      super.name(name)
        .description(description)
        .dima(dima);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder dima(String dima) {
      return (CopyOfBuilder) super.dima(dima);
    }
  }
  
}
