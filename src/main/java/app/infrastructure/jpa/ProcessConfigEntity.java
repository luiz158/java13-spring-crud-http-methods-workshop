package app.infrastructure.jpa;

import app.domain.ProcessConfig;
import com.google.common.collect.ImmutableMap;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "process_config")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProcessConfigEntity {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @ElementCollection
    @CollectionTable(name = "process_config_properties",
            joinColumns = {@JoinColumn(name = "process_config_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> properties = new HashMap<>();

    public static ProcessConfigEntity from(ProcessConfig processConfig) {
        ProcessConfigEntity processConfigEntity = new ProcessConfigEntity();
        if (processConfig.getId() != null) {
            processConfigEntity.id = processConfig.getId();
        }
        processConfigEntity.properties = processConfig.getProperties();

        return processConfigEntity;
    }

    public ProcessConfig toDomain() {
        return ProcessConfig.builder()
                .id(id)
                .properties(ImmutableMap.copyOf(properties))
                .build();
    }
}
