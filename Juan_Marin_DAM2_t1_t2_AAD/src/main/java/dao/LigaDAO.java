package dao;

import modelo.Entrenador;
import modelo.Equipos;
import modelo.Ligas;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class LigaDAO {

    private final SessionFactory sf;

    public LigaDAO() {
        this.sf = new Configuration().configure().buildSessionFactory();
    }

    public void close() {
        sf.close();
    }

    // CREAR LIGA
    public int crearLiga(String nombreLiga, String fechaInicio, String fechaFin) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Ligas liga = new Ligas();
                liga.setNombre_liga(nombreLiga);
                liga.setFecha_inicio(fechaInicio);
                liga.setFecha_fin(fechaFin);

                session.persist(liga);

                tx.commit();
                return liga.getId();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }

    // EDITAR LIGA
    public int editarLiga(int idLiga, String nuevoNombre, String nuevaFechaInicio, String nuevaFechaFin) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                int updated = session.createMutationQuery(
                                """
                                        update Ligas l
                                        set l.nombre_liga = :nombre,
                                            l.fecha_inicio = :inicio,
                                            l.fecha_fin = :fin
                                        where l.id = :id
                                        """
                        )
                        .setParameter("nombre", nuevoNombre)
                        .setParameter("inicio", nuevaFechaInicio)
                        .setParameter("fin", nuevaFechaFin)
                        .setParameter("id", idLiga)
                        .executeUpdate();

                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }

    // ELIMINAR LIGA
    // Nota: si hay equipos apuntando a la liga, primero hay que desasignarlos o borrarlos (según tu regla).
    public int eliminarLiga(int idLiga) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.createMutationQuery(
                                "update Equipos e set e.liga = null where e.liga.id = :idLiga"
                        )
                        .setParameter("idLiga", idLiga)
                        .executeUpdate();

                int deleted = session.createMutationQuery(
                                "delete from Ligas l where l.id = :idLiga"
                        )
                        .setParameter("idLiga", idLiga)
                        .executeUpdate();

                tx.commit();
                return deleted;
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }


    // LISTAR LIGAS
    public List<Ligas> listarLigas() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "select l from Ligas l order by l.id",
                    Ligas.class
            ).getResultList();
        }
    }

    // LISTAR LIGAS + EQUIPOS (evitar LazyInitialization)
    public List<Ligas> listarLigasConEquipos() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "select distinct l from Ligas l left join fetch l.listaEquipos order by l.id",
                    Ligas.class
            ).getResultList();
        }
    }

    // OBTENER LIGA POR ID
    public Ligas getLiga(int idLiga) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                            "select l from Ligas l where l.id = :id",
                            Ligas.class
                    )
                    .setParameter("id", idLiga)
                    .uniqueResult();
        }
    }

    // LISTAR EQUIPOS DE UNA LIGA
    public List<Equipos> listarEquiposDeLiga(int idLiga) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                            "select e from Equipos e where e.liga.id = :idLiga order by e.id",
                            Equipos.class
                    )
                    .setParameter("idLiga", idLiga)
                    .getResultList();
        }
    }

    // LISTAR ENTRENADORES DE LOS EQUIPOS DE UNA LIGA (un entrenador puede estar en varios equipos)
    public List<Entrenador> listarEntrenadoresDeLiga(int idLiga) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                            """
                                    select distinct en
                                    from Entrenador en
                                    join en.equipos eq
                                    where eq.liga.id = :idLiga
                                    order by en.id
                                    """,
                            Entrenador.class
                    )
                    .setParameter("idLiga", idLiga)
                    .getResultList();
        }
    }
}


