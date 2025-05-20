package com.equipoweb.bibliotecaonline.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Recurso REST para Jakarta EE 8.
 *
 * @author
 */
@Path("rest")
/**
 * Esta clase define un recurso REST para Jakarta EE 8.
 * Proporciona un método para verificar si el servidor está activo.
 */
public class JakartaEE8Resource {
    
    /**
     * Verifica si el servidor está activo.
     * @return Una respuesta con el mensaje "ping".
     */
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
}
