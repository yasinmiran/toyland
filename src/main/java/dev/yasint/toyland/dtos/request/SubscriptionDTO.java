package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.models.enumerations.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {

    @JsonProperty("subject_id")
    private Long subjectId;

    @JsonProperty
    private Event event;
}
