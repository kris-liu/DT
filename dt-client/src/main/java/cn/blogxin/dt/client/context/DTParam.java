package cn.blogxin.dt.client.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kris
 */
@Data
public class DTParam implements Serializable {

    private static final long serialVersionUID = -6165292202123183828L;

    /**
     * 事务ID
     */
    private String xid;

    /**
     * 事务开始时间
     */
    private Date startTime;

}
