package app.gateway;

import app.domain.ProcessConfigService;
import app.gateway.input.NewProcessConfigApiInput;
import app.gateway.input.UpdateProcessConfigApiInput;
import app.gateway.input.ReplaceProcessConfigApiInput;
import app.gateway.output.ProcessConfigApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

@RestController
@RequestMapping("app")
public class ProcessConfigController {

    @Autowired
    ProcessConfigService processConfigService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> findById(@PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.findById(id), ProcessConfigApiOutput::from);
    }

    @PostMapping
    public ResponseEntity<ProcessConfigApiOutput> create(@RequestBody NewProcessConfigApiInput creationInput, UriComponentsBuilder builder) {
        return ResponseEntityBuilder.created(processConfigService.create(creationInput.toDomain()),
                ProcessConfigApiOutput::from,
                processConfig -> builder.path("app/{id}").buildAndExpand(processConfig.getId()).toUri());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> update(@RequestBody UpdateProcessConfigApiInput updateInput, @PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.update(updateInput.toDomain(id)), ProcessConfigApiOutput::from);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> replace(@RequestBody ReplaceProcessConfigApiInput replaceInput, @PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.replace(replaceInput.toDomain(id)), ProcessConfigApiOutput::from);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.deleteById(id), Function.identity());
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET,
                        HttpMethod.HEAD,
                        HttpMethod.POST,
                        HttpMethod.PATCH,
                        HttpMethod.PUT,
                        HttpMethod.DELETE,
                        HttpMethod.OPTIONS)
                .build();
    }
}
