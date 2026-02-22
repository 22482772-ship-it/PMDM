package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ligas")
public class Ligas {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String nombre_liga;

    private String fecha_inicio;

    private String fecha_fin;

    @OneToMany(mappedBy = "liga")
    private List<Equipos> listaEquipos;

}
