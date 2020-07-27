package jwachala.project.reservationsapp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface ResourceLocationBuilder {
     URI build(String resourceId);
}

@Component
class ResourceLocationBuilderImpl implements ResourceLocationBuilder{
    public URI build(String resourceId){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(resourceId).toUri();
    }
}
