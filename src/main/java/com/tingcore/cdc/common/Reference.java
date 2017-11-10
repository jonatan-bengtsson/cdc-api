package com.tingcore.cdc.common;

import java.util.StringJoiner;
import java.util.function.Consumer;

import static java.lang.Long.parseUnsignedLong;
import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.*;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

public class Reference {
  public static final String SEPARATOR = "/";

  public static ReferenceElement fromString(final String reference) {
    notBlank(reference);
    int i = 0;
    String[] parts;
    ReferenceElement parent;

    if (reference.startsWith("//")) {
      parts = reference.substring(2).split(SEPARATOR);
      parent = new Service(parts[i++]);
    } else {
      if (reference.startsWith("/")) {
        parts = reference.substring(1).split(SEPARATOR);
      } else {
        parts = reference.split(SEPARATOR);
      }
      parent = new Local();
    }

    for (; i < parts.length; i++) {
      parent = parent.append(parts[i]);
    }
    return parent;
  }


  public interface ReferenceElement {
    void visit(final Consumer<String> consumer);

    default ReferenceElement append(final String name) {
      // TODO move to implementations
      if (this instanceof Host) {
        return ((Host) this).version(name);
      } else if (this instanceof Resource) {
        return ((Resource) this).collection(name);
      } else if (this instanceof Resources) {
        return ((Resources) this).entity(name);
      } else {
        throw new IllegalStateException("Illegal state.");
      }
    }

    default String asString() {
      final StringJoiner joiner = new StringJoiner(SEPARATOR);
      this.visit(joiner::add);
      return joiner.toString();
    }
  }

  public interface Host extends ReferenceElement {
    Resource version(final String name);
  }

  public interface Resource extends ReferenceElement {
    Resources collection(final String name);
  }

  public interface Resources extends ReferenceElement {
    Resource entity(final Long id);

    Resource entity(final String name);
  }

  public static class Local implements Host {
    @Override
    public Resource version(final String name) {
      return new Version(name, this);
    }

    @Override
    public void visit(final Consumer<String> consumer) {
      // Do nothing
    }
  }

  public static class Service implements Host {
    public final String name;

    public Service(final String name) {
      notBlank(name);
      isTrue(!name.contains(SEPARATOR), "Illegal character in service name.");
      // TODO validate domain name
      this.name = name;
    }

    @Override
    public Resource version(final String name) {
      return new Version(name, this);
    }

    @Override
    public void visit(final Consumer<String> consumer) {
      consumer.accept(format("//%s", name));
    }
  }

  public static class Version implements Resource {
    public final String name;
    public final Host owner;

    public Version(final String name,
                   final Host owner) {
      notBlank(name);
      isTrue(!name.contains(SEPARATOR), "Illegal character in version name.");
      // TODO version regex
      this.name = name;
      this.owner = notNull(owner);
    }

    @Override
    public Collection collection(final String name) {
      return new Collection(name, this);
    }

    @Override
    public void visit(final Consumer<String> consumer) {
      owner.visit(consumer);
      consumer.accept(name);
    }
  }

  public static class Entity implements Resource {
    public final String name;
    public final Collection collection;

    public Entity(final Long id,
                  final Collection collection) {
      notNull(id);
      this.name = notNull(id).toString();
      this.collection = notNull(collection);
    }

    public Entity(final String name,
                  final Collection collection) {
      notBlank(name);
      isTrue(!name.contains(SEPARATOR), "Illegal character in entity name.");
      this.name = name;
      this.collection = notNull(collection);
    }

    public Long id() {
      return parseUnsignedLong(name, 16);
    }

    @Override
    public Collection collection(final String name) {
      return new Collection(name, this);
    }

    @Override
    public void visit(final Consumer<String> consumer) {
      collection.visit(consumer);
      consumer.accept(name);
    }

    public static Entity fromCurrentRequest() {
      final ReferenceElement reference = fromString(currentRequestAttributes().getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, SCOPE_REQUEST).toString());
      if (reference instanceof Collection) {
        return Entity.class.cast(Collection.class.cast(reference).owner);
      } else {
        return Entity.class.cast(reference);
      }
    }
  }

  public static class Collection implements Resources {
    public final String name;
    public final Resource owner;

    public Collection(final String name,
                      final Resource owner) {
      notBlank(name);
      isTrue(!name.contains(SEPARATOR), "Illegal character in collection name.");
      isTrue(name.endsWith("s"), "Collections should be pluralized.");
      this.name = name;
      this.owner = notNull(owner);
    }

    @Override
    public Resource entity(final Long id) {
      return new Entity(id, this);
    }

    @Override
    public Entity entity(final String name) {
      return new Entity(name, this);
    }

    @Override
    public void visit(final Consumer<String> consumer) {
      owner.visit(consumer);
      consumer.accept(name);
    }

    public static Collection fromCurrentRequest() {
      final ReferenceElement reference = fromString(currentRequestAttributes().getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, SCOPE_REQUEST).toString());
      if (reference instanceof Entity) {
        return Collection.class.cast(Entity.class.cast(reference).collection);
      } else {
        return Collection.class.cast(reference);
      }
    }
  }
}
