package nc.impl.myemp.country;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.itf.myemp.country.ICountryQueryService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

public class CountryQueryServiceImpl implements ICountryQueryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryVO> getAll() throws BusinessException {
		return (List<CountryVO>) new BaseDAO().retrieveAll(CountryVO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryVO> getByCondition(String sqlWhere)
			throws BusinessException {
		if (sqlWhere == null || "".equals(sqlWhere.trim())) {
			return getAll();
		}
		return (List<CountryVO>) new BaseDAO().executeQuery(
				"select * from myemp_country where " + sqlWhere,
				new BeanListProcessor(CountryVO.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryVO> getAllByPks(String[] pks) throws BusinessException {
		StringBuffer sb = new StringBuffer("(");
		for (String pk : pks) {
			sb.append("'").append(pk).append("'").append(",");
		}
		// sb.deleteCharAt(sb.length() - 1).append(")");
		sb.replace(sb.length() - 1, sb.length(), ")");
		String sql = "select * from myemp_country where pk_country in "
				+ sb.toString();
		return (List<CountryVO>) new BaseDAO().executeQuery(sql,
				new BeanListProcessor(CountryVO.class));
	}

	@Override
	public String[] getPksByCondition(String sqlWhere) throws BusinessException {
		StringBuffer sb = new StringBuffer();
		sb.append("select pk_country from myemp_country");
		if (sqlWhere != null) {
			sb.append(" where ").append(sqlWhere);
		}
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) new BaseDAO().executeQuery(
				sb.toString(), new ColumnListProcessor("pk_country"));
		String[] pks = new String[list.size()];
		for (int i = 0; i < pks.length; i++) {
			pks[i] = list.get(i);
		}
		return pks;
	}

	@SuppressWarnings("unused")
	private String[] getPksUsingVOList(String sqlWhere) throws DAOException {
		@SuppressWarnings("unchecked")
		List<CountryVO> list = (List<CountryVO>) new BaseDAO()
				.retrieveByClause(CountryVO.class, sqlWhere,
						new String[] { "pk_country" });
		String[] pks = new String[list.size()];
		for (int i = 0; i < pks.length; i++) {
			pks[i] = list.get(i).getPk_country();
		}
		return pks;
	}

}
