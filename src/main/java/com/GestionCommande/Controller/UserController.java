package com.GestionCommande.Controller;

import com.GestionCommande.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public String setRole(@PathVariable long id, @RequestBody String role) {
        return service.setRole(id, role);
    }

    @GetMapping("/me")
    public String whoAmI() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return "User: " + auth.getName() + " | Roles: " + auth.getAuthorities();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable long id) {
        service.deleteUser(id);
    }


}
