
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Database.DBConnection;
import com.pss.exercicioavaliativopss.model.Admin;
import java.time.LocalDate;

public class Principal {

    public static void main(String[] args) {

        //Inicializa DB
        DBConnection.criaDiretorio();
        UsuarioDAO.criarTabelaUsuario();

        UsuarioDAO dao = new UsuarioDAO();

        dao.inserir(new Admin("Daniel Hand Santiago", "handaniel", "Senha*1415", LocalDate.now()));
        int u = UsuarioDAO.contaUsuarios();

        System.out.println(u + " usuários");

    }
}
