
import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Database.DBConnection;
import com.pss.exercicioavaliativopss.presenter.LoginPresenter;

public class Principal {

    public static void main(String[] args) {

        // Inicializa DB
        DBConnection.criaDiretorio();
        UsuarioDAO.criarTabelaUsuario();

        NotificacaoDAO.criarTabelaNotificacao();

        new LoginPresenter();

    }
}
