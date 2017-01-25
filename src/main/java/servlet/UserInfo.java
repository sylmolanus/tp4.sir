package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
	private EntityManagerFactory factory;

	public void init() {
		this.factory = Persistence.createEntityManagerFactory("example");
		this.manager = factory.createEntityManager();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		List<Person> resultList = manager.createQuery("Select a From Person a", Person.class).getResultList();
		out.println("<HTML>\n<BODY>\n");
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
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		manager.persist(new Person(req.getParameter("name"), req.getParameter("firstname"), req.getParameter("mail")));
		tx.commit();
		manager.close();
		factory.close();
		System.out.println(".. Personne ajoutée dans bdd");
	}
}