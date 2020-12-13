package data.models;

import java.util.UUID;

;

public class Fields {
    String name;
    FieldType type;
    UUID uuid;

    public Fields(UUID uuid, String name, FieldType type) {
        this.name = name;
        this.type = type;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
