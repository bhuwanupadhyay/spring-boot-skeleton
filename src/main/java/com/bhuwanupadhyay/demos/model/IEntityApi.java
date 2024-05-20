package com.bhuwanupadhyay.demos.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@RequestMapping(path = "${serviceApi.path}", produces = "application/json")
@Tag(name = "${serviceApi.tag}", description = "${serviceApi.description}")
public interface IEntityApi {

    @Operation(
            summary = "${serviceApi.create.summary}",
            description = "${serviceApi.create.description}",
            security = {@SecurityRequirement(name = "Authorization")},
            tags = {"${serviceApi.tag}"})
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Created",
                        content = {@Content(schema = @Schema(implementation = Ids.class))}),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "403",
                        description = "User not authorized to perform the action.",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "502",
                        description = "Bad Gateway",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "503",
                        description = "Service Unavailable",
                        content = {@Content(schema = @Schema(implementation = AppError.class))})
            })
    @PostMapping
    @PreAuthorize(
            "@authorizationFilter.hasRole('"
                    + ServiceRole.CREATOR
                    + "', '"
                    + ServiceRole.ADMIN
                    + "')")
    ResponseEntity<Ids> create(@RequestBody @Valid EntityProperties properties);

    @Operation(
            summary = "${serviceApi.patch.summary}",
            description = "${serviceApi.patch.description}",
            security = {@SecurityRequirement(name = "Authorization")},
            tags = {"${serviceApi.tag}"})
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Ok",
                        content = {@Content(schema = @Schema(implementation = Ids.class))}),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "403",
                        description = "User not authorized to perform the action.",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "502",
                        description = "Bad Gateway",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "503",
                        description = "Service Unavailable",
                        content = {@Content(schema = @Schema(implementation = AppError.class))})
            })
    @PatchMapping("/{entityId}")
    @PreAuthorize(
            "@authorizationFilter.hasRole('"
                    + ServiceRole.CREATOR
                    + "', '"
                    + ServiceRole.ADMIN
                    + "')")
    ResponseEntity<Ids> patch(
            @Parameter(description = "Entity Id") @PathVariable("entityId") String entityId,
            @RequestBody @Valid EntityProperties properties);

    @Operation(
            summary = "${serviceApi.get.summary}",
            description = "${serviceApi.get.description}",
            security = {@SecurityRequirement(name = "Authorization")},
            tags = {"${serviceApi.tag}"})
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = {@Content(schema = @Schema(ref = "#/components/schemas/Map"))}),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "403",
                        description = "User not authorized to perform the action.",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "502",
                        description = "Bad Gateway",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "503",
                        description = "Service Unavailable",
                        content = {@Content(schema = @Schema(implementation = AppError.class))})
            })
    @GetMapping("/{entityId}")
    @PreAuthorize(
            "@authorizationFilter.hasRole('"
                    + ServiceRole.VIEWER
                    + "', '"
                    + ServiceRole.CREATOR
                    + "', '"
                    + ServiceRole.ADMIN
                    + "')")
    ResponseEntity<EntityInfo> get(
            @Parameter(description = "Entity Id") @PathVariable("entityId") String entityId);

    @Operation(
            summary = "${serviceApi.delete.summary}",
            description = "${serviceApi.delete.description}",
            security = {@SecurityRequirement(name = "Authorization")},
            tags = {"${serviceApi.tag}"})
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "No Content"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "403",
                        description = "User not authorized to perform the action.",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "502",
                        description = "Bad Gateway",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "503",
                        description = "Service Unavailable",
                        content = {@Content(schema = @Schema(implementation = AppError.class))})
            })
    @DeleteMapping("/{entityId}")
    @PreAuthorize("@authorizationFilter.hasRole('" + ServiceRole.ADMIN + "')")
    ResponseEntity<Void> delete(
            @Parameter(description = "Entity Id") @PathVariable("entityId") String entityId);

    @Operation(
            summary = "${serviceApi.list.summary}",
            description = "${serviceApi.list.description}",
            security = {@SecurityRequirement(name = "Authorization")},
            tags = {"${serviceApi.tag}"})
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = {
                            @Content(
                                    array =
                                            @ArraySchema(
                                                    schema =
                                                            @Schema(implementation = String.class)))
                        }),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "403",
                        description = "User not authorized to perform the action.",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "502",
                        description = "Bad Gateway",
                        content = {@Content(schema = @Schema(implementation = AppError.class))}),
                @ApiResponse(
                        responseCode = "503",
                        description = "Service Unavailable",
                        content = {@Content(schema = @Schema(implementation = AppError.class))})
            })
    @GetMapping
    @PreAuthorize(
            "@authorizationFilter.hasRole('"
                    + ServiceRole.VIEWER
                    + "', '"
                    + ServiceRole.CREATOR
                    + "', '"
                    + ServiceRole.ADMIN
                    + "')")
    ResponseEntity<PageResults<EntityInfo>> search(SearchQuery query);
}
