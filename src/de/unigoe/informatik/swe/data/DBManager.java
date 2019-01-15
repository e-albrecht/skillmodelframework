package de.unigoe.informatik.swe.data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.cfg.Configuration;

import de.unigoe.informatik.swe.eval.SimilarityResult;
import de.unigoe.informatik.swe.eval.SimilarityResult.SimilarityType;

public class DBManager {
	
	private static DBManager instance;
	private static SessionFactory factory;
	private Logger log;
	
	private DBManager(){
		factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		log = Logger.getLogger("DBManager");
	}
	
	public static DBManager instance(){
		if (instance == null) instance = new DBManager();
		return instance;
	}
	
/*	public Student getStudentFromDB(int studentId){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        Student student = session.get(Student.class, studentId);
	        tx.commit();
	        return student;
	      
		}catch (TransactionException e){
				return getStudentFromDB(studentId);
		 
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        log.error("student id=" + studentId,e);
	    }
		catch (Throwable ex) { 
          log.error("Failed to create sessionFactory object.", ex);
       }finally {
    	   if (session != null) session.close(); 
	   }
		log.error("Student with id=" + studentId + " not found");
		return null;		
	}*/
	
	
	/**
	 * Returns all exercises of a course
	 * @param courseId Course id of the course
	 * @return List of exercises of the given course
	 */
	public List<Exercise> getExercisesToCourse(int courseId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Exercise> exercises = session.createQuery("FROM Exercise WHERE course_id='" + courseId +"'").list();
	        tx.commit();
	        return exercises;
		}catch (TransactionException e){
			return getExercisesToCourse(courseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }
		catch (Throwable ex) { 
          System.err.println("Failed to create sessionFactory object." + ex);
          throw new ExceptionInInitializerError(ex); 
       }finally {
    	   if (session != null) session.close(); 
	   }
		return null;				
	}
	
	public Solution getSolutionFromDB(int solutionId){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        Solution solution = session.get(Solution.class, solutionId);
	        tx.commit();
	        return solution;
		} catch (TransactionException e){
			return getSolutionFromDB(solutionId);
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        log.error("solution id=" + solutionId,e);
	    }
		catch (Throwable ex) { 
          log.error("Failed to create sessionFactory object.", ex);
       }finally {
    	   if (session != null) session.close(); 
	   }
		log.error("Solution with id=" + solutionId + " not found");
		return null;		
	}
	
	
	public Exercise getExerciseFromDB(int exerciseId){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        Exercise exercise = session.get(Exercise.class, exerciseId);
	        tx.commit();
	        return exercise;
		}catch (TransactionException e){
			return getExerciseFromDB(exerciseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        log.error("exercise id=" + exerciseId);
	    }
		catch (Throwable ex) { 
          log.error("Failed to create sessionFactory object.",ex);
          throw new ExceptionInInitializerError(ex); 
       }finally {
    	   if (session != null) session.close(); 
	   }
        log.error("Exercise with id=" + exerciseId + " not found");
		return null;		
	}
	
	public int getSubmissionNumber(int studentId, Exercise exercise){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<Solution> solutions = session.createQuery("FROM Solution WHERE exercise_id='" + exercise.getId() +"' AND student_id='" + studentId + "'").list();
	        tx.commit();
	        return solutions.size()+1;
		}catch (TransactionException e){
			return getSubmissionNumber(studentId,exercise);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return 0;				
	}
	
	public int getNumberOfSubmissions(int studentId, int exerciseId){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<Solution> solutions = session.createQuery("FROM Solution WHERE exercise_id='" + exerciseId +"' AND student_id='" + studentId + "'").list();
	        tx.commit();
	        return solutions.size();
		}catch (TransactionException e){
			return getNumberOfSubmissions(studentId,exerciseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return 0;				
	}
	
	public List<Exercise> getExercisesFromDB() {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<Exercise> exercises = session.createQuery("FROM Exercise").list();
	        tx.commit();
	        return exercises;
		}catch (TransactionException e){
			return getExercisesFromDB();

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;			
	}
	
	public List<Solution> getSolutionsToExercise(int exerciseId){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<Solution> solutions = session.createQuery("FROM Solution WHERE exercise_id='" + exerciseId + "'").list();
	        tx.commit();
	        return solutions;
		}catch (TransactionException e){
			return getSolutionsToExercise(exerciseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;			
	}
	
	public List<Solution> getSolutionsToCourse(int courseId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<Solution> solutions = session.createQuery("SELECT s FROM Solution s JOIN s.exercise e WHERE e.course='" + courseId + "'").list();
	        tx.commit();
	        return solutions;
		}catch (TransactionException e){
			return getSolutionsToExercise(courseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;					
	}
	
	public List<Course> getCoursesFromDB(){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<Course> courses = session.createQuery("FROM Course").list(); 
	        tx.commit();
	        return courses;
		}catch (TransactionException e){
			return getCoursesFromDB();

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }
		catch (Throwable ex) { 
          System.err.println("Failed to create sessionFactory object." + ex);
          throw new ExceptionInInitializerError(ex); 
       }finally {
    	   if (session != null) session.close(); 
	   }
		return null;
	}
	
	public void saveObjectToDB(Object o){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        session.saveOrUpdate(o);
	        tx.commit();
		}catch (TransactionException e){
			saveObjectToDB(o);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }
		catch (Throwable ex) { 
          System.err.println("Failed to create sessionFactory object." + ex);
          throw new ExceptionInInitializerError(ex); 
       }finally {
    	   if (session != null) session.close(); 
	   }		
	}

	public List<Solution> getCorrectSolutionsToExercise(int exerciseId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Solution> solutions = session.createQuery("FROM Solution WHERE exercise_id='" + exerciseId + "' AND result_percentage=100").list();
	        tx.commit();
	        return solutions;
		}catch (TransactionException e){
			return getSolutionsToExercise(exerciseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;	
	}

	public Set<Solution> getCompilableSolutionsToExercise(int exerciseId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<CompilationResult> results = session.createQuery("SELECT c FROM CompilationResult c JOIN c.solution s WHERE exercise_id='" + exerciseId + "'").list();
	        List<CompilationResult> compilable = new LinkedList<CompilationResult>();
	        for (CompilationResult result : results ) {
	        	if (result.hasPassed()) compilable.add(result);
	        }
	        Set<Solution> compilableSolutions = new HashSet<Solution>();
	        for (CompilationResult result : compilable) {
	        	compilableSolutions.add(result.getSolution());
	        }
	        tx.commit();
	        return compilableSolutions;
		}catch (TransactionException e){
			return getCompilableSolutionsToExercise(exerciseId);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;
	}


	public List<Solution> getMostSimilarSolutions(Solution solution, int level, SimilarityType type) {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<SimilarityResult> results = session.createQuery("FROM SimilarityResult WHERE solution1='" + solution + "' AND level='" + level + "' AND type='" + type +"') ").list();
	        double max = -1;
	        if (type.equals(SimilarityType.PATH)) max = Double.MAX_VALUE;
	        List<Solution> maxSol = new LinkedList<Solution>();
	        for(SimilarityResult result : results) {
	        	double sim = result.getSimilarity();
	        	if ((type.equals(SimilarityType.SET) && sim > max) || (type.equals(SimilarityType.PATH) && sim < max)) {
	        		max = sim;
	        		maxSol = new LinkedList<Solution>();
	        		maxSol.add(result.getSolution2());
	        	}
	        	else if (sim == max) {
	        		maxSol.add(result.getSolution2());
	        	}
	        }
	        
	        tx.commit();
	        return maxSol;
		}catch (TransactionException e){
			return getMostSimilarSolutions(solution, level, type);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;				
	}

	public SimilarityResult getSimilarityFromDB(Solution solution1,
			Solution solution2, int level, SimilarityType type) {
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
	        tx = session.beginTransaction();
	        List<SimilarityResult> results = session.createQuery("FROM SimilarityResult WHERE solution1='" + solution1 + "' AND solution2='" + solution2 + "' AND level='" + level + "' AND type='" + type +"') ").list();
	        tx.commit();
	        if (results.isEmpty()) return null;
	        return results.get(0);
		}catch (TransactionException e){
			return getSimilarityFromDB(solution1, solution2, level, type);

		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
    	   if (session != null) session.close(); 
	   }
		return null;				
	}
}
