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
@Table(name = "equipos")

public class Equipos {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String nombre_equipo;

    private String ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenador", nullable = true)
    private Entrenador entrenador;

    @OneToMany(mappedBy = "equipo")
    private List<Jugador> listaJugadores;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_liga", nullable = true)
    private Ligas liga;

    public Equipos(String nombre_equipo, String ciudad) {
        this.nombre_equipo = nombre_equipo;
        this.ciudad = ciudad;
    }

    public void mostrarDatos() {
        System.out.println("id = " + id);
        System.out.println("nombre_equipo = " + nombre_equipo);
        System.out.println("ciudad = " + ciudad);
    }
}


