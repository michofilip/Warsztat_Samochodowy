package pl.coderslab.servlets.customercontroll;

import pl.coderslab.dao.CustomerDao;
import pl.coderslab.models.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "CustomerAdd", urlPatterns = "/customer-add")
public class CustomerAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthdayStr = request.getParameter("birthday");

        LocalDate birthday = null;
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            try {
                birthday = LocalDate.parse(birthdayStr);
            } catch (DateTimeParseException ignored) {
            }
        }

        Customer customer = new Customer(name, surname, birthday);
        CustomerDao.save(customer);

        response.sendRedirect("/customer-show");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mode", "add");
        getServletContext().getRequestDispatcher("/customerform.jsp").forward(request, response);
    }
}
