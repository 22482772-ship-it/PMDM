package dao;


import modelo.Equipos;
import modelo.Ligas;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EquiposDAO {

    private final SessionFactory sf;

    public EquiposDAO() {
        this.sf = new Configuration().configure().buildSessionFactory();
    }

    public void close() {
        sf.close();
    }
    // REGISTRAR EQUIPO
    public int RegistrarEquipo(String nombreEquipo, String ciudad) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            Equipos equipo = new Equipos(nombreEquipo, ciudad);
            session.persist(equipo);

            tx.commit();
            return equipo.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // ASIGNAR EQUIPO A LIGA
    public int asignarEquipoALiga(int idEquipo, int idLiga) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Ligas liga = session.get(Ligas.class, idLiga);
                if (liga == null) {
                    tx.rollback();
                    return 0;
                }

                int updated = session.createMutationQuery(
                                "update Equipos e set e.liga = :liga where e.id = :idEquipo"
                        )
                        .setParameter("liga", liga)
                        .setParameter("idEquipo", idEquipo)
                        .executeUpdate();

                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }

    // MODIFICAR EQUIPO
    public int ModificarEquipo(int idEquipo, String nombreEquipo, String ciudad) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            String hql = "update Equipos e SET e.nombre_equipo = :nombre_equipo, e.ciudad = :ciudad where e.id = :idEquipo";
            int filasAfectadas = session.createMutationQuery(hql)
                    .setParameter("nombre_equipo", nombreEquipo)
                    .setParameter("ciudad", ciudad)
                    .setParameter("idEquipo", idEquipo)
                    .executeUpdate();

            tx.commit();
            return filasAfectadas;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // ELIMINAR EQUIPO
    public int quitarEquipoDeLiga(int idEquipo, int idLiga) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            String hql = "update Equipos e set e.liga = null where e.id = :idEquipo and e.liga.id = :idLiga";
            int filasAfectadas = session.createMutationQuery(hql)
                    .setParameter("idEquipo", idEquipo)
                    .setParameter("idLiga", idLiga)
                    .executeUpdate();

            tx.commit();
            return filasAfectadas;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // LISTAR EQUIPOS
    public List<Equipos> listarEquipos() {
        try (Session session = sf.openSession()) {
            return session.createQuery("select e from Equipos e order by e.id", Equipos.class)
                    .getResultList();
        }
    }
}