
import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Database.DBConnection;
import com.pss.exercicioavaliativopss.model.Notificacao;
import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;
import java.time.LocalDate;

public class Principal {

    public static void main(String[] args) {

        // Inicializa DB
        DBConnection.criaDiretorio();
        UsuarioDAO.criarTabelaUsuario();
        NotificacaoDAO.criarTabelaNotificacao();

        NotificacaoDAO dao = new NotificacaoDAO();

        //dao.inserirNotificacao(new Notificacao(2, 1, "mensagem de teste", LocalDate.now()));
        System.out.println(dao.contaNotificacaoNaoLida(3));

        new PrincipalPresenter();

    }
}
