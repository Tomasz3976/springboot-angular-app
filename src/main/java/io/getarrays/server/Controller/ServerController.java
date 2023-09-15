package io.getarrays.server.Controller;

import io.getarrays.server.Service.ServerService;
import io.getarrays.server.model.Response;
import io.getarrays.server.model.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import static io.getarrays.server.enumeration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        Collection<Server> servers = serverService.list(30);
        return ResponseEntity.ok(
                Response.builder()
                    .timeStamp(now())
                    .data(Map.of("servers", servers))
                    .message("Servers retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                    .timeStamp(now())
                    .data(Map.of("server", server))
                    .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        Server createdServer = serverService.create(server);
        return ResponseEntity.ok(
                Response.builder()
                    .timeStamp(now())
                    .data(Map.of("server", createdServer))
                    .message("Server created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id) {
        Server server = serverService.get(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", server))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id) {
        Boolean isServerDeleted = serverService.delete(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", isServerDeleted))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(value = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(
                Paths.get(System.getProperty("user.home") + "Downloads/images/" + fileName));
    }
}
