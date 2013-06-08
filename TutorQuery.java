package com.founder.eastlaser.item.query;

import java.util.Date;

import com.founder.eastlaser.common.base.BaseQuery;

/**
 * @author caohh
 * 
 */
public class TutorQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 课程名称
	 */
	private String name;

	/**
	 * 地区
	 */
	private Long studyAreaId;

	/**
	 * 学段
	 */
	private Long degreeId;

	/**
	 * 年级
	 */
	private Long gradeId;

	/**
	 * 科目
	 */
	private Long subjectId;

	/**
	 * 教材版本
	 */
	private Long bookVersion;

	/**
	 * 知识水平
	 */
	private Long knowledgeLevel;

	/**
	 * 教师等级/职称
	 */
	private Long teacherId;
	
	/**
	 * 教师等级/职称
	 */
	private Long teacherLevel;

	/**
	 * 课程开始时间
	 */
	private Date startdate;

	/**
	 * 课程结束时间
	 */
	private Date enddate;

	/**
	 * 总课时数
	 */
	private Long classNum;

	/**
	 * 单节课时间
	 */
	private Long classTime;

	/**
	 * 接受学生数
	 */
	private Long studentNum;

	/**
	 * 教师特长
	 */
	private String teacherDesc;

	/**
	 * 课程描述
	 */
	private String tutorshipDesc;

	/**
	 * 状态
	 */
	private Long status;

	/**
	 * 类型(一对一/成长小班)
	 */
	private String type;

	/**
	 * 需预支付金额
	 */
	private Double prepayAmount;

	/**
	 * 单节课金额
	 */
	private Double perclassAmount;

	/**
	 * 教师备注
	 */
	private String teacherRemark;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 课程表(list)
	 */
	private String timetable;

	/**
	 * 最后更新时间
	 */
	private Date lastModifyTime;

	/**
	 * 审核状态
	 */
	private Long verifyStatus;

	/**
	 * 审核人
	 */
	private String verifyStaff;

	/**
	 * 审核时间
	 */
	private Date verifyTime;

	/**
	 * 管理员备注
	 */
	private String staffRemark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStudyAreaId() {
		return studyAreaId;
	}

	public void setStudyAreaId(Long studyAreaId) {
		this.studyAreaId = studyAreaId;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getBookVersion() {
		return bookVersion;
	}

	public void setBookVersion(Long bookVersion) {
		this.bookVersion = bookVersion;
	}

	public Long getKnowledgeLevel() {
		return knowledgeLevel;
	}

	public void setKnowledgeLevel(Long knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getTeacherLevel() {
		return teacherLevel;
	}

	public void setTeacherLevel(Long teacherLevel) {
		this.teacherLevel = teacherLevel;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Long getClassNum() {
		return classNum;
	}

	public void setClassNum(Long classNum) {
		this.classNum = classNum;
	}

	public Long getClassTime() {
		return classTime;
	}

	public void setClassTime(Long classTime) {
		this.classTime = classTime;
	}

	public Long getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(Long studentNum) {
		this.studentNum = studentNum;
	}

	public String getTeacherDesc() {
		return teacherDesc;
	}

	public void setTeacherDesc(String teacherDesc) {
		this.teacherDesc = teacherDesc;
	}

	public String getTutorshipDesc() {
		return tutorshipDesc;
	}

	public void setTutorshipDesc(String tutorshipDesc) {
		this.tutorshipDesc = tutorshipDesc;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrepayAmount() {
		return prepayAmount;
	}

	public void setPrepayAmount(Double prepayAmount) {
		this.prepayAmount = prepayAmount;
	}

	public Double getPerclassAmount() {
		return perclassAmount;
	}

	public void setPerclassAmount(Double perclassAmount) {
		this.perclassAmount = perclassAmount;
	}

	public String getTeacherRemark() {
		return teacherRemark;
	}

	public void setTeacherRemark(String teacherRemark) {
		this.teacherRemark = teacherRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTimetable() {
		return timetable;
	}

	public void setTimetable(String timetable) {
		this.timetable = timetable;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Long getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Long verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyStaff() {
		return verifyStaff;
	}

	public void setVerifyStaff(String verifyStaff) {
		this.verifyStaff = verifyStaff;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getStaffRemark() {
		return staffRemark;
	}

	public void setStaffRemark(String staffRemark) {
		this.staffRemark = staffRemark;
	}


}