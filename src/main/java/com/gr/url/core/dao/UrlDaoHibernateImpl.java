package com.gr.url.core.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.gr.url.ws.model.ClickInfo;

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
	public String addNewUrl(Url url) {
		Session session = null;
		String shortUrl="";
		try {
			session = getSession();

			session.beginTransaction();
			int id = (Integer) session.save(url);
			shortUrl=(String) session.createCriteria(Url.class).add(Restrictions.eq("id", id))
					.setProjection(Projections.property("shortUrl")).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return shortUrl;
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
	public ClickInfo getDateStats(int UrlId) {
		Session session = null;
		ClickInfo clickInfo = null;
		try {
			session = getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("dateClicked"));
			criteria.setProjection(Projections.distinct(projList));

			List<Date> dates = criteria.list();
			List<String> labels = new ArrayList<>();
			List<Integer> data = new ArrayList<>();
			for (Date date : dates) {
				criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId))
						.add(Restrictions.eq("dateClicked", date)).setProjection(Projections.rowCount());

				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				String year = "" + cal.get(Calendar.YEAR);
				int count = (int) (long) criteria.uniqueResult();
				if (labels.contains(year)) {
					int i = labels.indexOf(year);
					data.set(i, (data.get(i) + count));
				} else {
					labels.add(year);
					data.add(count);
				}
			}
			clickInfo = new ClickInfo(labels, data);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return clickInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClickInfo getBrowserStats(int UrlId) {
		Session session = null;
		ClickInfo clickInfo = null;
		try {
			session = getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("browserClicked"));
			criteria.setProjection(Projections.distinct(projList));

			List<String> browsers = criteria.list();
			List<Integer> data = new ArrayList<>();
			for (String browser : browsers) {
				criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId))
						.add(Restrictions.eq("browserClicked", browser)).setProjection(Projections.rowCount());
				data.add((int) (long) criteria.uniqueResult());
			}
			clickInfo = new ClickInfo(browsers, data);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return clickInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClickInfo getPlatformStats(int UrlId) {
		Session session = null;
		ClickInfo clickInfo = null;
		try {
			session = getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("platformClicked"));
			criteria.setProjection(Projections.distinct(projList));

			List<String> platforms = criteria.list();
			List<Integer> data = new ArrayList<>();
			for (String platform : platforms) {
				criteria = session.createCriteria(Click.class).add(Restrictions.eq("urlId", UrlId))
						.add(Restrictions.eq("platformClicked", platform)).setProjection(Projections.rowCount());
				data.add((int) (long) criteria.uniqueResult());
			}
			clickInfo = new ClickInfo(platforms, data);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			closeSession(session);
		}
		return clickInfo;
	}
}
