package com.Aniket.User_service.model;

import com.Aniket.User_service.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String username;
    
    @Indexed(unique = true)
    private String email;
    
    private String password; // later encrypt
    private Roles role;     // USER, SHOP_KEEPER, DELIVERY_BOY
}