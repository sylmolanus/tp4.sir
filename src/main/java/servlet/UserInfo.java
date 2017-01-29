package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;

@SuppressWarnings("serial")
@WebServlet(name = "userinfo", urlPatterns = { "/UserInfo" })
public class UserInfo extends HttpServlet {
	
	private ManagerSingleton manager = ManagerSingleton.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		List<Person> resultList = this.manager.getManager().createQuery("Select a From Person a", Person.class).getResultList();
		out.println("<HTML>\n<BODY>\n");
		out.println("<H1>Recapitulatif des informations</H1>\n");
		out.println(resultList.size());
		for (Person p : resultList) {
			out.println("<UL>\n");
			out.println("<LI>Nom: " + p.getName() + "\n");
			out.println("<LI>Prenom: " + p.getFirstName() + "\n");
			out.println("<LI>Email: " + p.getMail() + "\n");
			out.println("</UL>\n");
		}
		out.println("</BODY></HTML>");
		System.out.println(".. Personnes affichées");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		EntityTransaction tx = this.manager.getManager().getTransaction();
		tx.begin();
		this.manager.getManager().persist(new Person(req.getParameter("name"), req.getParameter("firstname"), req.getParameter("mail")));
		tx.commit();
		PrintWriter out = resp.getWriter();
		out.println("<p>Une personne a été ajoutée</p>\n");
		out.println("<UL>\n");
		out.println("<LI>Nom: " + req.getParameter("name") + "\n");
		out.println("<LI>Prenom: " + req.getParameter("firstname") + "\n");
		out.println("<LI>Email: " + req.getParameter("mail") + "\n");
		out.println("</UL>\n");
		//manager.close();
		//factory.close();
		System.out.println(".. Personne ajoutée dans bdd");
	}
}