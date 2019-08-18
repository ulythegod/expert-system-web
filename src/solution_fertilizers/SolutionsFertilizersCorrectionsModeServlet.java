package solution_fertilizers;

import core.data.Agricultures;
import core.logic.DataManagerMode1;
import core.logic.DataManagerModeFertilizers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SolutionsFertilizersCorrectionsModeServlet extends HttpServlet {
    private DataManagerModeFertilizers dm;
    private DataManagerMode1 dm1;
    Agricultures agriculture1;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/solution_fertilizers/solution_frtilizers_corrections_mode.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
