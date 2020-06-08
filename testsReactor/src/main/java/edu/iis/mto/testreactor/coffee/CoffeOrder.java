package edu.iis.mto.testreactor.coffee;

import java.util.Objects;

public class CoffeOrder {

    private final CoffeeSize size;
    private final CoffeType type;

    private CoffeOrder(Builder builder) {
        this.size = Objects.requireNonNull(builder.size, "size == null");
        this.type = Objects.requireNonNull(builder.type, "type == null");
    }

    public CoffeeSize getSize() {
        return size;
    }

    public CoffeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CoffeReceipe [size=" + size + ", type=" + type + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CoffeeSize size;
        private CoffeType type;

        private Builder() {}

        public Builder withSize(CoffeeSize size) {
            this.size = size;
            return this;
        }

        public Builder withType(CoffeType type) {
            this.type = type;
            return this;
        }

        public CoffeOrder build() {
            return new CoffeOrder(this);
        }
    }
}
