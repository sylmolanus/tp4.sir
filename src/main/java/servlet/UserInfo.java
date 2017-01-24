package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;

@SuppressWarnings("serial")
@WebServlet(name = "userinfo", urlPatterns = { "/UserInfo" })
public class UserInfo extends HttpServlet {

	private EntityManager manager;

	public UserInfo() {
		super();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
		this.manager = factory.createEntityManager();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		List<Person> resultList = manager.createQuery("Select a From Person a", Person.class).getResultList();
		
		out.println("<H1>Recapitulatif des informations</H1>\n");
		for (Person p : resultList) {
			out.println("<UL>\n");
			out.println("<LI>Nom: " + p.getName() + "\n");
			out.println("<LI>Prenom: " + p.getFirstName() + "\n");
			out.println("<LI>Email: " + p.getMail() + "\n");
			out.println("</UL>\n");
		}
		out.println("</BODY></HTML>");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String name = req.getParameter("name");
		String fname = req.getParameter("firstname");
		String mail = req.getParameter("mail");
		manager.persist(new Person(name, fname, mail));
	}
}