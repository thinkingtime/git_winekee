/**
 * 课程辅导(一对一&&小班)
 */
package com.founder.eastlaser.www.web.action.tutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.eastlaser.common.base.BaseAction;
import com.founder.eastlaser.common.constants.ConstantsHelper;
import com.founder.eastlaser.common.constants.ConstantsHelper.DataConfigType;
import com.founder.eastlaser.common.utils.TutorUtil;
import com.founder.eastlaser.item.domain.ServItem;
import com.founder.eastlaser.item.domain.StuIdea;
import com.founder.eastlaser.item.domain.TchIdea;
import com.founder.eastlaser.item.domain.Tutor;
import com.founder.eastlaser.item.domain.TutorStuApply;
import com.founder.eastlaser.item.domain.TutorStuTimetable;
import com.founder.eastlaser.item.domain.TutorTchApply;
import com.founder.eastlaser.item.domain.TutorTchTimetable;
import com.founder.eastlaser.item.domain.config.BookVersion;
import com.founder.eastlaser.item.domain.config.Degree;
import com.founder.eastlaser.item.domain.config.ExamType;
import com.founder.eastlaser.item.domain.config.Grade;
import com.founder.eastlaser.item.domain.config.LiveArea;
import com.founder.eastlaser.item.domain.config.StudyArea;
import com.founder.eastlaser.item.domain.config.Subject;
import com.founder.eastlaser.item.domain.config.Teacherlevel;
import com.founder.eastlaser.item.query.ServItemQuery;
import com.founder.eastlaser.item.query.StuIdeaQuery;
import com.founder.eastlaser.item.query.TchIdeaQuery;
import com.founder.eastlaser.item.query.TutorQuery;
import com.founder.eastlaser.item.query.TutorStuApplyQuery;
import com.founder.eastlaser.item.query.TutorStuTimetableQuery;
import com.founder.eastlaser.item.query.TutorTchTimetableQuery;
import com.founder.eastlaser.item.service.tutor.TutorService;
import com.founder.eastlaser.user.domain.UserCustomerDO;
import com.founder.eastlaser.user.domain.attr.TypeDO;
import com.founder.eastlaser.user.service.user.UserService;
import com.itextpdf.text.log.SysoLogger;


public class TutorAction extends BaseAction {
	
	private static final long serialVersionUID = -4826596781278007758L;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private UserService userService;

	@Autowired
	public Tutor tutor;
	
	@Autowired
	public TutorStuApply stuApply;
	
	@Autowired
	public TutorStuTimetable stuTimetable;

	// 每页记录数
	private Integer PAGE_SIZE = 10; 
	
	public Map<String, String> rsMap = new HashMap<String, String>();
	
	public TutorQuery tutorQuery = new TutorQuery();//
	
	// 分页信息
	public String pageBar;
	
	/*
	 * 文件上传相关
	 */
	public File cPlan;//课程教案
	/**
	 * 课程教案上传路径
	 */
	public String planPath = realPath + "//upload/tutor//plan//";
	//文件类型
	public String cPlanContentType;
	//文件名称
	public String cPlanFileName;
	
	/**
	 * 模拟学生登录
	 * @return
	 * @throws IOException 
	 */
	public String slogin() throws IOException{
		UserCustomerDO user = new UserCustomerDO();
		String return_url = getRequestParameter("return_url");
		user.setId(1000l);
		user.setCustype("s");
		user.setDegreeName("小学");
		user.setGradeName("二年级");
		user.setRealname("王同学");
		user.setEmail("stu@founder.com");
		user.setAccount("10000");
		setSessionAttribute("user", user);
		if (return_url.length()>0) {
			getResponse().sendRedirect(return_url);
		}else getResponse().sendRedirect("/tutor-index.htm");
		return "index";
	}
	
	/**
	 * 模拟教师登录
	 * @return
	 * @throws IOException 
	 */
	public String tlogin() throws IOException{
		UserCustomerDO user = new UserCustomerDO();
		String return_url = getRequestParameter("return_url");
		user.setId(2000l);
		user.setCustype("t");
		user.setTealevel("1000000002");
		user.setRealname("张老师");
		user.setEmail("tch@founder.com");
		user.setAccount("10000");
		setSessionAttribute("user", user);
		if (return_url.length()>0) {
			getResponse().sendRedirect(return_url);
		}else 
		getResponse().sendRedirect("/tutor-index.htm");
		return "index";
	}
	
	/**
	 * 模拟教师登录
	 * @return
	 * @throws IOException 
	 */
	public String logout() throws IOException{
		getRequest().getSession().removeAttribute("user");
		String return_url = getRequestParameter("return_url");
		if (return_url.length()>0) {
			getResponse().sendRedirect(return_url);
		}else 
		getResponse().sendRedirect("/tutor-index.htm");
		//getRequest().getSession().invalidate();
		return "index";
	}
	
	/**
	 * 辅导课程首页
	 * @return
	 */
	public String index() {
		if (ConstantsHelper.studyAreaList==null) {
			dataInit();
		}
		//显示课程列表（5行）
		//tutorQuery.setStatus(1l);
		tutorQuery.setPageNum(1);
		tutorQuery.setPageSize(6);
		List<Tutor> tutors = tutorService.findTutor4Page(tutorQuery);
		getRequest().setAttribute("tutors", tutors);
		
		Map<Long, List> tutorMap = new HashMap<Long, List>();
		tutorQuery.setPageSize(10);
		tutorQuery.setStudyAreaId(3000002060l);//学生的areaId
		for (Degree degree : ConstantsHelper.degreeList) {
			tutorQuery.setDegreeId(degree.getId());
			tutorMap.put(degree.getId(), tutorService.findTutor4Page(tutorQuery));
		}
		setRequestAttribute("tutorMap", tutorMap);
		List<StuIdea> stuIdeas = tutorService.findStuIdea(new StuIdeaQuery());
		setRequestAttribute("stuIdeas", stuIdeas);
		List<ServItem> servItems = tutorService.findServItem(new ServItemQuery());
		setRequestAttribute("servItems", servItems);
		TchIdeaQuery tIdeaQuery = new TchIdeaQuery();
		List<TchIdea> tchIdeas = tutorService.findTchIdea4Page(tIdeaQuery);
		setRequestAttribute("tchIdeas", tchIdeas);
		return "index";
	}
	
	
	/**
	 * 学生登录
	 * @param 显示课程列表（5行）；显示最热教师（4行）；显示学生信息
	 * @return
	 */
	public String stuLogin() {
		//显示课程列表（5行）
		TutorQuery tutorQuery = new TutorQuery();
		tutorQuery.setStudyAreaId(3000002060l);//学生的areaId
		tutorQuery.setStatus(1l);
		tutorQuery.setPageNum(1);
		tutorQuery.setPageSize(6);
		List<Tutor> tutors = tutorService.findTutor4Page(tutorQuery);
		List<StuIdea> stuIdeas = tutorService.findStuIdea(new StuIdeaQuery());
		setRequestAttribute("stuIdeas", stuIdeas);
		List<ServItem> servItems = tutorService.findServItem(new ServItemQuery());
		setRequestAttribute("servItems", servItems);
		getRequest().setAttribute("tutors", tutors);
		tutorQuery.setPageSize(10);
		TutorQuery tutorQuery_prim = tutorQuery;
		tutorQuery_prim.setDegreeId(100l);//小学的id：100
		List<Tutor> tutors_prim = tutorService.findTutor4Page(tutorQuery_prim);
		setRequestAttribute("tutors_prim", tutors_prim);
		TutorQuery tutorQuery_jun = tutorQuery;
		tutorQuery_jun.setDegreeId(101l);//初中的id：101
		List<Tutor> tutors_jun = tutorService.findTutor4Page(tutorQuery_jun);
		setRequestAttribute("tutors_jun", tutors_jun);
		TutorQuery tutorQuery_sen = tutorQuery;
		tutorQuery_sen.setDegreeId(102l);//初中的id：101
		List<Tutor> tutors_sen = tutorService.findTutor4Page(tutorQuery_sen);
		setRequestAttribute("tutors_sen", tutors_sen);
		
		/*//显示学生信息
		//UserCustomerDO stuInfo = userService.findCustomerById(1000000004l);
		//getRequest().setAttribute("stuInfo", stuInfo);
		//显示最热教师（4行）
		UserCustomerQuery userCustomerQuery = new UserCustomerQuery();
		userCustomerQuery.setCustype("USER_TEA");
		userCustomerQuery.setRownum(4l);
		List<UserCustomerDO> teacInfos = userService.findCustomers(userCustomerQuery);
		getRequest().setAttribute("teacInfos", teacInfos);*/

		return "stuLogin";
	}
	
	/**
	 * 教师申请一对一
	 * @return
	 */
	public String tchApply() {
		return "tchApply";
	}
	/**
	 * 教师申请一对一
	 * @return
	 */
	public String teacher_Apply() {
		return "teacher_Apply";
	}

	/**
	 * 教师申请成为一对一教师
	 * @return
	 */
	public String tchApplyed() {
		TutorTchApply tchApply = new TutorTchApply();
		tchApply.setAreaId(Long.valueOf(getRequestParameter("tutor.areaId")));
		tchApply.setDegreeId(Long.valueOf(getRequestParameter("tutor.degreeId")));
		tchApply.setGradeId(Long.valueOf(getRequestParameter("tutor.gradeId")));
		tchApply.setSubjectId(Long.valueOf(getRequestParameter("tutor.subjectId")));
		tchApply.setTutorshipDesc(getRequestParameter("tutor.tutorDesc"));
		tchApply.setTeacherDesc(getRequestParameter("tutor.tchDesc"));
		try{
			tutorService.addTchApply(tchApply);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}
	
	
	/**
	 * 教师开设辅导课程
	 * @return
	 */
	public String set() {
		return "setting";
	}

	/**
	 * 开设课程：设置完成
	 * @return
	 * @throws IOException 
	 */
	public String setting() throws IOException {
		UserCustomerDO tch = (UserCustomerDO)getSession("user");
		tutor.setTchName(tch.getRealname());
		tutor.setTeacherId(tch.getId());
		tutor.setTeacherLevel(1000000002l);
		long tid = tutorService.addTutor(tutor);
		rsMap.put("msg", "课程设置成功!");
		rsMap.put("href1", "/");
		rsMap.put("href2", "/tutor-detail.htm?id="+ tid);
		getRequest().getSession().setAttribute("rsMap", rsMap);
		getResponse().sendRedirect("/tutor-success.htm");
		return "success";
	}

	/**
	 * 辅导课程列表页(分页)
	 * @return
	 */
	public String list() {
		
		Integer count = tutorService.findTutorCount(tutorQuery);
		this.paginator.init(count, PAGE_SIZE);
		tutorQuery.setPageNum(this.paginator.getCurrentPage());
		tutorQuery.setPageSize(PAGE_SIZE);
		if (count > 0) {
			List<Tutor> tutorList = tutorService.findTutor4Page(tutorQuery);
			setRequestAttribute("tutorList", tutorList);
			pageBar = paginator.getMmyPageinatorBar();
		}
		return "list";
	}
	
	/**
	 * 辅导课程列表页：教师所见；(分页)
	 * @return
	 */
	public String tlist() {
		//地区
		String studyAreaId = getRequestParameter("studyAreaId");
		if (studyAreaId.length()>0) {
			tutorQuery.setStudyAreaId(Long.parseLong(studyAreaId));
		}
		//学段
		String degreeId = getRequestParameter("degreeId");
		if (degreeId.length()>0) {
			tutorQuery.setDegreeId(Long.parseLong(degreeId));
		}
		//年级
		String gradeId = getRequestParameter("gradeId");
		if (gradeId.length()>0) {
			tutorQuery.setGradeId(Long.parseLong(gradeId));
		}
		//学科
		String subjectId = getRequestParameter("subjectId");
		if (subjectId.length()>0) {
			tutorQuery.setStudyAreaId(Long.parseLong(subjectId));
		}
		//课程名称
		String name = getRequestParameter("name");
		if (name.length()>0) {
			tutorQuery.setName(name);
		}
		Integer count = tutorService.findTutorCount(tutorQuery);
		this.paginator.init(count, PAGE_SIZE);
		tutorQuery.setPageNum(this.paginator.getCurrentPage());
		tutorQuery.setPageSize(PAGE_SIZE);
		if (count > 0) {
			List<Tutor> tutorList = tutorService.findTutor4Page(tutorQuery);
			setRequestAttribute("tutorList", tutorList);
			pageBar = paginator.getMmyPageinatorBar();
		}
		
		return "tlist";
	}

	/**
	 * 辅导课程详情
	 * @return
	 */
	public String detail() {
		String id = getRequestParameter("id");
		if (id.length() > 0 && StringUtils.isNumeric(id)) {
			tutor = tutorService.findTutorById(Long.parseLong(id));
			TutorTchTimetableQuery tchTTQuery = new TutorTchTimetableQuery();
			tchTTQuery.setTutorshipId(Long.parseLong(id));
			List<TutorTchTimetable> tchTimetables = tutorService.findTchTimetable(tchTTQuery);
			tutor.setTchTTList(tchTimetables);
			Map<Long, Long> timetableMap = new HashMap<Long, Long>();
			//List<Long> timeids = new ArrayList<Long>();
			for (TutorTchTimetable tt : tchTimetables) {
				//timeids.add(tt.getStarttimeId());
				//timeids.add(tt.getEndtimeId());
				timetableMap.put(tt.getStarttimeId(), tt.getStatus());
				timetableMap.put(tt.getEndtimeId(), tt.getStatus());
			}
			tutor.setTchTTMap(timetableMap);
			//tutor.setTimeids(timeids);
			String html = TutorUtil.getTimeTableHtml(tutor);
			setRequestAttribute("html", html);
		}
		return "detail";
	}
	
	/**
	 * 辅导课程--学生申请
	 * @return
	 * @throws IOException 
	 */
	public String sApply() throws IOException {
		UserCustomerDO stu = (UserCustomerDO)getSession("user");
		stuApply.setStudentId(stu.getId());
		tutor = tutorService.findTutorById(stuApply.getTutorshipId());
		long id = tutorService.stuApply(stuApply, tutor);
		 
		rsMap.put("msg", "申请成功");
		rsMap.put("href1", "/");
		rsMap.put("href2", "/tutor-saDetail.htm?id="+ id);
		getRequest().getSession().setAttribute("rsMap", rsMap);
		getResponse().sendRedirect("/tutor-success.htm");
		return "success";
	}
	
	/**
	 * 辅导课程--学生申请()
	 * @return
	 * @throws IOException 
	 */
	public String spreApply() throws IOException {
		String rs = "";
		String tutorId = getRequestParameter("tutorshipId");
		TutorTchTimetableQuery ttQuery = new TutorTchTimetableQuery();
		ttQuery.setTutorshipId(Long.parseLong(tutorId));
		ttQuery.setStatus(2l);
		List<TutorTchTimetable> ttList = tutorService.findTchTimetable(ttQuery);
		String timetable = getRequestParameter("timetable");
		String timetables[] = timetable.split(",");
		List<Long> tidList = new ArrayList<Long>();//已经被选择的时间段id
		if (ttList!=null && ttList.size()>0) {
			for (TutorTchTimetable ttable : ttList) {
				tidList.add(ttable.getStarttimeId());
				tidList.add(ttable.getEndtimeId());
			}
		}
		for (int i = 0; i < timetables.length; i++) {
			if (tidList.contains(Long.parseLong(timetables[i]))) {
				rs = "您选择了已被申请的课程表，请刷新后重新选择";
				break;
			}
		}
		setRequestAttribute("rs", rs);
		return "ajaxReturn";
	}
	
	public TutorStuApplyQuery saQuery = new TutorStuApplyQuery(); 
	/**
	 * 辅导课程列表页(分页)
	 * @return
	 */
	public String saList() {
		//stuApplyQuery.setTeacherId(101l);
		//stuApplyQuery.setTutorshipId(0l);
		
		UserCustomerDO user = (UserCustomerDO)getSession("user");
		if (user.getCustype().equals("s")) {
			saQuery.setStudentId(user.getId());
			//saQuery.setCourseStatus(2l);
		}
		if (user.getCustype().equals("t")) {
			saQuery.setTeacherId(user.getId());
		}
		
		Integer count = tutorService.findStuApplyCount(saQuery);
		this.paginator.init(count, PAGE_SIZE);
		saQuery.setPageNum(this.paginator.getCurrentPage());
		saQuery.setPageSize(PAGE_SIZE);
		if (count > 0) {
			List<TutorStuApply> saList = tutorService.findStuApply4Page(saQuery);
			setRequestAttribute("saList", saList);
			pageBar = paginator.getMmyPageinatorBar();
		}
		
		return "saList";
	}

	/**
	 * 辅导课程--学生申请--查看申请详情
	 * @return
	 */
	public String saDetail() {
		String id = getRequestParameter("id");
		if (id.length() > 0 && StringUtils.isNumeric(id)) {
			TutorStuApplyQuery saQuery = new TutorStuApplyQuery();
			saQuery.setId(Long.parseLong(id));
			List<TutorStuApply> stuApplies = tutorService.findStuApplys(saQuery);
			if (stuApplies != null && stuApplies.size() > 0) {
				TutorStuApply stuApply = stuApplies.get(0);
				tutor = tutorService.findTutorById(stuApply.getTutorshipId());
				
				//查询教师课程表
				Map<Long, Long> ttMap = new HashMap<Long, Long>();
				TutorTchTimetableQuery tchTTQuery = new TutorTchTimetableQuery();
				tchTTQuery.setTutorshipId(tutor.getId());
				List<TutorTchTimetable> tchTTList = tutorService.findTchTimetable(tchTTQuery);
				for (TutorTchTimetable tt : tchTTList) {
					ttMap.put(tt.getStarttimeId(), tt.getStatus());
					ttMap.put(tt.getEndtimeId(), tt.getStatus());
				}
				tutor.setTchTTMap(ttMap);
				//查询学生课程表
				ttMap = new HashMap<Long, Long>();
				TutorStuTimetableQuery stuTTQuery = new TutorStuTimetableQuery();
				stuTTQuery.setStudentAplyId(stuApply.getId());
				List<TutorStuTimetable> stuTTList = tutorService.findStuTimetable(stuTTQuery);
				for (TutorStuTimetable tt : stuTTList) {
					ttMap.put(tt.getStarttimeId(), 2l);
					ttMap.put(tt.getEndtimeId(), 2l);
				}
				tutor.setStuTTMap(ttMap);
				String html = TutorUtil.getTimeTableHtml(tutor);
				setRequestAttribute("html", html);
				setRequestAttribute("stuApply", stuApply);
			}
		}
		return "saDetail";
	}
	
	/**
	 * 学生申请--教师审核
	 * @return
	 */
	public String saVerify() {
		String said = getRequestParameter("said");
		if (said.length()>0 && StringUtils.isNumeric(said)) {
			TutorStuApplyQuery saQuery = new TutorStuApplyQuery();
			saQuery.setId(Long.parseLong(said));
			List<TutorStuApply> stuApplies = tutorService.findStuApplys(saQuery);
			if (stuApplies!=null && stuApplies.size()>0) {
				TutorStuApply stuApply = stuApplies.get(0);
				tutor = tutorService.findTutorById(stuApply.getTutorshipId());
				
				//查询教师课程表
				Map<Long, Long> ttMap = new HashMap<Long, Long>();
				TutorTchTimetableQuery tchTTQuery = new TutorTchTimetableQuery();
				tchTTQuery.setTutorshipId(tutor.getId());
				List<TutorTchTimetable> tchTTList = tutorService.findTchTimetable(tchTTQuery);
				for (TutorTchTimetable tt : tchTTList) {
					ttMap.put(tt.getStarttimeId(), tt.getStatus());
					ttMap.put(tt.getEndtimeId(), tt.getStatus());
				}
				tutor.setTchTTMap(ttMap);
				//查询学生课程表
				ttMap = new HashMap<Long, Long>();
				TutorStuTimetableQuery stuTTQuery = new TutorStuTimetableQuery();
				stuTTQuery.setStudentAplyId(stuApply.getId());
				List<TutorStuTimetable> stuTTList = tutorService.findStuTimetable(stuTTQuery);
				for (TutorStuTimetable tt : stuTTList) {
					ttMap.put(tt.getStarttimeId(), 2l);
					ttMap.put(tt.getEndtimeId(), 2l);
				}
				tutor.setStuTTMap(ttMap);
				String html = TutorUtil.getTimeTableHtml(tutor);
				setRequestAttribute("html", html);
				
				setRequestAttribute("stuApply", stuApply);
				setRequestAttribute("said", stuApply.getId());
			}
		}
		return "saVerify";
	}
	
	/**
	 * 学生申请--课程设置/上传教案
	 * @return
	 */
	public String crSet() {
		String said = getRequestParameter("said");//学生申请id
		if (said.length()>0 && StringUtils.isNumeric(said)) {
				TutorStuApplyQuery saQuery = new TutorStuApplyQuery();
				saQuery.setId(Long.parseLong(said));
				List<TutorStuApply> saList = tutorService.findStuApplys(saQuery);
				if (saList.size()>0) {
					stuApply = saList.get(0);
					setRequestAttribute("stuApply", stuApply);
			
					TutorStuTimetableQuery stuTTQuery = new TutorStuTimetableQuery();
					stuTTQuery.setStudentAplyId(Long.parseLong(said));
					Integer count = tutorService.findStuTTCount(stuTTQuery);
					this.paginator.init(count, PAGE_SIZE);
					stuTTQuery.setPageNum(this.paginator.getCurrentPage());
					stuTTQuery.setPageSize(PAGE_SIZE);
					if (count > 0) {
						List<TutorStuTimetable> stuTTList = tutorService.findStuTimetable4Page(stuTTQuery);
						setRequestAttribute("stuTTList", stuTTList);
						pageBar = paginator.getMmyPageinatorBar();
					}
				}
			}
		return "crSet";
	}
	
	/**
	 * 课程设置(课程名称，课程教案上传)
	 * @return
	 * @throws IOException
	 */
	public String crSeting() throws IOException{
		String said = getRequestParameter("said"); //学生申请id
		String stid = getRequestParameter("stid"); //学生课程表id
		String courseName = getRequestParameter("courseName"); //课程名称
		String programme = getRequestParameter("programme"); 
		if (stid.length()>0 && StringUtils.isNumeric(stid)) {
			TutorStuTimetable sTT = new TutorStuTimetable();
			sTT.setId(Long.parseLong(stid));
			sTT.setCourseName(courseName);//课程名称
			sTT.setProgramme(programme);//教学计划
			sTT.setCourseStatus(2l);//教学计划
			if (cPlanFileName!=null && StringUtils.trim(cPlanFileName).length()>0) {//上传教案
				sTT.setCoursePlan(cPlanFileName);
				File destDir = new File(planPath + cPlanFileName);
				FileUtils.copyFile(cPlan, destDir);
			}
			tutorService.updateStuTimetable(sTT);
			//getResponse().setContentType("text/html");
			setRequestAttribute("rs", cPlanFileName);
			stuApply.setId(Long.parseLong(said));
			stuApply.setCourseStatus(1l);
			tutorService.updateStuApply(stuApply);
		}
		return "ajaxReturn";
	}
	/**
	 * 课程设置(课程名称，课程教案上传)
	 * @return
	 * @throws IOException
	 */
	public String crVerify() throws IOException{
		String said = getRequestParameter("said"); //学生申请id
		stuApply.setId(Long.parseLong(said));
		stuApply.setCourseStatus(2l);
		tutorService.updateStuApply(stuApply);
		getResponse().sendRedirect("/tutor-saList.htm");
		setRequestAttribute("rs", "");
		return "ajaxReturn";
	}

	/**
	 * 辅导课程--学生申请--查看课程详情/下载教案
	 * @return
	 */
	public String crDetail() {
		String said = getRequestParameter("id");//学生申请id
		if (said.length()>0 && StringUtils.isNumeric(said)) {
			TutorStuTimetableQuery stuTTQuery = new TutorStuTimetableQuery();
			stuTTQuery.setStudentAplyId(Long.parseLong(said));
			Integer count = tutorService.findStuTTCount(stuTTQuery);
			this.paginator.init(count, PAGE_SIZE);
			tutorQuery.setPageNum(this.paginator.getCurrentPage());
			tutorQuery.setPageSize(PAGE_SIZE);
			if (count > 0) {
				List<TutorStuTimetable> stuTimetables = tutorService.findStuTimetable4Page(stuTTQuery);
				setRequestAttribute("stuTimetables", stuTimetables);
				pageBar = paginator.getMmyPageinatorBar();
			}
		}
		return "crDetail";
	}
	
	/**
	 * 学生查看课程设置/下载教案
	 * @return
	 */
	public String scrList() {
		String said = getRequestParameter("said");//学生申请id
		if (said.length()>0 && StringUtils.isNumeric(said)) {
			TutorStuTimetableQuery stuTTQuery = new TutorStuTimetableQuery();
			stuTTQuery.setStudentAplyId(Long.parseLong(said));
			Integer count = tutorService.findStuTTCount(stuTTQuery);
			this.paginator.init(count, PAGE_SIZE);
			stuTTQuery.setPageNum(this.paginator.getCurrentPage());
			stuTTQuery.setPageSize(PAGE_SIZE);
			if (count > 0) {
				List<TutorStuTimetable> stuTTList = tutorService.findStuTimetable4Page(stuTTQuery);
				setRequestAttribute("stuTTList", stuTTList);
				pageBar = paginator.getMmyPageinatorBar();
			}
		}
		return "scrList";
	}
	
	/**
	 * 学生是否同意课程设置
	 * @return
	 * @throws IOException 
	 */
	public String scrVerify() throws IOException {
		tutorService.updateStuApply(stuApply);
		
		if (stuApply.getCourseStatus() == 5) {//同意课程设置
			TutorStuTimetableQuery stuTTQuery = new TutorStuTimetableQuery();
			stuTTQuery.setStudentAplyId(stuApply.getId());
			Integer count = tutorService.findStuTTCount(stuTTQuery);
			
			Map<String, String> order = new HashMap<String, String>();
			order.put("count", String.valueOf(count));
			order.put("rid", String.valueOf(stuApply.getId()));
			order.put("tamount", String.valueOf(count*20));
			order.put("pamount", "20");
			setRequestAttribute("order", order);
			return "orderpay";
		}
		getResponse().sendRedirect("/tutor-saList.htm");
		return "success";
	}
	
	/**
	 * 教案下载
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String pdownload() throws UnsupportedEncodingException {
		String stid = getRequestParameter("stid");
		if (stid.length()>0 && StringUtils.isNumeric(stid)) {
			TutorStuTimetableQuery stuTimetableQuery = new TutorStuTimetableQuery();
			stuTimetableQuery.setId(Long.parseLong(stid));
			List<TutorStuTimetable> list = tutorService.findStuTimetable(stuTimetableQuery);
			if (list!=null && list.size()>0) {
				stuTimetable = list.get(0); 
				cPlanFileName = stuTimetable.getCoursePlan();
			}else {
				setRequestAttribute("rs", "文件不存在或您没有下载权限!");
				return "ajaxReturn";
			}
		}else {
			setRequestAttribute("rs", "文件不存在或您没有下载权限!");
			return "ajaxReturn";
		}
		return "download";
	}

	/**
	 *  获得下载文件的内容，可以直接读入一个物理文件或从数据库中获取内容
	 * @return
	 * @throws Exception
	 */
	public InputStream getDownloadFile() throws Exception {
		String tempName = cPlanFileName;
		cPlanFileName = new String(cPlanFileName.getBytes(), "ISO8859-1");//解决文件中文名乱码问题
		return new FileInputStream(planPath + tempName); //读取文件时使用文件原名(未经编码的)
	}
	
	/**
	 * 通用页面：作为表单提交后的中间页面
	 * @return
	 */
	public String success(){
		return "success";
	}
	
	/**
	 * 学生申请审核
	 * @return
	 * @throws IOException
	 */
	public String verify() throws IOException{
		/*String id = getRequestParameter("id");
		if (id.length()>0 && StringUtils.isNumeric(id)) {
			TutorStuApplyQuery stuApplyQuery = new TutorStuApplyQuery();
			stuApplyQuery.setId(Long.parseLong(id));
			List<TutorStuApply> stuApplies = tutorService.findStuApplys(stuApplyQuery);
			if (stuApplies!=null && stuApplies.size()>0) {
				TutorStuApply stuApply = stuApplies.get(0);
				tutor = tutorService.findTutorById(stuApply.getTutorshipId());
				TutorStuTimetableQuery stuTimetableQuery = new TutorStuTimetableQuery();
				stuTimetableQuery.setStudentAplyId(stuApply.getId());
				List<TutorStuTimetable> stuTimetables = tutorService.findStuTimetable(stuTimetableQuery);
				tutor.setStuTTList(stuTimetables);
				List<Long> timeids = new ArrayList<Long>(); 
				for (TutorStuTimetable timetable : stuTimetables) {
					timeids.add(timetable.getStarttimeId());
				}
				tutor.setTimeids(timeids);
			}
		}*/
		tutorService.updateStuApply(stuApply, tutor);
		//getRequest().getSession().setAttribute("rsMap", rsMap);
		getResponse().sendRedirect("/tutor-saList.htm");
		return "success";
	}


	/**
	 * 学生发表心声
	 * @return
	 */
	public String addStuIdea() {
		return "addStuIdea";
	}
	public String addSIdea(){
		StuIdea stuIdea = new StuIdea();
		stuIdea.setTitle(getRequestParameter("ideatitle"));
		stuIdea.setDescript(getRequestParameter("ideacontent"));
		try{
			tutorService.addStuIdea(stuIdea);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}
	
	/**
	/**
	 * 学生获取心声列表
	 * @return
	 * @throws ParseException 
	 */
	public String milist() throws ParseException{
		StuIdeaQuery stuIdeaQuery = new StuIdeaQuery();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (getRequestParameter("ideatitle")!="") {
			rsMap.put("title", getRequestParameter("ideatitle"));
			stuIdeaQuery.setTitle(getRequestParameter("ideatitle"));
		}
		if (getRequestParameter("begintime")!="") {
			rsMap.put("begintime", getRequestParameter("begintime"));
		Date begintime=(Date) df.parse(getRequestParameter("begintime")+" 00:00:00");
		stuIdeaQuery.setBegintime(begintime);
		}
		if (getRequestParameter("endtime")!="") {
			rsMap.put("endtime", getRequestParameter("endtime"));
			Date endtime=(Date) df.parse(getRequestParameter("endtime")+" 23:59:59");
			stuIdeaQuery.setEndtime(endtime);
		}
		setRequestAttribute("stuIdeaQ", rsMap);
		List<StuIdea> stuIdeas = tutorService.findStuIdea(stuIdeaQuery);
		setRequestAttribute("stuIdeas", stuIdeas);
		return "milist";
	}
	
	/**
	 * 学生心声详情
	 * @return
	 */
	public String myIdea(){
		StuIdeaQuery stuIdeaQuery = new StuIdeaQuery();
		stuIdeaQuery.setId(Long.valueOf(getRequestParameter("id")));
		List<StuIdea> stuIdeas = tutorService.findStuIdea(stuIdeaQuery);
		StuIdea stuIdea1 = new StuIdea();
		stuIdea1=stuIdeas.get(0);
		setRequestAttribute("stuIdea1", stuIdea1);
		return "myIdea";
	}

	
	/**
	 * 学生心声：分页查询
	 * @return
	 */
	public String siList() {
		StuIdeaQuery sIdeaQuery = new StuIdeaQuery();
		//标题
		String title = getRequestParameter("title");
		if (title.length()>0) {
			sIdeaQuery.setTitle(title);
		}
		/*String ctime = getRequestParameter("ctime");
		if (ctime.length()>0) {
			sIdeaQuery.setBegintime(ctime);
		}*/
		Integer count = tutorService.findStuIdeaCount(sIdeaQuery);
		this.paginator.init(count, PAGE_SIZE);
		sIdeaQuery.setPageNum(this.paginator.getCurrentPage());
		sIdeaQuery.setPageSize(PAGE_SIZE);
		if (count > 0) {
			List<StuIdea> siList = tutorService.findStuIdea4Page(sIdeaQuery);
			setRequestAttribute("siList", siList);
			pageBar = paginator.getMmyPageinatorBar();
		}
		return "siList";
	}
	
	/**
	 * 他山之石：分页查询
	 * @return
	 */
	public String tiList() {
		TchIdeaQuery tIdeaQuery = new TchIdeaQuery();
		//标题
		String title = getRequestParameter("title");
		if (title.length()>0) {
			tIdeaQuery.setTitle(title);
		}
		/*String ctime = getRequestParameter("ctime");
		if (ctime.length()>0) {
			sIdeaQuery.setBegintime(ctime);
		}*/
		Integer count = tutorService.findTchIdeaCount(tIdeaQuery);
		this.paginator.init(count, PAGE_SIZE);
		tIdeaQuery.setPageNum(this.paginator.getCurrentPage());
		tIdeaQuery.setPageSize(PAGE_SIZE);
		if (count > 0) {
			List<TchIdea> tiList = tutorService.findTchIdea4Page(tIdeaQuery);
			setRequestAttribute("tiList", tiList);
			pageBar = paginator.getMmyPageinatorBar();
		}
		return "tiList";
	}
	
	/**
	 * 他山之石详情
	 * @return
	 */
	public String tchIdea(){
		TchIdeaQuery tchIdeaQuery = new TchIdeaQuery();
		tchIdeaQuery.setId(Long.valueOf(getRequestParameter("id")));
		List<TchIdea> tchIdeas = tutorService.findTchIdea4Page(tchIdeaQuery);
		TchIdea tchIdea = new TchIdea();
		tchIdea = tchIdeas.get(0);
		setRequestAttribute("stuIdea1", tchIdea);
		return "myIdea";
	}
	
	/**
	 * 发表特色服务
	 * @return
	 */
	public String addServ() {
		return "addServ";
	}
	public String addServI(){
		ServItem servItem = new ServItem();
		servItem.setTitle(getRequestParameter("servtitle"));
		servItem.setDescript(getRequestParameter("ideacontent"));
		try{
			tutorService.addServItem(servItem);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}
	
	/**
	 * 获取特色服务列表
	 * @return
	 * @throws ParseException 
	 */
	public String silist() throws ParseException{
		ServItemQuery servItemQuery = new ServItemQuery();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (getRequestParameter("servtitle")!="") {
			rsMap.put("title", getRequestParameter("ideatitle"));
			servItemQuery.setTitle(getRequestParameter("ideatitle"));
		}
		if (getRequestParameter("begintime")!="") {
			rsMap.put("begintime", getRequestParameter("begintime"));
		Date begintime=(Date) df.parse(getRequestParameter("begintime")+" 00:00:00");
		servItemQuery.setBegintime(begintime);
		}
		if (getRequestParameter("endtime")!="") {
			rsMap.put("endtime", getRequestParameter("endtime"));
			Date endtime=(Date) df.parse(getRequestParameter("endtime")+" 23:59:59");
			servItemQuery.setEndtime(endtime);
		}
		setRequestAttribute("servItemQ", rsMap);
		List<ServItem> servItems = tutorService.findServItem(servItemQuery);
		setRequestAttribute("servItems", servItems);
		return "silist";
	}
	
	/**
	 * 特殊服务详情
	 * @return
	 */
	public String servItem(){
		ServItemQuery servItemQuery = new ServItemQuery();
		servItemQuery.setId(Long.valueOf(getRequestParameter("id")));
		List<ServItem> servItems = tutorService.findServItem(servItemQuery);
		ServItem servItem1 = new ServItem();
		servItem1=servItems.get(0);
		setRequestAttribute("servItem1", servItem1);
		return "servItem";
	}
	
	public String getType(){
		String degreeId = getRequestParameter("degreeId");
		if (degreeId!=null && StringUtils.isNumeric(degreeId)) {
			StringBuilder rs = new StringBuilder();
			rs.append("<option selected=\"selected\" value=\"\">全部</option>");
			for (ExamType type : ConstantsHelper.examTypeList) {
				if (type.getDegreeId()==Long.parseLong(degreeId)) {
					rs.append("<option value=\"").append(type.getId()).append("\">").append(type.getName()).append("</option>");
				}
			}
			setRequestAttribute("rs", rs.toString());
		}
		return "ajaxReturn";
	}
	
	/**
	 * 数据字典初始化(暂放于此)
	 * @return
	 */
	public String dataInit() {
		Map<DataConfigType, List> map = tutorService.dataInit();
		ConstantsHelper.dataConfigMap = map;
		System.out.println("=====getLiveareaList======" + ConstantsHelper.getLiveareaList());
		
		ConstantsHelper.liveareaList = map.get(DataConfigType.LiveArea);
		ConstantsHelper.degreeList = map.get(DataConfigType.Degree);
		ConstantsHelper.gradeList = map.get(DataConfigType.Grade);
		ConstantsHelper.subjectList = map.get(DataConfigType.Subject);
		ConstantsHelper.studyAreaList = map.get(DataConfigType.StudyArea);
		ConstantsHelper.bookVersionList = map.get(DataConfigType.BookVersion);
		ConstantsHelper.examTypeList = map.get(DataConfigType.ExamType);
		ConstantsHelper.tchLevelList = map.get(DataConfigType.TeacherLevel);
		
		
		for (LiveArea area : (List<LiveArea>)ConstantsHelper.dataConfigMap.get(DataConfigType.LiveArea)) {
			ConstantsHelper.liveareaMap.put(area.getId(), area.getName());
		}
		for (Degree degree : (List<Degree>)ConstantsHelper.dataConfigMap.get(DataConfigType.Degree)) {
			ConstantsHelper.degreeMap.put(degree.getId(), degree.getName());
		}
		for (Grade grade : (List<Grade>)ConstantsHelper.dataConfigMap.get(DataConfigType.Grade)) {
			ConstantsHelper.gradeMap.put(grade.getId(), grade.getName());
		}
		for (Subject subject : (List<Subject>)ConstantsHelper.dataConfigMap.get(DataConfigType.Subject)) {
			ConstantsHelper.subjectMap.put(subject.getId(),	subject.getName());
		}
		
		for (StudyArea area : (List<StudyArea>)ConstantsHelper.dataConfigMap.get(DataConfigType.StudyArea)) {
			ConstantsHelper.studyAreaMap.put(area.getId(), area.getName());
		}
		for (BookVersion version : (List<BookVersion>)ConstantsHelper.dataConfigMap.get(DataConfigType.BookVersion)) {
			ConstantsHelper.bookVersionMap.put(version.getId(), version.getName());
		}
		for (ExamType type : (List<ExamType>)ConstantsHelper.dataConfigMap.get(DataConfigType.ExamType)) {
			ConstantsHelper.examTypeMap.put(type.getId(), type.getName());
		}
		for (Teacherlevel tchLevel : (List<Teacherlevel>)ConstantsHelper.dataConfigMap.get(DataConfigType.TeacherLevel)) {
			ConstantsHelper.tchLevelMap.put(tchLevel.getId(), tchLevel.getName());
		}
		return null;
	}

}
