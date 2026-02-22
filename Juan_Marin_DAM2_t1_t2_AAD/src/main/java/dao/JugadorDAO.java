package dao;

import modelo.Equipos;
import modelo.Jugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class JugadorDAO {
    private final SessionFactory sf;

    public JugadorDAO() {
        this.sf = new Configuration().configure().buildSessionFactory();
    }

    public void close() {
        sf.close();
    }

    public int registrarJugador(String nombre,
                                String posicion,
                                double valorMercado,
                                int goles,
                                String nacionalidad,
                                int idEquipo) {

        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            Equipos equipo = session.get(Equipos.class, idEquipo);
            if (equipo == null) {
                tx.rollback();
                return 0;
            }

            Jugador jugador = new Jugador();
            jugador.setNombre(nombre);
            jugador.setPosicion(posicion);
            jugador.setValor_mercado(valorMercado);
            jugador.setGoles(goles);
            jugador.setNacionalidad(nacionalidad);
            jugador.setEquipo(equipo);

            session.persist(jugador);

            tx.commit();
            return jugador.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public int ficharJugador(int idJugador, int idEquipoNuevo) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            Equipos equipoNuevo = session.get(Equipos.class, idEquipoNuevo);
            if (equipoNuevo == null) {
                tx.rollback();
                return 0;
            }

            int updated = session.createMutationQuery(
                            "update Jugador j set j.equipo = :equipo where j.id = :idJugador"
                    )
                    .setParameter("equipo", equipoNuevo)
                    .setParameter("idJugador", idJugador)
                    .executeUpdate();

            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<Jugador> listarJugadoresDeEquipo(int idEquipo) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                            "select j from Jugador j where j.equipo.id = :idEquipo order by j.id",
                            Jugador.class
                    )
                    .setParameter("idEquipo", idEquipo)
                    .getResultList();
        }
    }
}
