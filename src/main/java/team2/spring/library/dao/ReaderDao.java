package team2.spring.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

import team2.spring.library.LibLog;
import team2.spring.library.dao.interfaces.Dao;
import team2.spring.library.dao.interfaces.ReaderDaoInfs;
import team2.spring.library.entities.Reader;
import team2.spring.library.entities.Story;

@Transactional
@Repository
public class ReaderDao implements ReaderDaoInfs {

  private static final String TAG = ReaderDao.class.getName();

  private SessionFactory sessionFactory;

  @Autowired
  public ReaderDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public int insert(Reader entity) throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    int id = (int) session.save(entity);
    LibLog.debug(TAG, "inserted : " + session.find(Reader.class, id));
    return id;
  }

  @Override
  public Reader retrieve(int id) throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    return session.find(Reader.class, id);
  }

  @Override
  public List<Reader> retrieveAll() throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Reader> cq = cb.createQuery(Reader.class);
    Root<Reader> root = cq.from(Reader.class);
    cq.select(root);
    Query query = session.createQuery(cq);
    return query.getResultList();
  }

  @Override
  public Reader update(Reader entity) throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(entity);
    return session.find(Reader.class, entity.getId());
  }

  @Override
  public boolean delete(int id) throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    Reader reader = session.find(Reader.class, id);
    session.delete(reader);
    return null != reader;
  }

  //  3.1 Переглянути статистику по читачу (які книжки брав, які на руках, скільки часу користується
  // послугами бібліотеки)
  @Override
  public Reader findByName(String name) throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Reader> cq = cb.createQuery(Reader.class);
    Root<Reader> root = cq.from(Reader.class);

    cq.select(root).where(cb.equal(root.get("name"), name));

    return session.createQuery(cq).getSingleResult();
  }

  // 7
  public List<Story> getBlackList() throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Story> cq = cb.createQuery(Story.class);
    Root<Story> root = cq.from(Story.class);

    cq.select(root).where(cb.isNull(root.get("timeReturn"))).groupBy(root.get("reader"));
    return session.createQuery(cq).getResultList();
  }

  // 8 Рахуємо через retrieveAll
  // 8.2, 8.3
  public List<Story> getReaderStory(Reader reader) throws NoResultException {
    Session session = sessionFactory.getCurrentSession();
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Story> cq = cb.createQuery(Story.class);
    Root<Story> root = cq.from(Story.class);
    root.join("reader");

    cq.select(root).where(cb.equal(root.get("reader"), reader));
    return session.createQuery(cq).getResultList();
  }
}
