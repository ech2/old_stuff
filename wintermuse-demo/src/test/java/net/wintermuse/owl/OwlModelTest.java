package net.wintermuse.owl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class OwlModelTest  {
    Repository repo;
    OwlModel model;

    @Before
    public void setUp() throws Exception {
        repo = new SailRepository(new MemoryStore());
        repo.initialize();
        model = new OwlModel(repo);
    }
    @Test
    public void testQueryTuple() throws Exception {

    }
    @Test
    public void testQueryGraph() throws Exception {
    }
    @Test
    public void testQueryUpdate() throws Exception {

    }

    @Test
    public void testAddStatement() throws Exception {
        URI s = uri("testAddStatement_subj");
        URI p = uri("testAddStatement_pred");
        Value o = literal(1, XMLSchema.INT);
        Statement statement = new StatementImpl(s, p, o);
        model.addStatement(statement);

        RepositoryConnection con = repo.getConnection();
        RepositoryResult<Statement> r = con.getStatements(s, p, o, false);

        List<Statement> statements = new ArrayList<>();
        while (r.hasNext()) {
            statements.add(r.next());
        }

        con.close();

        assertEquals(1, statements.size());
        assertEquals(statement, statements.get(0));
    }

    @Test
    public void testAddStatements() throws Exception {
        URI s = uri("testAddStatements_subj");
        URI p1 = uri("testAddStatements_pred_1");
        URI p2 = uri("testAddStatements_pred_2");
        Value o1 = literal(1, XMLSchema.INT);
        Value o2 = literal(2, XMLSchema.INT);
        Statement statement1 = new StatementImpl(s, p1, o1);
        Statement statement2 = new StatementImpl(s, p2, o2);

        model.addStatements(Arrays.asList(statement1, statement2));

        RepositoryConnection con = repo.getConnection();
        RepositoryResult<Statement> r = con.getStatements(s, null, null, false);

        List<Statement> statements = new ArrayList<>();
        while (r.hasNext()) {
            statements.add(r.next());
        }

        con.close();

        assertEquals(2, statements.size());
        assertEquals(statement1, statements.get(0));
        assertEquals(statement2, statements.get(1));

    }

    @Test
    public void testGetStatements() throws Exception {
        URI s = uri("testGetStatements_subj");
        URI p1 = uri("testGetStatements_pred_1");
        URI p2 = uri("testGetStatements_pred_2");
        Value o1 = literal(1, XMLSchema.INT);
        Value o2 = literal(2, XMLSchema.INT);
        Statement statement1 = new StatementImpl(s, p1, o1);
        Statement statement2 = new StatementImpl(s, p2, o2);

        model.addStatements(Arrays.asList(statement1, statement2));

        List<Statement> statements = model.getStatements(s, null, null);

        assertEquals(2, statements.size());
        assertEquals(statement1, statements.get(0));
        assertEquals(statement2, statements.get(1));
    }

    private URI uri(String append) {
        return ValueFactoryImpl.getInstance().createURI(
                "http://wintermuse.net/owl/test#" + append);
    }
    private Literal literal(Object value, URI dataType) {
        return ValueFactoryImpl.getInstance().createLiteral(value.toString(), dataType);
    }
}