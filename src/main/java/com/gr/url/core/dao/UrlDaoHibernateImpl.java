package com.gr.url.core.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.gr.common.dao.AbstractHibernateDao;
import com.gr.common.dao.DaoException;
import com.gr.common.dao.DaoManager;
import com.gr.url.core.model.Click;
import com.gr.url.core.model.Url;

public class UrlDaoHibernateImpl extends AbstractHibernateDao<Url, Integer> implements UrlDao {

	public static UrlDao getDao() {
		return DaoManager.getInstance().getDao(UrlDao.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Url> getAllUrls() {
		Session session = null;
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(Url.class);
			criteria.setMaxResults(100);

			List<Url> urls = criteria.list();
			return urls;
		} catch (Exception aex) {
			throw new DaoException(aex);
		} finally {
			closeSession(session);

		}
	}

	@Override
	public Url getUrl(int urlId) {
		Session session = null;
		Url url = null;
		try {
			session = getSession();
			session.beginTransaction();

			url = (Url) session.get(Url.class, urlId);

			session.getTransaction().commit();
		} catch (Exception ex) {
			throw new DaoException(ex);
		} finally {
			closeSession(session);
		}
		return url;
	}

	@Override
	public int addNewUrl(Url url) {
		Session session = null;
		int urlId = -1;
		try {
			session = getSession();

			session.beginTransaction();
			urlId = (Integer) session.save(url);

			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return urlId;
	}

	@Override
	public Url updateUrl(int id, Url urlNew) {
		Session session = null;
		try {
			session = getSession();
			session.beginTransaction();

			String hql = "UPDATE Url SET DateCreated = :newDate, ShortUrl = :newShort,"
					+ " LongUrl = :newLong WHERE Id = :Id";
			session.createQuery(hql).setString("newDate", "" + urlNew.getDateCreated())
					.setString("newShort", urlNew.getShortUrl()).setString("newLong", urlNew.getLongUrl())
					.setString("Id", "" + id).executeUpdate();

			session.getTransaction().commit();
		} catch (Exception ex) {
			throw new DaoException(ex);
		} finally {
			closeSession(session);
		}
		return urlNew;
	}

	@Override
	public int deleteUrl(int id) {
		Session session = null;
		try {
			session = getSession();
			session.beginTransaction();

			String hql = "DELETE FROM Url WHERE Id = :Id";
			session.createQuery(hql).setString("Id", "" + id).executeUpdate();

			session.getTransaction().commit();
		} catch (Exception ex) {
			throw new DaoException(ex);
		} finally {
			closeSession(session);
		}
		return id;
	}

	@Override
	public List<Click> getAllClicksOnUrl(int UrlId) {
		Session session = null;
		try {
			session = getSession();
			session.beginTransaction();

			Url url = (Url) session.get(Url.class, UrlId);
			List<Click> clicks = url.getClicks();

			session.getTransaction().commit();
			return clicks;
		} catch (Exception ex) {
			throw new DaoException(ex);
		} finally {
			closeSession(session);
		}
	}

	@Override
	public int addClickOnUrl(Click click) {
		Session session = null;
		int clickId = -1;
		try {
			session = getSession();

			session.beginTransaction();
			clickId = (Integer) session.save(click);

			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return clickId;
	}

	@Override
	public Url getLastUrl() {
		Session session = null;

		try {
			session = getSession();
			session.beginTransaction();

			String hql = "FROM Url ORDER BY Id DESC";
			Url url = (Url) session.createQuery(hql).setMaxResults(1).uniqueResult();

			session.getTransaction().commit();
			return url;
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<Date, Integer> getDateStats(int UrlId) {
		Session session = null;
		HashMap<Date, Integer> hmap = new HashMap<>();
		try {
			session = getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("dateClicked"));
			criteria.setProjection(Projections.distinct(projList));

			List<Date> dates = criteria.list();
			for (Date date : dates) {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(date);
//				cal.get(Calendar.YEAR);

				criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId))
						.add(Restrictions.eq("dateClicked", date)).setProjection(Projections.rowCount());

				hmap.put(date, (int) (long) criteria.uniqueResult());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return hmap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Integer> getBrowserStats(int UrlId) {
		Session session = null;
		HashMap<String, Integer> hmap = new HashMap<>();
		try {
			session = getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("browserClicked"));
			criteria.setProjection(Projections.distinct(projList));

			List<String> browsers = criteria.list();
			for (String browser : browsers) {
				criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId))
						.add(Restrictions.eq("browserClicked", browser)).setProjection(Projections.rowCount());
				hmap.put(browser, (int) (long) criteria.uniqueResult());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return hmap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Integer> getPlatformStats(int UrlId) {
		Session session = null;
		HashMap<String, Integer> hmap = new HashMap<>();
		try {
			session = getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("platformClicked"));
			criteria.setProjection(Projections.distinct(projList));

			List<String> platforms = criteria.list();
			for (String platform : platforms) {
				criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId))
						.add(Restrictions.eq("platformClicked", platform)).setProjection(Projections.rowCount());
				hmap.put(platform, (int) (long) criteria.uniqueResult());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return hmap;
	}
}
