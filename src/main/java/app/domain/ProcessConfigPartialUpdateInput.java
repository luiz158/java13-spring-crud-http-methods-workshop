package app.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
public class ProcessConfigPartialUpdateInput {
    Map<String, String> props;
}