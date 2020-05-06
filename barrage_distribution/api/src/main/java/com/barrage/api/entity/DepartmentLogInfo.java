package com.barrage.api.entity;//package barrage.demo.barrageapi.entity;
//
//import java.util.Date;
//import javax.persistence.*;
//
//@Table(name = "department_log_info")
//public class DepartmentLogInfo {
//    @Id
//    @Column(name = "table_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
//    private Integer tableId;
//
//    @Column(name = "department_id")
//    private Integer departmentId;
//
//    @Column(name = "operator_id")
//    private Integer operatorId;
//
//    @Column(name = "operation_type")
//    private String operationType;
//
//    @Column(name = "operation_content")
//    private String operationContent;
//
//    @Column(name = "create_time")
//    private Date createTime;
//
//    @Column(name = "update_time")
//    private Date updateTime;
//
//    /**
//     * @return table_id
//     */
//    public Integer getTableId() {
//        return tableId;
//    }
//
//    /**
//     * @param tableId 表条目id
//     */
//    public void setTableId(Integer tableId) {
//        this.tableId = tableId;
//    }
//
//    /**
//     * @return department_id 部门id
//     */
//    public Integer getDepartmentId() {
//        return departmentId;
//    }
//
//    /**
//     * @param departmentId 部门id
//     */
//    public void setDepartmentId(Integer departmentId) {
//        this.departmentId = departmentId;
//    }
//
//    /**
//     * @return operator_id 操作者id
//     */
//    public Integer getOperatorId() {
//        return operatorId;
//    }
//
//    /**
//     * @param operatorId 操作者id
//     */
//    public void setOperatorId(Integer operatorId) {
//        this.operatorId = operatorId;
//    }
//
//    /**
//     * @return operation_type 操作类型
//     */
//    public String getOperationType() {
//        return operationType;
//    }
//
//    /**
//     * @param operationType 操作类型
//     */
//    public void setOperationType(String operationType) {
//        this.operationType = operationType;
//    }
//
//    /**
//     * @return operation_content 操作内容
//     */
//    public String getOperationContent() {
//        return operationContent;
//    }
//
//    /**
//     * @param operationContent 操作内容
//     */
//    public void setOperationContent(String operationContent) {
//        this.operationContent = operationContent;
//    }
//
//    /**
//     * @return create_time 创建时间
//     */
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    /**
//     * @param createTime 创建时间
//     */
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    /**
//     * @return update_time 更新时间
//     */
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    /**
//     * @param updateTime 更新时间
//     */
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//}