package com.lara.c2c.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.sql2o.Sql2o;

import com.lara.c2c.dto.OtpData;
import com.lara.c2c.dto.learner.LearnerAverageMarks;
import com.lara.c2c.dto.learner.LearnerProfileReqResp;
import com.lara.c2c.dto.user.UserDetailDto;
import com.lara.c2c.model.Learner;

public interface LearnerRepository extends JpaRepository<Learner, String>{
	
	@Query("SELECT u.email FROM Learner u WHERE u.id = (:id)")
    public Learner findLearnerRecordById(String id);
	
	@Modifying
	@Query("update Learner u set u.status = '2' where u.userId=(:id) and u.activationCode=(:activationCode)")
	@Transactional
	public int activateLearnerStatus(String id, String activationCode);
	
	/*@Query("select u.id from Learner u where u.email = (:email) and u.password = (:password)")
	public String getUserDetailsForAuth(@Param("email") String email, @Param("password") String password);*/
	
	@Query("select u.userId, u.password, u.otp, u.mobileNo, u.firstName from Learner u where u.email = (:email)")
	public String [] getUserDetailsForAuth(@Param("email") String email);

	public Optional<Learner> findByUserId(String id);
	
	@Modifying
	@Query("update Learner l set l.otp = (:otpValue) where l.userId = (:userId)")
	@Transactional
	public void updateOtp(String userId, String otpValue);	
	
	@Query("select new com.lara.c2c.dto.learner.LearnerProfileReqResp(l.userId, l.firstName, l.email, l.mobileNo, ld.lastName, ld.gender, ld.dateOfBirth) from Learner l" + 
			" inner join LearnerDetail ld on ld.userId = l.userId and l.userId = (:id)")
	public List<LearnerProfileReqResp> findLearnerByUserId(String id);
	
	@Modifying
	@Query("update Learner l set l.firstName = (:firstName), l.mobileNo = (:mobileNo) where l.userId=(:userId)")
	@Transactional
	public void updateLearnerDetail(String userId, String firstName, String mobileNo);

	Optional<Learner> findByEmail(String email);    
    Boolean existsByEmail(String email);
    
    @Query("select new com.lara.c2c.dto.OtpData(l.email, l.otp) from Learner l where l.email = :email")
	public OtpData findByEmailForOtp(String email);
    
    @Modifying
    @Query("update Learner l set l.status = '1' where l.email = :email")
    @Transactional
	public void updateUserStatus(String email);
    
    @Query("select new com.lara.c2c.dto.user.UserDetailDto(u.userId, u.firstName, u.mobileNo, u.email) from Learner u where u.userId = :userId")
	public UserDetailDto findUserDetails(String userId);
    
    @Query("select new com.lara.c2c.dto.user.UserDetailDto(u.userId, u.firstName, u.mobileNo, u.email) from Learner u where u.email = :email")
	public UserDetailDto findUserDetailsByEmail(String email);
    
    @Modifying
    @Query("update Learner l set l.password = :encodedPassword where l.userId = :userId")
    @Transactional
	public int updatePassword(String userId, String encodedPassword);    
    
    @Query("select new com.lara.c2c.dto.user.UserDetailDto(u.userId, u.firstName, u.mobileNo, u.email) from Learner u where u.collegeId = :collegeId")
	public List<UserDetailDto> findLearnersByCollegeId(int collegeId);
    
    @Query("select collegeId from Learner l where l.userId = :userId")
	public int findCollegeIdByUserId(String userId);

    @Query("select new com.lara.c2c.dto.user.UserDetailDto("
    		+ "lc.userId, "
    		+ "u.firstName, "
    		+ "u.mobileNo, "
    		+ "u.email,"
    		+ "lc.status,"
    		+ "u.mailSubscription,"
    		+ "u.activationCode) "
    		+ "from LearnerCourse lc "
    		+ " right join Learner u on u.userId = lc.userId "
    		+ "where u.userType = :userType")
	public List<UserDetailDto> findAllLearners(String userType);
    
    @Query("select new com.lara.c2c.dto.user.UserDetailDto("
    		+ "lc.coursePackageId, "
    		+ "cpg.coursePackageName, "
    		+ "lc.userId, "
    		+ "u.firstName, "
    		+ "u.mobileNo, "
    		+ "u.email,"
    		+ "lc.status) "
    		+ "from Learner u "
    		+ " left join LearnerCourse lc on lc.userId = u.userId "
    		+ " left join CoursePackage cpg on cpg.coursePackageId = lc.coursePackageId"
    		+ " where u.userId = :userId")
	public List<UserDetailDto> findUserSubscriptionDetail(String userId);
    
    @Modifying
	@Query("update Learner u set u.mailSubscription = '0' where u.email=(:email)  and u.activationCode=(:activationCode)")
	@Transactional
	public int unsubscribeUserSunbscription(String email, String activationCode);

    @Modifying
	@Query("update Learner u set u.mailSubscription = '1' where u.email=(:email)  and u.activationCode=(:activationCode)")
	@Transactional
	public int subscribeUserSunbscription(String email, String activationCode);
    
    /*@Query("select new com.lara.c2c.dto.Learner.LearnerAverageMarks("
    		+ "SUM(average_marks) AS totalAverageMarks, sum(total_marks) As totalMarks, courseId, courseName, coursePackageId, coursePackageName)\n " + 
    		" FROM \n " + 
    		"\n" + 
    		"   SELECT  new Object(AVG(cecr.marks_obtained) AS totalAverageMarks,\n" + 
    		"         cecr.total_marks as totalMarks,\n" + 
    		"         cc.course_id as courseId,\n" + 
    		"         cc.course_name as courseName,\n" + 
    		"         cc.course_package_id as coursePackageId,\n" + 
    		"         cpg.course_package_name as coursePackageName)\n" + 
    		"   FROM ExamCompletedRecord cecr\n" + 
    		" inner join MicroTopic lmt on (lmt.microTopicId = cecr.microTopicId)\n" + 
    		" inner join SubTopic lst on (lst.subTopicId = lmt.subTopicId)\n" + 
    		" inner join Topic lt on (lt.topicId = lst.topicId)\n" + 
    		" inner join Course cc on (cc.courseId = lt.courseId)\n" + 
    		" inner join MapCourseUnderPackage mcup on (mcup.courseId = cc.courseId)\n" + 
    		" inner join CoursePackage cpg on (cpg.coursePackageId = mcup.coursePackageId)\n" + 
    		"   where cecr.userId = 'LR1910140310131013'\n" + 
    		"   GROUP BY cecr.microTopicId\n" + 
    		"AS T group by courseId")
	public List<LearnerAverageMarks> findLearnerAvgMarks(String learnerId);*/
   		
    @Query("select u.userId from Learner u where u.email = (:email)")
    public String findUserId(String email);
}





