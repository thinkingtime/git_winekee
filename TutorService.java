package com.founder.eastlaser.item.service.tutor;

import java.util.List;
import java.util.Map;

import com.founder.eastlaser.common.constants.ConstantsHelper.DataConfigType;
import com.founder.eastlaser.item.domain.ServItem;
import com.founder.eastlaser.item.domain.StuIdea;
import com.founder.eastlaser.item.domain.TchIdea;
import com.founder.eastlaser.item.domain.Tutor;
import com.founder.eastlaser.item.domain.TutorStuApply;
import com.founder.eastlaser.item.domain.TutorStuTimetable;
import com.founder.eastlaser.item.domain.TutorTchApply;
import com.founder.eastlaser.item.domain.TutorTchTimetable;
import com.founder.eastlaser.item.query.ServItemQuery;
import com.founder.eastlaser.item.query.StuIdeaQuery;
import com.founder.eastlaser.item.query.TchIdeaQuery;
import com.founder.eastlaser.item.query.TutorQuery;
import com.founder.eastlaser.item.query.TutorStuApplyQuery;
import com.founder.eastlaser.item.query.TutorStuTimetableQuery;
import com.founder.eastlaser.item.query.TutorTchTimetableQuery;

public interface TutorService {

	public  Integer addTchApply(TutorTchApply tchApply);

	public  List<TutorTchApply> findTchApplyByTeacherId(long tchid);

	/**
	 * 辅导课程：新增11
	 * @param tutor
	 * @return
	 */
	public long addTutor(Tutor tutor);

	/**
	 * 辅导课程：查询单个记录1122
	 * @param id
	 * @return
	 */
	public  Tutor findTutorById(long id);

	/**
	 * 辅导课程：查询
	 * @param tutorQuery
	 * @return
	 */
	public  List<Tutor> findTutors(TutorQuery tutorQuery);

	/**
	 * 辅导课程：查询总记录数
	 * @param tutorQuery
	 * @return
	 */
	public Integer findTutorCount(TutorQuery tutorQuery);

	/**
	 * 辅导课程：分页查询
	 * @param tutorQuery
	 * @return
	 */
	public  List<Tutor> findTutor4Page(TutorQuery tutorQuery);

	/**
	 * 辅导课程:更新
	 * @param id
	 * @return
	 */
	public int updateTutorshipTeacherApplyById(long id);
	
	//public  List<TutorshipStudentApplyDO> getTutorshipStudentApplys(TutorshipStudentApplyQuery studentApplyQuery);

	/**
	 * 教师课程表：查询
	 * @param tchTimetableQuery
	 * @return
	 */
	public  List<TutorTchTimetable> findTchTimetable(TutorTchTimetableQuery tchTimetableQuery);

	/**
	 * 学生申请：新增
	 * @param stuApply
	 * @param tutor
	 * @return
	 */
	public  long stuApply(TutorStuApply stuApply, Tutor tutor);

	/**
	 * 学生申请：更新
	 * @param stuApply
	 * @param tutor
	 * @return
	 */
	public int updateStuApply(TutorStuApply stuApply, Tutor tutor);
	
	/**
	 * 学生申请：更新
	 * @param stuApply
	 * @param tutor
	 * @return
	 */
	public int updateStuApply(TutorStuApply stuApply);

	/**
	 * 学生申请：查询
	 * @param stuApplyQuery
	 * @return
	 */
	public  List<TutorStuApply> findStuApplys(TutorStuApplyQuery stuApplyQuery);

	/**
	 * 学生申请：查询总记录数
	 * @param stuApplyQuery
	 * @return
	 */
	public Integer findStuApplyCount(TutorStuApplyQuery stuApplyQuery);

	/**
	 * 学生申请：分页查询
	 * @param stuApplyQuery
	 * @return
	 */
	public List<TutorStuApply> findStuApply4Page(TutorStuApplyQuery stuApplyQuery);

	/**
	 * 学生课程表：更新
	 * @param stuTimetable
	 * @return
	 */
	public int updateStuTimetable(TutorStuTimetable stuTimetable);

	/**
	 * 学生课程表：查询
	 * @param stuTimetableQuery
	 * @return
	 */
	public  List<TutorStuTimetable> findStuTimetable(TutorStuTimetableQuery stuTimetableQuery);
	
	/**
	 * 学生课程表：统计总记录数
	 * @param stuTimetableQuery
	 * @return
	 */
	public Integer findStuTTCount(TutorStuTimetableQuery stuTimetableQuery);

	/**
	 * 学生课程表：分页查询
	 * @param stuTimetableQuery
	 * @return
	 */
	public  List<TutorStuTimetable> findStuTimetable4Page(TutorStuTimetableQuery stuTimetableQuery);

	//public  List<TutorshipStudentApplyDO> getTutorshipStudentApplys(TutorshipStudentApplyQuery studentApplyQuery);
	
	/**
	 * 学生心声：查询（未分页）
	 * @param stuIdeaQuery
	 * @return
	 */
	public List<StuIdea> findStuIdea(StuIdeaQuery stuIdeaQuery);

	/**
	 * 学生心声：添加
	 * @param stuIdea
	 * @return
	 */
	public int addStuIdea(StuIdea stuIdea);
	
	/**
	 * 学生心声：统计总记录数
	 * @param sIdeaQuery
	 * @return
	 */
	public Integer findStuIdeaCount(StuIdeaQuery sIdeaQuery);
	
	/**
	 * 学生心声：分页查询
	 * @param sIdeaQuery
	 * @return
	 */
	public List<StuIdea> findStuIdea4Page(StuIdeaQuery sIdeaQuery);
	
	/**
	 * 他山之石：统计总记录数
	 * @param tIdeaQuery
	 * @return
	 */
	public Integer findTchIdeaCount(TchIdeaQuery tIdeaQuery);
	
	/**
	 * 他山之石：分页查询
	 * @param tIdeaQuery
	 * @return
	 */
	public List<TchIdea> findTchIdea4Page(TchIdeaQuery tIdeaQuery);
	
	
	
	/**
	 * 特色服务：查询（未分页）
	 * @param stuIdeaQuery
	 * @return
	 */
	public List<ServItem> findServItem(ServItemQuery servItemQuery);

	/**
	 * 特色服务：添加
	 * @param stuIdea
	 * @return
	 */
	public int addServItem(ServItem servItem);
	/**
	 * 特色服务：统计总记录数
	 * @param sIdeaQuery
	 * @return
	 */
	public Integer findServItemCount(ServItemQuery servItemQuery);
	
	/**
	 * 特色服务：分页查询
	 * @param sIdeaQuery
	 * @return
	 */
	public List<ServItem> findServItem4Page(ServItemQuery servItemQuery);
	
	/**
	 * 数据字典初始化
	 * @return
	 */
	public Map<DataConfigType, List> dataInit();
	/*
	public List<DegreeDO> findDegree();

	public List<SubjectDO> findSubject();

	public List<LiveAreaDO> findArea();

	public List<Grade> findGrade();

	*//**
	 * 数据字典初始化: 教材版本
	 * @return
	 *//*
	public List<VersionDO> findBookVersion();
	*//**
	 * 数据字典初始化: 教师层级
	 * @return
	 *//*
	public List<TeacherlevelDO> findTeacherlevel();
	*//**
	 * 数据字典初始化: 学区
	 * @return
	 *//*
	public List<StudyAreaDO> findStudyArea();
	
	*//**
	 * 数据字典初始化: 考试类型(会考，中考，高考....)
	 * @return
	 *//*
	public List<TypeDO> findUserType();*/
}
