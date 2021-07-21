package code.chat.service;

import code.chat.Repo.MessageRepo;
import code.chat.Repo.UserDetailsRepo;
import code.chat.Repo.join.EventInfoSpec;
import code.chat.domain.Message;
import code.chat.domain.Message_;
import code.chat.domain.User;
import code.chat.domain.User_;
import code.chat.utils.spec.SpecUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MesService {

    @PersistenceContext
    private EntityManager em;


    private final MessageRepo messageRepo;
    private final UserDetailsRepo userDetailsRepo;


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

    @Transactional
    public ResponseEntity<Message> saveMessage(String text) throws RuntimeException, InterruptedException {
        Message message = new Message();
        message.setTitle(text);
        message.setCreationDate(LocalDate.now());
        message.setUser(userDetailsRepo.findAll().get(0));
        Message save = messageRepo.save(message);
        doError();
        return new ResponseEntity<>( save, HttpStatus.OK);
    }

    private void doError() throws InterruptedException {
        Thread.sleep(2000);
        throw new RuntimeException();
    }

    public String getJoin(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        CriteriaQuery<String> query2 = cb.createQuery(String.class);

        Root<User> user = query.from(User.class);
        Root<Message> message = query.from(Message.class);
        Subquery<Long> ids = query.subquery(Long.class);

        ids.select(user.get(User_.id)).where(cb.equal(user.get(User_.id), id));
        query2.select(message.get(Message_.title)).where(message.get(Message_.id).in(ids));
        TypedQuery<String> typedQuery = em.createQuery(query2);
        typedQuery.getResultList().forEach(System.out::println);
        return typedQuery.toString();
    }

    public void getAllText() {
        List<Message> messages = messageRepo.findAll(EventInfoSpec.hasText("привет"));
    }

    public List<Message> test() {
       return messageRepo.findAll();
    }
}
