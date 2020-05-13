package lambda;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import nva.commons.utils.JacocoGenerated;

public class Publication {

    @JsonProperty("id")
    private String id;

    @JsonProperty("owner_id")
    private String ownerId;

    public Publication(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    @JacocoGenerated
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Publication that = (Publication) o;
        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getOwnerId(), that.getOwnerId());
    }

    @Override
    @JacocoGenerated
    public int hashCode() {
        return Objects.hash(getId(), getOwnerId());
    }
}
