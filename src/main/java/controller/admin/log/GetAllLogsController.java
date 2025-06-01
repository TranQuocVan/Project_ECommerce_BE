package controller.admin.log;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import service.log.LogService;
import model.request.LogModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetAllLogsController", value = "/GetAllLogsController")
public class GetAllLogsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        List<LogModel> logs = LogService.getAllLogs();

        String json = gson.toJson(logs);

        PrintWriter out = response.getWriter();
        out.write(json);
        out.flush();
    }
}
