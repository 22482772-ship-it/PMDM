package dao;

import modelo.Entrenador;
import modelo.Equipos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EntrenadorDAO {

    private final SessionFactory sf;

    public EntrenadorDAO() {
        this.sf = new Configuration().configure().buildSessionFactory();
    }

    public void close() {
        sf.close();
    }

    public int registrarEntrenador(String nombre, double calificacion, int titulos) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            Entrenador entrenador = new Entrenador();
            entrenador.setNombre(nombre);
            entrenador.setCalificacion(calificacion);
            entrenador.setTitulos(titulos);

            session.persist(entrenador);

            tx.commit();
            return entrenador.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public int asignarEntrenadorEquipo(int idEntrenador, int idEquipo) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            Entrenador entrenador = session.get(Entrenador.class, idEntrenador);
            if (entrenador == null) {
                throw new IllegalArgumentException("No existe entrenador con id = " + idEntrenador);
            }

            Equipos equipo = session.get(Equipos.class, idEquipo);
            if (equipo == null) {
                throw new IllegalArgumentException("No existe equipo con id = " + idEquipo);
            }

            equipo.setEntrenador(entrenador);
            session.merge(equipo);

            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}