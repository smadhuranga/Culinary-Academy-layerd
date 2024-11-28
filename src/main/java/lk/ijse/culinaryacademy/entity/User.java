
package lk.ijse.culinaryacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "username" , length = 100)
    private String username;

    @Column(name = "name" , length = 100, nullable = false)
    private String name;

    @Column(name = "email" , length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "role" , length = 100, nullable = false)
    private String role;

    @Column(name = "password" , length = 100, nullable = false)
    private String password;

}
