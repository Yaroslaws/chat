package code.chat.service;

import code.chat.Repo.MessageRepo;
import code.chat.Repo.join.EventInfoSpec;
import code.chat.domain.Message;
import code.chat.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class MesService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageRepo messageRepo;

    public void nameByCourse() {
        Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
        crit.add(Restrictions.eq("name", "yar"));
        List<User> students = crit.list();
    }

    public void getAllText() {
        List<Message> messages = messageRepo.findAll(EventInfoSpec.hasText("привет"));
    }

}
