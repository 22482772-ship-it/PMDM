package org.example;

import dao.EntrenadorDAO;
import dao.EquiposDAO;
import dao.JugadorDAO;
import dao.LigaDAO;
import modelo.Ligas;

public class MainDAO {
    public static void main(String[] args) {
        // Liga
        // LigaDAO ligaDAO = new LigaDAO();

        // Crea 1 liga
        //ligaDAO.crearLiga("Liga 2", "2021-01-01", "2022-01-01");

        // Equipos
         // EquiposDAO equiposDAO = new EquiposDAO();

        // Crea 3 equipos y asócialos a la liga
       // equiposDAO.RegistrarEquipo("Elche CF", "Elche");
        // equiposDAO.asignarEquipoALiga(5, 2);

        // equiposDAO.RegistrarEquipo("Valencia CF", "Valencia");
        // equiposDAO.asignarEquipoALiga(4, 2);

        // equiposDAO.RegistrarEquipo("Real Betis", "Madrid");
        // equiposDAO.asignarEquipoALiga(4, 2);

        // Jugadores
        //JugadorDAO jugadorDAO = new JugadorDAO();
        // jugadorDAO.ficharJugador(5, 2);
        // jugadorDAO.ficharJugador(9, 2);

        // Entrenadores
        // EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

        // Primer Entrenador
        // entrenadorDAO.registrarEntrenador("Juan", 7, 10);
        // entrenadorDAO.asignarEntrenadorEquipo(4,1);

        // Segundo Entrenador
        // entrenadorDAO.registrarEntrenador("Jorge", 5, 20);
        // entrenadorDAO.asignarEntrenadorEquipo(5,2);

        // Tercer Entrenador
        // entrenadorDAO.registrarEntrenador("Maria", 1, 30);
        //  entrenadorDAO.asignarEntrenadorEquipo(6,3);

        // Cuarto Entrenador
        // entrenadorDAO.registrarEntrenador("Sergio", 2, 15);
        // entrenadorDAO.asignarEntrenadorEquipo(7,1);

        // Quinto Entrenador
        // entrenadorDAO.registrarEntrenador("Carlos", 4, 21);
        // entrenadorDAO.asignarEntrenadorEquipo(8,2);

        // Sexto Entrenador
        // entrenadorDAO.registrarEntrenador("Alba", 1, 35);
        //  entrenadorDAO.asignarEntrenadorEquipo(9,3);
    
        //Mostrar datos de todos los equipos
        // System.out.println(equiposDAO.listarEquipos());

        //Mostrar los jugadores de un equipo
        // System.out.println(jugadorDAO.listarJugadoresDeEquipo(2));

        //Mostrar los equipos de una liga
        // System.out.println(ligaDAO.listarEquiposDeLiga(1));

        //Mostrar los entrenadores de los equipos de una liga
        // System.out.println(ligaDAO.listarEquiposDeLiga(1));
    }
}
