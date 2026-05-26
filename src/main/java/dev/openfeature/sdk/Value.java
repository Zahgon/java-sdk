package dev.openfeature.sdk;

import static dev.openfeature.sdk.Structure.mapToStructure;
import dev.openfeature.sdk.exceptions.TypeMismatchError;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;

/**
 * Values serve as a generic return type for structure data from providers.
 * Providers may deal in JSON, protobuf, XML or some other data-interchange format.
 * This intermediate representation provides a good medium of exchange.
 */
@ToString
@EqualsAndHashCode
@SuppressWarnings({ "PMD.BeanMembersShouldSerialize", "checkstyle:MissingJavadocType", "checkstyle:NoFinalizer" })
public class Value implements Cloneable {

    private final Object innerObject;

    protected final void finalize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Construct a new null Value.
     */
    public Value() {
        this.innerObject = null;
    }

    /**
     * Construct a new Value with an Object.
     *
     * @param value to be wrapped.
     * @throws InstantiationException if value is not a valid type
     *                                (boolean, string, int, double, list, structure, instant)
     */
    public Value(Object value) throws InstantiationException {
        this.innerObject = value;
        if (!this.isNull() && !this.isBoolean() && !this.isString() && !this.isNumber() && !this.isStructure() && !this.isList() && !this.isInstant()) {
            throw new InstantiationException("Invalid value type: " + value.getClass());
        }
    }

    public Value(Value value) {
        this.innerObject = value.innerObject;
    }

    public Value(Boolean value) {
        this.innerObject = value;
    }

    public Value(String value) {
        this.innerObject = value;
    }

    public Value(Integer value) {
        this.innerObject = value;
    }

    public Value(Double value) {
        this.innerObject = value;
    }

    public Value(Structure value) {
        this.innerObject = value;
    }

    public Value(List<Value> value) {
        this.innerObject = value;
    }

    public Value(Instant value) {
        this.innerObject = value;
    }

    /**
     * Check if this Value represents null.
     *
     * @return boolean
     */
    public boolean isNull() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Value represents a Boolean.
     *
     * @return boolean
     */
    public boolean isBoolean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Value represents a String.
     *
     * @return boolean
     */
    public boolean isString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Value represents a numeric value.
     *
     * @return boolean
     */
    public boolean isNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Value represents a Structure.
     *
     * @return boolean
     */
    public boolean isStructure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Value represents a List of Values.
     *
     * @return boolean
     */
    public boolean isList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Value represents an Instant.
     *
     * @return boolean
     */
    public boolean isInstant() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying Boolean value, or null.
     *
     * @return Boolean
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value = "NP_BOOLEAN_RETURN_NULL", justification = "This is not a plain true/false method. It's understood it can return null.")
    public Boolean asBoolean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying object.
     *
     * @return Object
     */
    public Object asObject() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying String value, or null.
     *
     * @return String
     */
    public String asString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying numeric value as an Integer, or null.
     * If the value is not an integer, it will be rounded using Math.round().
     *
     * @return Integer
     */
    public Integer asInteger() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying numeric value as a Double, or null.
     *
     * @return Double
     */
    public Double asDouble() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying Structure value, or null.
     *
     * @return Structure
     */
    public Structure asStructure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying List value, or null.
     *
     * @return List
     */
    public List<Value> asList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve the underlying Instant value, or null.
     *
     * @return Instant
     */
    public Instant asInstant() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform deep clone of value object.
     *
     * @return Value
     */
    @SneakyThrows
    @Override
    protected Value clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Wrap an object into a Value.
     *
     * @param object the object to wrap
     * @return the wrapped object
     */
    public static Value objectToValue(Object object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
