package org.sms.project.roleresource.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.sms.SysConstants;
import org.sms.project.helper.ben.BeanHelper;
import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.sms.project.roleresource.entity.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Sunny
 */
public class RoleResourceDao {

  @Autowired
  private SysJdbcTemplate sysJdbcTemplate;

  /**
   * @param query
   * @param order
   * @param startIndex
   * @param size
   * @return
   */
  public List<RoleResource> getRoleResource(String query, String order, int startIndex, int size) {
    String sql = "SELECT * FROM ROLE_RESOURCE";
    List<RoleResource> list = sysJdbcTemplate.queryPage(sql, startIndex, size);
    return list;
  }

  /**
   * @param roleResource
   * @return
   */
  public int insert(RoleResource roleResource) {
    String sql = "INSERT INTO ROLE_RESOURCE(ID, ROLE_ID, RESOURCE_ID, USABLE_STATUS, CREATE_USER_ID) VALUES(?,?,?,?,?)";
    Object[] params = new Object[] { roleResource.getId(), roleResource.getRole_id(), roleResource.getResource_id(), SysConstants.ENABLE,
        roleResource.getCreate_user_id() };
    int[] types = new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param roleResource
   * @return
   */
  public int update(RoleResource roleResource) {
    String sql = "UPDATE ROLE_RESOURCE SET USABLE_STATUS = ?, MODIFY_DATE = ?, MODIFY_USER_ID = ?  WHERE ID = ?";
    Object[] params = new Object[] { roleResource.getUsable_status(), roleResource.getModify_date(), roleResource.getModify_user_id(), roleResource.getId() };
    int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @return
   */
  public int delete(long id) {
    String sql = "DELETE FROM ROLE_RESOURCE WHERE ID = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.INTEGER };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param id
   * @return
   */
  public RoleResource findById(long id) {
    return sysJdbcTemplate.queryForObject("SELECT ID,ROLE_ID,RESOURCE_ID,CREATE_DATE FROM ROLE_RESOURCE WHERE ID = ?", new Object[] { id },
        new RowMapper<RoleResource>() {
          @Override
          public RoleResource mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoleResource roleResource = new RoleResource();
            BeanHelper.INSTANCE.buildBean(roleResource, rs);
            return roleResource;
          }
        });
  }
}