package pl.orange.coherence;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Servlet implementation class Test
 */
@WebServlet("/test")
public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@PersistenceUnit(unitName = "coherence")
	private EntityManagerFactory entityManagerFactory;

	@Resource
	private UserTransaction utx;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Test() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		 EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("coherence");
		//     EntityManager entitymanager = emfactory.createEntityManager( );
		try {
			utx.begin();
		} catch (NotSupportedException | SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Table1 employee;
		employee = entityManager.find(Table1.class, 1);

		if (employee == null) {
			employee = new Table1();
			employee.setColumn1(1);
			employee.setColumn2("2");
			entityManager.persist(employee);
		}else{
			System.out.println(employee);
		}

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Sample Application Servlet Page</title>");
		writer.println("</head>");
		writer.println("<body bgcolor=white>");
		writer.println("<table border=\"0\">");
		writer.println("<tr>");
		writer.println("<td>");
		writer.println("<img src=\"images/tomcat.gif\">");
		writer.println("</td>");
		writer.println("<td>");
		writer.println("<h1>Sample Application Servlet</h1>");
		writer.println("This is the output of a servlet that is part of");
		writer.println(employee);
		writer.println("</td>");
		writer.println("</tr>");
		writer.println("</table>");
		writer.println("</body>");
		writer.println("</html>");


		try {
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//     entityManager.close( );
		//     emfactory.close( );

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
