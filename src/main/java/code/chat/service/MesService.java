package code.chat.service;

import code.chat.Repo.MessageRepo;
import code.chat.Repo.join.EventInfoSpec;
import code.chat.domain.Message;
import code.chat.domain.Message_;
import code.chat.domain.User;
import code.chat.utils.spec.SpecUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        System.out.println(students);
    }

    public String getMessage(String title) {
        Specification<Message> specs = Specification.where(SpecUtils.equals(Message_.title, title));
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.multiselect(root.get(Message_.id),
                          root.get(Message_.title),
                          root.get(Message_.creationDate))
                .where(specs.toPredicate(root, query, builder))
                .orderBy(builder.desc(root.get(Message_.creationDate)))
                .distinct(true);
        TypedQuery<Message> messageTypedQuery = em.createQuery(query);

        System.out.println(messageTypedQuery.getResultList());
        return "ghbdtn";
    }

    public void getAllText() {
        List<Message> messages = messageRepo.findAll(EventInfoSpec.hasText("привет"));
    }

}
